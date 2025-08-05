package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.TileDTO;

import java.util.ArrayList;
import java.util.List;

public class GetGameStateResponse {
    private List<CraftingItemDTO> craftingItems;
    private List<TileDTO> tiles;
    private LightningStateDTO lightningStateDTO;

    public GetGameStateResponse() {
    }

    public GetGameStateResponse(List<CraftingItemDTO> craftingItems, List<TileDTO> tiles, LightningStateDTO lightningStateDTO) {
        this.craftingItems = craftingItems;
        this.tiles = tiles;
        this.lightningStateDTO = lightningStateDTO;
    }

    public List<CraftingItemDTO> getCraftingItems() {
        return craftingItems;
    }

    public void setCraftingItems(List<CraftingItemDTO> craftingItems) {
        this.craftingItems = craftingItems;
    }

    public List<TileDTO> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileDTO> tiles) {
        this.tiles = tiles;
    }

    public LightningStateDTO getLightningStateDTO() {
        return lightningStateDTO;
    }

    public void setLightningStateDTO(LightningStateDTO lightningStateDTO) {
        this.lightningStateDTO = lightningStateDTO;
    }
}
