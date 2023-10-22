package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Buffalo extends Animal implements Herbivore {
    public Buffalo() {
        super(AnimalConfig.animalWeight.get(Buffalo.class), AnimalConfig.animalSpeed.get(Buffalo.class), AnimalConfig.animalMaxStarving.get(Buffalo.class));
    }
}