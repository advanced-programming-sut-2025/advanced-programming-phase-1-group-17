package io.github.StardewValley.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.Placeable;

public class Quarry implements Placeable {
    private Texture texture = new Texture("assets/Rock/Quarry_Boulder.png");
    @Override
    public Texture getTexture() {
        return texture;
    }
}
