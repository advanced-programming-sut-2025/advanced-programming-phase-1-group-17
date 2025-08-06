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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.animal.Animal;
import io.github.StardewValley.shared.models.animal.AnimalPlace;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class AnimalPlaceShow implements Screen {
    private Stage stage;
    private Table table;
    private Label label;
    private Skin skin;

    public AnimalPlaceShow(Skin skin, GameView gameView, AnimalPlace animalPlace) {
        this.skin = skin;

        //stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.center();

        for (Animal animal : animalPlace.getAnimals()) {
            StringBuilder sb = new StringBuilder();
            Label label = new Label("", skin);
            sb.append(animal.getName())
                .append(" : ")
                .append(animal.isFedToday() ? "Fed Today" : "Not Fed")
                .append(" | ")
                .append(animal.isPettedToday() ? "Petted" : "Not Petted");
            label.setText(sb.toString());

            table.add(label);
            TextButton tb = new TextButton(animal.isOutside()?"isOutside":"isInside", skin);
            tb.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    animal.setOutside(!animal.isOutside());
                    tb.setText(animal.isOutside()?"isOutside":"isInside");
                }
            });
            table.add(tb);
            table.row();

        }

        //label.setWrap(true);
        TextButton isOpenButton = new TextButton(animalPlace.isOpen()?"Open":"close", skin);
        isOpenButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                animalPlace.setOpen(!animalPlace.isOpen());
                isOpenButton.setText(animalPlace.isOpen()?"Open":"close");
            }
        });
        table.add(isOpenButton);
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        table.row();
        table.add(backButton);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.7f); // پس‌زمینه نیمه‌شفاف
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
