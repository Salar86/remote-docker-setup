package dk.lyngby.dto;

import dk.lyngby.model.Plant;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlantDTO {
    private int id;
    private String plantType;
    private String name;
    private double price;
    private int maxHeight;
    private ResellerDTO reseller;


    public PlantDTO(String plantType, String name, double price, int maxHeight) {
        this.plantType = plantType;
        this.name = name;
        this.price = price;
        this.maxHeight = maxHeight;
    }

    public PlantDTO(int id, String plantType, String name, double price, int maxHeight) {
        this.id = id;
        this.plantType = plantType;
        this.name = name;
        this.price = price;
        this.maxHeight = maxHeight;
    }

    public PlantDTO(Plant plant) {
        this.id = plant.getId();
        this.plantType = plant.getPlantType();
        this.name = plant.getName();
        this.price = plant.getPrice();
        this.maxHeight = plant.getMaxHeight();
        if (plant.getReseller() != null) {
            this.reseller = new ResellerDTO(plant.getReseller());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantDTO plantDTO)) return false;
        return getId() == plantDTO.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static List<PlantDTO> toPlantDTOList(List<Plant> plants) {
        return plants.stream().map(PlantDTO::new).collect(Collectors.toList());
    }
}
