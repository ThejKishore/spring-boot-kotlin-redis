package com.kish.cust.msec.resource;

import com.kish.cust.msec.model.Foo;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    MainController mainController;


    @BeforeEach
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(mainController);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8082;
    }

    @Test
    public void givenUserWithReadPrivilegeAndHasPermission_whenGetFooById_thenOK() {
        Response response = givenAuth("namhm", "namhm").get("/foos/1");
        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("id"));
    }

    @Test
    public void givenUserWithNoWritePrivilegeAndHasPermission_whenPostFoo_thenForbidden() {
        Response response = givenAuth("namhm", "namhm").contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new Foo("sample"))
                .post("/foos");
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void givenUserWithWritePrivilegeAndHasPermission_whenPostFoo_thenOk() {
        Response response = givenAuth("admin", "admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new Foo("sample"))
                .post("/foos");
        assertEquals(201, response.getStatusCode());
        assertTrue(response.asString().contains("id"));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.simple()
                .forClass(Foo.class)
                .verify();
    }

    //
    private RequestSpecification givenAuth(String username, String password) {
        return RestAssured.given().log().uri().auth().basic(username,password);
    }

}