package org.example.config;

import org.example.model.plant.*;
import org.example.util.PropertiesReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlantConfig {
    private static final String PAC_NAME_FOR_PLANT_CONFIG = "\\plantconfig\\";
    private static final String EXTENSION = ".properties";
    public static final Map<Class<? extends Plant>, Integer> plantMaxCount = new HashMap<>();
    public static final Map<Class<? extends Plant>, Integer> plantWeight = new HashMap<>();
    public static final Map<Class<? extends Plant>, String> plantTitles = new HashMap<>();
    public static final Set<Class<? extends Plant>> plantClasses;
    static {
        plantClasses = Set.of(Grass.class, Flower.class, Fruit.class, Vegetable.class);
        plantClasses.forEach(pClass -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PAC_NAME_FOR_PLANT_CONFIG).append(pClass.getSimpleName().toLowerCase()).append(EXTENSION);
            String fileName = stringBuilder.toString();
            PropertiesReader propertiesReader = new PropertiesReader(fileName);
            plantMaxCount.put(pClass, propertiesReader.getMaxCount());
            plantWeight.put(pClass, propertiesReader.getWeight());
            plantTitles.put(pClass, propertiesReader.getTitle());

        });
    }
}

