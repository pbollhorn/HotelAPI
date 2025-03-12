package app.controllers;

import app.dtos.HotelDto;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.daos.HotelDao;
import app.daos.RoomDao;
import app.entities.Hotel;
import app.entities.Room;

public class HotelControllerTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final HotelDao hotelDao = HotelDao.getInstance(emf);
    private static HotelDto h1;
    private static HotelDto h2;

    @BeforeAll
    static void setupAll()
    {
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(Routes.getRoutes())
                .handleException()
                .startServer(7777);

        RestAssured.baseURI = "http://localhost:7777/api";
    }

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {

            // Delete everything from tables and reset id's to start with 1
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM Room").executeUpdate();
            em.createNativeQuery("DELETE FROM Hotel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE room_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hotel_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();

            // Populate database with the two test hotels, including their rooms
            HotelDto[] hotelDtos = app.populators.HotelPopulator.populate(hotelDao);
            h1 = hotelDtos[0];
            h2 = hotelDtos[1];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @AfterAll
    static void tearDown()
    {
        if (emf != null && emf.isOpen())
        {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }


    @Test
    void getAll()
    {
        given()
                .when()
                .get("/hotel")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void get()
    {
        given()
                .when()
                .get("/hotel/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Hotel 1"))
                .body("address", equalTo("Address 1"));

        // Negative test: Test with id that does not exist
        given()
                .when()
                .get("/hotel/0")
                .then()
                .statusCode(404);

        // Negative test: Test with id that is not a number
        given()
                .when()
                .get("/hotel/notnumber")
                .then()
                .statusCode(400);
    }


    @Test
    void create()
    {

    }

    @Test
    void update()
    {
    }

    @Test
    void delete()
    {
        given()
                .when()
                .delete("/hotel/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Hotel 1"))
                .body("address", equalTo("Address 1"));

        // Negative test: Try to delete hotel 1 again
        given()
                .when()
                .delete("/hotel/1")
                .then()
                .statusCode(404)
                .body("code", equalTo(404));

        // Negative tests: Try to delete some hotels that have never existed
        given()
                .when()
                .delete("/hotel/0")
                .then()
                .statusCode(404)
                .body("code", equalTo(404));
        given()
                .when()
                .delete("/hotel/-1")
                .then()
                .statusCode(404)
                .body("code", equalTo(404));
        given()
                .when()
                .delete("/hotel/17")
                .then()
                .statusCode(404)
                .body("code", equalTo(404));

        // Negative test: Try to delete hotel with id that is not a number
        given()
                .when()
                .delete("/hotel/notnumber")
                .then()
                .statusCode(400)
                .body("code", equalTo(400));
    }
}
