package org.example;

import org.example.models.App;
import org.example.models.Foraging;
import org.example.models.Game;
import org.example.models.Player;
import org.example.models.map.*;
import org.example.models.plant.Tree;

import java.util.HashMap;
import java.util.Scanner;

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
            for (int i = 0; i < size + 2; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
        for (int i = x; i < x + size; i++) {
            System.out.print("|");
            for (int j = y; j < y + size; j++) {
                if (j == 51) {
                    System.out.print("|");
                }
                Tile tile = game.getTileByIndex(i, j);
                if (tile == null) {
                    System.out.print("null");
                } else if (tile.getPlaceable() instanceof Foraging) {
                    System.out.print(BOLD + RED + "F" + RESET);
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
                } else if (tile.getPlaceable() instanceof Tree) {
                    System.out.print(BOLD + GREEN + "T" + RESET);
                } else if (tile.getWhoIsHere() != null) {
                    System.out.print(BOLD + WHITE + "P" + RESET);
                }
                //TODO
                else {
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
            System.out.println();
        }
        if ((x + size == 101)) {
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
    }
}
