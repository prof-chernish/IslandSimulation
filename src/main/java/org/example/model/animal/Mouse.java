package org.example.model.animal;

import org.example.Herbivore;
import org.example.Predator;
import org.example.config.AnimalConfig;

public class Mouse extends Animal implements Predator, Herbivore {
    public Mouse() {
        super(AnimalConfig.animalWeight.get(Mouse.class), AnimalConfig.animalSpeed.get(Mouse.class), AnimalConfig.animalMaxStarving.get(Mouse.class));
    }
}

