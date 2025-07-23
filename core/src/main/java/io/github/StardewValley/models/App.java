package io.github.StardewValley.models;

import io.github.StardewValley.shared.model.Game;
import io.github.StardewValley.shared.model.UserDTO;

public class App {
    private static UserDTO loggedInUser;
    private static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(UserDTO loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
}
