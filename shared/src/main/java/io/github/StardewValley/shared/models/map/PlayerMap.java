package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Fence;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.greenhouse.GreenHouseLake;
import io.github.StardewValley.shared.models.NPCS.*;
import io.github.StardewValley.shared.models.animal.Animal;
import io.github.StardewValley.shared.models.animal.AnimalPlace;
import io.github.StardewValley.shared.models.foraging.ForagingController;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerMap {
    private int x_start;
    private int y_start;
    private int row;
    private int col;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private Player player;
    private int type;
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<AnimalPlace> animalPlaces;
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
        return tiles;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerMap(int index, Player owner) {
        this.animalPlaces = new ArrayList<>();
        this.animals = new ArrayList<>();
        if (index == 0) {
            this.row = 0;
            this.col = 0;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = 0; i < 100; i++) {
                for (int i1 = 0; i1 < 100; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    this.tiles.add(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 1) {
            this.row = 0;
            this.col = 200;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 100 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    this.tiles.add(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 2) {
            this.row = 200;
            this.col = 0;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 100 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    this.tiles.add(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 3) {
            this.row = 200;
            this.col = 200;
            owner.setX(1 + row);
            owner.setY(1 + col);
            for (int i = row; i < 100 + row; i++) {
                for (int i1 = col; i1 < 100 + col; i1++) {
                    Tile tile = new Tile(i + 1, i1 + 1, owner);
                    this.tiles.add(tile);
                    this.player = owner;
                    owner.setPlayerMap(this);
                }
            }
        } else if (index == 4) {
            // NPC FORM
            for (int i = 101; i <= 200; i++) {
                for (int j = 101; j <= 200; j++) {
                    this.tiles.add(new Tile(i, j, NPC.getFatherPlayer()));
                }
            }
            for (int i = 101; i < 201; i++) {
                for (int j = 1; j < 101; j++) {
                    this.tiles.add(new Tile(i, j, NPC.getFatherPlayer()));
                }
            }
            for (int i = 101; i < 201; i++) {
                for (int j = 201; j < 301; j++) {
                    this.tiles.add(new Tile(i, j, NPC.getFatherPlayer()));
                }
            }
            for (int i = 1; i < 101; i++) {
                for (int j = 101; j < 201; j++) {
                    this.tiles.add(new Tile(i, j, NPC.getFatherPlayer()));
                }
            }
            for (int i = 201; i < 301; i++) {
                for (int j = 101; j < 201; j++) {
                    this.tiles.add(new Tile(i, j, NPC.getFatherPlayer()));
                }
            }
            this.player = owner;
            owner.setPlayerMap(this);

            Abigail abigail2 = (Abigail) App.getCurrentGame().getNPCs().get(0);
            Harvey harvey2 = (Harvey) App.getCurrentGame().getNPCs().get(1);
            Lia lia2 = (Lia) App.getCurrentGame().getNPCs().get(2);
            Robin robin2 = (Robin) App.getCurrentGame().getNPCs().get(3);
            Sebastian sebastian2 = (Sebastian) App.getCurrentGame().getNPCs().get(4);
            Abigail abigail = new Abigail(false, 145, 102);
            Harvey harvey = new Harvey(false, 145, 122);
            Lia lia = new Lia(false, 145, 142);
            Robin robin = new Robin(false, 145, 162);
            Sebastian sebastian = new Sebastian(false, 145, 182);

            App.getCurrentGame().getNPCHuts().add(abigail);
            App.getCurrentGame().getNPCHuts().add(harvey);
            App.getCurrentGame().getNPCHuts().add(lia);
            App.getCurrentGame().getNPCHuts().add(robin);
            App.getCurrentGame().getNPCHuts().add(sebastian);
            for (int k = 0; k < 81; k += 20) {
                for (int i = 145; i <= 149; i++) {
                    for (int j = 102 + k; j <= 106 + k; j++) {
                        if (k == 0) {
                            Tile.getTile(i, j).setPlaceable(abigail);
                            Tile.getTile(i, j).setWalkAble(false);
                        } else if (k == 20) {
                            Tile.getTile(i, j).setPlaceable(harvey);
                            Tile.getTile(i, j).setWalkAble(false);
                        } else if (k == 40) {
                            Tile.getTile(i, j).setPlaceable(lia);
                            Tile.getTile(i, j).setWalkAble(false);
                        } else if (k == 60) {
                            Tile.getTile(i, j).setPlaceable(robin);
                            Tile.getTile(i, j).setWalkAble(false);
                        } else {
                            Tile.getTile(i, j).setPlaceable(sebastian);
                            Tile.getTile(i, j).setWalkAble(false);
                        }
                    }
                }
            }
            out:
            for (int k = 0; k < 81; k += 20) {
                for (int i = 140; i < 145; i++) {
                    for (int j = 102 + k; j < 111 + k; j++) {
                        if (Tile.getTile(i, j).getPlaceable() == null
                            && Tile.getTile(i, j).isWalkAble()) {
                            if (k == 0) {
                                abigail2.setX(i);
                                abigail2.setY(j);
                                Tile.getTile(i, j).setWalkAble(false);
                                Tile.getTile(i, j).setPlaceable(abigail2);
                                abigail2.setTile_x(i);
                                abigail2.setTile_y(j);
                                Tile.getTile(i, j).setNpcIsHere(abigail2);
                                continue out;
                            } else if (k == 20) {
                                harvey2.setX(i);
                                harvey2.setY(j);
                                Tile.getTile(i, j).setWalkAble(false);
                                Tile.getTile(i, j).setPlaceable(harvey2);
                                harvey2.setTile_x(i);
                                harvey2.setTile_y(j);
                                Tile.getTile(i, j).setNpcIsHere(harvey2);
                                continue out;
                            } else if (k == 40) {
                                lia2.setX(i);
                                lia2.setY(j);
                                Tile.getTile(i, j).setWalkAble(false);
                                Tile.getTile(i, j).setPlaceable(lia2);
                                lia2.setTile_x(i);
                                lia2.setTile_y(j);
                                Tile.getTile(i, j).setNpcIsHere(lia2);
                                continue out;
                            } else if (k == 60) {
                                robin2.setX(i);
                                robin2.setY(j);
                                Tile.getTile(i, j).setWalkAble(false);
                                Tile.getTile(i, j).setPlaceable(robin2);
                                robin2.setTile_x(i);
                                robin2.setTile_y(j);
                                Tile.getTile(i, j).setNpcIsHere(robin2);
                                continue out;
                            } else {
                                sebastian2.setX(i);
                                sebastian2.setY(j);
                                Tile.getTile(i, j).setWalkAble(false);
                                Tile.getTile(i, j).setPlaceable(sebastian2);
                                sebastian2.setTile_x(i);
                                sebastian2.setTile_y(j);
                                Tile.getTile(i, j).setNpcIsHere(sebastian2);
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
            int numOfForagings = randomInt(10, 20);
            while (numOfTrees != 0) {
                int randomIndex_x = randomInt(101, 200);
                int randomIndex_y = randomInt(101, 200);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(false, ForagingController.getRandomTreeType(), tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees--;
                }
            }
            int counter = 0;
            while (numOfForagings != 0) {
                int randomIndex_x = randomInt(101, 200);
                int randomIndex_y = randomInt(101, 200);
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
            //forest
            int numOfTrees2 = randomInt(30, 40);
            while (numOfTrees2 != 0) {
                int randomIndex_x = randomInt(101, 200);
                int randomIndex_y = randomInt(1, 100);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(true, ForagingController.getRandomTreeType(), tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees2--;
                }
            }
            int numOfTrees3 = randomInt(30, 40);
            while (numOfTrees3 != 0) {
                int randomIndex_x = randomInt(101, 200);
                int randomIndex_y = randomInt(201, 300);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(true, ForagingController.getRandomTreeType(), tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees3--;
                }
            }

            int numOfTrees4 = randomInt(30, 40);
            while (numOfTrees4 != 0) {
                int randomIndex_x = randomInt(1, 100);
                int randomIndex_y = randomInt(101, 200);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(true, ForagingController.getRandomTreeType(), tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees4--;
                }
            }

            int numOfTrees5 = randomInt(30, 40);
            while (numOfTrees5 != 0) {
                int randomIndex_x = randomInt(201, 300);
                int randomIndex_y = randomInt(101, 200);
                Tile tile = Tile.getTile(randomIndex_x, randomIndex_y);
                if (tile.getPlaceable() == null) {
                    Tree tree = new Tree(true, ForagingController.getRandomTreeType(), tile, false);
                    tile.setPlaceable(tree);
                    tile.setWalkAble(false);
                    numOfTrees5--;
                }
            }
            Fence fence = new Fence(false);
            Fence fence2 = new Fence(true);
            for (int i = 0; i <= 301; i++) {
                Tile tile1 = new Tile(i, 0, NPC.getFatherPlayer());
                tile1.setPlaceable(fence);
                tile1.setWalkAble(false);
                this.tiles.add(tile1);
                Tile tile2 = new Tile(i, 301, NPC.getFatherPlayer());
                tile2.setPlaceable(fence);
                tile2.setWalkAble(false);
                this.tiles.add(tile2);
            }
            for (int i = 1; i <= 300; i++) {
                if (i < 60 && i > 55)
                    continue;
                if (i < 155 && i > 150)
                    continue;
                if (i < 255 && i > 250)
                    continue;

                if (Tile.getTile(101, i).getPlaceable() == null) {
                    Tile.getTile(101, i).setPlaceable(fence2);
                    Tile.getTile(101, i).setWalkAble(false);
                }
                if (Tile.getTile(200, i).getPlaceable() == null) {
                    Tile.getTile(200, i).setPlaceable(fence2);
                    Tile.getTile(200, i).setWalkAble(false);
                }
                if (Tile.getTile(i, 101).getPlaceable() == null) {
                    Tile.getTile(i, 101).setPlaceable(fence);
                    Tile.getTile(i, 101).setWalkAble(false);
                }

                if (Tile.getTile(i, 200).getPlaceable() == null) {
                    Tile.getTile(i, 200).setPlaceable(fence);
                    Tile.getTile(i, 200).setWalkAble(false);
                }
            }

            for (int j = 1; j <= 300; j++) {
                Tile tile1 = new Tile(0, j, NPC.getFatherPlayer());
                tile1.setPlaceable(fence2);
                tile1.setWalkAble(false);
                this.tiles.add(tile1);
                Tile tile2 = new Tile(301, j, NPC.getFatherPlayer());
                tile2.setPlaceable(fence2);
                tile2.setWalkAble(false);
                this.tiles.add(tile2);
            }


        }

    }

    private void createShippingBins() {
        if (Tile.getTile(10, 5).getPlaceable() == null) {
            Tile.getTile(10, 5).setPlaceable(new ShippingBin(10, 5));
        }
        if (Tile.getTile(30, 230).getPlaceable() == null) {
            Tile.getTile(30, 230).setPlaceable(new ShippingBin(30, 230));
        }
        if (Tile.getTile(280, 30).getPlaceable() == null) {
            Tile.getTile(280, 30).setPlaceable(new ShippingBin(280, 30));
        }
        if (Tile.getTile(180, 130).getPlaceable() == null) {
            Tile.getTile(180, 130).setPlaceable(new ShippingBin(180, 130));
        }
        if (Tile.getTile(70, 50).getPlaceable() == null) {
            Tile.getTile(70, 50).setPlaceable(new ShippingBin(70, 50));

        }
        if (Tile.getTile(70, 250).getPlaceable() == null) {
            Tile.getTile(70, 250).setPlaceable(new ShippingBin(70, 250));
        }
        if (Tile.getTile(100, 105).getPlaceable() == null) {
            Tile.getTile(100, 105).setPlaceable(new ShippingBin(100, 105));
        }
        if (Tile.getTile(220, 50).getPlaceable() == null) {
            Tile.getTile(220, 50).setPlaceable(new ShippingBin(220, 50));
        }
        if (Tile.getTile(220, 250).getPlaceable() == null) {
            Tile.getTile(220, 250).setPlaceable(new ShippingBin(220, 250));
        }
    }

    private void createStores() {
        Store store = new Store(StoreType.Blacksmith, 60, 130, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.Blacksmith, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.Ranch, 80, 180, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.Ranch, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.StardropSaloon, 160, 30, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.StardropSaloon, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.CarpentersShop, 180, 80, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.CarpentersShop, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.JojaMart, 230, 180, 12, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.JojaMart, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.PierresGeneralStore, 110, 230, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.PierresGeneralStore, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }

        store = new Store(StoreType.FishShop, 130, 280, 8, 8);
        for (int i = store.getStart_x(); i < store.getStart_x() + store.getWidth(); i++) {
            for (int j = store.getStart_y(); j < store.getStart_y() + store.getHeight(); j++) {
                //App.getCurrentGame().getStoreManager().addStore(StoreType.FishShop, store);
                Tile.getTile(i, j).setPlaceable(store);
                Tile.getTile(i, j).setWalkAble(false);
            }
        }
    }

    public void setMapType(int type, Game game) {
        if (type == 1) {
            this.hut = new Hut("hut.png", 4 + row, 4 + col);
            this.lakes.add(new Lake());
            this.quarry = new Quarry();
            this.greenHouse = new GreenHouse(this.player, 6, 8, 35 + row, 2 + col, new GreenHouseLake());
            this.x_start = 4 + row;
            this.y_start = 4 + col;
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    game.getTile(4 + row + x, 4 + col + y).setPlaceable(hut);
                    game.getTile(4 + row + x, 4 + col + y).setWalkAble(false);
                }
            }
            for (int i = 1 + row; i < 12 + row; i++) {
                for (int j = 20 + col; j < 31 + col; j++) {
                    game.getTile(i, j).setPlaceable(quarry);
                    game.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 40 + row; i < 46 + row; i++) {
                for (int j = 30 + col; j < 36 + col; j++) {
                    game.getTile(i, j).setPlaceable(lakes.get(0));
                    game.getTile(i, j).setWalkAble(false);
                }
            }
            createGreenHouse(game);
        } else if (type == 2) {
            this.hut = new Hut("hut.png", 40 + row, 40 + col);
            this.lakes.add(new Lake());
            this.lakes.add(new Lake());
            this.greenHouse = new GreenHouse(this.player, 6, 8, 35 + row, 2 + col, new GreenHouseLake());
            this.quarry = new Quarry();
            this.x_start = 40 + row;
            this.y_start = 40 + col;
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    game.getTile(40 + row + x, 40 + col + y).setPlaceable(hut);
                    game.getTile(40 + row + x, 40 + col + y).setWalkAble(false);
                }
            }
            for (int i = 25 + row; i < 31 + row; i++) {
                for (int j = 35 + col; j < 41 + col; j++) {
                    game.getTile(i, j).setPlaceable(quarry);
                    game.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = 30 + row; i < 36 + row; i++) {
                for (int j = 15 + col; j < 21 + col; j++) {
                    game.getTile(i, j).setPlaceable(lakes.get(0));
                    game.getTile(i, j).setWalkAble(false);
                }
            }
            for (int i = row + 1; i < 7 + row; i++) {
                for (int j = 25 + col; j < 31 + col; j++) {
                    game.getTile(i, j).setPlaceable(lakes.get(1));
                    game.getTile(i, j).setWalkAble(false);
                }
            }
            createGreenHouse(game);
        }
        randomFillMap(game);
    }

    public void createGreenHouse(Game game) {
        for (int i = 35 + row; i < 41 + row; i++) {
            for (int j = 2 + col; j < 10 + col; j++) {
                if (j == 8 + col || j == 9 + col)
                    game.getTile(34 + row, j).setPlaceable(greenHouse.getLake());

                else if (j == 2 + col) {
                    if (i == 38 + row)
                        continue;
                    game.getTile(i, j).setPlaceable(greenHouse.getFence());
                } else if (j == 7 + col)
                    game.getTile(i, j).setPlaceable(greenHouse.getFence());
                else if (i == 35 + row || i == 40 + row) {
                    game.getTile(i, j).setPlaceable(greenHouse.getFence());
                } else
                    game.getTile(i, j).setPlaceable(greenHouse);

                if ((game.getTile(i, j).getPlaceable() instanceof GreenHouse))
                    game.getTile(i, j).setWalkAble(true);
                else
                    game.getTile(i, j).setWalkAble(false);
            }
        }
    }

    public int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void randomFillMap(Game game) {
        int numOfTrees = randomInt(14, 20);
        int numOfForagings = randomInt(14, 20);
        generateTrees(numOfTrees, game);
        generateForagings(numOfForagings, game);
    }

    private void generateForagings(int numOfForagings, Game game) {
        int counter = 0;
        while (numOfForagings != 0) {
            int randomIndex_x = randomInt(1 + row, 100 + row);
            int randomIndex_y = randomInt(1 + col, 100 + col);
            Tile tile = game.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                if (counter < 2)
                    ForagingController.setTreeForaging(tile);
                else if (counter < 4)
                    ForagingController.setCropForaging(tile);
                else if (counter < 6)
                    ForagingController.setSeedForaging(tile);
                tile.setWalkAble(false);
                numOfForagings--;
                counter++;
            }
        }
    }

    private void generateTrees(int numOfTrees, Game game) {
        while (numOfTrees != 0) {
            int randomIndex_x = randomInt(1 + row, 50 + row);
            int randomIndex_y = randomInt(1 + col, 100 + col);
            Tile tile = game.getTile(randomIndex_x, randomIndex_y);
            if (tile.getPlaceable() == null) {
                Tree tree = new Tree(false, ForagingController.getRandomTreeType(), tile, false);
                tile.setPlaceable(tree);
                tile.setWalkAble(false);
                numOfTrees--;
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

    public int getX_start() {
        return x_start;
    }

    public void setX_start(int x_start) {
        this.x_start = x_start;
    }

    public int getY_start() {
        return y_start;
    }

    public void setY_start(int y_start) {
        this.y_start = y_start;
    }


    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<AnimalPlace> getAnimalPlaces() {
        return animalPlaces;
    }
}
