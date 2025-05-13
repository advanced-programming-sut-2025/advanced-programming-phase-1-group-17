package org.example;

import org.example.models.App;
import org.example.models.Game;
import org.example.models.NPCS.*;
import org.example.models.NormalItem;
import org.example.models.NormalItemType;
import org.example.models.foraging.Mineral;
import org.example.models.map.*;
import org.example.models.plant.Crop;
import org.example.models.plant.Seed;
import org.example.models.plant.Tree;
import org.example.models.market.ShippingBin;
import org.example.models.market.ItemQuality;
import org.example.models.market.Store;
import org.example.models.market.StoreType;

public class display {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String CHOCOLATE = "\u001B[38;2;123;63;0m";

    public static void run(int x, int y, int size) {
        Game game = App.getCurrentGame();
        if (game == null) {
            return;
        }
        if (x == 1) {
            System.out.print("    ");
            for (int i = 0; i < size + 2; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
        for (int i = x; i < x + size; i++) {
            System.out.printf("%3d|", i);
            for (int j = y; j < y + size; j++) {
                if (j == 101) {
                    System.out.print("|");
                }
                Tile tile = game.getTileByIndex(i, j);
                if (tile == null) {
                    System.out.print("null");
                } else if (tile.getPlaceable() instanceof NPC) {
                    if (tile.getNpcIsHere() == null) {
                        if (tile.getPlaceable() instanceof Abigail) {
                            System.out.print(BOLD + BLUE + "A" + RESET);
                        } else if (tile.getPlaceable() instanceof Harvey) {
                            System.out.print(BOLD + GREEN + "H" + RESET);
                        } else if (tile.getPlaceable() instanceof Lia) {
                            System.out.print(BOLD + CHOCOLATE + "L" + RESET);
                        } else if (tile.getPlaceable() instanceof Robin) {
                            System.out.print(BOLD + RED + "R" + RESET);
                        } else if (tile.getPlaceable() instanceof Sebastian) {
                            System.out.print(BOLD + YELLOW + "S" + RESET);
                        }
                    } else {
                        if (tile.getPlaceable() instanceof Abigail) {
                            System.out.print(BOLD + WHITE + "A" + RESET);
                        } else if (tile.getPlaceable() instanceof Harvey) {
                            System.out.print(BOLD + WHITE + "H" + RESET);
                        } else if (tile.getPlaceable() instanceof Lia) {
                            System.out.print(BOLD + WHITE + "L" + RESET);
                        } else if (tile.getPlaceable() instanceof Robin) {
                            System.out.print(BOLD + WHITE + "R" + RESET);
                        } else if (tile.getPlaceable() instanceof Sebastian) {
                            System.out.print(BOLD + WHITE + "S" + RESET);
                        }
                    }
                } else if (tile.getWhoIsHere() != null) {
                    if (tile.getWhoIsHere().equals(game.getPlayers().get(0))) {
                        System.out.print(BOLD + WHITE + "P" + RESET);
                    } else if (tile.getWhoIsHere().equals(game.getPlayers().get(1))) {
                        System.out.print(BOLD + RED + "P" + RESET);
                    } else if (tile.getWhoIsHere().equals(game.getPlayers().get(2))) {
                        System.out.print(BOLD + BLUE + "P" + RESET);
                    } else if (tile.getWhoIsHere().equals(game.getPlayers().get(3))) {
                        System.out.print(BOLD + YELLOW + "P" + RESET);
                    }
                } else if (tile.getPlaceable() instanceof Quarry) {
                    System.out.print(BOLD + CYAN + "Q" + RESET);
                } else if (tile.getPlaceable() instanceof Lake) {
                    System.out.print(BOLD + BLUE + "L" + RESET);
                } else if (tile.getPlaceable() instanceof Mineral) {
                    System.out.print(BOLD + CHOCOLATE + "M" + RESET);
                } else if (tile.getPlaceable() instanceof Hut) {
                    System.out.print(BOLD + YELLOW + "H" + RESET);
                } else if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
                    if (greenHouse.isActive())
                        System.out.print(BOLD + GREEN + "G" + RESET);
                    else
                        System.out.print(BOLD + PURPLE + "G" + RESET);
                } else if (tile.getPlaceable() instanceof Tree) {
                    System.out.print(BOLD + GREEN + "T" + RESET);
                } else if (tile.getPlaceable() instanceof Crop crop) {
                    if (crop.isGiant())
                        System.out.print(BOLD + RED + "G" + RESET);
                    else
                        System.out.print(BOLD + RED + "C" + RESET);
                } else if (tile.getPlaceable() instanceof NormalItem normalItem) {
                    if (normalItem.getType().equals(NormalItemType.Wood))
                        System.out.print(BOLD + CHOCOLATE + "W" + RESET);
                    else if (normalItem.getType().equals(NormalItemType.Grass))
                        System.out.print(BOLD + WHITE + "G" + RESET);
                    else if (normalItem.getType().equals(NormalItemType.Fibre))
                        System.out.print(BOLD + GREEN + "F" + RESET);
                }



                else if (tile.getPlaceable() instanceof Abigail) {
                    System.out.print(BOLD + BLUE + "A" + RESET);
                } else if (tile.getPlaceable() instanceof Harvey) {
                    System.out.print(BOLD + GREEN + "H" + RESET);
                } else if (tile.getPlaceable() instanceof Lia) {
                    System.out.print(BOLD + WHITE + "L" + RESET);
                } else if (tile.getPlaceable() instanceof Robin) {
                    System.out.print(BOLD + RED + "R" + RESET);
                } else if (tile.getPlaceable() instanceof Sebastian) {
                    System.out.print(BOLD + YELLOW + "S" + RESET);
                } else if (tile.getPlaceable() instanceof Seed) {
                    System.out.printf(BOLD + GREEN + "S" + RESET);
                } else if (tile.getPlaceable() instanceof GreenHouseFence) {
                    System.out.printf(BOLD + CHOCOLATE + "F" + RESET);
                } else if (tile.getPlaceable() instanceof ShippingBin) {
                    System.out.print(BOLD + RED + "X" + RESET);
                } else if (tile.getPlaceable() instanceof Store store) {
                    if (store.getType().equals(StoreType.Blacksmith))
                        System.out.print(BOLD + YELLOW + "B" + RESET);
                    else if (store.getType().equals(StoreType.Ranch))
                        System.out.print(BOLD + YELLOW + "R" + RESET);
                    else if (store.getType().equals(StoreType.StardropSaloon))
                        System.out.print(BOLD + BLUE + "S" + RESET);
                    else if (store.getType().equals(StoreType.CarpentersShop))
                        System.out.print(BOLD + YELLOW + "C" + RESET);
                    else if (store.getType().equals(StoreType.JojaMart))
                        System.out.print(BOLD + YELLOW + "J" + RESET);
                    else if (store.getType().equals(StoreType.PierresGeneralStore))
                        System.out.print(BOLD + YELLOW + "P" + RESET);
                    else if (store.getType().equals(StoreType.FishShop))
                        System.out.print(BOLD + YELLOW + "F" + RESET);
                } else {
                    if (tile.getPlaceable() instanceof Seed) {
                        System.out.print(BOLD + GREEN + "S" + RESET);
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.print("|");

            if (i == 50) {
                System.out.println();
                for (int t = 0; t < size + 6; t++) {
                    System.out.print("-");
                }
            }

            if (i == 150) {
                System.out.println();
                for (int t = 0; t < size + 6; t++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        if ((x + size == 201)) {
            System.out.print("   ");
            for (int i = 0; i < size + 3; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public static void helpReadingMap () {
        //Markets
        System.out.println(BOLD + YELLOW + "B" + RESET + "is Blacksmith");
        System.out.println(BOLD + YELLOW + "R" + RESET + "is Marine's Ranch");
        System.out.println(BOLD + BLUE + "S" + RESET + "is Stardrop's Salon");
        System.out.println(BOLD + YELLOW + "C" + RESET + "is Carpenter's Shop");
        System.out.println(BOLD + YELLOW + "J" + RESET + "is JojaMart");
        System.out.println(BOLD + YELLOW + "P" + RESET + "is Pierre's General Store");
        System.out.println(BOLD + YELLOW + "F" + RESET + "is Fish Shop");
        System.out.println(BOLD + RED + "X" + RESET + "is Shipping Bin");

        System.out.println(BOLD + RED + "C" + RESET + " : is Crop");
        System.out.println(BOLD + RED + "G" + RESET + " : is Giant Crop");
        System.out.println(BOLD + GREEN + "S" + RESET + " : is Seed");
        System.out.println(BOLD + CYAN + "Q" + RESET + " : is quarry");
        System.out.println(BOLD + BLUE + "L" + RESET + " : is lake");
        System.out.println(BOLD + CHOCOLATE + "M" + RESET + " : is mineral");
        System.out.println(BOLD + YELLOW + "H" + RESET + " : is hut");
        System.out.println(BOLD + PURPLE + "G" + RESET + " : is inactive greenhouse");
        System.out.println(BOLD + GREEN + "G" + RESET + " : is active greenhouse");
        System.out.println(BOLD + GREEN + "T" + RESET + " : is tree");
        System.out.println(BOLD + WHITE + "P" + RESET + " : is player");
        System.out.println(BOLD + CHOCOLATE + "F" + RESET + " : is fence");
        System.out.println(BOLD + CHOCOLATE + "W" + RESET + " : is Wood");
        System.out.println(BOLD + WHITE + "G" + RESET + " : is Grass");
        System.out.println(BOLD + GREEN + "F" + RESET + " : is Fibre");

        System.out.println(BOLD + BLUE + "A" + RESET + " : is Abigail Home");
        System.out.println(BOLD + CHOCOLATE + "L" + RESET + " : is Lia Home");
        System.out.println(BOLD + RED + "R" + RESET + " : is Robin Home");
        System.out.println(BOLD + GREEN + "H" + RESET + " : is Harvey Home");
        System.out.println(BOLD + YELLOW + "S" + RESET + " : is Sebastian Home");
        System.out.println(BOLD + WHITE + "A" + RESET + " : is Abigail");
        System.out.println(BOLD + WHITE + "H" + RESET + " : is Harvey");
        System.out.println(BOLD + WHITE + "L" + RESET + " : is Lia");
        System.out.println(BOLD + WHITE + "R" + RESET + " : is Robin");
        System.out.println(BOLD + WHITE + "S" + RESET + " : is Sebastian");
    }
}
