package dk.lyngby.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private final PlantShopRouteA plantShopRouteA = new PlantShopRouteA();
    private final PMockRoute pMockRoute = new PMockRoute();
    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.routes(() -> {
                path("/", plantShopRouteA.getRoutes());
                path("/", pMockRoute.getRoutes());
            });
        };
    }
}
