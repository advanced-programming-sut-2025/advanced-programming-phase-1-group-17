package io.github.StardewValley.shared.models.game;

import io.github.StardewValley.shared.TimeAndDateDTO;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.dto.LightningStateDTO;
import io.github.StardewValley.shared.models.TileDTO;

import java.util.List;

public class GameState {
    private List<CraftingItemDTO> craftingItems;
    private List<TileDTO> tiles;
    private LightningStateDTO lightningStateDTO;
    TimeAndDateDTO timeAndDateDTO;

    public GameState() {
    }

    public GameState(List<CraftingItemDTO> craftingItems, List<TileDTO> tiles, LightningStateDTO lightningStateDTO,
                     TimeAndDateDTO timeAndDateDTO) {
        this.craftingItems = craftingItems;
        this.tiles = tiles;
        this.lightningStateDTO = lightningStateDTO;
        this.timeAndDateDTO = timeAndDateDTO;
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

    public TimeAndDateDTO getTimeAndDateDTO() {
        return timeAndDateDTO;
    }

    public void setTimeAndDateDTO(TimeAndDateDTO timeAndDateDTO) {
        this.timeAndDateDTO = timeAndDateDTO;
    }
}
