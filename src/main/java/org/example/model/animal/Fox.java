package org.example.model.animal;

import org.example.Predator;
import org.example.config.AnimalConfig;

public class Fox extends Animal implements Predator {
    public Fox() {
        super(AnimalConfig.animalWeight.get(Fox.class), AnimalConfig.animalSpeed.get(Fox.class), AnimalConfig.animalMaxStarving.get(Fox.class));
    }
}

