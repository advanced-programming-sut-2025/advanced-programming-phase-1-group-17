package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.dto.LightningStateDTO;
import io.github.StardewValley.shared.models.TimeAndDate;
import io.github.StardewValley.shared.models.game.Game;

import java.util.ArrayList;
import java.util.List;

public class FullGameDTO {
    private List<TileSave> tiles = new ArrayList<>();
    private List<NPCSave> NPCs = new ArrayList<>();
    private List<NPCSave> NPCHuts = new ArrayList<>();
    private MarketsControllerSave marketsControllerSave;
    private TimeAndDate timeAndDate;
    private List<PlayerSave> playerSaves = new ArrayList<>();
    private String creatorUsername;
    private LightningStateDTO lightningStateDTO;

    public FullGameDTO() {}

    public FullGameDTO(Game game) {
        game.getTiles().forEach((tile) -> this.tiles.add(new TileSave(tile)));
        game.getNPCs().forEach((npc -> this.NPCs.add(new NPCSave(npc))));
        game.getNPCHuts().forEach((npc -> this.NPCHuts.add(new NPCSave(npc))));
        this.marketsControllerSave = new MarketsControllerSave(game.getMarketsController());
        this.timeAndDate = game.getDate();
        game.getPlayers().forEach((player -> this.playerSaves.add(new PlayerSave(player))));
        this.creatorUsername = game.getCreator().getUser().getUsername();
        this.lightningStateDTO = game.getLightningLogicController().getLightningStateDTO();
    }

    public List<TileSave> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileSave> tiles) {
        this.tiles = tiles;
    }

    public List<NPCSave> getNPCs() {
        return NPCs;
    }

    public void setNPCs(List<NPCSave> NPCs) {
        this.NPCs = NPCs;
    }

    public List<NPCSave> getNPCHuts() {
        return NPCHuts;
    }

    public void setNPCHuts(List<NPCSave> NPCHuts) {
        this.NPCHuts = NPCHuts;
    }

    public MarketsControllerSave getMarketsControllerSave() {
        return marketsControllerSave;
    }

    public void setMarketsControllerSave(MarketsControllerSave marketsControllerSave) {
        this.marketsControllerSave = marketsControllerSave;
    }

    public TimeAndDate getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(TimeAndDate timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public List<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }

    public void setPlayerSaves(List<PlayerSave> playerSaves) {
        this.playerSaves = playerSaves;
    }

    public LightningStateDTO getLightningStateDTO() {
        return lightningStateDTO;
    }

    public void setLightningStateDTO(LightningStateDTO lightningStateDTO) {
        this.lightningStateDTO = lightningStateDTO;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
