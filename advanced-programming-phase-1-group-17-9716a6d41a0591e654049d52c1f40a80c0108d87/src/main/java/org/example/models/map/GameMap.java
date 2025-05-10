package org.example.models.map;

import org.example.models.Player;
import org.example.models.User;
import org.example.models.enums.Gender;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public GameMap(ArrayList<Player> players) {
        players.add(new Player(new User("NPC", "NPC", "NPC", "NPC", Gender.Male), true));
        for (int i = 0; i < 5; i++) {
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
