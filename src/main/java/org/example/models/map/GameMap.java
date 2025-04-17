package org.example.models.map;

import org.example.models.Game;
import org.example.models.Player;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public GameMap(ArrayList<Player> players) {
        for (int i = 0; i < 4; i++) {
            playerMaps.add(new PlayerMap(i, players.get(i)));
        }
    }

    public static int[] coordinateShifter(int index) {
        if (index == 1) {
            return new int[]{0, 0};
        } else if (index == 2) {
            return new int[]{PlayerMap.getLength(), 0};
        } else if (index == 3) {
            return new int[]{0, PlayerMap.getWidth() };
        } else {
            return new int[]{PlayerMap.getLength(),PlayerMap.getWidth()};
        }
    }

    public ArrayList<PlayerMap> getPlayerMaps() {
        return playerMaps;
    }

    public void setPlayerMaps(ArrayList<PlayerMap> playerMaps) {
        this.playerMaps = playerMaps;
    }
    //TODO NPC village
}
