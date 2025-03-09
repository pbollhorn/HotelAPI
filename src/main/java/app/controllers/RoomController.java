package app.controllers;

import java.util.List;

import io.javalin.http.Context;

import app.daos.RoomDao;
import app.dtos.RoomDto;
import app.exceptions.DaoException;

public class RoomController
{

    private static RoomDao roomDao = RoomDao.getInstance();

    public static void getAll(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try
        {
            List<RoomDto> roomDtos = roomDao.readAllRoomsForHotel(id);
            ctx.json(roomDtos);
        }
        catch (DaoException e)
        {
            ctx.status(500);
        }
    }

//    private static void getById(Context ctx) {
//
//    }
//
//    private static void create(Context ctx) {
//
//    }
//
//
//    private static void update(Context ctx) {
//
//    }
//
//    private static void delete(Context ctx) {
//
//    }


}
