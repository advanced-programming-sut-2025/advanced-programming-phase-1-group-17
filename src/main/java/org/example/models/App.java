package org.example.models;

import org.example.models.enums.Menu;
import org.example.models.tools.ToolType;

import java.util.ArrayList;

public class App {
    private static User loggedInUser;
    private static User lastUser;
    private static Menu currentMenu = Menu.SignUpMenu;
    private static Game currentGame;
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<Game>();


    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void setGames(ArrayList<Game> games) {
        App.games = games;
    }
    public static User getUserWithUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static int[] handleDirection(int direction) {
        //returns (dy, dx)
        switch (direction) {
            case 1 -> {
                return (new int[]{-1, 0}); //Up
            }
            case 2 -> {
                return (new int[]{-1, 1}); //Up-Right
            }
            case 3 -> {
                return (new int[]{0, 1}); //Right
            }
            case 4 -> {
                return (new int[]{1, 1}); //Down-Right
            }
            case 5 -> {
                return (new int[]{1, 0}); //Down
            }
            case 6 -> {
                return (new int[]{1, -1}); //Down-Left
            }
            case 7 -> {
                return (new int[]{0, -1}); //Left
            }
            case 8 -> {
                return (new int[]{1, -1}); //Up-Left
            }
        }
        return new int[]{10, 10}; // never happens
    }


    public static ToolType getToolTypeByName(String name) {
        for (ToolType type : ToolType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}