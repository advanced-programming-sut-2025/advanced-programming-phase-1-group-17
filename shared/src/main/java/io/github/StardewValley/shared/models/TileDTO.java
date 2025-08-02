package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.map.Placeable;

public class TileDTO {
    private int x;
    private int y;
    private boolean crowImmunity = false;
    private Placeable placeable;
    private boolean isWalkAble = true;
    private boolean isPlowed = false;

    TileDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCrowImmunity() {
        return crowImmunity;
    }

    public void setCrowImmunity(boolean crowImmunity) {
        this.crowImmunity = crowImmunity;
    }

    public Placeable getPlaceable() {
        return placeable;
    }

    public void setPlaceable(Placeable placeable) {
        this.placeable = placeable;
    }

    public boolean isWalkAble() {
        return isWalkAble;
    }

    public void setWalkAble(boolean walkAble) {
        isWalkAble = walkAble;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }
}
