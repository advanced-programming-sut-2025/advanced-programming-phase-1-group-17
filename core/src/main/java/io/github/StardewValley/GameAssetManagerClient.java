package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

public class GameAssetManagerClient {
    private static GameAssetManagerClient gameAssetManager;
    private final Texture plowedTexture = new Texture("Flooring/Flooring_57.png");
    private final HashMap<String, Texture> textures = new HashMap<>();

    private Skin skin = new Skin(Gdx.files.internal("Skin/StardewSkin.json"));

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

    public Texture getTexture(String key) {
        if (textures.containsKey(key)) return textures.get(key);
        else {
            try {
                Texture texture = new Texture(key);
                textures.put(key, texture);
                return texture;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
