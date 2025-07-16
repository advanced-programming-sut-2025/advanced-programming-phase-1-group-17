package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Game;
import io.github.StardewValley.models.NPCS.Abigail;
import io.github.StardewValley.models.NPCS.NPC;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.map.Hut;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.views.MapView;

import java.util.HashMap;

public class MapViewController {
    private MapView view;

    private final OrthographicCamera uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private final float cameraSpeed = 300;
    private final Texture backgroundTile = GameAssetManager.getGameAssetManager().getBackgroundTexture();


    public void setView(MapView view) {
        this.view = view;
        uiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCam.update();
    }

    //    public void showMap() {
//        for (Tile tile : Tile.getTiles()) {
//            if (tile.getPlaceable().getTexture() == null)
//                continue;
//            Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * 2, tile.getY() * 2,5,5);
//        }
//    }
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

        for (Tile tile : Tile.getTiles()) {
            try {
                float drawX = offsetX + tile.getX() * tileSize;
                float drawY = offsetY + tile.getY() * tileSize;
                if (tile.getPlaceable() == null || tile.getPlaceable().getTexture() == null)
                    continue;
                Main.getBatch().draw(tile.getPlaceable().getTexture(), drawX, drawY, tileSize, tileSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (NPC npc : App.getCurrentGame().getNPCs()) {
            Main.getBatch().draw(npc.getTexture(), offsetX + npc.getX() * tileSize - 10, offsetY + npc.getY() * tileSize, tileSize * 10, tileSize * 10);
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC"))
                continue;
            if (!player.isMoved())
                Main.getBatch().draw(player.getTexture(), offsetX + player.getX() * tileSize, offsetY + player.getY() * tileSize, tileSize * 10, tileSize * 10);
            else
                Main.getBatch().draw(player.getTexture(), offsetX + player.getTileX() * tileSize, offsetY + player.getTileY() * tileSize, tileSize * 10, tileSize * 10);
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
