package io.github.StardewValley.shared.models;

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
