package app.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import app.dtos.HotelDto;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Hotel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    Set<Room> rooms = new HashSet<>();

    public void addRoom(Room room)
    {
        if (room != null)
        {
            this.rooms.add(room);
            room.setHotel(this);
        }
    }

    public Hotel(String name, String address)
    {
        this.name = name;
        this.address = address;
    }

    // This constructor ignores id in hotelDto
    // and also ignores rooms in hotelDto
    public Hotel(HotelDto hotelDto)
    {
        this.name = hotelDto.name();
        this.address = hotelDto.address();
    }

    public Hotel(int id, HotelDto hotelDto)
    {
        this.id = id;
        this.name = hotelDto.name();
        this.address = hotelDto.address();
    }

}
