package app.controllers;

import java.util.List;

import io.javalin.http.Context;

import app.daos.HotelDao;
import app.dtos.HotelDto;
import app.exceptions.DaoException;

public class HotelController
{

    private static HotelDao hotelDao = HotelDao.getInstance();

    public static void getAll(Context ctx)
    {
        try
        {
            List<HotelDto> hotelDtos = hotelDao.getAll();
            ctx.json(hotelDtos);
        }
        catch (DaoException e)
        {
            ctx.status(500);
        }
    }

    // TODO: Also implement 404 not found status code
    public static void get(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try
        {
            HotelDto hotelDto = hotelDao.get(id);
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
        int id = Integer.parseInt(ctx.pathParam("id"));  // Go by this id
        HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);   // Ignore any id in the HotelDto

        try
        {
            hotelDto = hotelDao.update(id, hotelDto);
        }
        catch (DaoException e)
        {
            ctx.status(500);
            return;
        }

        ctx.json(hotelDto);
    }

    public static void delete(Context ctx)
    {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try
        {
            HotelDto hotelDto = hotelDao.delete(id);
            ctx.json(hotelDto);
        }
        catch (DaoException e)
        {
            ctx.status(500);
        }
    }


}