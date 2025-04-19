package org.example.models.map;

import org.example.models.Foraging;
import org.example.models.Player;
import org.example.models.plant.Tree;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerMap {


    private static int  width = 50;
    private static int length = 50;
    private ArrayList<Tile> Tiles;
    private Farm farm;
    private Player player;
    private int type;

    public Player getPlayer() {
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
    public PlayerMap(int index, Player owner) {
        if (index == 0) {
            for (int i = 0; i < length; i++) {
                for (int i1 = 0; i1 < width; i1++) {
                    Tiles.add(new Tile(i +1, i1 +1, owner));
                }
            }
        }
        else if (index == 1) {
            for (int i = length; i < 2 * length; i++) {
                for (int i1 = 0; i1 < width; i1++) {
                    Tiles.add(new Tile(i +1, i1 +1, owner));
                }
            }
        }
        else if (index == 2) {
            for (int i = 0; i < length; i++) {
                for (int i1 = width; i1 < 2 * width; i1++) {
                    Tiles.add(new Tile(i +1, i1 +1, owner));
                }
            }
        }
        else if (index == 3) {
            for (int i = length; i < 2*length; i++) {
                for (int i1 = width; i1 < 2*width; i1++) {
                    Tiles.add(new Tile(i +1, i1 +1, owner));
                }
            }
        }
    }


    public void setMapType(int type,int index ) {
        int[] coordinateShifters = GameMap.coordinateShifter(index);
        if (type == 1) {

        } else if (type == 2) {

        } else if (type == 3) {

        } else if (type == 4) {

        }
        randomFillMap();
    }

    public int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void randomFillMap() {
        for (Tile tile : Tiles) {
            if (tile.getPlaceable() != null) {
                continue;
            }
            int random = randomInt(1, 100);
            if (random <= 10) {
                tile.setPlaceable(new Tree());
            } else if (random <= 20) {
                tile.setPlaceable(new Stone());
            } else if (random <= 30) {
                tile.setPlaceable(new Foraging());
            }
        }

        //TODO add trees and stones and foregings
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type > 0 && type < 5) {
            this.type = type;

        }
    }
    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }
}
