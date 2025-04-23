package org.example.models.tools;

import org.example.models.BackPackable;
import org.example.models.Product;
import org.example.models.enums.BackPackType;
import org.example.models.plant.Seed;

import java.util.HashMap;

public class BackPack {
    //Attention: It seems there is only one backpack of each type in the whole game

    private HashMap<BackPackable, Integer> backPackItems = new HashMap<>();

    private final BackPackType type;

    public BackPack(BackPackType type) {
        this.type = type;
    }

    private boolean isBackPackFull() {
        return backPackItems.size() >= type.getCapacity();
    }

    public HashMap<BackPackable, Integer> getBackPackItems() {
        return backPackItems;
    }

    public void setBackPackItems(HashMap<BackPackable, Integer> backPackItems) {
        this.backPackItems = backPackItems;
    }

    public BackPackType getType() {
        return type;
    }
}
