package org.example.model.animal;

import org.example.Herbivore;
import org.example.Predator;
import org.example.config.AnimalConfig;

public class Duck extends Animal implements Predator, Herbivore {
    public Duck() {
        super(AnimalConfig.animalWeight.get(Duck.class), AnimalConfig.animalSpeed.get(Duck.class), AnimalConfig.animalMaxStarving.get(Duck.class));
    }
}

