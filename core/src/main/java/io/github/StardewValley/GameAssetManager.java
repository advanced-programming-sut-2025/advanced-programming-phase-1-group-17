package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
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

    public String getBackgroundTexture() {
        return "Flooring/Flooring_28.png";
    }
    public String getTreeTexture() {
        return "tree.png";
    }
    public String getTree2Texture() {
        return "tree2.png";
    }
}
