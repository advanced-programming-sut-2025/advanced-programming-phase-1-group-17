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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GiftMenuController;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPack;


public class GiftMenu implements Screen {
    private final ScrollPane itemsPane;
    private final Skin skin;
    private final GiftMenuController controller;
    private final Player player;
    private final BackPack backPack;
    private final Table itemsTable;
    private final Table mainTable;
    private Stage stage;
    private final Label itemPickLabel;
    private final TextField amountTextField;
    private final TextButton back;
    private final TextButton gift;




    public GiftMenu(Player player, GiftMenuController controller, Skin skin, Player targetPlayer, GameView gameView, NPC npc) {
        this.player = player;
        this.controller = controller;
        this.skin = skin;
        this.backPack = player.getBackPack();
        this.itemsTable = new Table();
        this.mainTable = new Table();
        this.itemPickLabel = new Label("", skin);
        this.amountTextField = new TextField("", skin);
        amountTextField.setMessageText("amount");
        this.back = new TextButton("Back", skin);
        this.gift = new TextButton("Gift", skin);
        for (BackPackableType backPackableType : player.getBackPack().getBackPackItems().keySet()) {
            // 1. Prepare image button style:
            //TODO: need to delete this null-check
            if (backPackableType.getInventoryTexturePath() == null)
                continue;

            Texture itemTexture =new Texture (backPackableType.getInventoryTexturePath());
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backPack.getBackPackItems().get(backPackableType).size()),
                labelStyle);
            countLabel.setTouchable(Touchable.disabled);
            countLabel.setFontScale(1.3f); // Adjust size
            countLabel.setAlignment(Align.bottomRight);

            Stack itemStack = new Stack();
            itemStack.setSize(64, Math.min(64, backPack.getBackPackItems().size()));
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backPackableType);
                    itemPickLabel.setText("You chose the %s".formatted(backPackableType.getName()));
                }
            });

            itemsTable.add(itemStack).size(64, 64).pad(5);
        }

        this.itemsPane = new ScrollPane(itemsTable, skin);
        if (npc == null)
            controller.setView(this, targetPlayer,gameView);
        else
            controller.setView(this, npc,gameView);


    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        mainTable.setFillParent(true);
        mainTable.top().pad(10);
        mainTable.add(itemsPane).height(300).width(500).row();
        mainTable.row().pad(10,0,10,0);
        mainTable.add(itemPickLabel).row();
        mainTable.add(amountTextField).width(100).row();
        mainTable.add(gift).row();
        mainTable.add(back).row();




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

    public GiftMenuController getController() {
        return controller;
    }

    public Player getPlayer() {
        return player;
    }

    public BackPack getBackPack() {
        return backPack;
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

    public TextField getAmountTextField() {
        return amountTextField;
    }

    public TextButton getBackButton() {
        return back;
    }

    public TextButton getGift() {
        return gift;
    }
}
