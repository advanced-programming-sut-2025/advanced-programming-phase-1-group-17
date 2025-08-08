package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.UserDTO;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public GameMap(ArrayList<Player> players, Game game) {
        UserDTO user = new UserDTO("NPC",  "NPC", Gender.Male);
        NPC.setFatherUser(user);
        Player player = new Player(user, true);
        NPC.setFatherPlayer(player);
        players.add(player);
        for (int i = 0; i < 5; i++) {
            playerMaps.add(new PlayerMap(i, players.get(i),game));
        }
    }

    public ArrayList<PlayerMap> getPlayerMaps() {
        return playerMaps;
    }

    public void setPlayerMaps(ArrayList<PlayerMap> playerMaps) {
        this.playerMaps = playerMaps;
    }
}
