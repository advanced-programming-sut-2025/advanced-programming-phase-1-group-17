package io.github.StardewValley.shared.models.map;

import com.badlogic.gdx.graphics.Texture;

public class Quarry implements Placeable {
    private String texture = "assets/Rock/Quarry_Boulder.png";
    @Override
    public String getTexture() {
        return texture;
    }
}
