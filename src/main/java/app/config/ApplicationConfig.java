package app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.config.JavalinConfig;

import static io.javalin.apibuilder.ApiBuilder.path;

import app.exceptions.ApiException;

public class ApplicationConfig
{
    private static ApplicationConfig applicationConfig;
    private static Javalin app;
    private static JavalinConfig javalinConfig;
    private ObjectMapper objectMapper = new ObjectMapper();

    private ApplicationConfig()
    {
    }

    public static ApplicationConfig getInstance()
    {
        if (applicationConfig == null)
        {
            applicationConfig = new ApplicationConfig();
        }
        return applicationConfig;
    }

    public ApplicationConfig initiateServer()
    {
        app = Javalin.create(config -> {
            javalinConfig = config;
            config.http.defaultContentType = "application/json";
            config.router.contextPath = "/api";
            config.bundledPlugins.enableRouteOverview("/routes");
            config.bundledPlugins.enableDevLogging();
        });
        return applicationConfig;
    }

    public ApplicationConfig setRoute(EndpointGroup routes)
    {
        javalinConfig.router.apiBuilder(() -> {
            path("/", routes);
        });
        return applicationConfig;
    }

    public ApplicationConfig startServer(int port)
    {
        app.start(port);
        return applicationConfig;
    }

    public ApplicationConfig handleException()
    {
        app.exception(ApiException.class, (e, ctx) -> {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("code", e.getCode());
            node.put("msg", e.getMessage());
            ctx.status(e.getCode());
            ctx.json(node);
        });
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("code", "500");
            node.put("msg", "Internal server error");
            ctx.status(500);
            ctx.json(node);
        });
        return applicationConfig;
    }


    // My own method for auto shutdown, based on code from Javalin documentation:
    // https://javalin.io/documentation#server-setup
    public ApplicationConfig autoShutdown()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            app.stop();
        }));

        return applicationConfig;
    }

}