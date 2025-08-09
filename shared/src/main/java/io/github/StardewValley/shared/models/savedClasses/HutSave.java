package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.map.Hut;

public class HutSave {
    private RefrigeratorSave refrigerator;
    private String texture;
    private int x;
    private int y;

    public HutSave() {
    }

    public HutSave(Hut hut) {
        this.refrigerator = new RefrigeratorSave(hut.getRefrigerator());
        this.texture = hut.getTexture();
        this.x = hut.getX();
        this.y = hut.getY();
    }

    public RefrigeratorSave getRefrigerator() {
        return refrigerator;
    }

    public String getTexture() {
        return texture;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
