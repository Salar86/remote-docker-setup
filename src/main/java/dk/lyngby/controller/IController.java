package dk.lyngby.controller;

import dk.lyngby.exception.ApiException;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public interface IController<T, ID> {
    Handler read();
    Handler readAll();
    Handler create();
    Handler update();
    Handler delete();


}
