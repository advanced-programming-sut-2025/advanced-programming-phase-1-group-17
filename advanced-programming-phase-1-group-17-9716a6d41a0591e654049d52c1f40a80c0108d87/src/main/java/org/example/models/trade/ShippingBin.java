package org.example.models.market;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ShippingBin implements Placeable {
    //TODO: add shipping bins to map when generating map
    private static ArrayList<ShippingBin> allShippingBins = new ArrayList<>();
    private HashMap<BackPackableType, ArrayList<BackPackable>> items = new HashMap<>();
    private final ShippingBinType type;
    private final Player owner;

    public ShippingBin(ShippingBinType type, Player owner) {
        this.type = type;
        this.owner = owner;
        allShippingBins.add(this);
    }

    public HashMap<BackPackableType, ArrayList<BackPackable>> getItems() {
        return items;
    }

    public void setItems(HashMap<BackPackableType, ArrayList<BackPackable>> items) {
        this.items = items;
    }

    public ShippingBinType getType() {
        return type;
    }

    public static void goToNextDay() {
        for (ShippingBin shippingBin : allShippingBins) {
            double total = 0;
            for (BackPackableType item : shippingBin.items.keySet()) {
                total += shippingBin.items.get(item).size() * item.getPrice();
            }
            shippingBin.owner.getBackPack().addCoin(total);
        }
    }
}
