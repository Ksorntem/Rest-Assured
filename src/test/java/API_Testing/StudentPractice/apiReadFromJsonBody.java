package API_Testing.StudentPractice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

public class apiReadFromJsonBody {

    @Test
    public void readFromJson() {
        // File is reading from the json body
        File file = new File("src/resources/requestBody.json");

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(file)
                .when()
                .post("https://fakerestapi.azurewebsites.net//api/v1/Activities")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

    }
}
