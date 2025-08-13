package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.map.Tile;

public class TileSave {
    private int x;
    private int y;
    private PlaceableSave placeableSave;
    private boolean isWalkAble;
    private boolean isPlowed;
    private String owner;
    private NPCSave npcIsHere;
    private boolean crowImmunity;

    public TileSave() {}

    public TileSave(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.owner = tile.getOwner().getUser().getUsername();
        this.placeableSave = tile.getPlaceable() == null ? null : tile.getPlaceable().toDTO(x, y ,owner);
        this.isWalkAble = tile.isWalkAble();
        this.isPlowed = tile.isPlowed();
        this.npcIsHere = (tile.getNpcIsHere() == null) ? null : new NPCSave(tile.getNpcIsHere());
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

    public PlaceableSave getPlaceableSave() {
        return placeableSave;
    }

    public void setPlaceableSave(PlaceableSave placeableSave) {
        this.placeableSave = placeableSave;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public NPCSave getNpcIsHere() {
        return npcIsHere;
    }

    public void setNpcIsHere(NPCSave npcIsHere) {
        this.npcIsHere = npcIsHere;
    }

    public boolean isCrowImmunity() {
        return crowImmunity;
    }

    public void setCrowImmunity(boolean crowImmunity) {
        this.crowImmunity = crowImmunity;
    }
}
