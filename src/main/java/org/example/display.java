package org.example;

import org.example.models.App;
import org.example.models.Game;
import org.example.models.NPCS.*;
import org.example.models.map.*;
import org.example.models.plant.Tree;

public class display {
    //TODO khoshgel kardanesh
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
                } else if (tile.getWhoIsHere() != null) {
                    System.out.print(BOLD + WHITE + "P" + RESET);
                } else if (tile.getPlaceable() instanceof Quarry) {
                    System.out.print(BOLD + CYAN + "Q" + RESET);
                } else if (tile.getPlaceable() instanceof Lake) {
                    System.out.print(BOLD + BLUE + "L" + RESET);
                } else if (tile.getPlaceable() instanceof Stone) {
                    System.out.print(BOLD + CHOCOLATE + "S" + RESET);
                } else if (tile.getPlaceable() instanceof Hut) {
                    System.out.print(BOLD + YELLOW + "H" + RESET);
                } else if (tile.getPlaceable() instanceof GreenHouse) {
                    System.out.print(BOLD + PURPLE + "G" + RESET);
                } else if (tile.getPlaceable() instanceof Tree tree) {
                    if (tree.isForaging())
                        System.out.print(BOLD + RED + "F" + RESET);
                    else
                        System.out.print(BOLD + GREEN + "T" + RESET);
                }
                //TODO: Add Crop (with checking isForaging)
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
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            if (i == 50) {
                System.out.println();
                for (int t = 0; t < size + 2; t++) {
                    System.out.print("-");
                }
            }
            if (i == 150) {
                System.out.println();
                for (int t = 0; t < size + 2; t++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        if ((x + size == 201)) {
            for (int i = 0; i < size + 2; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public static void helpReadingMap() {
        System.out.println(BOLD + RED + "F" + RESET + " : is foraging");
        System.out.println(BOLD + CYAN + "Q" + RESET + " : is quarry");
        System.out.println(BOLD + BLUE + "L" + RESET + " : is lake");
        System.out.println(BOLD + CHOCOLATE + "S" + RESET + " : is stone");
        System.out.println(BOLD + YELLOW + "H" + RESET + " : is hut");
        System.out.println(BOLD + PURPLE + "G" + RESET + " : is greenhouse");
        System.out.println(BOLD + GREEN + "T" + RESET + " : is tree");
        System.out.println(BOLD + WHITE + "P" + RESET + " : is player");
        System.out.println(BOLD + BLUE + "A" + RESET + " : is Abigail Home");
        System.out.println(BOLD + WHITE + "L" + RESET + " : is Lia Home");
        System.out.println(BOLD + RED + "R" + RESET + " : is Robin Home");
        System.out.println(BOLD + GREEN + "H" + RESET + " : is Harvey Home");
        System.out.println(BOLD + YELLOW + "S" + RESET + " : is Sebastian Home");
    }
}
