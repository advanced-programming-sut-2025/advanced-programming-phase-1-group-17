package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.ChooseMapController;

public class chooseMap implements Screen {
    private ChooseMapController controller;
    private Skin skin;
    private Stage stage;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private Table table;
    private TextButton Next;
    private Label playerUserName;
    private Image imageMap1;
    private Image imageMap2;





    public chooseMap(ChooseMapController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        table = new Table();
        Next = new TextButton("Select", skin);
        Next.setColor(0,1,0,1);
        checkBox1 = new CheckBox("Map1", skin);
        checkBox2 = new CheckBox("Map2", skin);
        playerUserName = new Label("", skin);
        playerUserName.setColor(0,1,0,1);
        this.imageMap1 = new Image(new Texture("map1.png"));
        this.imageMap2 = new Image(new Texture("map2.png"));

        checkBox1.setChecked(true);
        checkBox2.setChecked(false);
        this.controller.setView(this);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.add(playerUserName).center();
        table.row().pad(10,0,10,0);
        table.row().pad(10,0,10,0);
        table.add(imageMap1).center();
        table.row().pad(10,0,10,0);
        table.add(checkBox1).center();
        table.row().pad(10,0,10,0);
        table.add(imageMap2).center();
        table.row().pad(10,0,10,0);
        table.add(checkBox2).center();
        table.row().pad(10,0,10,0);
        table.add(Next);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        if (!controller.done) {
            try {
                setPlayerUserName(GameClient.getGameStateApiClient().getUserName()
                    + " ,Enter the number of the gameMapType you would like to play (1 or 2)");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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

    public ChooseMapController getController() {
        return controller;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public CheckBox getCheckBox1() {
        return checkBox1;
    }

    public CheckBox getCheckBox2() {
        return checkBox2;
    }

    public Table getTable() {
        return table;
    }

    public TextButton getNext() {
        return Next;
    }
    public void setPlayerUserName(String playerUserName) {
        this.playerUserName.setText(playerUserName);
    }
}
