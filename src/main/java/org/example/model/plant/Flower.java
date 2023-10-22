package org.example.model.plant;

import org.example.config.PlantConfig;

public class Flower extends Plant {
    public Flower() {
        super(PlantConfig.plantWeight.get(Flower.class));
    }
}
