package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.controllers.ArtisanCraftMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.BackPackable;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.crafting.CraftingItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArtisanCraftMenu implements Screen {
    private final ArtisanCraftMenuController controller;
    private Stage stage;
    private final Skin skin;
    private final CraftingItem craftingItem;

    private final Table mainTable;
    private final Table inventoryTable;
    private final Table selectedItemsTable;

    private final Label errorLabel;
    private final TextButton craftButton;

    private final HashMap<BackPackableType, ArrayList<BackPackable>> selectedItems = new HashMap<>();

    public ArtisanCraftMenu(ArtisanCraftMenuController controller, Skin skin, CraftingItem craftingItem) {
        this.controller = controller;
        controller.setView(this);
        this.craftingItem = craftingItem;
        controller.setArtisan(craftingItem);

        this.skin = skin;

        this.mainTable = new Table();
        this.inventoryTable = new Table();
        this.selectedItemsTable = new Table();
        this.errorLabel = new Label("", skin);

        this.craftButton = new TextButton("Craft", skin);
        this.craftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.craft();
            }
        });
    }

    private void refreshInventory() {
        inventoryTable.clear();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        for (BackPackableType item : player.getBackPack().getBackPackItems().keySet()) {
            if (item.getInventoryTexture() == null) continue;

            int count = player.getBackPack().getBackPackItems().get(item).size();
            if (count == 0) continue;

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(item.getInventoryTexture()));
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
                }
            });

            inventoryTable.add(row).left().row();
        }
    }

    private void refreshSelectedItems() {
        selectedItemsTable.clear();

        for (Map.Entry<BackPackableType, ArrayList<BackPackable>> entry : selectedItems.entrySet()) {
            BackPackableType item = entry.getKey();
            int count = entry.getValue().size();

            Image image = new Image(new TextureRegionDrawable(new TextureRegion(item.getInventoryTexture())));
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

    public HashMap<BackPackableType, ArrayList<BackPackable>> getSelectedItems() {
        return selectedItems;
    }
}
