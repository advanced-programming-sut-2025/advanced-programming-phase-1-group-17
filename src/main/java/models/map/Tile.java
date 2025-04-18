package models.map;

import models.Placeable;
import models.Player;

public class Tile {
    private int x;
    private int y;
    private Placeable placeable;
    private boolean isWalkAble;
    private boolean isPlowed = false;
    private Player owner;


    public Tile(int x, int y, Player owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }
    public void plant(String plantName) {
        isPlowed = false;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Placeable getPlaceable() {
        return placeable;
    }

    public void setPlaceable(Placeable placeable) {
        this.placeable = placeable;
    }
}
