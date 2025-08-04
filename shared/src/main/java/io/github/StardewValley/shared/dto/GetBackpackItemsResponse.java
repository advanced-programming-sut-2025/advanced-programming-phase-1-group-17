package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

import java.util.HashMap;

public class GetBackpackItemsResponse {
    private HashMap<BackpackableTypeDTO, Integer> items;

    public GetBackpackItemsResponse() {
    }

    public GetBackpackItemsResponse(HashMap<BackpackableTypeDTO, Integer> items) {
        this.items = items;
    }

    public HashMap<BackpackableTypeDTO, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<BackpackableTypeDTO, Integer> items) {
        this.items = items;
    }
}
