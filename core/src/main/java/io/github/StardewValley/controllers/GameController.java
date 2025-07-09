package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.StardewValley.models.Game;
import io.github.StardewValley.models.GameSave;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.PlayerController;
import io.github.StardewValley.views.GameView;

public class GameController {
    public GameView view;
    private OrthographicCamera camera;
    //private final Game game;

    private final WorldController worldController;

    {
        create();
    }

    //public GameController(Game game) {
    public GameController() {
        //this.game = game;

//        for (Player player : game.getPlayers()) {
//            PlayerController playerController = new PlayerController(player);
//            this.game.getPlayerControllers().add(playerController);
//        }
        //Player player = game.getCurrentPlayingPlayer();
        //this.camera.position.set(player.getX(), player.getY(), 0);

        this.worldController = new WorldController(this.camera);
        this.worldController.initTransients();
    }

    public void setView(GameView gameView) {
        this.view = gameView;
    }

    public void updateGame(float delta) {
        if (view != null) {
            //updateCamera(game.getCurrentPlayingPlayer());
            worldController.update();
        }
    }


    private void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void updateCamera(Player player) {
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }
}
