package dk.lyngby.dao.impl;

import dk.lyngby.dto.PlantDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PMockDAO extends AMockDAO<PlantDTO, Integer> {
    public PMockDAO() {
        data.add(new PlantDTO(1, "Rose", "Albertine", 199.50, 400));
        data.add(new PlantDTO(2, "Bush", "Aronia", 169.50, 200));
        data.add(new PlantDTO(3, "FruitAndBerries", "AromaApple", 399.50, 350));
        data.add(new PlantDTO(4, "Rhododendron", "Astrid", 269.50, 40));
        data.add(new PlantDTO(5, "Rose", "The DarkLady", 199.50, 100));
    }

    @Override
    protected Integer getId(PlantDTO plantDTO) {
        return plantDTO.getId();
    }

    public List<PlantDTO> getPlantsByType(String type) {
        List<PlantDTO> plantsByType = new ArrayList<>();
        data.forEach(plant -> {
            if (plant.getPlantType().equals(type)) {
                plantsByType.add(plant);
            }
        });
        return plantsByType;
    }

    public List<PlantDTO> getPlantsWithMaxHeight(List<PlantDTO> plantDTOs) {
        Predicate<PlantDTO> maxHeightPredicate = plant -> plant.getMaxHeight() <= 100;
        return plantDTOs.stream()
                .filter(maxHeightPredicate)
                .collect(Collectors.toList());
    }

    public List<String> getPlantNames(List<PlantDTO> plantDTOs) {
        return plantDTOs.stream()
                .map(PlantDTO::getName)
                .collect(Collectors.toList());
    }

    public List<PlantDTO> sortPlantsByName(List<PlantDTO> plantDTOs) {
        return plantDTOs.stream()
                .sorted(Comparator.comparing(PlantDTO::getName))
                .collect(Collectors.toList());
    }
}
