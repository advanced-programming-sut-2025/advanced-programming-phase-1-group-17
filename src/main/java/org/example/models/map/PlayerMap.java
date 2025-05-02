package org.example.models.map;

import org.example.models.foraging.ForagingController;
import org.example.models.Player;
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
    private ArrayList<Lake> lakes = new ArrayList<>();
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
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = 0; i < length; i++) {
                for (int i1 = 0; i1 < width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 1) {
            this.row = 0;
            this.col = 50;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 50 + row; i++) {
                for (int i1 = col; i1 < 50 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 2) {
            this.row = 50;
            this.col = 0;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 50 + row; i++) {
                for (int i1 = col; i1 < 50 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 3) {
            this.row = 50;
            this.col = 50;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = length; i < 2 * length; i++) {
                for (int i1 = width; i1 < 2 * width; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
//            Player NPCOwner = new Player(new User("NPC","NPC","NPC","NPC",Gender.Male),true, App.getCurrentGame());
//            for (int i = 26 ; i <= 75 ; i++) {
//                for (int j = 101; j <= 150 ; j++) {
//                    Tile tile = new Tile(i , j ,NPCOwner);
//                }
//            }
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
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 1 + row; i < 12 + row; i++) {
                for (int j = 20 + col; j < 31 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(quarry);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 40 + row; i < 46 + row; i++) {
                for (int j = 30 + col; j < 36 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(0));
                    Tile.getTile(i, j).setWalkAble(false);
                    Tile.getTile(i, j).setWater(true);
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 9 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(greenHouse);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
        } else if (type == 2) {
            this.hut = new Hut();
            this.lakes.add(new Lake());
            this.lakes.add(new Lake());
            this.quarry = new Quarry();
            for (int i = 40 + row; i < 44 + row; i++) {
                for (int j = 40 + col; j < 44 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(hut);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 25 + row; i < 31 + row; i++) {
                for (int j = 35 + col; j < 41 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(quarry);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 30 + row; i < 36 + row; i++) {
                for (int j = 10 + col; j < 16 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(0));
                    Tile.getTile(i, j).setWalkAble(false);
                    Tile.getTile(i, j).setWater(true);
                }
            }
            for (int i = row + 1; i < 7 + row; i++) {
                for (int j = 25 + col; j < 31 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(1));
                    Tile.getTile(i, j).setWalkAble(false);
                    Tile.getTile(i, j).setWater(true);
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 9 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(greenHouse);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }

        }
        randomFillMap();
    }

    public int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void randomFillMap() {
        int numOfTrees = randomInt(8, 11);
        int numOfStones = randomInt(7, 10);
        int numOfForagings = randomInt(4, 10);
        while (numOfTrees != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 50 + col);
            Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                Tree tree = new Tree(false, ForagingController.getRandomTreeType(), false, tile);
                tile.setPlaceable(tree);
                tile.setWalkAble(false);
                numOfTrees--;
            }
        }
        while (numOfStones != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 50 + col);
            Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                Stone stone = new Stone();
                tile.setPlaceable(stone);
                tile.setWalkAble(false);
                numOfStones--;
            }
        }
        int counter = 0;
        while (numOfForagings != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 50 + col);
            Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                if (counter < 2)
                    ForagingController.setTreeForaging(tile);
                else if (counter < 4)
                    ForagingController.setCropForaging(tile);
                else if (counter < 6)
                    ForagingController.setSeedForaging(tile);
                else
                    ForagingController.setMineralForaging(tile);
                tile.setWalkAble(false);
                numOfForagings--;
                counter ++;
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

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(ArrayList<Lake> lakes) {
        this.lakes = lakes;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public void setQuarry(Quarry quarry) {
        this.quarry = quarry;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
