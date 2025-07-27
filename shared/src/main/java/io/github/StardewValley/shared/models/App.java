package io.github.StardewValley.shared.models;

public class App {
    private static UserDTO loggedInUser;
    private static Game currentGame;
    private static String jwtToken;

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

    public static void setJwt(String token) {
        jwtToken = token;
    }
    public static String getJwt() {
        return jwtToken;
    }
}

