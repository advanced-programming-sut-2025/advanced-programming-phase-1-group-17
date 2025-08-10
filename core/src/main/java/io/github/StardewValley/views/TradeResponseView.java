package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.TradeResponseController;

import java.util.HashMap;

public class TradeResponseView implements Screen {
    private TradeResponseController controller;
    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton rejectButton;
    private TextButton acceptButton;
    private Label suggestion;
    private Label requested;
    private String targetPlayer;
    private float timer = 0.0f;
    private HashMap<String, Integer> suggestions = new HashMap<>();
    private HashMap<String, Integer> requests = new HashMap<>();


    public TradeResponseView(TradeResponseController controller, Skin skin, String targetPlayer, GameView gameView) {
        this.controller = controller;
        this.skin = skin;
        this.table = new Table(skin);
        this.rejectButton = new TextButton("Reject", skin);
        this.acceptButton = new TextButton("Accept", skin);
        this.targetPlayer = targetPlayer;
        this.suggestion = new Label(targetPlayer + "'s suggestion\n", skin);
        this.requested = new Label(targetPlayer + "'s requested\n", skin);
        suggestion.setWidth(400);
        requested.setWidth(400);
        controller.setView(this, gameView, targetPlayer);


    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(this.stage);
        table.setFillParent(true);
        table.center();
        table.add(suggestion).padRight(100);
        table.add(requested).padLeft(100);
        table.row().pad(10, 0, 10, 0);
        table.add(rejectButton);
        table.add(acceptButton);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        timer += v;
        if (timer > 2f) {
            UpdateLabels();
            timer = 0f;
        }
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

    public TradeResponseController getController() {
        return controller;
    }

    public void setController(TradeResponseController controller) {
        this.controller = controller;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public TextButton getRejectButton() {
        return rejectButton;
    }

    public void setRejectButton(TextButton rejectButton) {
        this.rejectButton = rejectButton;
    }

    public TextButton getAcceptButton() {
        return acceptButton;
    }

    public void setAcceptButton(TextButton acceptButton) {
        this.acceptButton = acceptButton;
    }

    public Label getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Label suggestion) {
        this.suggestion = suggestion;
    }

    public Label getRequested() {
        return requested;
    }

    public void setRequested(Label requested) {
        this.requested = requested;
    }

    public String getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(String targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public void UpdateLabels() {
        suggestions = GameClient.getGameStateApiClient().getSuggestions(targetPlayer);
        requests = GameClient.getGameStateApiClient().getRequests(targetPlayer);
        StringBuilder temp = new StringBuilder();
        temp.append(targetPlayer).append("'s suggestion\n");
        for (String s : suggestions.keySet()) {
            temp.append(s).append(": ").append(suggestions.get(s)).append("\n");
        }
        suggestion.setText(temp.toString());
        StringBuilder temp1 = new StringBuilder();
        temp1.append(targetPlayer).append("'s requested\n");
        for (String s : requests.keySet()) {
            temp1.append(s).append(": ").append(requests.get(s)).append("\n");
        }
        requested.setText(temp1.toString());

    }

    public HashMap<String, Integer> getRequests() {
        return requests;
    }

    public void setRequests(HashMap<String, Integer> requests) {
        this.requests = requests;
    }

    public HashMap<String, Integer> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(HashMap<String, Integer> suggestions) {
        this.suggestions = suggestions;
    }
}
