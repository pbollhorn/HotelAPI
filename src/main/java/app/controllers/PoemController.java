package app.controllers;

import java.util.List;

import app.exceptions.DaoException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import app.daos.PoemDao;
import app.dtos.PoemDto;

public class PoemController {

    private static PoemDao poemDao = PoemDao.getInstance();

    public static void addRoutes(String resource, Javalin app) {
        app.get(resource + "/", ctx -> getAll(ctx));
        app.get(resource + "/{id}", ctx -> getById(ctx));
        app.post(resource + "/", ctx -> create(ctx));
        app.put(resource + "/{id}", ctx -> update(ctx));
        app.delete(resource + "/{id}", ctx -> delete(ctx));
    }

    private static void getAll(Context ctx) {
        List<PoemDto> poemDtos = poemDao.readAll();
        ctx.json(poemDtos);
    }

    private static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDto poemDto = poemDao.readById(id);
        ctx.json(poemDto);
    }

    private static void create(Context ctx) {
        PoemDto poemDto = ctx.bodyAsClass(PoemDto.class);
        try {
            poemDto = poemDao.create(poemDto);
        }
        catch(DaoException e)
        {
            ctx.status(500);
            return;
        }
        ctx.json(poemDto);
    }

    // Update an existing poem
    private static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));  // Go by this id
        PoemDto poemDto = ctx.bodyAsClass(PoemDto.class);   // Ignore any id in the PoemDto
        poemDto = poemDao.update(id, poemDto);
        ctx.json(poemDto);
    }

    private static void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        poemDao.deleteById(id);
    }


}
