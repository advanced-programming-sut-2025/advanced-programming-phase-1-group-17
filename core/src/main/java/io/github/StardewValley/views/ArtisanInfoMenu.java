package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.controllers.ArtisanInfoMenuController;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.artisan.IngredientGroup;
import io.github.StardewValley.shared.models.crafting.CraftingItem;

public class ArtisanInfoMenu implements Screen {
    private Stage stage;
    private final ArtisanInfoMenuController controller;
    private final CraftingItem craftingItem;

    private final TextButton cheatReadyButton;
    private final TextButton cancelButton;
    private final TextButton takeProduct;
    private final Label messageLabel;

    private final Table recipeTable;
    private final ScrollPane recipePane;
    private final Table mainTable;

    public ArtisanInfoMenu(ArtisanInfoMenuController controller, Skin skin, CraftingItem craftingItem) {
        this.controller = controller;
        controller.setView(this);
        this.craftingItem = craftingItem;
        this.cheatReadyButton = new TextButton("Cheat Ready", skin);
        this.messageLabel = new Label("", skin);

        this.cheatReadyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.takeProduct();
            }
        });

        this.cancelButton = new TextButton("Cancel Crafting", skin);
        this.cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.cancel();
            }
        });

        this.takeProduct = new TextButton("Take Product", skin);
        this.takeProduct.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.takeProduct();
            }
        });

        this.recipeTable = new Table();
        this.recipeTable.add(new Label("Recipes", skin)).center();
        for (ArtisanProductType artisanProductType : ArtisanProductType.values()) {
            if (artisanProductType.getArtisan().equals(craftingItem.getTargetItem())) {
                //Table itemTable = new Table();
                StringBuilder description = new StringBuilder();
                description.append("\nItem Name: %s => Ingredients:\n".formatted(artisanProductType.getName()));

                artisanProductType.getIngredients().forEach((key, value) -> {
                    String itemName;
                    if (key instanceof IngredientGroup ingredientGroup) {
                        itemName = ingredientGroup.name();
                    } else
                        itemName = ((BackPackableType) key).getName();
                    description.append("\t%s (Count: %d)\n".formatted(itemName, value));
                });
                //itemTable.add(new TextArea(description.toString().trim(), skin));
                //recipeTable.add(itemTable).expandX().row();
                recipeTable.add(new Label(description.toString(), skin)).expandX().row();
            }
        }
        this.recipePane = new ScrollPane(recipeTable);
        this.mainTable = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);

        mainTable.top().padTop(20);
        mainTable.add(messageLabel).colspan(2).padBottom(10).row();
        mainTable.add(recipePane).colspan(2).width(600).height(300).padBottom(20).row();

        Table buttonRow = new Table();
        if (craftingItem.getArtisanProductInProgress() != null && craftingItem.getArtisanProductInProgress().isReady())
            buttonRow.add(takeProduct).pad(10);
        buttonRow.add(cheatReadyButton).pad(10);
        buttonRow.add(cancelButton).pad(10);

        mainTable.add(buttonRow).colspan(2).center().row();

        if (craftingItem.getArtisanProductInProgress() != null) {
            Label craftingLabel = new Label("Currently crafting: " +
                craftingItem.getArtisanProductInProgress().getType().getName(),
                GameAssetManager.getGameAssetManager().getSkin());

            ProgressBar craftingProgress = craftingItem.getProgressBar();
            if (craftingProgress != null) {
                craftingProgress.setWidth(300);
                craftingProgress.setHeight(50f);
            }

            mainTable.add(craftingLabel).colspan(2).pad(10).row();
            mainTable.add(craftingProgress).colspan(2).pad(10).row();
        }

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.handlePlayerInput();
        stage.act(delta);
        stage.draw();
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

    @Override
    public void dispose() {

    }

    public CraftingItem getCraftingItem() {
        return craftingItem;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
