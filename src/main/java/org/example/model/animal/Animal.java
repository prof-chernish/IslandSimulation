package org.example.model.animal;

import org.example.IslandItem;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements IslandItem {
    protected final int weight;
    protected final int speed;
    protected final int maxStarving;
    protected int starving;

    public void setStarving(int starving) {
        this.starving = starving;
    }

    public int getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxStarving() {
        return maxStarving;
    }

    public int getStarving() {
        return starving;
    }

    public Animal(int weight, int speed, int maxStarving) {
        this.weight = weight;
        this.speed = speed;
        this.maxStarving = maxStarving;
        this.starving = ThreadLocalRandom.current().nextInt(maxStarving/2, maxStarving + 1);
    }
}
