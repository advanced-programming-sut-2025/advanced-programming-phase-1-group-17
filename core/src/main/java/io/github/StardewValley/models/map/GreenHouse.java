package io.github.StardewValley.models.map;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.Player;

import java.util.ArrayList;

public class GreenHouse implements Placeable {
    //TODO: handle sprinkler
    private boolean isActive;
    private GreenHouseFence fence;
    private int width;
    private int height;


    public GreenHouse(Player player, int width, int height) {
        this.fence = new GreenHouseFence();
        this.isActive = false;
        this.width = width;
        this.height = height;
        App.getCurrentGame().addGreenHouses(this);
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
    public Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseTexture();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
