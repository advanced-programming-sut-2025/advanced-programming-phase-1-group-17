package org.example.models.trade;

import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ShippingBin implements Placeable {
    private static ArrayList<ShippingBin> allShippingBins = new ArrayList<>();
    private HashMap<BackPackableType, Integer> items = new HashMap<>();
    private final ShippingBinType type;

    //only one player can have items in a shipping bin each day
    private Player todayItemOwner = null;

    public ShippingBin(ShippingBinType type) {
        this.type = type;
        allShippingBins.add(this);
    }

    public void addItems(BackPackableType type, Integer count, Player owner) {
        this.todayItemOwner = owner;
        if (this.items.get(type) == null) {
            this.items.put(type, items.get(type));
        } else {
            this.items.put(type, this.items.get(type) + count);
        }
    }

    public ShippingBinType getType() {
        return type;
    }

    public static void goToNextDay() {
        for (ShippingBin shippingBin : allShippingBins) {
            double total = 0;
            for (BackPackableType item : shippingBin.items.keySet()) {
                total += shippingBin.items.get(item) * item.getPrice();
            }
            if (shippingBin.todayItemOwner == null)
                return;

            shippingBin.todayItemOwner.getBackPack().addCoin(
                    Math.floor(total * shippingBin.getType().getLeverage()));
            shippingBin.items = new HashMap<>();
            shippingBin.todayItemOwner = null;
        }
    }

    public void setItems(HashMap<BackPackableType, Integer> items) {
        this.items = items;
    }

    public Player getTodayItemOwner() {
        return todayItemOwner;
    }

    public void setTodayItemOwner(Player todayItemOwner) {
        this.todayItemOwner = todayItemOwner;
    }
}
