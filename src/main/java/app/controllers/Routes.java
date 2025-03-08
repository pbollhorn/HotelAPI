package app.controllers;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes
{

    public static EndpointGroup getRoutes()
    {
        return () -> {
            path("dog", () -> {


            });
        };

    }
}