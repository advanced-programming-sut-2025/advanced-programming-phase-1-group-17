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

    //For voting
    private boolean paused;
    private String targetUsername;
    private VotingSession.VotingType type;

    public GameState() {
    }

    public GameState(List<CraftingItemDTO> craftingItems, List<TileDTO> tiles, LightningStateDTO lightningStateDTO,
                     TimeAndDateDTO timeAndDateDTO, boolean paused, String targetUsername, VotingSession.VotingType type) {
        this.craftingItems = craftingItems;
        this.tiles = tiles;
        this.lightningStateDTO = lightningStateDTO;
        this.timeAndDateDTO = timeAndDateDTO;

        //For Voting
        this.paused = paused;
        this.targetUsername = targetUsername;
        this.type = type;

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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public VotingSession.VotingType getType() {
        return type;
    }

    public void setType(VotingSession.VotingType type) {
        this.type = type;
    }
}
