package io.github.StardewValley.shared.models.market;

import io.github.StardewValley.shared.models.enums.Season;

import java.util.ArrayList;
import java.util.List;

public class StoreInventory {
    private StoreType storeType;
    private final List<ShopItem> items = new ArrayList<>();
    private final List<UpgradeService> upgrades = new ArrayList<>();

    public StoreInventory() {
    }

    public StoreInventory(StoreType storeType) {
        this.storeType = storeType;
    }

    public void addItem(ShopItem item) {
        items.add(item);
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

    public List<ShopItemDTO> getItemDTOs(Season season, StoreType storeType) {
        List<ShopItemDTO> itemDTOs = new ArrayList<>();
        for (ShopItem item : items) {
            ShopItemDTO shopItemDTO = new ShopItemDTO(item, season);
            if (storeType.equals(StoreType.PierresGeneralStore))
                shopItemDTO.setPrice(MarketsController.getSeasonalPrice(item, season));
            itemDTOs.add(shopItemDTO);
        }
        return itemDTOs;
    }

    public List<UpgradeServiceDTO> getUpgradeServiceDTOs() {
        List<UpgradeServiceDTO> upgradeServiceDTOs = new ArrayList<>();
        for (UpgradeService upgradeService : upgrades) {
            upgradeServiceDTOs.add(UpgradeService.getUpgradeServiceDTO(upgradeService));
        }
        return upgradeServiceDTOs;
    }

    public List<UpgradeService> getUpgradeServices() {
        return upgrades;
    }

    public List<UpgradeService> getUpgrades() {
        return upgrades;
    }
}
