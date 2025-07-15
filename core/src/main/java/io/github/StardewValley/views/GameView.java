package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.MapViewController;
import io.github.StardewValley.display;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final GameController controller;


    public GameView(GameController controller) {
        this.controller = controller;
//        display.run(1,1,200);

        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.getCamera().update();
        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);
        Main.getBatch().begin();

        controller.updateGame(v);
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Main.getMain().getScreen().dispose();
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getMain().setScreen(new MapView(new MapViewController(), this));
        }
        //TODO handle input key

        Main.getBatch().end();


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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


    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        controller.setKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        controller.setKey(keycode, false);
        return true;
    }

    public GameController getController() {
        return controller;
    }
}
