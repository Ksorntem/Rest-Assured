package API_Testing.StudentPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class api_Activities {

    // Testing URI = https://fakerestapi.azurewebsites.net/api/v1/
    // for endpoint /Activities
    // Task 1: Using Rest Assured validate the status code for endpoint /Activities
    @Test
    public void verifyStatusCode(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .assertThat()
                .statusCode(200);

    }

    // Task 2: Using Rest Assured Validate Content-Type  is application/json; charset=utf-8; v=1.0
    @Test
    public void validateContentType(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .contentType(ContentType.JSON);
    }

    @Test
    public void validateContentTypeOption2(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .assertThat().header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }

    // for endpoint /Activities/12
    // Task 1: Using Rest Assured validate the status code for endpoint /Activities/12
    @Test
    public void verifyStatusCode2(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities/12")
                .then()
                .assertThat()
                .statusCode(200);

    }
    // Task 2: Using Rest Assured Validate Content-Type  is application/json; charset=utf-8; v=1.0
    @Test
    public void validateContentType2Option2(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities/12")
                .then()
                .assertThat().header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }
}
