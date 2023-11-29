package dk.lyngby.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString (exclude = "reseller")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String plantType;
    private String name;
    private double price;
    private int maxHeight;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "reseller_id")
    private Reseller reseller;

    public Plant(String plantType, String name, double price, int maxHeight) {
        this.plantType = plantType;
        this.name = name;
        this.price = price;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant plant)) return false;
        return getId() == plant.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

