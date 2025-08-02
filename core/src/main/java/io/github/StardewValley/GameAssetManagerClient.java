package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManagerClient {
    private static GameAssetManagerClient gameAssetManager;
    private final Texture  plowedTexture =new Texture("Flooring/Flooring_57.png");

    private Skin skin =  new Skin(Gdx.files.internal("skin/StardewSkin.json"));

    public static GameAssetManagerClient getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManagerClient();
        }
        return gameAssetManager;
    }


    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Texture getPlowedTexture() {
        return plowedTexture;
    }

}
