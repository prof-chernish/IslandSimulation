package org.example.service;

import org.example.IslandItem;
import org.example.model.animal.Animal;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalService {
    public void eat(Animal animal, IslandItem food) {
        int starving = animal.getStarving();
        if (food == null) {
            starving--;
        } else {
            starving = Math.min(starving + food.getWeight(), animal.getMaxStarving());
        }
        animal.setStarving(starving);
    }

    public <T extends Animal> T reproduce(Class<T> animalClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return animalClass.getConstructor().newInstance();
    }


    public int move(Animal animal) {
        return ThreadLocalRandom.current().nextInt(0, animal.getSpeed() + 1);
    }

    public boolean animalIsDead(Animal animal) {
        return animal.getStarving() == 0;
    }
}
