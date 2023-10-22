package org.example.model.plant;

import org.example.config.PlantConfig;

public class Grass extends Plant {
    public Grass() {
        super(PlantConfig.plantWeight.get(Grass.class));
    }
}
