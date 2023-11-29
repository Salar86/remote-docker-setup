package dk.lyngby.controller.impl;

import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.PlantADAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Plant;
import io.javalin.http.Handler;

import java.time.LocalDateTime;
import java.util.List;

public class PlantControllerA implements IController {
    private final PlantADAO plantDAO = new PlantADAO();
    @Override
    public Handler read() {
        return context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Plant plant = plantDAO.read(id);
            if (plant == null) {
                throw new ApiException(404, "Plant not found", LocalDateTime.now().toString());
            }
            PlantDTO plantDTO = new PlantDTO(plant);
            context.res().setStatus(200);
            context.json(plantDTO, PlantDTO.class);
        };
    }

    @Override
    public Handler readAll() {
        return context -> {
            List<Plant> plants = plantDAO.readAll();
            List<PlantDTO> plantDTOs = PlantDTO.toPlantDTOList(plants);
            context.res().setStatus(200);
            context.json(plantDTOs, PlantDTO.class);
        };
    }

    @Override
    public Handler create() {
        return context -> {
            Plant plant = context.bodyAsClass(Plant.class);
            plantDAO.create(plant);
            PlantDTO plantDTO = new PlantDTO(plant);
            context.res().setStatus(201);
            context.json(plantDTO, PlantDTO.class);
        };
    }

    @Override
    public Handler update() {
        return context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Plant plant = context.bodyAsClass(Plant.class);
            Plant updatedPlant = plantDAO.update(id, plant);
            PlantDTO plantDTO = new PlantDTO(updatedPlant);
            context.res().setStatus(200);
            context.json(plantDTO, PlantDTO.class);
        };
    }

    @Override
    public Handler delete() {
        return context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            plantDAO.delete(id);
            context.res().setStatus(200);

        };
    }

    public Handler readByType() {
        return context -> {
            String type = context.pathParam("type");
            List<Plant> plants = plantDAO.getByType(type);
            List<PlantDTO> plantDTOs = PlantDTO.toPlantDTOList(plants);
            context.json(plantDTOs, PlantDTO.class);
        };

    }

    public Handler addPlantToReseller() {
        return context -> {
            int resellerId = Integer.parseInt(context.pathParam("resellerId"));
            int plantId = Integer.parseInt(context.pathParam("id"));
            plantDAO.addPlantToReseller(resellerId, plantId);
            PlantDTO plantDTO = new PlantDTO(plantDAO.read(plantId));
            context.res().setStatus(200);
            context.json(plantDTO, PlantDTO.class);
        };
    }


}
