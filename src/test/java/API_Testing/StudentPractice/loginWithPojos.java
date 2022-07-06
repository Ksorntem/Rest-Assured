package API_Testing.StudentPractice;

import Pojos.loginPojos;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class loginWithPojos {

    @Test
    public void loginWithPojosData(){
        loginPojos data = new loginPojos();
        data.setPassWord("Myroom55");
        data.setUserName("Ksorntem@gmail.com");

        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .queryParam("username", data.getUserName())
                .queryParam("password", data.getPassWord())
                .when()
                .post(path)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .log().all();

    }
}
