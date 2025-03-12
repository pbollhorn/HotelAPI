package app.controllers;

import java.util.List;

import app.exceptions.ApiException;
import app.exceptions.IdNotFoundException;
import io.javalin.http.Context;

import app.daos.HotelDao;
import app.dtos.HotelDto;

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
        catch (RuntimeException e)
        {
            throw new ApiException(400, "Bad Request: Malformed id");
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
        HotelDto hotelDto;

        try
        {
            hotelDto = ctx.bodyAsClass(HotelDto.class);
        }
        catch (RuntimeException e)
        {
            throw new ApiException(400, "Bad Request: Malformed json");
        }

        hotelDto = hotelDao.create(hotelDto); // ignores id in hotelDto, and puts id in hotelDto
        ctx.json(hotelDto);
    }


    public static void update(Context ctx) throws Exception
    {
        int id;
        HotelDto hotelDto;

        try
        {
            id = Integer.parseInt(ctx.pathParam("id")); // Use this id, and ignore id in the HotelDto
            hotelDto = ctx.bodyAsClass(HotelDto.class);
        }
        catch (RuntimeException e)
        {
            throw new ApiException(400, "Bad Request: Malformed id or json");
        }

        try
        {
            hotelDto = hotelDao.update(id, hotelDto);
        }
        catch (IdNotFoundException e)
        {
            throw new ApiException(404, e.getMessage());
        }

        ctx.json(hotelDto);
    }

    public static void delete(Context ctx) throws Exception
    {
        int id;
        HotelDto hotelDto;

        try
        {
            id = Integer.parseInt(ctx.pathParam("id"));
        }
        catch (Exception e)
        {
            throw new ApiException(400, "Bad Request: Malformed id");
        }

        try
        {
            hotelDto = hotelDao.delete(id);
        }
        catch (IdNotFoundException e)
        {
            throw new ApiException(404, e.getMessage());
        }

        ctx.json(hotelDto);
    }


}