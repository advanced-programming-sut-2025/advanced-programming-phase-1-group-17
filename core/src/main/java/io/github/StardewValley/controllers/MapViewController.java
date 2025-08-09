package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.GameState;
import io.github.StardewValley.shared.models.NPCdto;
import io.github.StardewValley.shared.models.PlayerDto;
import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.views.MapView;

public class MapViewController {
    private MapView view;

    private final OrthographicCamera uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private final float cameraSpeed = 300;
    private final Texture backgroundTile = new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture1());


    public void setView(MapView view) {
        this.view = view;
        uiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCam.update();
    }

    public void showMap(float delta) {
        uiCam.update();
        handleCameraInput(delta);

        Main.getBatch().setProjectionMatrix(uiCam.combined);

        float tileSize = 3f;
        int mapWidthInTiles = 300;
        int mapHeightInTiles = 300;

        float totalMapWidth = mapWidthInTiles * tileSize;
        float totalMapHeight = mapHeightInTiles * tileSize;

        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;

        float offsetX = screenCenterX - totalMapWidth / 2f;
        float offsetY = screenCenterY - totalMapHeight / 2f;

        for (TileDTO tile : GameClient.getGameStateApiClient().getAllTiles()) {
            float drawX = offsetX + tile.getX() * tileSize;
            float drawY = offsetY + tile.getY() * tileSize;
            if (tile.getPlaceableType() == null || tile.getTexturePath() == null)
                continue;
            try {
                Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture(tile.getTexturePath()), drawX, drawY, tileSize, tileSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int x : GameClient.getPlayersHutLocations().keySet()) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture("hut.png")
                , offsetX + GameClient.getPlayersHutLocations().get(x).get(0) * tileSize,
                offsetY + GameClient.getPlayersHutLocations().get(x).get(1) * tileSize, tileSize * 10, tileSize * 10);
        }
        for (int x : GameClient.getNPCsHutsLocations().keySet()) {
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture("hut2.png"),
                offsetX + GameClient.getNPCsHutsLocations().get(x).get(0) * tileSize, offsetY + GameClient.getNPCsHutsLocations().get(x).get(1) * tileSize, tileSize * 10, tileSize * 10);
        }
        for (String username : GameClient.getUserNameOfPlayers()) {
            PlayerDto pd = GameClient.getGameStateApiClient().getPlayerDTOByUserName(username);
            int playerWidth = 120;
            float centerX = (pd.getX() == 0 ? 1 : pd.getX()) + playerWidth / 2f;
            float centerY = (pd.getY() == 0 ? 1 : pd.getY()) + playerWidth / 2f;
            Main.getBatch().draw(new Texture(pd.getGender().equals("Male") ? "Alex/Alex11.png" : "Emily/Emily11.png"), offsetX + ((int) (centerX / 120)) * tileSize, offsetY + ((int) (centerY / 120)) * tileSize, (float) GameClient.getPlayer().getBackgroundTexture().getWidth() / 4f, (float) GameClient.getPlayer().getBackgroundTexture().getHeight() / 4f);
        }
        for (int i = 0; i < 5; i++) {
            NPCdto npc = GameClient.getGameStateApiClient().getNPCDtoByIndex(i);
            Main.getBatch().draw(GameAssetManagerClient.getGameAssetManager().getTexture(npc.getTexture()), offsetX + npc.getX() * tileSize, offsetY + npc.getY() * tileSize, (float) GameClient.getPlayer().getBackgroundTexture().getWidth() / 4f, (float) GameClient.getPlayer().getBackgroundTexture().getHeight() / 4f);
        }
        GameState gameState = GameClient.getGameStateApiClient().getGameState(1, 2, 1, 2);
        GameAssetManagerClient assets = GameAssetManagerClient.getGameAssetManager();
        Season season = gameState.getTimeAndDateDTO().getSeason();

        for (StoreType storeType : StoreType.values()) {
            Main.getBatch().draw(assets.getStoreTexture(season, storeType),
                offsetX + storeType.getStart_x() * tileSize, offsetY + storeType.getStart_y() * tileSize,
                tileSize * 15, tileSize * 15);
        }


    }

    public void handleExit() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(view.getGameView());
        }
    }

    public void drawTiledBackground() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        int tileWidth = backgroundTile.getWidth();
        int tileHeight = backgroundTile.getHeight();

        for (int x = 0; x < screenWidth; x += tileWidth) {
            for (int y = 0; y < screenHeight; y += tileHeight) {
                Main.getBatch().draw(backgroundTile, x, y);
            }
        }
    }

    private void handleCameraInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            uiCam.position.x -= cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            uiCam.position.x += cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            uiCam.position.y += cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            uiCam.position.y -= cameraSpeed * delta;
        }
    }
}
