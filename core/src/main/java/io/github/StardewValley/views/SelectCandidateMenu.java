package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.SelectCandidateController;
import io.github.StardewValley.controllers.VotingMenuController;

import java.util.ArrayList;

public class SelectCandidateMenu implements Screen {
    private Stage stage;
    private SelectCandidateController controller;

    private Label titleLabel;
    private Table mainTable;

    public SelectCandidateMenu(SelectCandidateController controller, Skin skin) {
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
        titleLabel = new Label("Select a Player to Vote Out", skin);
        mainTable.add(titleLabel).pad(10).row();

        ArrayList<String> playerUsernames = GameClient.getGameStateApiClient().getVoteCandidates().getPlayerUsernames();
        for (String playerUsername : playerUsernames) {
            TextButton button = new TextButton(playerUsername, skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.getMain().getScreen().dispose();
                    GameClient.getGameStateApiClient().sendCandidate(playerUsername);
                    System.out.println("Candidate: playerUsername");
                    Main.getMain().setScreen(new VotingMenu(
                        new VotingMenuController(),
                        GameAssetManagerClient.getGameAssetManager().getSkin(),
                        playerUsername
                    ));
                }
            });
            mainTable.add(button).pad(5).row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
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
}
