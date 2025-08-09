package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.market.ShippingBin;

import java.util.ArrayList;

public class ShippingBinSave {
    //TODO
    private ArrayList<BackPackable> items = new ArrayList<>();
    //TODo
    private Player todayItemOwner = null;

    private int tileX;
    private int tileY;

    public ShippingBinSave() {}

    public ShippingBinSave(ShippingBin shippingBin) {
        this.tileX = shippingBin.getTileX();
        this.tileY = shippingBin.getTileY();
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }
}
