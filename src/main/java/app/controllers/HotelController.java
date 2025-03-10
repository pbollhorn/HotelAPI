package app.controllers;

import java.util.List;

import app.exceptions.ApiException;
import app.exceptions.IdNotFoundException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import app.daos.HotelDao;
import app.dtos.HotelDto;
import app.exceptions.DaoException;

public class HotelController
{

    private static HotelDao hotelDao = HotelDao.getInstance();

    public static void getAll(Context ctx) throws Exception
    {
        List<HotelDto> hotelDtos = hotelDao.getAll();

        ctx.json(hotelDtos);
    }

    public static void get(Context ctx) throws Exception
    {
        int id;
        HotelDto hotelDto;

        try
        {
            id = Integer.parseInt(ctx.pathParam("id"));
        }
        catch (Exception e)
        {
            throw new ApiException(400, "Bad id, not a proper id");
        }

        try
        {
            hotelDto = hotelDao.get(id);
        }
        catch (IdNotFoundException e)
        {
            throw new ApiException(404, e.getMessage());
        }

        ctx.json(hotelDto);
    }

    public static void create(Context ctx) throws Exception
    {
        HotelDto hotelDto = null;

        try
        {
            hotelDto = ctx.bodyAsClass(HotelDto.class);
        }
        catch (Exception e)
        {
            throw new BadRequestResponse("Invalid json");
        }

        hotelDto = hotelDao.create(hotelDto); // ignores id in hotelDto, and puts id in hotelDto
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