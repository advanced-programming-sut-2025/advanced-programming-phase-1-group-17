package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GiftMenuController;
import io.github.StardewValley.controllers.TradeMenuController;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;

import java.util.HashMap;


public class TradeMenu implements Screen {
    private final ScrollPane itemsPane;
    private final Skin skin;
    private final TradeMenuController controller;
    private final Table itemsTable;
    private final Table mainTable;
    private Stage stage;
    private final Label itemPickLabel;
    private final TextButton back;
    private final TextButton addItem;
    private final TextButton removeitem;
    private final TextButton addCoin;
    private final TextButton removeCoin;
    private java.util.List<BackpackableTypeDTO> backPackItems;
    private final Label coin;
    private HashMap<String, Integer> items = new HashMap<>();
    private HashMap<String, Integer> required = new HashMap<>();
    private final ScrollPane scrollPane;
    private final Label label;
    private final Label requiredLabel;
    private final TextButton addItem2;
    private final TextButton removeItem2;
    private final TextField nameItem;
    private final TextField amountItem;
    private final Table requiredTable;
    private final Table lableBar;
    private final TextButton RegistertheOffer;
    private String targetPlayer;


    public TradeMenu(TradeMenuController controller, Skin skin, GameView gameView, String targetPlayer) {
        this.controller = controller;
        this.targetPlayer = targetPlayer;
        this.skin = skin;
        this.itemsTable = new Table();
        this.mainTable = new Table();
        this.itemPickLabel = new Label("", skin);
        this.back = new TextButton("Back", skin);
        this.addItem = new TextButton("addItem", skin);
        this.removeitem = new TextButton("removeItem", skin);
        this.addCoin = new TextButton("+", skin);
        this.removeCoin = new TextButton("-", skin);
        this.coin = new Label("0", skin);
        label = new Label("your suggestion : \n", skin);
        label.setWidth(300);
        label.setColor(Color.WHITE);
        scrollPane = new ScrollPane(label);
        scrollPane.setFadeScrollBars(false);
        requiredLabel = new Label("required : \n", skin);
        requiredLabel.setWidth(300);
        addItem2 = new TextButton("addItem", skin);
        amountItem = new TextField("", skin);
        amountItem.setWidth(100);
        amountItem.setMessageText("amount");
        nameItem = new TextField("", skin);
        nameItem.setWidth(200);
        nameItem.setMessageText("nameItem");
        removeItem2 = new TextButton("removeItem", skin);
        requiredTable = new Table(skin);
        RegistertheOffer = new TextButton("Register The Offer", skin);


        this.lableBar = new Table(skin);


        itemsTable.clear();
        backPackItems = GameClient.getGameStateApiClient().getBackpackItems().getItems();

        for (BackpackableTypeDTO backpackableTypeDTO : backPackItems) {
            Texture itemTexture = GameAssetManagerClient.getGameAssetManager().getTexture(backpackableTypeDTO.getInventoryTexturePath());
            if (itemTexture == null) {
                System.out.println(backpackableTypeDTO.getInventoryTexturePath() + " " + backpackableTypeDTO.getName());
                continue;
            }
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backpackableTypeDTO.getCountInBackPack()),
                labelStyle);
            countLabel.setTouchable(Touchable.disabled);
            countLabel.setFontScale(1.3f);
            countLabel.setAlignment(Align.bottomRight);

            Stack itemStack = new Stack();
            itemStack.setSize(64, Math.min(64, backpackableTypeDTO.getCountInBackPack()));
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backpackableTypeDTO);
                    itemPickLabel.setText("You picked: %s".formatted(backpackableTypeDTO.getName()));
                }
            });

            itemsTable.add(itemStack).size(64, 64).pad(5);
        }
        itemsTable.invalidate();


        this.itemsPane = new ScrollPane(itemsTable, skin);
        controller.setView(this, gameView);

    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(this.stage);
        mainTable.setFillParent(true);
        lableBar.setFillParent(true);
        requiredTable.setFillParent(true);
        requiredTable.center();
        lableBar.right();
        requiredTable.add(nameItem).row();
        requiredTable.add(amountItem).row();
        requiredTable.row().pad(10, 0, 10, 0);
        requiredTable.add(addItem2).row();
        requiredTable.row().pad(10, 0, 10, 0);
        requiredTable.add(requiredLabel).row();
        requiredTable.row().pad(10, 0, 10, 0);
        requiredTable.add(removeItem2).row();
        requiredTable.row().pad(10, 0, 10, 0);
        requiredTable.add(RegistertheOffer).row();
        mainTable.left().pad(10);
        mainTable.add(itemsPane).height(300).width(500).row();
        mainTable.row().pad(10, 0, 10, 0);
        mainTable.add(itemPickLabel).row();
        mainTable.add(addItem).row();
        mainTable.add(removeitem).row();
        mainTable.row().pad(10, 0, 10, 0);
        mainTable.add(coin).row();
        mainTable.row().pad(10, 0, 10, 0);
        mainTable.add(addCoin).row();
        mainTable.add(removeCoin).row();
        mainTable.row().pad(10, 0, 10, 0);
        mainTable.add(back).row();
        lableBar.add(label).row();
        stage.addActor(requiredTable);
        stage.addActor(lableBar);
        stage.addActor(mainTable);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
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

    public ScrollPane getItemsPane() {
        return itemsPane;
    }

    public Skin getSkin() {
        return skin;
    }

    public TradeMenuController getController() {
        return controller;
    }


    public Table getItemsTable() {
        return itemsTable;
    }

    public Table getMainTable() {
        return mainTable;
    }

    public Stage getStage() {
        return stage;
    }

    public Label getItemPickLabel() {
        return itemPickLabel;
    }

    public void setText(String text) {
        itemPickLabel.setText(text);
    }


    public TextButton getBackButton() {
        return back;
    }

    public TextButton getAddItem() {
        return addItem;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextButton getBack() {
        return back;
    }

    public TextButton getAddCoin() {
        return addCoin;
    }

    public TextButton getRemoveCoin() {
        return removeCoin;
    }

    public java.util.List<BackpackableTypeDTO> getBackPackItems() {
        return backPackItems;
    }

    public void setBackPackItems(java.util.List<BackpackableTypeDTO> backPackItems) {
        this.backPackItems = backPackItems;
    }

    public void setCoin(String coin) {
        this.coin.setText(coin);
    }

    public int getCoin() {
        return Integer.parseInt(coin.getText().toString());
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void addItem(BackpackableTypeDTO item) {
        if (this.items.containsKey(item.getName())) {
            if (this.items.get(item.getName()) + 1 <= item.getCountInBackPack()) {
                this.items.put(item.getName(), this.items.get(item.getName()) + 1);
            }
        } else {
            this.items.put(item.getName(), 1);
        }
    }

    public void removeItem(String item) {
        if (this.items.containsKey(item)) {
            if (this.items.get(item).equals(1)) {
                this.items.remove(item);
            } else
                this.items.put(item, this.items.get(item) - 1);
        }
    }

    public TextButton getRemoveitem() {
        return removeitem;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    public void refreshLabel() {
        StringBuilder text = new StringBuilder();
        this.items.put("coin", this.getCoin());
        text.append("your suggestion : \n");
        for (String item : this.items.keySet()) {
            text.append(item).append(" : ").append(this.items.get(item)).append("\n");
        }
        this.label.setText(text.toString());
        GameClient.gameStateApiClient.updateRequestAndSuggestions(items, required);
    }

    public HashMap<String, Integer> getRequired() {
        return required;
    }

    public void setRequired(HashMap<String, Integer> required) {
        this.required = required;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Label getLabel() {
        return label;
    }

    public Label getRequiredLabel() {
        return requiredLabel;
    }

    public TextButton getAddItem2() {
        return addItem2;
    }

    public TextField getNameItem() {
        return nameItem;
    }

    public TextField getAmountItem() {
        return amountItem;
    }

    public Table getRequiredTable() {
        return requiredTable;
    }

    public Table getLableBar() {
        return lableBar;
    }

    public TextButton getRegistertheOffer() {
        return RegistertheOffer;
    }

    public void refreshRequired() {
        StringBuilder text = new StringBuilder();
        text.append("required : \n");
        for (String item : this.required.keySet()) {
            text.append(item).append(" : ").append(this.required.get(item)).append("\n");
        }
        requiredLabel.setText(text.toString());
        GameClient.gameStateApiClient.updateRequestAndSuggestions(items, required);
    }

    public TextButton getRemoveItem2() {
        return removeItem2;
    }
}
