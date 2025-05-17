package org.example.models.market;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.animal.AnimalProduct;
import org.example.models.animal.AnimalProductType;
import org.example.models.enums.FishType;
import org.example.models.plant.Crop;
import org.example.models.plant.CropType;
import org.example.models.plant.Fruit;

import java.util.ArrayList;
import java.util.HashMap;

public class ShippingBin implements Placeable {
    private static ArrayList<ShippingBin> allShippingBins = new ArrayList<>();
    private ArrayList<BackPackable> items = new ArrayList<>();
    //only one player can have items in a shipping bin each day
    private Player todayItemOwner = null;

    public ShippingBin() {
        allShippingBins.add(this);
    }

    public void addItem(BackPackable backPackable) {
        items.add(backPackable);
    }

    public static void goToNextDay() {
        for (ShippingBin shippingBin : allShippingBins) {
            double total = 0;
            for (BackPackable item : shippingBin.items) {
                if (item.getClass().equals(Crop.class)) {
                    ItemQuality quality = ((Crop)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(AnimalProduct.class)) {
                    ItemQuality quality = ((AnimalProduct)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(Fish.class)) {
                    ItemQuality quality = ((Fish)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(Fruit.class)) {
                    ItemQuality quality = ((Fruit)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else
                    total += item.getPrice();
            }
            if (shippingBin.todayItemOwner == null)
                continue;

            shippingBin.todayItemOwner.getBackPack().addCoin(
                    Math.floor(total));
            shippingBin.items = new ArrayList<>();
            shippingBin.todayItemOwner = null;
        }
    }

    public Player getTodayItemOwner() {
        return todayItemOwner;
    }

    public void setTodayItemOwner(Player todayItemOwner) {
        this.todayItemOwner = todayItemOwner;
    }
}
