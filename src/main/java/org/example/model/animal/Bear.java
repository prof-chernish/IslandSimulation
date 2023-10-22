package org.example.model.animal;

import org.example.Predator;
import org.example.config.AnimalConfig;

public class Bear extends Animal implements Predator {
    public Bear() {
        super(AnimalConfig.animalWeight.get(Bear.class), AnimalConfig.animalSpeed.get(Bear.class), AnimalConfig.animalMaxStarving.get(Bear.class));
    }
}

