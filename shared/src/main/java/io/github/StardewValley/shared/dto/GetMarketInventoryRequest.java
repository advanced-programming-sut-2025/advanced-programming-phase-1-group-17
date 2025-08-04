package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.market.StoreType;

public class GetMarketInventoryRequest {
    private StoreType storeType;

    public GetMarketInventoryRequest() {
    }

    public GetMarketInventoryRequest(StoreType storeType) {
        this.storeType = storeType;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }
}
