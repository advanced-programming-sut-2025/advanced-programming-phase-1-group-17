package io.github.StardewValley.shared.models.map;

import com.badlogic.gdx.graphics.Texture;

public class Quarry implements Placeable {
    private Texture texture = new Texture("assets/Rock/Quarry_Boulder.png");
    @Override
    public Texture getTexture() {
        return texture;
    }
}
