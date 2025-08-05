package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

public class SellItemRequest {
    private TileDTO shippingBinTile;
    private int quantity;
    private BackpackableTypeDTO item;

    public SellItemRequest() {
    }

    public SellItemRequest(TileDTO shippingBinTile, int quantity, BackpackableTypeDTO item) {
        this.shippingBinTile = shippingBinTile;
        this.quantity = quantity;
        this.item = item;
    }

    public TileDTO getShippingBinTile() {
        return shippingBinTile;
    }

    public void setShippingBinTile(TileDTO shippingBinTile) {
        this.shippingBinTile = shippingBinTile;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BackpackableTypeDTO getItem() {
        return item;
    }

    public void setItem(BackpackableTypeDTO item) {
        this.item = item;
    }
}
