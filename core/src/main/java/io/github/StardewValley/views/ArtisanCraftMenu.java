package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.controllers.UIControllers.ArtisanCraftMenuController;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.crafting.CraftingItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArtisanCraftMenu implements Screen {
    private final ArtisanCraftMenuController controller;
    private Stage stage;
    private final Skin skin;
    private final CraftingItem craftingItem;

    private final Table mainTable;
    private final Label titleLabel;
    private final Table inventoryTable;
    private final Table selectedItemsTable;

    private final Label errorLabel;
    private final TextButton craftButton;

    private final HashMap<BackpackableTypeDTO, Integer> selectedItems = new HashMap<>();


    public ArtisanCraftMenu(ArtisanCraftMenuController controller, Skin skin, CraftingItem craftingItem) {
        this.controller = controller;
        controller.setView(this);
        this.craftingItem = craftingItem;
        controller.setArtisan(craftingItem);

        this.skin = skin;

        this.mainTable = new Table();

        this.titleLabel = new Label("%s Crafting Menu".formatted(craftingItem.getName()), skin);
        this.titleLabel.setFontScale(2f);
        titleLabel.setAlignment(Align.center);

        this.inventoryTable = new Table();
        this.selectedItemsTable = new Table();
        this.errorLabel = new Label("", skin);
        this.errorLabel.setAlignment(Align.center);

        this.craftButton = new TextButton("Craft %s".formatted(craftingItem.getName()), skin);
        this.craftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //used to delay the method call so that the UI input is executed(selectedItems is updated properly)
                Gdx.app.postRunnable(controller::craft);
            }
        });
    }

    private void refreshInventory() {
        inventoryTable.clear();
        HashMap< BackpackableTypeDTO, Integer> backpackItems = GameClient.getGameStateApiClient().getBackpackItems().getItems();

        for (BackpackableTypeDTO item : backpackItems.keySet()) {
            if (item.getInventoryTexturePath() == null) continue;

            int count = backpackItems.get(item);
            if (count == 0) continue;

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(item.getInventoryTexturePath())));
            ImageButton itemButton = new ImageButton(style);

            Label countLabel = new Label("x" + count, skin);

            Table row = new Table();
            row.add(itemButton).size(48);
            row.add(new Label(item.getName(), skin)).padLeft(10);
            row.add(countLabel).padLeft(10);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.selectItem(item);
                    refreshSelectedItems();
                    refreshInventory();
                }
            });

            inventoryTable.add(row).left().row();
        }
    }

    private void refreshSelectedItems() {
        selectedItemsTable.clear();

        for (Map.Entry<BackpackableTypeDTO, Integer> entry : selectedItems.entrySet()) {
            BackpackableTypeDTO item = entry.getKey();
            int count = entry.getValue();

            Image image = new Image(new TextureRegionDrawable(new TextureRegion(
                GameAssetManagerClient.getGameAssetManager().getTexture(entry.getKey().getInventoryTexturePath()
            ))));
            Label nameLabel = new Label(item.getName(), skin);
            Label countLabel = new Label("x" + count, skin);

            Table row = new Table();
            row.add(image).size(48);
            row.add(nameLabel).padLeft(10);
            row.add(countLabel).padLeft(10);

            selectedItemsTable.add(row).left().row();
        }
    }

    @Override public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);
        mainTable.defaults().pad(10);
        mainTable.add(titleLabel).padTop(20).padBottom(30);

        Label inventoryLabel = new Label("Inventory", skin);
        Label selectedLabel = new Label("Selected Items", skin);

        ScrollPane inventoryScroll = new ScrollPane(inventoryTable, skin);
        ScrollPane selectedScroll = new ScrollPane(selectedItemsTable, skin);

        mainTable.add(inventoryLabel).left().padBottom(5).colspan(2).row();
        mainTable.add(inventoryScroll).width(300).height(400);
        mainTable.add(selectedScroll).width(300).height(400);
        mainTable.row();
        mainTable.add(errorLabel).expandX().left();
        mainTable.add(craftButton).right();

        stage.addActor(mainTable);

        refreshInventory();
        refreshSelectedItems();
    }

    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.handlePlayerInput();
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Label getErrorLabel() {
        return errorLabel;
    }

    public HashMap<BackpackableTypeDTO, Integer> getSelectedItems() {
        return selectedItems;
    }
}
