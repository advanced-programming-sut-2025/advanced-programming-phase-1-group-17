package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.map.Tile;

public class TileDTO {
    public static int i = 9;
    private int x;
    private int y;
    private boolean crowImmunity = false;
    private String texture;
    private boolean isWalkAble = true;
    private String placeableType;
    private String ownerUsername;
    private boolean isPlowed = false;
    public TileDTO(){}

    public TileDTO(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.isWalkAble = tile.isWalkAble();
        this.isPlowed = tile.isPlowed();
        if (tile.getPlaceable() != null) texture = tile.getPlaceable().getTexture();
        this.placeableType = tile.getPlaceable() != null ? tile.getPlaceable().getClass().getSimpleName() : "s";
        this.ownerUsername = tile.getOwner() != null ? tile.getOwner().getUser().getUsername() : "s";
        this.crowImmunity = tile.isCrowImmunity();
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

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getPlaceableType() {
        return placeableType;
    }

    public void setPlaceableType(String placeableType) {
        this.placeableType = placeableType;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
