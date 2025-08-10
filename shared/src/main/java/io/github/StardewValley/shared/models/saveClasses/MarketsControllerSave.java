package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.market.MarketsController;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreInventory;

import java.util.ArrayList;
import java.util.List;

public class MarketsControllerSave {
    private List<StoreInventory> shopInventories = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();

    public MarketsControllerSave() {}

    public MarketsControllerSave(MarketsController marketsController) {
        marketsController.getShopInventories().forEach(((storeType, storeInventory) ->
            this.shopInventories.add(storeInventory)));
        marketsController.getStores().forEach(((storeType, store) ->
            this.stores.add(store)));
    }

    public List<StoreInventory> getShopInventories() {
        return shopInventories;
    }

    public void setShopInventories(List<StoreInventory> shopInventories) {
        this.shopInventories = shopInventories;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
