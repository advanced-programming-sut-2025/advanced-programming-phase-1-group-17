package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.market.ShippingBin;

import java.util.ArrayList;

public class ShippingBinSave {
    private ArrayList<BackPackableSave> items = new ArrayList<>();
    private String todayItemOwner;
    private int tileX;
    private int tileY;

    public ShippingBinSave() {}

    public ShippingBinSave(ShippingBin shippingBin) {
        shippingBin.getItems().forEach((backPackable) -> {
            this.items.add(backPackable.toBackpackableSave());
        });
        this.todayItemOwner = (shippingBin.getTodayItemOwner() == null) ? null : shippingBin.getTodayItemOwner().getUser().getUsername();
        this.tileX = shippingBin.getTileX();
        this.tileY = shippingBin.getTileY();
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public ArrayList<BackPackableSave> getItems() {
        return items;
    }

    public void setItems(ArrayList<BackPackableSave> items) {
        this.items = items;
    }

    public String getTodayItemOwner() {
        return todayItemOwner;
    }

    public void setTodayItemOwner(String todayItemOwner) {
        this.todayItemOwner = todayItemOwner;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }


}
