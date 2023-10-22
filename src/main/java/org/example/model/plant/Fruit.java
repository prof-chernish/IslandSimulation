package org.example.model.plant;

import org.example.config.PlantConfig;

public class Fruit extends Plant {
    public Fruit() {
        super(PlantConfig.plantWeight.get(Fruit.class));
    }
}
