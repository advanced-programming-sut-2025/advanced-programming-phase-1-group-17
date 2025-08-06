package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

import java.util.HashMap;
import java.util.List;

public class GetBackpackItemsResponse {
    private List<BackpackableTypeDTO> items;

    public GetBackpackItemsResponse() {
    }

    public GetBackpackItemsResponse(List<BackpackableTypeDTO> items) {
        this.items = items;
    }

    public List<BackpackableTypeDTO> getItems() {
        return items;
    }

    public void setItems(List<BackpackableTypeDTO> items) {
        this.items = items;
    }
}
