package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Deer extends Animal implements Herbivore {
    public Deer() {
        super(AnimalConfig.animalWeight.get(Deer.class), AnimalConfig.animalSpeed.get(Deer.class), AnimalConfig.animalMaxStarving.get(Deer.class));
    }
}

