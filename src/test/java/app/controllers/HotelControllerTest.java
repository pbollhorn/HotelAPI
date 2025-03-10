package app.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import app.config.ApplicationConfig;

public class HotelControllerTest
{
    @BeforeAll
    static void setupAll()
    {
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(Routes.getRoutes())
                .startServer(7777);
        RestAssured.baseURI = "http://localhost:7777/api";
    }

    @BeforeEach
    void setUp()
    {

    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    @DisplayName("Test getting hotel by id")
    void hotelByIdTest()
    {
        given()
                .when()
                .get("/hotel/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

}
