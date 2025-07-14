package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.models.enums.BackPackType;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));

    private final Texture backgroundTexture = new Texture("Flooring/Flooring_28.png");
    private final Texture farmTexture = new Texture("Flooring/Flooring_14.png");

    //For GreenHouse
    private final Texture lakeTexture = new Texture("Flooring/Flooring_37.png");
    private final Texture greenHouseFenceTexture = new Texture("Fence/Hardwood_Fence.png");
    private final Texture greenHouseTexture = new Texture("Greenhouse/greenhouse.png");

    //For backpack
    private final Texture backPackTexture = new Texture("Tools/36_Backpack.png");

    private GameAssetManager() {

    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getGreenHouseTexture() {
        return greenHouseTexture;
    }

    public Texture getFarmTexture() {
        return farmTexture;
    }

    public Texture getGreenHouseFenceTexture() {
        return greenHouseFenceTexture;
    }

    public Texture getLakeTexture() {
        return lakeTexture;
    }

    public Texture getBackPackTexture() {
        return backPackTexture;
    }
}
