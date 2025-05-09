package org.example.models.map;

import org.example.models.NPCS.NPC;
import org.example.models.Placeable;
import org.example.models.Player;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private Placeable placeable;
    private boolean isWater=false;
    private boolean isWalkAble = true;
    private boolean isPlowed = false;
    private Player owner;
    private Player whoIsHere;
    private NPC npcIsHere;
    private static ArrayList<Tile> tiles = new ArrayList<Tile>() ;
    private boolean isLightninged = false;

    public Tile(int x, int y, Player owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        tiles.add(this);
    }

    public static ArrayList<Tile> getTiles() {
        return tiles;
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }

    public static Tile getTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    public Player getWhoIsHere() {
        return whoIsHere;
    }

    public void setWhoIsHere(Player whoIsHere) {
        this.whoIsHere = whoIsHere;
    }
    public boolean isLightninged() {
        return isLightninged;
    }

    public void setLightninged(boolean lightninged) {
        isLightninged = lightninged;
    }

    public NPC getNpcIsHere() {
        return npcIsHere;
    }

    public void setNpcIsHere(NPC npcIsHere) {
        this.npcIsHere = npcIsHere;
    }
}
