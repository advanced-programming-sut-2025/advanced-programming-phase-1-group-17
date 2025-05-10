package org.example.models.market;

import java.util.ArrayList;
import java.util.List;

public class StoreInventory {
    private final StoreType storeType;
    private final List<ShopItem> items = new ArrayList<>();
    private final List<AnimalItem> animals = new ArrayList<>();
    private final List<UpgradeService> upgrades = new ArrayList<>();

    public StoreInventory(StoreType storeType) {
        this.storeType = storeType;
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }

    public void addAnimal(AnimalItem animal) {
        animals.add(animal);
    }

    public void addUpgradeService(UpgradeService upgradeService) {
        upgrades.add(upgradeService);
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public List<AnimalItem> getAnimals() {
        return animals;
    }

    public List<UpgradeService> getUpgradeServices() {
        return upgrades;
    }
}
