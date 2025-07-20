package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.ItemMenuController;
import io.github.StardewValley.controllers.StoreMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.enums.Season;
import io.github.StardewValley.models.market.*;

public class StoreMenu implements Screen {
    private Stage stage;
    private final StoreMenuController controller;
    private final StoreType storeType;

    private final Label titleLabel;
    private final CheckBox showAvailableBox;

    private final Table itemsTable;
    private final ScrollPane itemsPane;

    private final Table upgradeTable;
    private final ScrollPane upgradePane;

    private final Label errorLabel;
    private final TextButton exitButton;

    public StoreMenu(StoreMenuController controller, Skin skin, StoreType storeType) {
        this.controller = controller;
        this.controller.setView(this);
        this.storeType = storeType;

        this.titleLabel = new Label("Store Name: %s".formatted(storeType), skin);
        this.itemsTable = new Table();
        controller.showAllProducts();
        this.itemsPane = new ScrollPane(itemsTable);
        itemsPane.setFadeScrollBars(false);

        this.showAvailableBox = new CheckBox("Show Available Only", skin);
        showAvailableBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (showAvailableBox.isChecked()) {
                    controller.showAllAvailableProducts();
                    controller.addAvailableUpgradeServices();
                } else {
                    controller.showAllProducts();
                    controller.addUpgradeServices();
                }
            }
        });

        this.upgradeTable = new Table();
        this.upgradePane = new ScrollPane(upgradeTable);
        upgradePane.setFadeScrollBars(false);

        this.errorLabel = new Label("", skin);

        this.exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().pad(10);
        mainTable.add(titleLabel).colspan(2).row();
        mainTable.add(showAvailableBox).colspan(2).pad(10).row();
        mainTable.add(itemsPane).width(1000).height(300).left().colspan(2).row();
        if (storeType.equals(StoreType.Blacksmith)) {
            controller.addUpgradeServices();
            mainTable.add(upgradePane).width(1000).height(150).left().colspan(2).row();
        }
        mainTable.add(errorLabel).colspan(2);
        mainTable.add(exitButton).colspan(2);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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
        stage.dispose();
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public Table getUpgradeTable() {
        return upgradeTable;
    }

    public Table getItemsTable() {
        return itemsTable;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
