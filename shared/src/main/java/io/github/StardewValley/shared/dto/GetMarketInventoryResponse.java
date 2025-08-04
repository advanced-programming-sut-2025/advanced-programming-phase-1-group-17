package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.market.ShopItemDTO;
import io.github.StardewValley.shared.models.market.UpgradeServiceDTO;

import java.util.List;

public class GetMarketInventoryResponse {
    private List<ShopItemDTO> items;
    private List<UpgradeServiceDTO> upgradeServices;

    public GetMarketInventoryResponse() {
    }

    public GetMarketInventoryResponse(List<ShopItemDTO> items, List<UpgradeServiceDTO> upgradeServices) {
        this.items = items;
        this.upgradeServices = upgradeServices;
    }

    public List<ShopItemDTO> getItems() {
        return items;
    }

    public List<UpgradeServiceDTO> getUpgradeServices() {
        return upgradeServices;
    }
}
