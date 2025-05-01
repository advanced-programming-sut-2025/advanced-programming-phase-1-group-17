package org.example.models.tools;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Product;
import org.example.models.enums.BackPackType;
import org.example.models.plant.Seed;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
    //Attention: It seems there is only one backpack of each type in the whole game

    //previous backpackItems
    //private HashMap<BackPackable, Integer> backPackItems = new HashMap<>();
    private HashMap<BackPackableType, ArrayList<BackPackable>> backPackItems = new HashMap<>();

    private final BackPackType type;

    public BackPack(BackPackType type) {
        this.type = type;
    }

    public boolean isBackPackFull() {
        return backPackItems.size() >= type.getCapacity();
    }

    public HashMap<BackPackableType, ArrayList<BackPackable>> getBackPackItems() {
        return backPackItems;
    }

    public void setBackPackItems(HashMap<BackPackableType, ArrayList<BackPackable>> backPackItems) {
        this.backPackItems = backPackItems;
    }

    public BackPackType getType() {
        return type;
    }

    public void useItem(BackPackable backPackable) {
        backPackItems.get(backPackable.getType()).remove(backPackable);
    }

    public void addItemToInventory(BackPackable backPackable) {
        backPackItems.get(backPackable.getType()).add(backPackable);
    }
}
