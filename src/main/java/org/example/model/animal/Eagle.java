package org.example.model.animal;

import org.example.Predator;
import org.example.config.AnimalConfig;

public class Eagle extends Animal implements Predator {
    public Eagle() {
        super(AnimalConfig.animalWeight.get(Eagle.class), AnimalConfig.animalSpeed.get(Eagle.class), AnimalConfig.animalMaxStarving.get(Eagle.class));
    }
}

