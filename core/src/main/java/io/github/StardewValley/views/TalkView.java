package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.TalkController;
import io.github.StardewValley.models.App;

public class TalkView implements Screen {

    private TalkController controller;
    private Skin skin;
    private Stage stage;
    private Table table;
    private Label label;
    private TextButton backButton;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextButton button4;
    private TextButton button5;
    private TextButton button6;
    private TextButton button7;
    private TextButton button8;
    private Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private Window window;
    private Label label1;
    private TextButton closeX;


    public TalkView(TalkController controller, Skin skin, GameView gameView) {
        label1 = new Label("", skin);
        label1.setWrap(true);
        ScrollPane scrollPane = new ScrollPane(label1, skin);
        scrollPane.setScrollingDisabled(true, false);
        window = new Window("                     messages", skin);
        window.setSize(500, 500);
        window.setPosition(
            (Gdx.graphics.getWidth() - window.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - window.getHeight()) / 2f
        );
        window.add(scrollPane).expand().fill().row();
        closeX = new TextButton("×", skin);
        closeX.getLabel().setFontScale(1.2f);
        closeX.setWidth(30);
        closeX.setHeight(30);
        closeX.pad(0);
        window.getTitleTable().add(closeX).right().padRight(5);


        this.controller = controller;
        this.skin = skin;
        this.table = new Table(skin);
        this.backButton = new TextButton("Back", skin);
        this.label = new Label("", skin);
        label.setColor(1, 0, 0, 1);
        this.button1 = new TextButton(App.getCurrentGame().getPlayers().get(0).getUser().getUsername(), skin);
        this.button2 = new TextButton(App.getCurrentGame().getPlayers().get(1).getUser().getUsername(), skin);
        this.button3 = new TextButton(App.getCurrentGame().getPlayers().get(2).getUser().getUsername(), skin);
        this.button4 = new TextButton(App.getCurrentGame().getNPCs().get(0).getName(), skin);
        this.button5 = new TextButton(App.getCurrentGame().getNPCs().get(1).getName(), skin);
        this.button6 = new TextButton(App.getCurrentGame().getNPCs().get(2).getName(), skin);
        this.button7 = new TextButton(App.getCurrentGame().getNPCs().get(3).getName(), skin);
        this.button8 = new TextButton(App.getCurrentGame().getNPCs().get(4).getName(), skin);




        controller.setView(this, gameView);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        label.setPosition(800,1000);
        table.left();
        table.add(button1).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button2).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button3).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button4).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button5).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button6).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button7).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(button8).width(200).height(100);
        table.row().pad(10,0,10,0);
        table.add(backButton).width(200).height(100);
//        stage.addActor(window);
        stage.addActor(label);
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

    public TalkController getController() {
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

    public TextButton getButton1() {
        return button1;
    }

    public TextButton getButton2() {
        return button2;
    }

    public TextButton getButton3() {
        return button3;
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
        this.label.setText(text);
    }
    public Window getWindow() {
        return window;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public TextButton getCloseX() {
        return closeX;
    }
}
