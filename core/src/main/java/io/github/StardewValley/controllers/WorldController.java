package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.map.Tile;

public class WorldController {
    private final OrthographicCamera camera;

    private transient Texture backgroundTexture;
    private Tile[][] tiles;
    private int tileWidth;
    private int tileHeight;

    public WorldController(OrthographicCamera camera) {
        this.camera = camera;
        this.tiles = Tile.getTiles();
    }

    public void initTransients() {
        this.backgroundTexture = new Texture(GameAssetManager.getGameAssetManager().getBackgroundTexture());
        this.tileWidth = backgroundTexture.getWidth();
        this.tileHeight = backgroundTexture.getHeight();
    }

    public void update() {
        float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
        float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
        float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
        float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

        int minTileX = Math.max((int)(camLeft / tileWidth), 0);
        int maxTileX = Math.min((int)(camRight / tileWidth) + 1, tiles[0].length);
        int minTileY = Math.max((int)(camBottom / tileHeight), 0);
        int maxTileY = Math.min((int)(camTop / tileHeight) + 1, tiles.length);


        for (int y = minTileY; y < maxTileY; y++) {
            for (int x = minTileX; x < maxTileX; x++) {
                Tile tile = tiles[y][x];
                //TODO
                //Texture tileTexture = getTileTexture(tile);
//                if (tileTexture != null) {
//                    Main.getBatch().draw(tileTexture, x * TILE_SIZE, y * TILE_SIZE);
//                }
                Main.getBatch().draw(backgroundTexture, x * tileWidth, y * tileHeight);
            }
        }

        Main.getBatch().draw(backgroundTexture, 0, 0);
    }
}
