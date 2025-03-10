package app.populators;

import app.daos.HotelDao;
import app.entities.Hotel;
import app.entities.Room;

public class HotelPopulator
{
    public static Hotel[] populate(HotelDao hotelDao){

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




    }



}
