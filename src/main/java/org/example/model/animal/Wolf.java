package org.example.model.animal;

import org.example.Predator;
import org.example.config.AnimalConfig;

public class Wolf extends Animal implements Predator {
    public Wolf() {
        super(AnimalConfig.animalWeight.get(Wolf.class), AnimalConfig.animalSpeed.get(Wolf.class), AnimalConfig.animalMaxStarving.get(Wolf.class));
    }
}