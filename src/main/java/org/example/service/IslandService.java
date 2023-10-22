package org.example.service;

import org.example.model.animal.Animal;
import org.example.model.Island;
import org.example.model.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class IslandService {
    private final Island island = new Island();
    private final LocationService locationService = new LocationService();
    public Island getIsland() {
        return island;
    }


    public void initialize() throws InterruptedException {
        Location[][] locations = island.getLocations();
        int id = 0;
        ExecutorService service;
        do {
            service = Executors.newFixedThreadPool(3);
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations[0].length; j++) {
                    locations[i][j] = new Location();
                    locations[i][j].setId(id++);
                    int finalI = i;
                    int finalJ = j;
                    service.submit(() -> {
                        try {
                            locationService.initialize(locations[finalI][finalJ]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            service.shutdown();

        } while (!service.awaitTermination(10, TimeUnit.MINUTES));
    }

    public int getAllAnimalsCount() {
        Location[][] locations = island.getLocations();
        AtomicInteger sum = new AtomicInteger();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                locations[i][j].getAnimalsCount().values().forEach(sum::addAndGet);
            }
        }
        return sum.get();
    }

    public void feedAllAnimals() throws InterruptedException {
        Location[][] locations = island.getLocations();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                service.submit(() -> {
                    locationService.feedAnimalsInLocation(locations[finalI][finalJ]);
                });
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    public void reproduceAllAnimals() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Location[][] locations = island.getLocations();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                service.submit(() -> {
                    try {
                        locationService.reproduceAnimalsInLocation(locations[finalI][finalJ]);
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    public void moveAllAnimals() throws InterruptedException {
        Location[][] locations = island.getLocations();
        Map<Animal, Location> newLocationsForAnimals = new HashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(3);
        final Location[] newLocation = new Location[1];
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Location location = locations[i][j];
                int finalI = i;
                int finalJ = j;
                service.submit(() -> {
                    Map<Animal, Integer> stepsForAnimals = null;
                    try {
                        stepsForAnimals = locationService.getStepsForAnimalsInLocation(location);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Map.Entry<Animal, Integer> entry: stepsForAnimals.entrySet()) {
                        Animal animal = entry.getKey();
                        int step = entry.getValue();
                        if (step > 0) {
                            newLocation[0] = getNewLocationForAnimal(finalI, finalJ, step);
                            if (newLocation[0] != location) {
                                Location locationWithMinId = location.getId() < newLocation[0].getId() ? location : newLocation[0];
                                Location locationWithMaxId = location.getId() > newLocation[0].getId() ? location : newLocation[0];
                                synchronized (locationWithMinId) {
                                    synchronized (locationWithMaxId) {
                                        if (locationService.locationHasEnoughSpace(newLocation[0], animal.getClass())) {
                                            newLocationsForAnimals.put(animal, newLocation[0]);
                                            locationService.decreaseAnimalCount(location, animal.getClass());
                                            locationService.removeAnimal(location, animal);
                                            locationService.increaseAnimalCount(newLocation[0], animal.getClass());
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        newLocationsForAnimals.entrySet().forEach(entry -> locationService.addAnimal(entry.getKey(), entry.getValue()));
    }

    private Location getNewLocationForAnimal(int i, int j, int step) {
        int x = ThreadLocalRandom.current().nextInt(-step, step + 1);
        int y = ThreadLocalRandom.current().nextInt(-(step - Math.abs(x)), (step - Math.abs(x)) + 1);
        Location[][] locations = island.getLocations();
        int sizey = locations.length;
        int sizex = locations[0].length;
        return locations[(i + y + sizey) % sizey][(j + x + sizex) % sizex];

    }

    public void removeAllDeadAnimals() throws InterruptedException {
        Location[][] locations = island.getLocations();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                service.submit(() -> {
                    try {
                        locationService.removeDeadAnimalsInLocation(locations[finalI][finalJ]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }


    public void growAllPlants() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Location[][] locations = island.getLocations();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                service.submit(() -> {
                    try {
                        locationService.growPlantsInLocation(locations[finalI][finalJ]);
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
