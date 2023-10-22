package org.example.model;

import org.example.model.animal.Animal;
import org.example.model.plant.Plant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private int id;
    private final Map<Class<? extends Animal>, List<Animal>> animalMap = new HashMap<>();
    private final Map<Class<? extends Plant>, List<Plant>> plantMap = new HashMap<>();
    private final Map<Class<? extends Animal>, Integer> animalsCount = new HashMap<>();

    public Map<Class<? extends Animal>, Integer> getAnimalsCount() {
        return animalsCount;
    }

    public Map<Class<? extends Animal>, List<Animal>> getAnimalMap() {
        return animalMap;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Class<? extends Plant>, List<Plant>> getPlantMap() {
        return plantMap;
    }

}
