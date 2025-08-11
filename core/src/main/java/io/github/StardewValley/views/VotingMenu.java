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
import com.badlogic.gdx.utils.Align;
import io.github.StardewValley.controllers.VotingMenuController;

public class VotingMenu implements Screen {
    private Stage stage;
    private final VotingMenuController controller;
    private final Label titleLabel;
    private final TextButton yesButton;
    private final TextButton noButton;
    private final Label messageLabel;

    private boolean hasVoted = false;
    private boolean vote = false;

    private float refreshTimer = 0f;
    private final float refreshInterval = 3f; // seconds
    private String targetUsername;

    public VotingMenu(VotingMenuController controller, Skin skin, String targetUsername) {
        this.controller = controller;
        this.targetUsername = targetUsername;
        this.controller.setView(this);

        this.titleLabel = new Label("Voting for kicking user %s".formatted(targetUsername), skin);

        this.yesButton = new TextButton("Yes", skin);
        this.yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasVoted) {
                    hasVoted = true;
                    vote = true;
                    controller.vote(true);
                    messageLabel.setColor(255, 255, 255, 1);
                    messageLabel.setText("Your vote has been placed.\nWaiting for others...");
                } else {
                    messageLabel.setColor(255, 0, 0, 1);
                    messageLabel.setText("You have already voted.\nPlease wait for others...");
                }
            }
        });

        this.noButton = new TextButton("No", skin);
        this.noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasVoted) {
                    hasVoted = true;
                    vote = false;
                    controller.vote(false);
                    messageLabel.setColor(255, 255, 255, 1);
                    messageLabel.setText("Your vote has been placed.\nWaiting for others...");
                } else {
                    messageLabel.setColor(255, 0, 0, 1);
                    messageLabel.setText("You have already voted.\nPlease wait for others...");
                }
            }
        });

        this.messageLabel = new Label("", skin);
        this.messageLabel.setAlignment(Align.center);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(titleLabel).pad(10).row();
        table.add(yesButton).pad(10).row();
        table.add(noButton).pad(10).row();
        table.add(messageLabel).padTop(30).fillX().row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        // Auto-refresh after vote
        if (hasVoted) {
            refreshTimer += delta;
            if (refreshTimer >= refreshInterval) {
                refreshTimer = 0f;
                controller.vote(vote); // re-check voting status
            }
        }
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    public String getTargetUsername() {
        return targetUsername;
    }
}
