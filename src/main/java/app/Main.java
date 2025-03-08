package app;

import jakarta.persistence.EntityManagerFactory;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.Routes;
import app.daos.HotelDao;
import app.daos.RoomDao;
import app.entities.Hotel;
import app.entities.Room;

public class Main
{

    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        HotelDao hotelDao = HotelDao.getInstance(emf);

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
                .startServer(7070);


        // Close EntityManagerFactory when program shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (emf != null && emf.isOpen())
            {
                emf.close();
                System.out.println("EntityManagerFactory closed.");
            }
        }));

    }

}