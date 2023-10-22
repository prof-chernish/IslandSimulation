package org.example.model.animal;

import org.example.Predator;
import org.example.config.AnimalConfig;

public class Snake extends Animal implements Predator {
    public Snake() {
        super(AnimalConfig.animalWeight.get(Snake.class), AnimalConfig.animalSpeed.get(Snake.class), AnimalConfig.animalMaxStarving.get(Snake.class));
    }
}
  