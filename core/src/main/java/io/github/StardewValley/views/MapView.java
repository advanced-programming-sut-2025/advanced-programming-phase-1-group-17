package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MapViewController;
import io.github.StardewValley.models.Game;

public class MapView implements Screen {
    private MapViewController controller;
    private GameView gameView;

    public MapView( MapViewController controller , GameView view) {

        this.controller = controller;
        this.gameView = view;
        controller.setView(this);
        controller.showMap();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(gameView);
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
