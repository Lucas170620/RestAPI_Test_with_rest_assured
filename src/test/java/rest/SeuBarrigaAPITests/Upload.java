package rest.SeuBarrigaAPITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class Upload{
    String BASE_URL = "https://restapi.wcaquino.me";
    @Test
    public void deveObrigar(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .post(BASE_URL+"/upload")
        .then()
                .statusCode(404)
                .body("error",is("Arquivo não enviado"))
                .log().all()
        ;
    }
    @Test
    public void deveFazerUploadImagem(){
        given()
                .multiPart("arquivo",new File("src/test/resources/S5_Slides.pdf"))
        .when()
                .post(BASE_URL+"/upload")
        .then()
                .statusCode(200)
                .body("name",is("S5_Slides.pdf"))
                .log().all()

        ;
    }
    @Test
    public void deveFazerDownloadImagem() throws IOException {
        byte[] image =given()
        .when()
                .get(BASE_URL+"/download")
        .then()
                .statusCode(200)
                .extract().asByteArray()
        ;
        File imagem = new File("src/test/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();
    }
}
