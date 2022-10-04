package rest.SeuBarrigaAPITests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HTML {
    String BASE_URL = "https://restapi.wcaquino.me";
    @Test
    public void devemanipularHtml(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(BASE_URL+"/v2/users")
        .then()
                .statusCode(200)
                .body("html.body.div.table.tbody.tr.size()",is(3))
                .body("html.body.div.table.tbody.tr[1].td[2]",is("25"))
                .appendRootPath("html.body.div.table.tbody")
                .body("tr.find{it.toString().startsWith('2')}.td[1]",is("Maria Joaquina"))
                .log().all()
        ;
    }
}
