package org.example.models.tools;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.enums.BackPackType;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
    //Attention: It seems there is only one backpack of each type in the whole game

    //previous backpackItems
    //private HashMap<BackPackable, Integer> backPackItems = new HashMap<>();
    private Player player;
    private HashMap<BackPackableType, ArrayList<BackPackable>> backPackItems = new HashMap<>();


    private final BackPackType type;
    private double coin = 0;

    public BackPack(BackPackType type, Player player) {
        this.type = type;
        this.player = player;
    }

    public double getCoin() {
        return coin;
    }

    public void addCoin(double coin) {
        if (!player.getPartner().equals(player)) {
            player.getBackPack().addcoin(coin);
            player.getPartner().getBackPack().addcoin(coin);
        } else {
            player.getBackPack().addcoin(coin);
        }
    }

    public void addcoin(double coin) {
        this.coin += coin;
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
        } else if (backPackItems.get(backPackType).isEmpty()) {
            return null;
        } else {
            BackPackable b = backPackItems.get(backPackType).get(0);
            backPackItems.get(backPackType).remove(0);
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
            if (b.getName().equals(item)) {
                return backPackItems.get(b).size();
            }
        }
        return 0;
    }

    public void addItemToInventory(BackPackable backPackable) {
        if (isBackPackFull() && backPackItems.get(backPackable.getType()) == null)
            return;
        backPackItems.computeIfAbsent(backPackable.getType(), k -> new ArrayList<>());
        backPackItems.get(backPackable.getType()).add(backPackable);
    }


    public Player getPlayer() {
        return player;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }
}
