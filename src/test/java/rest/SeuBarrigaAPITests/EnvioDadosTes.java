package rest.SeuBarrigaAPITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class EnvioDadosTes {
    String BASE_URL = "https://restapi.wcaquino.me";
    @Test
    public void deveEnviarViaQuery(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(BASE_URL+"/v2/users?format=xml")
        .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .log().all()
        ;
    }
    @Test
    public void deveEnviarViaQueryViaParam(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("format","xml")
        .when()
                .get(BASE_URL+"/v2/users")
        .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .log().all()
        ;
    }


}
