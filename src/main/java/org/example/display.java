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
    public static void run(int x, int y, int size) {
        Game game = App.getCurrentGame();
        if (game == null) {
            return;
        }
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                Tile tile = game.getTileByIndex(i, j);
                if (tile == null) {
                    System.out.print("*");
                } else if (tile.getPlaceable() instanceof Foraging) {
                    System.out.print("F");
                } else if (tile.getPlaceable() instanceof Quarry) {
                    System.out.print("Q");
                } else if (tile.getPlaceable() instanceof Lake) {
                    System.out.print("L");
                } else if (tile.getPlaceable() instanceof Stone) {
                    System.out.print("S");
                } else if (tile.getPlaceable() instanceof Hut) {
                    System.out.print("H");
                } else if (tile.getPlaceable() instanceof GreenHouse) {
                    System.out.print("G");
                } else if (tile.getPlaceable() instanceof Tree) {
                    System.out.print("T");
                }
                //TODO
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
