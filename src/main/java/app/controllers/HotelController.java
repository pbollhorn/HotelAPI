package app.controllers;

import app.daos.HotelDao;
import app.daos.PoemDao;
import app.daos.RoomDao;
import app.dtos.HotelDto;
import app.dtos.PoemDto;
import app.dtos.RoomDto;
import app.exceptions.DaoException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class HotelController
{

    private static HotelDao hotelDao = HotelDao.getInstance();
    private static RoomDao roomDao = RoomDao.getInstance();

    public static void addRoutes(String resource, Javalin app)
    {
        app.get(resource + "/", ctx -> getAll(ctx));
        app.get(resource + "/{id}", ctx -> getById(ctx));
        app.post(resource + "/", ctx -> create(ctx));
        app.put(resource + "/{id}", ctx -> update(ctx));
        app.delete(resource + "/{id}", ctx -> delete(ctx));
        app.get(resource + "/{id}/room", ctx -> getAllRoomsForHotel(ctx));
    }

    public static void getAllRoomsForHotel(Context ctx)
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

    public static void getAll(Context ctx)
    {
        try
        {
            List<HotelDto> hotelDtos = hotelDao.readAll();
            ctx.json(hotelDtos);
        }
        catch (DaoException e)
        {
            ctx.status(500);
        }
    }

    // TODO: Also implement 404 not found status code
    public static void getById(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try
        {
            HotelDto hotelDto = hotelDao.readById(id);
            ctx.json(hotelDto);
        }
        catch (DaoException e)
        {
            ctx.status(500);
        }
    }

    public static void create(Context ctx)
    {
        HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);
        System.out.println(hotelDto);
        try
        {
            hotelDto = hotelDao.create(hotelDto); // ignores id in hotelDto, and puts id in hotelDto
        }
        catch (DaoException e)
        {
            ctx.status(500);
            return;
        }
        ctx.json(hotelDto);
    }


    public static void update(Context ctx)
    {
//        int id = Integer.parseInt(ctx.pathParam("id"));  // Go by this id
//        PoemDto poemDto = ctx.bodyAsClass(PoemDto.class);   // Ignore any id in the PoemDto
//        poemDto = poemDao.update(id, poemDto);
//        ctx.json(poemDto);
    }

    public static void delete(Context ctx)
    {
//        int id = Integer.parseInt(ctx.pathParam("id"));
//        poemDao.deleteById(id);
    }


}