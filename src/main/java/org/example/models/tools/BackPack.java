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

    public BackPackable useItem(String item) {
        BackPackableType backPackType = null;
        for (BackPackableType b : backPackItems.keySet()) {
            if (b.getName().equals(item)) {
                backPackType = b;
                break;
            }
        }
        if (backPackType == null) {
            return null;
        }
        else if (backPackItems.get(backPackType).isEmpty()) {
            return null;
        }
        else {
            BackPackable b = backPackItems.get(backPackType).get(0);
            backPackItems.get(backPackType).remove(0);
            return b;
        }
    }
    public int getInventorySize(String item) {
        for (BackPackableType b : backPackItems.keySet()) {
            if (b.getName().equals(item)) {
                return backPackItems.get(b).size();
            }
        }
        return 0;
    }

    public void addItemToInventory(BackPackable backPackable) {
        backPackItems.computeIfAbsent(backPackable.getType(), k -> new ArrayList<>());
        backPackItems.get(backPackable.getType()).add(backPackable);
    }
}
