package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.NPCMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Player;

public class NPCMenu implements Screen {
    private NPCMenuController controller;
    private Skin skin;
    private Stage stage;
    private Table table;
    private Label label;
    private TextButton backButton;
    private TextButton button4;
    private TextButton button5;
    private TextButton button6;
    private TextButton button7;
    private TextButton button8;
    private TextButton button10;
    private TextButton button13;
    private TextButton button1;
    private Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private Label label1;
    private Table tableLabel;
    private Table table2;

    public NPCMenu(NPCMenuController controller, Skin skin, GameView gameView) {
        label1 = new Label("", skin);
        label1.setWrap(true);
        label1.setColor(Color.GREEN);
        tableLabel = new Table(skin);


        this.controller = controller;
        this.skin = skin;
        this.table = new Table(skin);

        this.table2 = new Table(skin);
        this.backButton = new TextButton("Back", skin);
        this.label = new Label("", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(1);
        this.button4 = new TextButton(App.getCurrentGame().getNPCs().get(0).getName(), skin);
        this.button5 = new TextButton(App.getCurrentGame().getNPCs().get(1).getName(), skin);
        this.button6 = new TextButton(App.getCurrentGame().getNPCs().get(2).getName(), skin);
        this.button7 = new TextButton(App.getCurrentGame().getNPCs().get(3).getName(), skin);
        this.button8 = new TextButton(App.getCurrentGame().getNPCs().get(4).getName(), skin);
        this.button10 = new TextButton("Quests List", skin);
        this.button13 = new TextButton("Quests Finish", skin);
        this.button1 = new TextButton("Gift", skin);


        //TODO


        controller.setView(this, gameView);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table2.setFillParent(true);
        tableLabel.setFillParent(true);
        tableLabel.add(label).center().top().padBottom(900);
        table.left();
        table2.right();
        table.add(button4).width(200).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(button5).width(200).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(button6).width(200).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(button7).width(200).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(button8).width(200).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).width(200).height(80);
        table2.add(button10).width(300).height(80);
        table2.row().pad(10, 0, 10, 0);
        table2.add(button13).width(300).height(80);
        table2.row().pad(10, 0, 10, 0);
        table2.add(button1).width(300).height(80);
        stage.addActor(table2);
        stage.addActor(tableLabel);
        stage.addActor(table);

    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        OrthographicCamera uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCam.update();
        Main.getBatch().setProjectionMatrix(uiCam.combined);
        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    public NPCMenuController getController() {
        return controller;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public Table getTable() {
        return table;
    }

    public Label getLabel() {
        return label;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getButton4() {
        return button4;
    }

    public TextButton getButton5() {
        return button5;
    }

    public TextButton getButton6() {
        return button6;
    }

    public TextButton getButton7() {
        return button7;
    }

    public TextButton getButton8() {
        return button8;
    }

    public void setError(String error) {
        this.label.setText(error);
    }

    public void setText(String text) {
        this.label1.setText(text);
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Label getLabel1() {
        return label1;
    }



    public TextButton getButton10() {
        return button10;
    }


    public TextButton getButton13() {
        return button13;
    }


    public Table getTable2() {
        return table2;
    }

    public TextButton getButton1() {
        return button1;
    }
}
