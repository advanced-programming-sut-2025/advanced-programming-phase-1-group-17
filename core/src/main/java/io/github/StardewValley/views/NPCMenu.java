package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.NPCMenuController;

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
    private TextButton button1;
    private Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private Label label1;
    private Table tableLabel;
    private Table table2;
    private Label questfinishLable;


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
        this.button4 = new TextButton("Abigail", skin);
        this.button5 = new TextButton("Harvey", skin);
        this.button6 = new TextButton("Lia", skin);
        this.button7 = new TextButton("Robin", skin);
        this.button8 = new TextButton("Sebastian", skin);
        this.button10 = new TextButton("Quests List", skin);
        this.button1 = new TextButton("Gift", skin);
        questfinishLable = new Label("", skin);
        questfinishLable.setFontScale(2f);
        questfinishLable.setPosition(
            Gdx.graphics.getWidth() / 2f - 600,
            Gdx.graphics.getHeight() / 2f + 300
        );
        questfinishLable.setColor(Color.GOLDENROD);


        controller.setView(this, gameView);
    }

    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
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
        table2.add(button1).width(300).height(80);
        stage.addActor(questfinishLable);
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


    public Table getTable2() {
        return table2;
    }

    public TextButton getButton1() {
        return button1;
    }

    public void showQuestDialog(String npc) {
        Dialog dialog = new Dialog("Choose a Quest", skin);

        for (int i = 0; i < 3; i++) {
            final int index = i;
            String questText = GameClient.gameStateApiClient.getQuestWithIndex(npc, i);

            TextButton questButton = new TextButton(questText, skin);
            questButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dialog.hide();
                    controller.onQuestSelected(index + 1);
                }
            });

            dialog.getContentTable().add(questButton).pad(5).row();
        }

        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    public void showQuestFinishAnimation() {

        questfinishLable.addAction(Actions.sequence(
            Actions.fadeIn(0.5f),
            Actions.scaleTo(1.5f, 1.5f, 0.3f),
            Actions.delay(3.0f),
            Actions.fadeOut(0.5f),
            Actions.run(() -> {
                questfinishLable.setScale(1f);
            })
        ));
    }

    public Label getQuestFinishLabel() {
        return questfinishLable;
    }
}
