package io.github.StardewValley;

import com.badlogic.gdx.graphics.Camera;
import io.github.StardewValley.controllers.PlayerClient;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.shared.dto.CraftingItemDTO;

import java.util.ArrayList;

public class GameClient {
    public static GameStateApiClient gameStateApiClient = new GameStateApiClient(Main.getJwtToken());
    private static Camera camera;
    private static PlayerClient player;
    private static ArrayList<CraftingItemDTO> craftingItems = new ArrayList<>();
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

    public static ArrayList<CraftingItemDTO> getCraftingItems() {
        return craftingItems;
    }

    public static void setCraftingItems(ArrayList<CraftingItemDTO> craftingItems) {
        GameClient.craftingItems = craftingItems;
    }
}
