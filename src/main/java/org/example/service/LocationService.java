package org.example.service;

import org.example.IslandItem;
import org.example.Predator;
import org.example.config.AnimalConfig;
import org.example.config.PlantConfig;
import org.example.model.animal.Animal;
import org.example.model.Location;
import org.example.model.plant.Plant;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LocationService {
    private final AnimalService animalService = new AnimalService();
    private final PlantService plantService = new PlantService();



    public void initialize(Location location) throws InterruptedException {
        Set<Class<? extends Animal>> animalClasses = AnimalConfig.animalClasses;
        Set<Class<? extends Plant>> plantClasses = PlantConfig.plantClasses;
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        Map<Class<? extends Plant>, List<Plant>> plantMap = location.getPlantMap();
        Map<Class<? extends Animal>, Integer> animalsCount = location.getAnimalsCount();
        ExecutorService service;
        do {
            service = Executors.newFixedThreadPool(3);
            ExecutorService finalService = service;
            animalClasses.forEach(animalClass -> finalService.submit(() -> {
                List<Animal> animalList = new ArrayList<>();
                int animalMaxCount = AnimalConfig.animalMaxCount.get(animalClass);
                int animalCount = ThreadLocalRandom.current().nextInt(0, animalMaxCount + 1);
                animalsCount.put(animalClass, animalCount);
                for (int i = 0; i < animalCount; i++) {
                    try {
                        animalList.add(animalService.reproduce(animalClass));
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                animalMap.put(animalClass, animalList);
            }));


            plantClasses.forEach(plantClass -> finalService.submit(() -> {
                List<Plant> plantList = new ArrayList<>();
                int plantMaxCount = PlantConfig.plantMaxCount.get(plantClass);
                int plantCount = ThreadLocalRandom.current().nextInt(0, plantMaxCount + 1);
                for (int i = 0; i < plantCount; i++) {
                    try {
                        plantList.add(plantService.grow(plantClass));
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                plantMap.put(plantClass, plantList);
            }));
            service.shutdown();

        } while (!service.awaitTermination(10, TimeUnit.MINUTES));
    }


    public void feedAnimalsInLocation(Location location) {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry: animalMap.entrySet()) {
            Class<? extends Animal> animalClass = entry.getKey();
            entry.getValue().forEach(animal -> {
                IslandItem food;
                if (animal instanceof Predator) {
                    food = findFoodForPredator(location, animalClass);
                } else {
                    food = findFoodForHerbivore(location);
                }
                    animalService.eat(animal, food);
            });
        }
    }

    private IslandItem findFoodForPredator(Location location, Class<? extends Animal> animalClass) {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        Map<Class<? extends Animal>, Integer> eatingProbabilityMapForAnimal = AnimalConfig.eatingProbability.get(animalClass);
        IslandItem food = null;
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry: animalMap.entrySet()) {
            Class<? extends Animal> foodClass = entry.getKey();
            List<? extends Animal> foodList = entry.getValue();
            int probability = eatingProbabilityMapForAnimal.get(foodClass);
            if (foodList.size() > 0 && probability > 0) {
                if (foodIsEaten(probability)) {
                    food = foodList.get(0);
                    foodList.remove(0);
                }
                return food;
            }
        }
        return food;
    }

    private boolean foodIsEaten(int probability) {
        int random = ThreadLocalRandom.current().nextInt(0, 100);
        return random < probability;
    }

    private IslandItem findFoodForHerbivore(Location location) {
        Map<Class<? extends Plant>, List<Plant>> plantMap = location.getPlantMap();
        IslandItem food = null;
        for (Map.Entry<Class<? extends Plant>, List<Plant>> entry: plantMap.entrySet()) {
            Class<? extends Plant> plantClass = entry.getKey();
            synchronized (plantClass) {
                List<Plant> foodList = entry.getValue();
                if (foodList.size() > 0) {
                    food = foodList.get(0);
                    foodList.remove(0);
                    return food;
                }
            }
        }
        return food;
    }

    public void reproduceAnimalsInLocation(Location location) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry: animalMap.entrySet()) {
            service.submit(() -> {
                Class<? extends Animal> animalClass = entry.getKey();
                List<Animal> animalList = entry.getValue();
                int animalCount = animalList.size();
                int pairsCount = animalCount / 2;
                int animalMaxCount = AnimalConfig.animalMaxCount.get(animalClass);
                int newAnimalsCount = Math.min(pairsCount, animalMaxCount - animalCount);
                for (int i = 0; i < newAnimalsCount; i++) {
                    try {
                        animalList.add(animalService.reproduce(animalClass));
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    public void growPlantsInLocation(Location location) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Map<Class<? extends Plant>, List<Plant>> plantMap = location.getPlantMap();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (Map.Entry<Class<? extends Plant>, List<Plant>> entry: plantMap.entrySet()) {
            service.submit(() -> {
                Class<? extends Plant> plantClass = entry.getKey();
                synchronized (plantClass) {
                    List<Plant> plantList = entry.getValue();
                    int plantCount = plantList.size();
                    int plantMaxCount = PlantConfig.plantMaxCount.get(plantClass);
                    int newPlantsCount = ThreadLocalRandom.current().nextInt(0, plantMaxCount - plantCount + 1);
                    for (int i = 0; i < newPlantsCount; i++) {
                        try {
                            plantList.add(plantService.grow(plantClass));
                        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    public Map<Animal, Integer> getStepsForAnimalsInLocation(Location location) throws InterruptedException {
        Map<Animal, Integer> map = new HashMap<>();
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        ExecutorService service = Executors.newFixedThreadPool(3);
        animalMap.entrySet().forEach(entry -> service.submit(() -> entry.getValue().forEach(animal -> map.put(animal, animalService.move(animal)))));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        return map;
    }

    public boolean locationHasEnoughSpace(Location location, Class<? extends Animal> animalClass) {
        return location.getAnimalsCount().get(animalClass) < AnimalConfig.animalMaxCount.get(animalClass);

    }

    public void decreaseAnimalCount(Location location, Class<? extends Animal> animalClass) {
        Map<Class<? extends Animal>, Integer> animalsCount = location.getAnimalsCount();
            Integer animalCount = animalsCount.get(animalClass);
            animalsCount.put(animalClass, animalCount - 1);

    }

    public void removeAnimal(Location location, Animal animal) {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        animalMap.get(animal.getClass()).remove(animal);
    }

    public void increaseAnimalCount(Location location, Class<? extends Animal> animalClass) {
        Map<Class<? extends Animal>, Integer> animalsCount = location.getAnimalsCount();
            Integer animalCount = animalsCount.get(animalClass);
            animalsCount.put(animalClass, animalCount + 1);
    }

    public void addAnimal(Animal animal, Location location) {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        animalMap.get(animal.getClass()).add(animal);
    }

    public void removeDeadAnimalsInLocation(Location location) throws InterruptedException {
        Map<Class<? extends Animal>, List<Animal>> animalMap = location.getAnimalMap();
        Map<Class<? extends Animal>, Integer> animalsCount = location.getAnimalsCount();
        ExecutorService service = Executors.newFixedThreadPool(3);
        animalMap.entrySet().forEach(entry -> service.submit(() -> {
            Class<? extends Animal> animalClass = entry.getKey();
            List<Animal> animalList = entry.getValue();
            int i = 0;
            while (i < animalList.size()) {
                Animal animal = animalList.get(i);
                if (animalService.animalIsDead(animal)) {
                    animalList.remove(animal);
                    int animalCount = animalsCount.get(animalClass);
                    animalsCount.put(animalClass, animalCount - 1);
                } else {
                    i++;
                }
            }

        }));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
