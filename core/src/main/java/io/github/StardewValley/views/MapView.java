package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MapViewController;

public class MapView implements Screen {
    private Stage stage;
    private MapViewController controller;
    private GameView gameView;

    public MapView( MapViewController controller , GameView view) {
        this.controller = controller;
        this.gameView = view;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        Main.getBatch().begin();
        controller.drawTiledBackground();
        controller.showMap(delta);
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleExit();
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

    public GameView getGameView() {
        return gameView;
    }
}
