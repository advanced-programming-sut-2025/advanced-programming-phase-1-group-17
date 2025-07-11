package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.Game;
import io.github.StardewValley.models.GameSave;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.PlayerController;
import io.github.StardewValley.views.GameView;

public class GameController {
    public GameView view;
    private Player player;
    private OrthographicCamera camera;
    int mapWidthInPixels;
    int mapHeightInPixels;
    //private final Game game;

    private final WorldController worldController;

    {
        create();
    }

    //public GameController(Game game) {
    public GameController(Player player) {
        this.player = player;
        //this.game = game;

//        for (Player player : game.getPlayers()) {
//            PlayerController playerController = new PlayerController(player);
//            this.game.getPlayerControllers().add(playerController);
//        }
        //Player player = game.getCurrentPlayingPlayer();
        //this.camera.position.set(player.getX(), player.getY(), 0);

        this.worldController = new WorldController(this.camera);
        this.worldController.initTransients();
        this.mapWidthInPixels = worldController.getTileWidth();
        this.mapHeightInPixels = worldController.getTileHeight();
    }

    public void setView(GameView gameView) {
        this.view = gameView;
    }


    private void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void updateCamera(Player player) {
        float camHalfWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float camHalfHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float minX = camHalfWidth;
        float maxX = mapWidthInPixels * 200 - camHalfWidth;
        float minY = camHalfHeight;
        float maxY = mapHeightInPixels * 200 - camHalfHeight;

        float targetX = player.getX();
        float targetY = player.getY();
        float camX = Math.max(minX, Math.min(targetX, maxX));
        float camY = Math.max(minY, Math.min(targetY, maxY));

        camera.position.set(camX, camY, 0);
        camera.update();
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }

    public void updateGame(float delta) {
        if (view != null) {
            player.update(delta, upPressed, downPressed, leftPressed, rightPressed);
            updateCamera(player);
            worldController.update();
            player.draw(Main.getBatch());
        }
    }

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public void setKey(int keycode, boolean pressed) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.W:
                upPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                downPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                leftPressed = pressed;
                break;
            case com.badlogic.gdx.Input.Keys.D:
                rightPressed = pressed;
                break;
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
