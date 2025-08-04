package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.market.ShopItemDTO;
import io.github.StardewValley.shared.models.market.StoreType;

public class PurchaseRequest {
    ShopItemDTO shopItemDTO;
    int count;
    StoreType storeType;

    public PurchaseRequest() {
    }

    public PurchaseRequest(ShopItemDTO shopItemDTO, int count, StoreType storeType) {
        this.shopItemDTO = shopItemDTO;
        this.count = count;
        this.storeType = storeType;
    }

    public ShopItemDTO getShopItemDTO() {
        return shopItemDTO;
    }

    public void setShopItemDTO(ShopItemDTO shopItemDTO) {
        this.shopItemDTO = shopItemDTO;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }
}
