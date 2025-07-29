package io.github.StardewValley.shared.models.greenhouse;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;

public class GreenHouse implements Placeable {
    //TODO: handle sprinkler
    private boolean isActive;
    private GreenHouseFence fence;
    private GreenHouseLake lake;
    private int width;
    private int height;
    private int starting_x;
    private int starting_y;


    public GreenHouse(Player player, int width, int height, int starting_x, int starting_y, GreenHouseLake lake) {
        this.fence = new GreenHouseFence();
        this.isActive = false;
        this.width = width;
        this.height = height;
        this.starting_x = starting_x;
        this.starting_y = starting_y;
        this.lake = lake;
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
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getGreenHouseTexture();
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
}
