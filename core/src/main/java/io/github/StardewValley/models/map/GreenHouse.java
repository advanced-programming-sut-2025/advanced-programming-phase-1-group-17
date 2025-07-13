package io.github.StardewValley.models.map;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.Player;

import java.util.ArrayList;

public class GreenHouse implements Placeable {
    //TODO: handle sprinkler
    private static ArrayList<GreenHouse> greenHouse = new ArrayList<GreenHouse>();
    private boolean isActive;
    private GreenHouseFence fence;


    public GreenHouse(Player player) {
        this.fence = new GreenHouseFence();
        this.isActive = false;
        greenHouse.add(this);
        App.getCurrentGame().addGreenHouses(this);
    }

    public static ArrayList<GreenHouse> getGreenHouse() {
        return greenHouse;
    }

    public static void setGreenHouse(ArrayList<GreenHouse> greenHouse) {
        GreenHouse.greenHouse = greenHouse;
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
        String greenHouseImage = GameAssetManager.getGameAssetManager().getGreenHouseTexture();
        return new Texture(greenHouseImage);
    }
}
