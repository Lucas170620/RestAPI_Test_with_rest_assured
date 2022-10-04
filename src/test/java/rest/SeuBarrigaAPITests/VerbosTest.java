package rest.SeuBarrigaAPITests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class VerbosTest{
    String BASE_URL = "https://restapi.wcaquino.me";

    @Test
    public void deveSalvarUsuario(){
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Jose\",\"age\":50}")
        .when()
                .post(BASE_URL+"/users")
        .then()
                .statusCode(201)
                .body("id",is(notNullValue()))
                .body("name",is("Jose"))
                .body("age",is(50))
                .log().all()

        ;
    }
    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        given()
                .contentType(ContentType.JSON)
                .body("{\"age\":50}")
        .when()
                .post(BASE_URL+"/users")
        .then()
                .statusCode(400)
                .body("id",is(nullValue()))
                .body("error",is("Name é um atributo obrigatório"))
                .log().all()

        ;
    }
    @Test
    public void deveSalvarUsuarioViaXML(){
        given()
                .contentType(ContentType.XML)
                .body("<user><name>Jose</name><age>50</age></user>")
        .when()
                .post(BASE_URL+"/usersXML")
        .then()
                .statusCode(201)
                .body("user.@id",is(notNullValue()))
                .body("user.name",is("Jose"))
                .body("user.age",is("50"))
                .log().all()

        ;
    }
    @Test
    public void deveAlterarUsuario(){
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Usuario Alterado\",\"age\":80}")
        .when()
                .put(BASE_URL+"/users/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("id",is(1))
                .body("name",is("Usuario Alterado"))
                .body("age",is(80))
                .log().all()

        ;
    }
    @Test
    public void deveCostumizarURl(){
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Usuario Alterado\",\"age\":80}")
                .pathParams("entidade","users")
                .pathParams("userID",1)
        .when()
                .put(BASE_URL+"/{entidade}/{userID}")
        .then()
                .statusCode(200)
                .body("id",is(1))
                .body("name",is("Usuario Alterado"))
                .body("age",is(80))
                .log().all()

        ;
    }
    @Test
    public void deveRemoverUsuario(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(BASE_URL+"/users/1")
        .then()
                .log().all()
        ;
    }
    @Test
    public void naoDeveRemoverUsuario(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(BASE_URL+"/users/1000")
        .then()
                .log().all()
                .statusCode(400)
                .body("error",is("Registro inexistente"))
                .log().all()
        ;
    }
    @Test
    public void deveSalvarUsuarioUsandoMap(){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name","Usuario Via Map");
        params.put("age",25);
        given()
                .contentType(ContentType.JSON)
                .body(params)
        .when()
                .post(BASE_URL+"/users")
        .then()
                .statusCode(201)
                .body("id",is(notNullValue()))
                .body("name",is("Usuario Via Map"))
                .body("age",is(25))
                .log().all()

        ;
    }
     @Test
    public void deveSalvarUsuarioUsandoObject(){
        User user = new User("Usuario via Objeto",35);

        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post(BASE_URL+"/users")
        .then()
                .statusCode(201)
                .body("id",is(notNullValue()))
                .body("name",is("Usuario via Objeto"))
                .body("age",is(35))
                .log().all()

        ;
    }
    @Test
    public void deveDeserializarUsuarioUsandoObject(){
        User user = new User("Usuario deserializado",35);

        User usuario_inserio = given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post(BASE_URL+"/users")
        .then()
                .statusCode(201)
                .extract().body().as(User.class)
        ;
        System.out.println(usuario_inserio);
        assertEquals("Usuario deserializado",usuario_inserio.getName());
        assertEquals(35,usuario_inserio.getAge());
    }
}

