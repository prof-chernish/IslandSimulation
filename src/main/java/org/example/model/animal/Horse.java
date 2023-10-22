package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Horse extends Animal implements Herbivore {
    public Horse() {
        super(AnimalConfig.animalWeight.get(Horse.class), AnimalConfig.animalSpeed.get(Horse.class), AnimalConfig.animalMaxStarving.get(Horse.class));
    }
}

