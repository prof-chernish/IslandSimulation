package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Rabbit extends Animal implements Herbivore {
    public Rabbit() {
        super(AnimalConfig.animalWeight.get(Rabbit.class), AnimalConfig.animalSpeed.get(Rabbit.class), AnimalConfig.animalMaxStarving.get(Rabbit.class));
    }
}
