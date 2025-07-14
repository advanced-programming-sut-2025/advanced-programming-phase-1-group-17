package io.github.StardewValley.models.map;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.cooking.Refrigerator;

import java.util.ArrayList;

public class Hut implements Placeable {
    private Refrigerator refrigerator = new Refrigerator();
    private Texture texture;
    public Hut(Texture texture) {
        this.texture = texture;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
