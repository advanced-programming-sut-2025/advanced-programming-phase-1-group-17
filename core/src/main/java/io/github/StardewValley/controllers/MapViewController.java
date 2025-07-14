package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.Game;
import io.github.StardewValley.models.map.Hut;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.views.MapView;

import java.util.HashMap;

public class MapViewController {
    private MapView View;

    public void setView(MapView view) {
        this.View = view;
    }

    //    public void showMap() {
//        for (Tile tile : Tile.getTiles()) {
//            if (tile.getPlaceable().getTexture() == null)
//                continue;
//            Main.getBatch().draw(tile.getPlaceable().getTexture(), tile.getX() * 2, tile.getY() * 2,5,5);
//        }
//    }
    public void showMap() {
        OrthographicCamera uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCam.update();

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
            float drawX = offsetX + tile.getX() * tileSize;
            float drawY = offsetY + tile.getY() * tileSize;
            if (tile.getPlaceable() == null || tile.getPlaceable().getTexture() == null)
                continue;
            Main.getBatch().draw(tile.getPlaceable().getTexture(), drawX, drawY, tileSize, tileSize);
        }
    }
}
