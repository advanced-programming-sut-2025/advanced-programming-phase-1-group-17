package io.github.StardewValley;

import io.github.StardewValley.controllers.PlayerClient;

public class GameClient {
    private static PlayerClient player;
    public static PlayerClient getPlayer() {
        return player;
    }
    public static void setPlayer(PlayerClient player) {
        GameClient.player = player;
    }

}
