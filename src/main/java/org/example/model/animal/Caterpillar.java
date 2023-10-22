package org.example.model.animal;

import org.example.Herbivore;
import org.example.config.AnimalConfig;

public class Caterpillar extends Animal implements Herbivore {
    public Caterpillar() {
        super(AnimalConfig.animalWeight.get(Caterpillar.class), AnimalConfig.animalSpeed.get(Caterpillar.class), AnimalConfig.animalMaxStarving.get(Caterpillar.class));
    }
}