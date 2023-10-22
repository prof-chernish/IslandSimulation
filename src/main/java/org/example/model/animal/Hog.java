package org.example.model.animal;

import org.example.Herbivore;
import org.example.Predator;
import org.example.config.AnimalConfig;

public class Hog extends Animal implements Predator, Herbivore {
    public Hog() {
        super(AnimalConfig.animalWeight.get(Hog.class), AnimalConfig.animalSpeed.get(Hog.class), AnimalConfig.animalMaxStarving.get(Hog.class));
    }
}

