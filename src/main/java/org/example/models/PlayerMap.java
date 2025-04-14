package org.example.models;

import org.example.models.Farm;

import java.util.ArrayList;

public class PlayerMap {
    private ArrayList<Tile> Tiles;
    private Farm farm;

    private Player player;
    public Player getPlayer () {
        return player;
    }

    public ArrayList<Tile> getTiles() {
        return Tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        Tiles = tiles;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
