package org.example.ui;

import org.example.config.AnimalConfig;
import org.example.config.PlantConfig;
import org.example.model.Island;
import org.example.model.Location;
import org.example.service.IslandService;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class View {
    private final IslandService islandService = new IslandService();
    private static final int delay = 10;

    public void start() throws InterruptedException {
        islandService.initialize();
        showIteration();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleWithFixedDelay(()-> {
            try {
                islandService.growAllPlants();
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, delay, TimeUnit.MILLISECONDS);

        executorService.scheduleWithFixedDelay(() -> {
            try {
                islandService.feedAllAnimals();
                islandService.removeAllDeadAnimals();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                islandService.reproduceAllAnimals();
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                islandService.moveAllAnimals();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showIteration();
            if (islandService.getAllAnimalsCount() == 0) {
                executorService.shutdown();
            }
        }, 0, delay, TimeUnit.MILLISECONDS);

    }

    private void showIteration() {
        Island island = islandService.getIsland();
        Location[][] locations = island.getLocations();
        System.out.println("=".repeat(11 * locations[0].length));
        for (int i = 0; i < locations.length; i++) {
            int finalI = i;
            System.out.println("-".repeat(11 * locations[0].length));
            AnimalConfig.animalClasses.forEach(aClass -> {
                for (int j = 0; j < locations[0].length; j++) {
                    Location location = locations[finalI][j];
                    String title = AnimalConfig.animalTitles.get(aClass);
                    int animalsCount = location.getAnimalMap().get(aClass).size();
                    System.out.printf(" | %-2s = %3d", title, animalsCount);
                }
                System.out.println("\n");
            });
            PlantConfig.plantClasses.forEach(pClass -> {
                for (int j = 0; j < locations[0].length; j++) {
                    Location location = locations[finalI][j];
                    String title = PlantConfig.plantTitles.get(pClass);
                    int plantsCount = location.getPlantMap().get(pClass).size();
                    System.out.printf(" | %-2s = %3d", title, plantsCount);
                }
                System.out.println("\n");
            });
        }
    }
}
