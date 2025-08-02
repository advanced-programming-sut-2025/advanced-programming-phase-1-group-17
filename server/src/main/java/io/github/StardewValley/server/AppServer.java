package io.github.StardewValley.server;

import io.github.StardewValley.shared.models.Game;

public class AppServer {
    private static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame1) {
        currentGame = currentGame1;
    }
}
