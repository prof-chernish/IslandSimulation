package org.example.config;

import org.example.model.animal.*;
import org.example.util.PropertiesReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimalConfig {
    public static final Map<Class<? extends Animal>, String> animalTitles = new HashMap<>();
    private static final String PAC_NAME_FOR_EATING = "\\eating\\";
    private static final String PAC_NAME_FOR_ANIMAL_CONFIG = "\\animalconfig\\";
    private static final String EXTENSION = ".properties";
    public static final Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> eatingProbability = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> animalMaxCount = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> animalWeight = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> animalSpeed = new HashMap<>();
    public static final Map<Class<? extends Animal>, Integer> animalMaxStarving = new HashMap<>();
    public static final Set<Class<? extends Animal>> animalClasses;
    static {
        animalClasses = Set.of(Fox.class, Wolf.class, Bear.class, Buffalo.class, Caterpillar.class, Deer.class, Duck.class, Eagle.class, Goat.class, Hog.class, Horse.class, Mouse.class, Rabbit.class, Sheep.class, Snake.class);
        animalClasses.forEach(aClass -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PAC_NAME_FOR_EATING).append(aClass.getSimpleName().toLowerCase()).append(EXTENSION);
            String fileName = stringBuilder.toString();
            PropertiesReader propertiesReader = new PropertiesReader(fileName);
            Map<Class<? extends Animal>, Integer> animalProbabiltyMap = new HashMap<>();
            eatingProbability.put(aClass, animalProbabiltyMap);
            animalClasses.forEach(animalClass -> {
                animalProbabiltyMap.put(animalClass,
                        propertiesReader.getEatingProbabilityFor(animalClass.getSimpleName().toLowerCase()));
            });
        });

        animalClasses.forEach(aClass -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PAC_NAME_FOR_ANIMAL_CONFIG).append(aClass.getSimpleName().toLowerCase()).append(EXTENSION);
            String fileName = stringBuilder.toString();
            PropertiesReader propertiesReader = new PropertiesReader(fileName);
            animalMaxCount.put(aClass, propertiesReader.getMaxCount());
            animalWeight.put(aClass, propertiesReader.getWeight());
            animalSpeed.put(aClass, propertiesReader.getSpeed());
            animalMaxStarving.put(aClass, propertiesReader.getMaxStarving());
            animalTitles.put(aClass, propertiesReader.getTitle());
            
        });
    }
}
