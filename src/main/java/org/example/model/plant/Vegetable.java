package org.example.model.plant;

import org.example.config.PlantConfig;

public class Vegetable extends Plant {
    public Vegetable() {
        super(PlantConfig.plantWeight.get(Vegetable.class));
    }
}
