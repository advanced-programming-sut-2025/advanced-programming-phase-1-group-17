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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.controllers.ShippingBinScreenController;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.market.ShippingBin;

public class ShippingBinScreen implements Screen {
    private Stage stage;
    private final ShippingBin shippingBin;
    private final ShippingBinScreenController controller;

    private BackPackableType selectedItem;
    private int selectedAvailable;
    private int quantityToSell;

    private final Label titleLabel;
    private final Label errorLabel;
    private final TextButton plusButton;
    private final TextButton minusButton;

    private final Table mainTable;

    public ShippingBinScreen(ShippingBin shippingBin, ShippingBinScreenController controller, Skin skin) {
        this.controller = controller;
        this.shippingBin = shippingBin;

        this.titleLabel = new Label("Selling Products Menu", skin);
        this.errorLabel = new Label("", skin);
        this.plusButton = new TextButton("+", skin);
        this.minusButton = new TextButton("-", skin);
        this.mainTable = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        BackPack backPack = player.getBackPack();
        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();

        quantityToSell = 0;

        Table itemTable = new Table();
        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        Label quantityLabel = new Label("Sell: 0", skin);

        for (BackPackableType itemType : backPack.getBackPackItems().keySet()) {
            int available = backPack.getBackPackItems().get(itemType).size();
            if (itemType.getInventoryTexture() == null) continue;
            //TODO remove new Texture
            Texture texture = new Texture(itemType.getInventoryTexture());
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
            ImageButton itemButton = new ImageButton(style);

            Label countLabel = new Label("x" + available, skin);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedItem = itemType;
                    selectedAvailable = available;
                    quantityToSell = 0;
                    quantityLabel.setText("Sell: " + quantityToSell);
                }
            });

            Table singleItemRow = new Table();
            singleItemRow.add(itemButton).size(64).pad(5);
            singleItemRow.add(countLabel).pad(5);

            itemTable.add(singleItemRow).left().row();
        }

        // Control Buttons at Bottom
        TextButton plus = new TextButton("+", skin);
        plus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null && quantityToSell < selectedAvailable) {
                    quantityToSell++;
                    quantityLabel.setText("Sell: " + quantityToSell);
                }
            }
        });

        TextButton minus = new TextButton("-", skin);
        minus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null && quantityToSell > 0) {
                    quantityToSell--;
                    quantityLabel.setText("Sell: " + quantityToSell);
                }
            }
        });

        TextButton sellButton = new TextButton("Sell", skin);
        sellButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null && quantityToSell > 0) {
                    controller.sellItem(selectedItem, quantityToSell, player);
                    quantityToSell = 0;
                    quantityLabel.setText("Sell: 0");
                    // refresh item quantity if needed
                }
            }
        });

        // Layout
        Table controlsTable = new Table();
        controlsTable.add(minus).size(40).pad(5);
        controlsTable.add(quantityLabel).pad(5);
        controlsTable.add(plus).size(40).pad(5);
        controlsTable.add(sellButton).pad(10);

        mainTable.add(scrollPane).expand().fill().pad(10).row();
        mainTable.add(controlsTable).pad(10).bottom();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
        controller.handlePlayerInput();
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

    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
