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
import io.github.StardewValley.controllers.TalkController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.shared.models.Player;

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
    private TextButton button9;
    private TextButton button10;
    private TextButton button11;
    private TextButton button12;
    private TextButton button13;
    private TextButton button14;
    private TextButton button15;
    private TextButton button16;
    private TextField giftRate;
    private TextField giftNumber;
    private TextButton button17;
    private TextButton button18;
    private Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private Window window;
    private Label label1;
    private Table tableLabel;
    private TextButton closeX;
    private Table table2;
    private TextField textField;
    private TextButton send;
    private Player[] players = new Player[3];


    public TalkView(TalkController controller, Skin skin, GameView gameView) {
        label1 = new Label("", skin);
        label1.setWrap(true);
        label1.setColor(Color.GREEN);
        tableLabel = new Table(skin);

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
        this.textField = new TextField("", skin);
        textField.setMessageText("Message");
        textField.setWidth(400);
        this.send = new TextButton("Send", skin);
        send.setWidth(200);
        textField.setVisible(false);
        send.setVisible(false);
        textField.setPosition(700,600);
        send.setPosition(1100,600);

        this.table2 = new Table(skin);
        this.backButton = new TextButton("Back", skin);
        this.label = new Label("", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(1);

        int i = 0;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC") || player.equals(App.getCurrentGame().getCurrentPlayingPlayer()))
                continue;
            players[i] = player;
            i++;
        }
        this.button1 = new TextButton(players[0].getUser().getUsername(), skin);
        this.button2 = new TextButton(players[1].getUser().getUsername(), skin);
        this.button3 = new TextButton(players[2].getUser().getUsername(), skin);
        this.button9 = new TextButton("messages", skin);
        this.button11 = new TextButton("Trade List", skin);
        this.button10 = new TextButton("Trade History", skin);
        this.button12 = new TextButton("Gift History", skin);
        this.button13 = new TextButton("Talk History", skin);
        this.button14 = new TextButton("Talk", skin);
        this.button15 = new TextButton("Gift Rate", skin);
        this.giftRate = new TextField("", skin);
        giftRate.setMessageText("Rate[0-5]");
        this.giftNumber = new TextField("", skin);
        this.giftNumber.setMessageText("Number");
        this.button16 = new TextButton("Send Rate", skin);
        this.button17 = new TextButton("accept the marriage proposal", skin);
        this.button18 = new TextButton("reject the marriage proposal", skin);
        button17.setPosition(700,60);
        button18.setPosition(700,10);
        button17.setWidth(600);
        button18.setWidth(600);

        giftRate.setWidth(200);
        giftNumber.setWidth(200);
        button16.setWidth(200);
        giftNumber.setPosition(700,600);
        giftRate.setPosition(900,600);
        button16.setPosition(700,550);
        button16.setVisible(false);
        giftNumber.setVisible(false);
        giftRate.setVisible(false);
//        this.button14 = new TextButton("Talk", skin);
//        this.button15 = new TextButton("Gift", skin);



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
        table.add(button1).width(200).height(80);
        table.row().pad(10,0,10,0);
        table.add(button2).width(200).height(80);
        table.row().pad(10,0,10,0);
        table.add(button3).width(200).height(80);
        table.row().pad(10,0,10,0);
        table.add(backButton).width(200).height(80);
        table2.add(button9).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button14).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button10).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button11).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button12).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button13).width(300).height(80);
        table2.row().pad(10,0,10,0);
        table2.add(button15).width(300).height(80);
        stage.addActor(button17);
        stage.addActor(button18);
        stage.addActor(giftNumber);
        stage.addActor(giftRate);
        stage.addActor(button16);
        stage.addActor(textField);
        stage.addActor(send);
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

    public void setError(String error) {
        this.label.setText(error);
    }
    public void setText(String text) {
        this.label1.setText(text);
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

    public Label getLabel1() {
        return label1;
    }

    public TextButton getButton9() {
        return button9;
    }

    public Player[] getPlayers() {
        return players;
    }

    public TextButton getButton10() {
        return button10;
    }

    public TextButton getButton11() {
        return button11;
    }

    public TextButton getButton12() {
        return button12;
    }

    public TextButton getButton13() {
        return button13;
    }


    public Table getTable2() {
        return table2;
    }

    public TextButton getButton14() {
        return button14;
    }

    public TextField getTextField() {
        return textField;
    }

    public TextButton getSend() {
        return send;
    }

    public TextButton getButton15() {
        return button15;
    }

    public TextButton getButton16() {
        return button16;
    }

    public TextField getGiftRate() {
        return giftRate;
    }

    public TextField getGiftNumber() {
        return giftNumber;
    }

    public Table getTableLabel() {
        return tableLabel;
    }

    public TextButton getButton18() {
        return button18;
    }

    public TextButton getButton17() {
        return button17;
    }
}
