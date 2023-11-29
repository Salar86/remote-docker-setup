package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.PMockDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import io.javalin.http.Handler;

import java.time.LocalDateTime;
import java.util.List;

public class PMockController implements IController {

    private final PMockDAO plantDAO = new PMockDAO();
    @Override
    public Handler read() {
        return context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            PlantDTO plant = plantDAO.read(id);
            if (plant == null) {
                throw new ApiException(404, "Plant not found", LocalDateTime.now().toString());
            }
            context.res().setStatus(200);
            context.json(plant, PlantDTO.class);
        };
    }

    @Override
    public Handler readAll() {
        return context -> {
            List<PlantDTO> plants = plantDAO.readAll();
            context.res().setStatus(200);
            context.json(plants, PlantDTO.class);
        };
    }

    @Override
    public Handler create() {
        return context -> {
            PlantDTO plant = context.bodyAsClass(PlantDTO.class);
            plantDAO.create(plant);
            context.res().setStatus(201);
            context.json(plant, PlantDTO.class);
        };
    }

    @Override
    public Handler update() {
        return null;

    }

    @Override
    public Handler delete() {
        return null;
    }

    public Handler readByType() {
        return context -> {
            String type = context.pathParam("type");
            List<PlantDTO> plants = plantDAO.getPlantsByType(type);
            context.json(plants, PlantDTO.class);
        };

    }
}
