package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Sheep extends Animal implements Herbivore {
    public Sheep() {
        super(AnimalConfig.animalWeight.get(Sheep.class), AnimalConfig.animalSpeed.get(Sheep.class), AnimalConfig.animalMaxStarving.get(Sheep.class));
    }
}

