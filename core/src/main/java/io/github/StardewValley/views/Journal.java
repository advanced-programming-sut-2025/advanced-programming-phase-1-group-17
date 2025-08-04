package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.controllers.UIControllers.JournalController;

public class Journal implements Screen {
    private Stage stage;
    private final JournalController controller;
    private final Table mainTable;
    private final Label titleLabel;


    public Journal(JournalController controller, Skin skin) {
        this.controller = controller;
        this.controller.setView(this);
        this.mainTable = new Table();
        this.titleLabel = new Label("Journal", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);

        mainTable.top().padTop(40);
        mainTable.add(titleLabel).center().colspan(2).padBottom(20).row();
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        this.controller.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
