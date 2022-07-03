package workspaceRestAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.HashMap;
import java.util.Map;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class E2E_Project {

    public String path;
    public String memberOf = "/workspaces/member-of";
    Map<String, String> variables;
    String Id;
    String user_Id;
    String projectID;
    Response response;

    // What is a TestNG annotation that allows us to run a Test Before each Test
    @BeforeTest
    public String setupLogInAndToken(){

        RestAssured.baseURI = "https://api.octoperf.com/";
        path = "/public/users/login";

        Map<String, Object> map = new HashMap<String, Object>();
        //          key,     value
        map.put("password", "Myroom55");
        map.put("username", "Ksorntem@gmail.com");

        return RestAssured.given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(path)           // send request to end point
                .then()
                .statusCode(SC_OK)    // Verify status code = 200 or OK
                .extract()            // Method that extracts response JSON data
                .body()               // Body extracted as JSON data
                .jsonPath()           // Navigate using jsonPath
                .get("token");        // Get value for key token

    }

    // Write a test for API endpoint member-of
    @Test
    public void memberOf(){

        response = RestAssured.given()
                .header("Authorization", setupLogInAndToken())
                .when()
                .get(memberOf)
                .then()
                .log().all()
                .extract()
                .response();

        // Verify status code
        Assert.assertEquals(SC_OK, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        // TODO add tests for ID, userID, Description

        // Save the id, so it can be used in other requests
        Id = response.jsonPath().get("id[0]");

        // Save the userId, so it can be used in other requests
        user_Id = response.jsonPath().get("userId[0]");

        // what can we use to Store data as Key and Value
        variables = new HashMap<String, String>();
        variables.put("id", Id);
        variables.put("userId", user_Id);

        //System.out.println(Arrays.asList(variables));

    }

    @Test(dependsOnMethods = {"memberOf"})
    public void createProject(){
                             // String JSON
        String requestBody = "{\"id\":\"\",\"created\"" +
                ":\"2021-03-11T06:15:20.845Z\",\"lastModified\"" +
                ":\"2021-03-11T06:15:20.845Z\",\"userId\"" +
                ":\"" + variables.get("userId") + "\",\"workspaceId\":\"" + variables.get("id") + "\",\"name\"" +
                ":\"testing22\",\"description\"" +
                ":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());

        // TASK created TestNG Assertions name, id , userId, workspaceId
        Assert.assertEquals("testing22", response.jsonPath().getString("name"));
        // Using hamcrest Matching validation
        assertThat(response.jsonPath().getString("name"), is("testing22"));

        // Store projectID in a variable for future use
        projectID = response.jsonPath().get("id");
        //System.out.println("new id is" + projectID);

        Assert.assertEquals("DK-9wH0Bp7hMViDsIVRH", response.jsonPath().getString("userId"));
        Assert.assertEquals("66-9wH0Bp7hMViDs0V3m", response.jsonPath().getString("workspaceId"));
    }

    @Test(dependsOnMethods = {"memberOf", "createProject"})
    public void updateProject(){
        String requestBody1 = "{\"created\":1615443320845,\"description\"" +
                ":\"TLAUpate\",\"id\":\"" + projectID + "\",\"lastModified\"" +
                ":1629860121757,\"name\":\"tla\",\"tags\":[],\"type\"" +
                ":\"DESIGN\",\"userId\":\"" + variables.get("userID") + "\",\"workspaceId\":\"" + variables.get("id") + "\"}";

        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody1)
                .when()
                .put("/design/projects/"+ projectID)
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPeek());

        //TODO
    }

    @Test(dependsOnMethods = {"memberOf", "createProject", "updateProject"})
    public void deleteProject(){
        RestAssured.given()
                .header("Authorization", setupLogInAndToken())
                .when()
                .delete("design/projects/" + projectID)
                .then()
                .extract()
                .response();

        // TODO validate status code 204
    }
}
