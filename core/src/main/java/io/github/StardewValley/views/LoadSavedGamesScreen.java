package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.controllers.UIControllers.LoadSavedGamesController;
import io.github.StardewValley.shared.dto.SavedGameInfo;

import java.util.List;
import java.util.UUID;

public class LoadSavedGamesScreen implements Screen {
    private final LoadSavedGamesController controller;
    private final Skin skin;
    private Stage stage;

    private int refreshRate = 3;
    private float timeSinceLastRefresh = 0;
    private boolean hasSelected = false;
    private UUID id;

    public LoadSavedGamesScreen(LoadSavedGamesController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Saved Games", skin);
        root.add(title).pad(10).row();

        // Table inside the scroll pane
        Table contentTable = new Table();
        contentTable.top().pad(10);

        List<SavedGameInfo> savedGames = controller.getSavedGames();
        for (SavedGameInfo game : savedGames) {
            // Table for the text inside the big button
            Table gameInfoTable = new Table();
            gameInfoTable.defaults().left().pad(2);

            gameInfoTable.add(new Label("Creator: " + game.getCreatorUsername(), skin)).row();
            gameInfoTable.add(new Label("Date Saved: " + game.getDateSaved(), skin)).row();
            gameInfoTable.add(new Label("Players: " + String.join(", ", game.getParticipants()), skin)).row();

            // Big horizontal button
            TextButton gameButton = new TextButton("", skin);
            gameButton.add(gameInfoTable).expand().fill();

            gameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!hasSelected) {
                        hasSelected = true;
                        controller.waitForLoadGame(game.getId());
                        id = game.getId();
                    }
                }
            });

            // Make it span full width
            contentTable.add(gameButton)
                .width(Gdx.graphics.getWidth() * 0.9f) // 90% of screen width
                .pad(5)
                .row();
        }

        // ScrollPane for scrolling
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);

        root.add(scrollPane).expand().fill().row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        controller.handlePlayerInput();

        if (hasSelected) {
            timeSinceLastRefresh += delta;
            if (timeSinceLastRefresh > refreshRate) {
                controller.waitForLoadGame(id);
                timeSinceLastRefresh = 0;
            }
        }
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
