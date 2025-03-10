package app.controllers;

import io.restassured.RestAssured;
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
    @BeforeAll
    static void setupAll()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        HotelDao hotelDao = HotelDao.getInstance(emf);
        RoomDao roomDao = RoomDao.getInstance(emf);

        Hotel h1 = new Hotel("Mågevejens Hotel", "Mågevej 1, 2400 København NV");
        h1.addRoom(new Room("101", 1000.0));
        h1.addRoom(new Room("102", 1000.0));
        h1.addRoom(new Room("103", 1000.0));
        h1.addRoom(new Room("201", 1500.0));
        h1.addRoom(new Room("202", 1500.0));
        hotelDao.create(h1);

        Hotel h2 = new Hotel("Byens Hotel", "Bjergbygade 1, 4200 Slagelse");
        h2.addRoom(new Room("1A", 600.0));
        h2.addRoom(new Room("1B", 600.0));
        h2.addRoom(new Room("1C", 600.0));
        h2.addRoom(new Room("2A", 1800.0));
        hotelDao.create(h2);

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

    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void get()
    {
        given()
                .when()
                .get("/hotel/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void getAll()
    {
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
    }
}
