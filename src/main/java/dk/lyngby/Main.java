package dk.lyngby;

import dk.lyngby.config.ApplicationConfig;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        ApplicationConfig.startServer(app, 7071);
    }
}