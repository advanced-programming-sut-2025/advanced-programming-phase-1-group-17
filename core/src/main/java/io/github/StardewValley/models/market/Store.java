package io.github.StardewValley.models.market;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.Placeable;

import java.util.ArrayList;
public class Store implements Placeable {
    private StoreType type;

    public Store(StoreType type) {
        this.type = type;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }


    @Override
    public Texture getTexture() {
        //TODO
        return null;
    }
}
