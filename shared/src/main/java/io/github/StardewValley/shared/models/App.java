package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class App {
    private static Game currentGame;


    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame1) {
        currentGame = currentGame1;
    }

    public static Camera getCamera() {
        return null;
    }

    public static void setCamera(OrthographicCamera camera) {

    }

    public static UserDTO getLoggedInUser() {
        return null;
    }

    public static void setLoggedInUser(UserDTO userDTO) {

    }
    public static String getJwt() {
        return jwtToken;
    }
}

