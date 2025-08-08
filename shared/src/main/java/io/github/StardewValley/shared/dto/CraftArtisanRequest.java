package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

import java.util.ArrayList;

public class CraftArtisanRequest {
    private ArrayList<BackpackableTypeDTO> selectedItems;
    private CraftingItemDTO craftingItemDTO;

    public CraftArtisanRequest() {
    }

    public CraftArtisanRequest(ArrayList<BackpackableTypeDTO> selectedItems, CraftingItemDTO craftingItemDTO) {
        this.selectedItems = selectedItems;
        this.craftingItemDTO = craftingItemDTO;
    }

    public ArrayList<BackpackableTypeDTO> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<BackpackableTypeDTO> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public CraftingItemDTO getCraftingItemDTO() {
        return craftingItemDTO;
    }

    public void setCraftingItemDTO(CraftingItemDTO craftingItemDTO) {
        this.craftingItemDTO = craftingItemDTO;
    }
}
