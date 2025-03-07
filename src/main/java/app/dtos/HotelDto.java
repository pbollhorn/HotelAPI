package app.dtos;

import app.entities.Hotel;
import app.entities.Room;

public record HotelDto(Integer id, String name, String address, RoomDto[] rooms) {

    public HotelDto(Hotel hotel) {
        this(hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getRooms().stream()           // Convert the collection to a stream
                        .map(RoomDto::new)          // Map each Room to a RoomDto
                        .toArray(RoomDto[]::new));  // Collect as an array of RoomDto

    }

}