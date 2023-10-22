package org.example.service;

import org.example.model.plant.Plant;

import java.lang.reflect.InvocationTargetException;

public class PlantService {

    public <T extends Plant> T grow(Class<T> plantClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return plantClass.getConstructor().newInstance();
    }
}
