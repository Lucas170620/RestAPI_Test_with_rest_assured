package rest.SeuBarrigaAPITests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Users {
    String BASE_URL = "https://restapi.wcaquino.me";

    @Test
    public void deveVerificarFirstLevel(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(BASE_URL+"/users/1")
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name",containsString("Silva"))
                .body("age",greaterThan(18))
                .log().all()
        ;

    }
}
