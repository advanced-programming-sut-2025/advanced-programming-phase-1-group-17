package org.example.models.map;

import com.sun.source.tree.WhileLoopTree;
import org.example.models.App;
import org.example.models.Foraging;
import org.example.models.Player;
import org.example.models.enums.StoreType;
import org.example.models.plant.Tree;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerMap {


    private static int width = 50;
    private static int length = 50;
    private int row;
    private int col;
    private ArrayList<Tile> Tiles = new ArrayList<>();
    private Farm farm;
    private Player player;
    private int type;
    private GreenHouse greenHouse;
    private Hut hut;
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Foraging> foragings = new ArrayList<>();
    private ArrayList<Lake> lakes = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    private Quarry quarry;


    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }

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
            this.row = 0;
            this.col = 0;
            for (int i = 0; i < length; i++) {
                for (int i1 = 0; i1 < width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 1) {
            this.row = 0;
            this.col = 50;
            for (int i = length; i < 2 * length; i++) {
                for (int i1 = 0; i1 < width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 2) {
            this.row = 50;
            this.col = 0;
            for (int i = 0; i < length; i++) {
                for (int i1 = width; i1 < 2 * width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 3) {
            this.row = 50;
            this.col = 50;
            for (int i = length; i < 2 * length; i++) {
                for (int i1 = width; i1 < 2 * width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        }
    }


    public void setMapType(int type) {
        if (type == 1) {
            this.hut = new Hut();
            this.lakes.add(new Lake());
            this.quarry = new Quarry();
            this.greenHouse = new GreenHouse(this.player);
            for (int i = 4 + row; i < 8 + row; i++) {
                for (int j = 4 + col; j < 8 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(hut);
                }
            }
            for (int i = 20 + row; i < 31 + row; i++) {
                for (int j = 20 + col; j < 31 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(quarry);
                }
            }
            for (int i = 30 + row; i < 36 + row; i++) {
                for (int j = 10 + col; j < 16 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(0));
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 9 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(greenHouse);
                }
            }
        } else if (type == 2) {
            this.hut = new Hut();
            this.lakes.add(new Lake());
            this.lakes.add(new Lake());
            this.quarry = new Quarry();
            for (int i = 4 + row; i < 8 + row; i++) {
                for (int j = 4 + col; j < 8 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(hut);
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 35 + col; j < 41 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(quarry);
                }
            }
            for (int i = 30 + row; i < 36 + row; i++) {
                for (int j = 10 + col; j < 16 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(0));
                }
            }
            for (int i = 25 + row; i < 31 + row; i++) {
                for (int j = 25 + col; j < 31 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(1));
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 9 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(greenHouse);
                }
            }

        }
        randomFillMap();
    }

    public int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void randomFillMap() {
        int numOfTrees = randomInt(8 , 11);
        int numOfStones = randomInt(3 , 7);
        int numOfForagings = randomInt(2 , 6);
        while (numOfTrees != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 50 + col );
            if (Tile.getTile(randomIndex_x, randomIndex_y).getPlaceable() == null) {
                Tree tree = new Tree();
                trees.add(tree);
                Tile.getTile(randomIndex_x, randomIndex_y).setPlaceable(tree);
                numOfTrees--;
            }
        }
        while (numOfStones != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 50 + col);
            if (Tile.getTile(randomIndex_x, randomIndex_y).getPlaceable() == null) {
                Stone stone = new Stone();
                stones.add(stone);
                Tile.getTile(randomIndex_x, randomIndex_y).setPlaceable(stone);
                numOfStones--;
            }
        }
        while (numOfForagings != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row );
            int randomIndex_y = randomInt(1 + col, 50 + col);
            if (Tile.getTile(randomIndex_x, randomIndex_y).getPlaceable() == null) {
                Foraging foraging = new Foraging();
                foragings.add(foraging);
                Tile.getTile(randomIndex_x, randomIndex_y).setPlaceable(foraging);
                numOfForagings--;
            }
        }
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

    public static void setWidth(int width) {
        PlayerMap.width = width;
    }

    public static void setLength(int length) {
        PlayerMap.length = length;
    }

    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public ArrayList<Foraging> getForagings() {
        return foragings;
    }

    public void setForagings(ArrayList<Foraging> foragings) {
        this.foragings = foragings;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(ArrayList<Lake> lakes) {
        this.lakes = lakes;
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    public void setStones(ArrayList<Stone> stones) {
        this.stones = stones;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public void setQuarry(Quarry quarry) {
        this.quarry = quarry;
    }
}
