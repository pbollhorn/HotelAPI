package app.controllers;

import app.daos.HotelDao;
import app.daos.PoemDao;
import app.dtos.HotelDto;
import app.dtos.PoemDto;
import app.exceptions.DaoException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class HotelController {

    private static HotelDao hotelDao = HotelDao.getInstance();

    public static void addRoutes(String resource, Javalin app) {
        app.get(resource + "/", ctx -> getAll(ctx));
        app.get(resource + "/{id}", ctx -> getById(ctx));
        app.post(resource + "/", ctx -> create(ctx));
        app.put(resource + "/{id}", ctx -> update(ctx));
        app.delete(resource + "/{id}", ctx -> delete(ctx));
    }

    private static void getAll(Context ctx) {
        try {
            List<HotelDto> hotelDtos = hotelDao.readAll();
            ctx.json(hotelDtos);
        } catch (DaoException e) {
            ctx.status(500);
        }
    }

    private static void getById(Context ctx) {
//        int id = Integer.parseInt(ctx.pathParam("id"));
//        PoemDto poemDto = poemDao.readById(id);
//        ctx.json(poemDto);
    }

    private static void create(Context ctx) {
        HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);
        System.out.println(hotelDto);
        try {
            hotelDto = hotelDao.create(hotelDto); // ignores id in hotelDto, and puts id in hotelDto
        } catch (DaoException e) {
            ctx.status(500);
            return;
        }
        ctx.json(hotelDto);
    }


    private static void update(Context ctx) {
//        int id = Integer.parseInt(ctx.pathParam("id"));  // Go by this id
//        PoemDto poemDto = ctx.bodyAsClass(PoemDto.class);   // Ignore any id in the PoemDto
//        poemDto = poemDao.update(id, poemDto);
//        ctx.json(poemDto);
    }

    private static void delete(Context ctx) {
//        int id = Integer.parseInt(ctx.pathParam("id"));
//        poemDao.deleteById(id);
    }


}
