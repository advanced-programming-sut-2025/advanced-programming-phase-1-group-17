package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

import java.util.HashMap;

public class CraftArtisanRequest {
    private HashMap<BackpackableTypeDTO, Integer> selectedItems;
    private CraftingItemDTO craftingItemDTO;

    public CraftArtisanRequest() {
    }

    public CraftArtisanRequest(HashMap<BackpackableTypeDTO, Integer> selectedItems, CraftingItemDTO craftingItemDTO) {
        this.selectedItems = selectedItems;
        this.craftingItemDTO = craftingItemDTO;
    }

    public HashMap<BackpackableTypeDTO, Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(HashMap<BackpackableTypeDTO, Integer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public CraftingItemDTO getCraftingItemDTO() {
        return craftingItemDTO;
    }

    public void setCraftingItemDTO(CraftingItemDTO craftingItemDTO) {
        this.craftingItemDTO = craftingItemDTO;
    }
}
