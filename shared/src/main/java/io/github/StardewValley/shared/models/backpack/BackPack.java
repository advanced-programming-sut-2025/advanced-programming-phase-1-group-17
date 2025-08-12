package io.github.StardewValley.shared.models.backpack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackPack {
    private Player player;
    private HashMap<BackPackableType, ArrayList<BackPackable>> backPackItems = new HashMap<>();
    private final BackPackType type;

    public BackPack(BackPackType type, Player player) {
        this.type = type;
        this.player = player;
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
        item = item.trim();
        BackPackableType backPackType = null;
        for (BackPackableType b : backPackItems.keySet()) {
            if (b == null) {
                continue;
            }
            if (b.getName().trim().equals(item)) {
                backPackType = b;
                break;
            }
        }
        if (backPackType == null) {
            return null;
        } else if (backPackItems.get(backPackType).isEmpty()) {
            return null;
        } else {
            BackPackable b = backPackItems.get(backPackType).get(0);
            backPackItems.get(backPackType).remove(0);
            if (backPackItems.get(backPackType).isEmpty()) {
                backPackItems.remove(backPackType);
            }
            return b;
        }
    }

    public void useItem(BackPackableType type) {
        if (backPackItems.get(type) == null)
            return;
        if (backPackItems.get(type).isEmpty())
            return;

        backPackItems.get(type).remove(0);
        if(this.backPackItems.get(type).isEmpty()) {
            this.backPackItems.remove(type);
        }
    }

    public int getInventorySize(String item) {
        for (BackPackableType b : backPackItems.keySet()) {
            if (b == null) {
                continue;
            }
            if (b.getName().trim().equals(item)) {
                return backPackItems.get(b).size();
            }
        }
        return 0;
    }

    public boolean addItemToInventory(BackPackable backPackable) {
        ArrayList<BackPackableType> b = new ArrayList<>();
        for (BackPackableType backPackableType : backPackItems.keySet()) {
            if (backPackItems.get(backPackableType).isEmpty())
                b.add(backPackableType);
        }
        for (BackPackableType backPackableType : b) {
            backPackItems.remove(backPackableType);
        }
        if (isBackPackFull())
            return false;
        if (backPackable == null) {
            return false;
        }
        backPackItems.computeIfAbsent(backPackable.getType(), k -> new ArrayList<>());
        backPackItems.get(backPackable.getType()).add(backPackable);
        return true;
    }

    public Player getPlayer() {
        return player;
    }


    public BackPackable getFromDTO(String backpackableTypeDTO) {
        for (BackPackableType backPackableType : backPackItems.keySet()) {
            if (backPackableType.getName().equals(backpackableTypeDTO))
                return backPackItems.get(backPackableType).get(0);
        }
        return null;
    }
}
