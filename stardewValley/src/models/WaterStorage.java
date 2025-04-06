package models;

import java.util.ArrayList;

public class WaterStorage {
    private ArrayList<Tile> tile = new ArrayList<>();
    private double capacity;

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Tile> getTile() {
        return tile;
    }

    public void setTile(ArrayList<Tile> tile) {
        this.tile = tile;
    }
}
