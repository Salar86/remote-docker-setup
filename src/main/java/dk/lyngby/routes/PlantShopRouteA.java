package dk.lyngby.routes;


import dk.lyngby.controller.impl.PlantControllerA;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantShopRouteA {
    private final PlantControllerA plantController = new PlantControllerA();

    protected EndpointGroup getRoutes() {

        return () -> {
            path("/plantsa", () -> {
                post("/", plantController.create());
                get("/", plantController.readAll());
                get("/{id}", plantController.read());
                get("/type/{type}", plantController.readByType());
                put("/{id}", plantController.update());
                put("addReseller/{id}/{resellerId}", plantController.addPlantToReseller());
                delete("/{id}", plantController.delete());
            });
        };
    }
}
