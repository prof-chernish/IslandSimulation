package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Goat extends Animal implements Herbivore {
    public Goat() {
        super(AnimalConfig.animalWeight.get(Goat.class), AnimalConfig.animalSpeed.get(Goat.class), AnimalConfig.animalMaxStarving.get(Goat.class));
    }
}

