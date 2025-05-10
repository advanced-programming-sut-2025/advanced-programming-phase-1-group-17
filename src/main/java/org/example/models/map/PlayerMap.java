package org.example.models.map;

import org.example.models.foraging.ForagingController;
import org.example.models.App;
import org.example.models.NPCS.*;
import org.example.models.Player;
import org.example.models.plant.Tree;
import org.example.models.market.ShippingBin;
import org.example.models.market.ShippingBinType;
import org.example.models.market.Store;
import org.example.models.market.StoreType;

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
            for (int i = 0; i < 50; i++) {
                for (int i1 = 0; i1 < 100; i1++) {
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
            this.col = 100;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 50 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 2) {
            this.row = 150;
            this.col = 0;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 50 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 3) {
            this.row = 150;
            this.col = 100;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 50 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    Tiles.add(tile);
                    //App.getCurrentGame().addTile(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Tile.getTile(row + 1, col + 1).setWhoIsHere(owner);
        } else if (index == 4) {
            // NPC FORM
            for (int i = 51; i <= 150; i++) {
                for (int j = 1; j <= 200; j++) {
                    Tile tile = new Tile(i, j, NPC.getFatherPlayer());
                    Tiles.add(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
            Abigail abigail = (Abigail) App.getCurrentGame().getNPCs().get(0);
            Harvey harvey = (Harvey) App.getCurrentGame().getNPCs().get(1);
            Lia lia = (Lia) App.getCurrentGame().getNPCs().get(2);
            Robin robin = (Robin) App.getCurrentGame().getNPCs().get(3);
            Sebastian sebastian = (Sebastian) App.getCurrentGame().getNPCs().get(4);
            for (int k = 0; k < 121; k += 30) {
                for (int i = 91; i <= 100; i++) {
                    for (int j = 31 + k; j <= 40 + k; j++) {
                        if (k == 0) {
                            Tile.getTile(i, j).setPlaceable(abigail);
                        } else if (k == 30) {
                            Tile.getTile(i, j).setPlaceable(harvey);
                        } else if (k == 60) {
                            Tile.getTile(i, j).setPlaceable(lia);
                        } else if (k == 90) {
                            Tile.getTile(i, j).setPlaceable(robin);
                        } else {
                            Tile.getTile(i, j).setPlaceable(sebastian);
                        }
                    }
                }
            }
            out:
            for (int k = 0; k < 121; k += 30) {
                for (int i = 88; i < 103; i++) {
                    for (int j = 28 + k; j < 42 + k; j++) {
                        if (Tile.getTile(i, j).getPlaceable() == null && Tile.getTile(i, j).getWhoIsHere() == null
                                && Tile.getTile(i, j).isWalkAble()) {
                            if (k == 0) {
                                abigail.setX(i);
                                abigail.setY(j);
                                Tile.getTile(i, j).setPlaceable(abigail);
                                Tile.getTile(i, j).setNpcIsHere(abigail);
                                continue out;
                            } else if (k == 30) {
                                harvey.setX(i);
                                harvey.setY(j);
                                Tile.getTile(i, j).setPlaceable(harvey);
                                Tile.getTile(i, j).setNpcIsHere(harvey);
                                continue out;
                            } else if (k == 60) {
                                lia.setX(i);
                                lia.setY(j);
                                Tile.getTile(i, j).setPlaceable(lia);
                                Tile.getTile(i, j).setNpcIsHere(lia);
                                continue out;
                            } else if (k == 90) {
                                robin.setX(i);
                                robin.setY(j);
                                Tile.getTile(i, j).setPlaceable(robin);
                                Tile.getTile(i, j).setNpcIsHere(robin);
                                continue out;
                            } else {
                                sebastian.setX(i);
                                sebastian.setY(j);
                                Tile.getTile(i, j).setPlaceable(sebastian);
                                Tile.getTile(i, j).setNpcIsHere(sebastian);
                                continue out;
                            }
                        }
                    }

                }
            }

            //Creating Stores
            createStores();
            createShippingBins();

            //TREE FORAGING STONES
            int numOfTrees = randomInt(15, 25);
            int numOfStones = randomInt(10, 20);
            int numOfForagings = randomInt(10, 20);
            while (numOfTrees != 0) {
                int randomIndex_x = randomInt(51, 151);
                int randomIndex_y = randomInt(1, 200);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(false, ForagingController.getRandomTreeType(), false, tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees--;
                }
            }
            while (numOfStones != 0) {
                int randomIndex_x = randomInt(51, 151);
                int randomIndex_y = randomInt(1, 200);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
//TODO: Stone generating
                    //                    Stone stone = new Stone();
//                    tile.setPlaceable(stone);
//                    tile.setWalkAble(false);
                    numOfStones--;
                }
            }
            int counter = 0;
            while (numOfForagings != 0) {
                int randomIndex_x = randomInt(51, 151);
                int randomIndex_y = randomInt(1, 200);
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
                    counter++;
                }
            }
        }
    }

    private void createShippingBins() {
        Tile.getTile(30, 30).setPlaceable(new ShippingBin(ShippingBinType.Regular));
        Tile.getTile(30, 130).setPlaceable(new ShippingBin(ShippingBinType.Regular));
        Tile.getTile(180, 30).setPlaceable(new ShippingBin(ShippingBinType.Regular));
        Tile.getTile(180, 130).setPlaceable(new ShippingBin(ShippingBinType.Regular));

        Tile.getTile(70, 50).setPlaceable(new ShippingBin(ShippingBinType.Silver));
        Tile.getTile(70, 150).setPlaceable(new ShippingBin(ShippingBinType.Gold));
        Tile.getTile(100, 105).setPlaceable(new ShippingBin(ShippingBinType.Iridium));
        Tile.getTile(120, 50).setPlaceable(new ShippingBin(ShippingBinType.Gold));
        Tile.getTile(120, 150).setPlaceable(new ShippingBin(ShippingBinType.Silver));
    }

    private void createStores() {
        for (int i = 60; i < 78; i++) {
            for (int j = 30; j < 45; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.Blacksmith));
        }
        for (int i = 80; i < 88; i++) {
            for (int j = 80; j < 98; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.Ranch));
        }
        for (int i = 60; i < 78; i++) {
            for (int j = 130; j < 145; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.StardropSaloon));
        }
        for (int i = 80; i < 88; i++) {
            for (int j = 180; j < 198; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.CarpentersShop));
        }
        for (int i = 130; i < 148; i++) {
            for (int j = 180; j < 195; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.JojaMart));
        }
        for (int i = 110; i < 128; i++) {
            for (int j = 130; j < 145; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.PierresGeneralStore));
        }
        for (int i = 130; i < 148; i++) {
            for (int j = 80; j < 95; j++)
                Tile.getTile(i, j).setPlaceable(new Store(StoreType.FishShop));
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
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 10 + col; j++) {
                    Tile.getTile(34 + row, j).setPlaceable(lakes.get(0));
                    if (i == 40 + row) {
                        if (j == 6 + col)
                            continue;
                        Tile.getTile(i, j).setPlaceable(greenHouse.getFence());
                    } else if (j == 9 + col || j == 2 + col)
                        Tile.getTile(i, j).setPlaceable(greenHouse.getFence());
                    else
                        Tile.getTile(i, j).setPlaceable(greenHouse);
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
        } else if (type == 2) {
            this.hut = new Hut();
            this.lakes.add(new Lake());
            this.lakes.add(new Lake());
            this.greenHouse = new GreenHouse(this.player);
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
                for (int j = 15 + col; j < 21 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(0));
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = row + 1; i < 7 + row; i++) {
                for (int j = 25 + col; j < 31 + col; j++) {
                    Tile.getTile(i, j).setPlaceable(lakes.get(1));
                    Tile.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 35 + row; i < 41 + row; i++) {
                for (int j = 2 + col; j < 10 + col; j++) {
                    Tile.getTile(34 + row, j).setPlaceable(lakes.get(0));
                    if (i == 40 + row) {
                        if (j == 6 + col)
                            continue;
                        Tile.getTile(i, j).setPlaceable(greenHouse.getFence());
                    } else if (j == 9 + col || j == 2 + col)
                        Tile.getTile(i, j).setPlaceable(greenHouse.getFence());
                    else
                        Tile.getTile(i, j).setPlaceable(greenHouse);
                    Tile.getTile(i, j).setWalkAble(true);
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
            int randomIndex_y = randomInt(1 + col, 100 + col);
            Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                Tree tree = new Tree(false, ForagingController.getRandomTreeType(), false, tile, false);
                tile.setPlaceable(tree);
                tile.setWalkAble(false);
                numOfTrees--;
            }
        }
        while (numOfStones != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 100 + col);
            Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
//TODO: Stone
                //                Stone stone = new Stone();
//                tile.setPlaceable(stone);
//                tile.setWalkAble(false);
                numOfStones--;
            }
        }
        int counter = 0;
        while (numOfForagings != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 100 + col);
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
                counter++;
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
