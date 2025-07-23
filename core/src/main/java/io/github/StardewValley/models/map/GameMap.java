package io.github.StardewValley.models.map;

import io.github.StardewValley.models.NPCS.*;
import io.github.StardewValley.shared.model.Player;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.Gender;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public GameMap(ArrayList<Player> players) {
        User user = new User("NPC",  "NPC", "NPC", "NPC", "NPC", Gender.Male);
        NPC.setFatherUser(user);
        Player player = new Player(user, true);
        NPC.setFatherPlayer(player);
        players.add(player);
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
}
