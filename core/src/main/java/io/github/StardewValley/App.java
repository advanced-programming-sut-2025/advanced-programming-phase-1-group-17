package io.github.StardewValley;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.UserDTO;

public class App {
    private static Game currentGame;
//    private static String jwtToken;
    private static UserDTO LoggedInUser;


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
        return LoggedInUser;
    }


    public static void setLoggedInUser(UserDTO userDTO) {
        LoggedInUser = userDTO;
    }
//    public static String getJwt() {
//        return jwtToken;
//    }
//    public static void setJwt(String jwtToken) {
//        App.jwtToken = jwtToken;
//    }
}

