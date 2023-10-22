package org.example.model.plant;

import org.example.IslandItem;

public abstract class Plant implements IslandItem {
    protected final int weight;

    public Plant(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
