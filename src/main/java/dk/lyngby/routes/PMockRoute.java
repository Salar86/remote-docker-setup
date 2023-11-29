package dk.lyngby.routes;

import dk.lyngby.controller.impl.PMockController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PMockRoute {
    private final PMockController plantController = new PMockController();

    protected EndpointGroup getRoutes() {

        return () -> {
            path("/pmocks", () -> {
                post("/", plantController.create());
                get("/", plantController.readAll());
                get("/{id}", plantController.read());
                get("/type/{type}", plantController.readByType());
            });
        };
    }
}
