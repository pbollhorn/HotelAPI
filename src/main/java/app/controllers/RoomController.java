package app.controllers;

import app.dtos.HotelDto;
import app.exceptions.DaoException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class RoomController {

    public static void addRoutes(String resource, Javalin app) {
        app.get(resource + "/", ctx -> getAll(ctx));
//        app.get(resource + "/{id}", ctx -> getById(ctx));
//        app.post(resource + "/", ctx -> create(ctx));
//        app.put(resource + "/{id}", ctx -> update(ctx));
//        app.delete(resource + "/{id}", ctx -> delete(ctx));
    }

    private static void getAll(Context ctx) {

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
