package io.github.StardewValley.shared.models.greenhouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.saveClasses.GreenHouseSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class GreenHouse implements Placeable {
    //TODO: handle sprinkler
    private boolean isActive;
    private GreenHouseFence fence;
    private GreenHouseLake lake;
    private int width;
    private int height;
    private int starting_x;
    private int starting_y;
    @JsonIgnore
    private Player owner;


    public GreenHouse(Player owner, int width, int height, int starting_x, int starting_y, GreenHouseLake lake, Game game) {
        this.fence = new GreenHouseFence();
        this.isActive = false;
        this.width = width;
        this.height = height;
        this.starting_x = starting_x;
        this.starting_y = starting_y;
        this.lake = lake;
        this.owner = owner;
        game.addGreenHouses(this);
    }

    public GreenHouse(PlaceableSave dto) {
        GreenHouseSave save = dto.getGreenHouseSave();

        this.isActive = save.isActive();
        this.fence = new GreenHouseFence();
        //TODO see the logic in PlayerMap
        this.lake = new GreenHouseLake();
        this.width = save.getWidth();
        this.height = save.getHeight();
        this.starting_x = save.getStarting_x();
        this.starting_y = save.getStarting_y();

        //TODO
        //this.owner
    }

    public GreenHouseFence getFence() {
        return fence;
    }

    public void setFence(GreenHouseFence fence) {
        this.fence = fence;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseTexture();
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(GreenHouse.class.getSimpleName());
        placeableSave.setGreenHouseSave(new GreenHouseSave(this));
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return new GreenHouse(dto);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStarting_x() {
        return starting_x;
    }

    public int getStarting_y() {
        return starting_y;
    }

    public GreenHouseLake getLake() {
        return lake;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
