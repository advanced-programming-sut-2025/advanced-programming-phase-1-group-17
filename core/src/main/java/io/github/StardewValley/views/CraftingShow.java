package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.controllers.CraftingController;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.crafting.CraftingItemType;

public class CraftingShow implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Window infoWindow;
    private GameView gameView;
    private Label ingredients;
    private CraftingController controller;
    private Label errorMessage;
    private TextButton backButton;


    public CraftingShow(Skin skin, GameView gameView,CraftingController controller) {
        this.skin = skin;
        this.gameView = gameView;
        this.stage = new Stage();
        this.table = new Table();
        this.controller = controller;
        this.errorMessage = new Label("", skin);
        this.backButton = new TextButton("back",skin);
        controller.setView(this,gameView);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        for (CraftingItemType type : CraftingItemType.values()) {


            Texture texture = type.getInventoryTexture();
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            ImageButton btn = new ImageButton(style);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Vector2 stageCoords = btn.localToStageCoordinates(new Vector2(0, 0));
                    if(infoWindow != null) {
                        infoWindow.remove();
                        infoWindow = null;
                    }
                    else{
                        showDetails(type, stageCoords.x, stageCoords.y);
                    }
                }
            });

            table.add(btn).size(64, 64).pad(5);
        }

        stage.addActor(table);
        backButton.setPosition(Gdx.graphics.getWidth()-backButton.getWidth(),Gdx.graphics.getHeight()-backButton.getHeight());
        stage.addActor(backButton);
    }

    private void showDetails(CraftingItemType type, float x, float y) {
        if (infoWindow != null && infoWindow.hasParent()) {
            infoWindow.remove();
        }

        infoWindow = new Window("",skin);
        //infoWindow.pad(100);
        ingredients = new Label("first sentence",skin);
        controller.handleIngredientsLabel(type);
        errorMessage.setText("");
        //infoWindow.add(new Label("You clicked on: " + type.getName(), skin)).pad(10).row();

        TextButton okButton = new TextButton("Craft", skin);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleIngredients(type);
            }
        });
        infoWindow.add(ingredients).pad(10);
        infoWindow.row();
        infoWindow.add(errorMessage);
        infoWindow.row();
        infoWindow.add(okButton);
        infoWindow.pack();
        infoWindow.setSize(600, 300);
        infoWindow.setPosition(x + 70, y);
        stage.addActor(infoWindow);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Label getIngredients() {
        return ingredients;
    }

    public void setIngredients(Label ingredients) {
        this.ingredients = ingredients;
    }

    public Label getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Label errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }
}
