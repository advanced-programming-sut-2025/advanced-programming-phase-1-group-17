package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.controllers.UIControllers.ItemMenuController;
import io.github.StardewValley.shared.models.market.ShopItemDTO;
import io.github.StardewValley.shared.models.market.StoreType;

public class ItemMenu implements Screen {
    private Stage stage;
    private final ItemMenuController controller;
    private final ShopItemDTO item;
    private final StoreMenu storeMenu;
    private final StoreType storeType;

    private final Label titleLabel;
    private final TextButton plusButton;
    private final TextButton minusButton;
    private final Label itemNameLabel;

    private int count = 0;
    private final Label countLabel;
    private final Label dailyLimitLabel;

    private final Label errorLabel;
    private final TextButton purchaseButton;
    private final TextButton exitButton;

    public ItemMenu(ItemMenuController controller, Skin skin, ShopItemDTO item, StoreMenu storeMenu, StoreType storeType) {
        this.controller = controller;
        this.controller.setView(this);
        this.item = item;
        this.storeMenu = storeMenu;
        this.storeType = storeType;

        this.titleLabel = new Label("Item Buying Menu", skin);
        this.itemNameLabel = new Label(item.getType(), skin);

        this.plusButton = new TextButton("+", skin);
        this.plusButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.plus();
            }
        });

        this.minusButton = new TextButton("-", skin);
        this.minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.minus();
            }
        });

        this.purchaseButton = new TextButton("Purchase", skin);
        this.purchaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                errorLabel.setText(controller.purchase().message());
            }
        });

        this.errorLabel = new Label("", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });
        this.countLabel = new Label("Count: %d".formatted(count), skin);
        this.dailyLimitLabel = new Label("Items left today: %s".formatted(
            (item.getDailyLimit() > 200) ? "INFINITY" : item.getDailyLimit()
        ), skin);
    }

    @Override
    public void show() {
        //stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);

        table.add(titleLabel).colspan(3).row();
        table.add(itemNameLabel).colspan(3).row();
        table.add(minusButton);
        table.add(countLabel);
        table.add(plusButton).row();
        table.add(dailyLimitLabel).colspan(3).row();
        table.add(purchaseButton).colspan(3).row();
        table.add(exitButton).colspan(3).row();
        table.add(errorLabel).colspan(3).row();

        stage.addActor(table);
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
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

    public Label getErrorLabel() {
        return errorLabel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShopItemDTO getItem() {
        return item;
    }

    public Label getCountLabel() {
        return countLabel;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public StoreMenu getStoreMenu() {
        return storeMenu;
    }
}
