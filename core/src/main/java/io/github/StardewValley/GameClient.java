package io.github.StardewValley;

import com.badlogic.gdx.graphics.Camera;
import io.github.StardewValley.controllers.PlayerClient;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.shared.dto.CraftingItemDTO;
import io.github.StardewValley.shared.models.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameClient {
    public static GameStateApiClient gameStateApiClient = new GameStateApiClient(Main.getJwtToken());
    public static List<String> userNameOfPlayers = new ArrayList<>();
    private static Camera camera;
    private static PlayerClient player;
    private static ArrayList<CraftingItemDTO> craftingItems = new ArrayList<>();
    private static HashMap<Integer, ArrayList<Integer>> playersHutLocations = new HashMap<>();
    private static HashMap<Integer, ArrayList<Integer>> NPCsHutsLocations = new HashMap<>();
    private static HashMap<Integer, ArrayList<Integer>> greenHouseLocations = new HashMap<>();
    public static UserDTO LoggedInUser;


    public static PlayerClient getPlayer() {
        return player;
    }

    public static void setPlayer(PlayerClient player) {
        GameClient.player = player;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static void setCamera(Camera camera) {
        GameClient.camera = camera;
    }

    public static GameStateApiClient getGameStateApiClient() {
        return gameStateApiClient;
    }

    public static void setGameStateApiClient(GameStateApiClient gameStateApiClient) {
        GameClient.gameStateApiClient = gameStateApiClient;
    }

    public static List<String> getUserNameOfPlayers() {
        return userNameOfPlayers;
    }

    public static void setUserNameOfPlayers(List<String> userNameOfPlayers) {
        GameClient.userNameOfPlayers = userNameOfPlayers;
    }

    public static ArrayList<CraftingItemDTO> getCraftingItems() {
        return craftingItems;
    }

    public static void setCraftingItems(ArrayList<CraftingItemDTO> craftingItems) {
        GameClient.craftingItems = craftingItems;
    }

    public static HashMap<Integer,ArrayList<Integer>> getPlayersHutLocations() {
        if (playersHutLocations.isEmpty()) {
            playersHutLocations = new HashMap<>(getGameStateApiClient().getPlayerHutsLocationsFromServer());
        }
        return playersHutLocations;
    }
    public static HashMap<Integer, ArrayList<Integer>> getNPCsHutsLocations() {
        if (NPCsHutsLocations.isEmpty()) {
            NPCsHutsLocations = new HashMap<>(getGameStateApiClient().getNPCSHutsLocationsFromServer());
        }
        return NPCsHutsLocations;
    }
    public static HashMap<Integer, ArrayList<Integer>> getGreenHouseLocations() {
        if (greenHouseLocations.isEmpty()) {
            greenHouseLocations = new HashMap<>(getGameStateApiClient().getGreenHouseLocationsFromServer());
        }
        return greenHouseLocations;
    }

    public static UserDTO getLoggedInUser() {
        return LoggedInUser;
    }

    public static void setLoggedInUser(UserDTO loggedInUser) {
        LoggedInUser = loggedInUser;
    }
}
