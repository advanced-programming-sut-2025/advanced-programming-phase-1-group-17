package org.example.models.map;

import org.example.models.Player;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public GameMap(ArrayList<Player> players) {
        for (int i = 0; i < 4; i++) {
            playerMaps.add(new PlayerMap(i, players.get(i)));
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
