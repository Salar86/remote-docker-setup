package dk.lyngby.model;

import dk.lyngby.model.Plant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString (exclude = "plants")
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "reseller", fetch = FetchType.EAGER)
    private List<Plant> plants;

    public Reseller(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void addPlant(Plant plant) {
        if (plant != null) {
            this.plants.add(plant);
            plant.setReseller(this);
        }
    }

}
