package io.github.StardewValley.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.Placeable;

public class Lake implements Placeable {
    private Texture texture = new Texture("lake.png");
    @Override
    public Texture getTexture() {
        return texture;
    }
}
