package API_Testing.StudentPractice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.*;

public class apiReadFromJsonBody {
    Response response;

    @Test
    public void readFromJson() {
        // File is reading from the json body
        File file = new File("src/resources/requestBody.json");

        response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(file)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void readAuthors() {
            File responseAuthors = new File("src/resources/requestAuthors.json");

            response = RestAssured.given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(responseAuthors)
                    .when()
                    .post("https://fakerestapi.azurewebsites.net/api/v1/Authors")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .response();

            assertThat(response.statusCode(), is(200));
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8; v=1.0");
            assertThat(response.jsonPath().getString("id"), is("12"));
            assertThat(response.jsonPath().getString("idBook"), is("599"));
            assertThat(response.jsonPath().getString("firstName"), is("John"));
            assertThat(response.jsonPath().getString("lastName"), is("Doe"));

    }

    @Test
    public void readBooks(){
        File responseBooks = new File("src/resources/requestBooks.json");
        response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(responseBooks)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Books")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.statusCode(), is(200));
        Assert.assertEquals(response.header("Content-Type"),"application/json; charset=utf-8; v=1.0");
        assertThat(response.jsonPath().getString("id"), is("11"));
        assertThat(response.jsonPath().getString("title"), is("JAVA"));
        assertThat(response.jsonPath().getString("pageCount"), is("500"));
        assertThat(response.jsonPath().getString("excerpt"), is("TLA"));
        assertThat(response.jsonPath().getString("publishDate"), is("2022-07-06T00:20:37.648Z"));
    }
}
