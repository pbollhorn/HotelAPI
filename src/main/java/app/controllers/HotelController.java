package app.controllers;

import java.util.List;

import io.javalin.http.Context;

import app.daos.HotelDao;
import app.daos.RoomDao;
import app.dtos.HotelDto;
import app.dtos.RoomDto;
import app.exceptions.DaoException;

public class HotelController
{

    private static HotelDao hotelDao = HotelDao.getInstance();
    private static RoomDao roomDao = RoomDao.getInstance();

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