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
import io.github.StardewValley.controllers.InventoryController;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.tools.BackPack;
import io.github.StardewValley.models.tools.ToolType;

public class InventoryView implements Screen {
    private Stage stage;
    private final InventoryController controller;
    private final GameView gameView;

    private final Label titleLabel;
    private final Table mainTable;

    private final Player player;
    private final BackPack backPack;
    private final Table itemsTable;
    private final ScrollPane itemsPane;
    private final Label itemPickLabel;
    private final Label inventoryLabel;

    private final TextButton trashButton;
    private final TextButton skillMenuButton;
    private final TextButton socialMenuButton;
    private final TextButton mapButton;
    private final TextButton exitButton;

    public InventoryView(InventoryController controller, Skin skin, Player player, GameView gameView) {
        this.controller = controller;
        this.controller.setView(this);
        this.gameView = gameView;
        this.mainTable = new Table();

        this.titleLabel = new Label("Journal", skin);
        this.player = player;
        this.backPack = player.getBackPack();

        this.itemPickLabel = new Label("", skin);
        this.inventoryLabel = new Label("Inventory", skin);
        this.itemsTable = new Table();
        for (BackPackableType backPackableType : player.getBackPack().getBackPackItems().keySet()) {
            // 1. Prepare image button style:
            //TODO: need to delete this null-check
            if (backPackableType.getInventoryTexture() == null)
                continue;

            Texture itemTexture = backPackableType.getInventoryTexture();
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            // 2. Prepare label for count:
            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backPack.getBackPackItems().get(backPackableType).size()),
                labelStyle);
            countLabel.setTouchable(Touchable.disabled);
            countLabel.setFontScale(1.3f); // Adjust size
            countLabel.setAlignment(Align.bottomRight);

            // 3. Use a Stack:
            Stack itemStack = new Stack();
            itemStack.setSize(64, Math.min(64, backPack.getBackPackItems().size()));
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            // 4. Handle click logic:
            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backPackableType, player);
                    itemPickLabel.setText("You picked: %s".formatted(backPackableType.getName()));
                }
            });

            // 5. Add to inventory layout:
            itemsTable.add(itemStack).size(64, 64).pad(5);
        }

        this.itemsPane = new ScrollPane(itemsTable, skin);

        this.trashButton = new TextButton("Trash picked Item", skin);
        this.trashButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleItemTrash(player);
            }
        });

        this.skillMenuButton = new TextButton("Skill Menu", skin);
        this.skillMenuButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               controller.handleSkillMenu();
           }
        });

        this.socialMenuButton = new TextButton("Social Menu", skin);
        this.socialMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSocialMenu();
            }
        });

        this.mapButton = new TextButton("Map", skin);
        this.mapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleMap();
            }
        });

        this.exitButton = new TextButton("Exit", skin);
        this.exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToGameView();
            }
        });
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        mainTable.setFillParent(true);
        mainTable.top().pad(10);

        // Example layout:
        mainTable.add(titleLabel).colspan(4).center().row();
        mainTable.add(inventoryLabel).left().row();
        mainTable.add(itemsPane).height(300).width(500).row();
        mainTable.add(itemPickLabel).left().row();
        mainTable.add(trashButton).left().padTop(10).row();
        mainTable.add(skillMenuButton).left().padTop(10).row();
        mainTable.add(socialMenuButton).left().padTop(10).row();
        mainTable.add(mapButton).left().padTop(10).row();
        mainTable.add(exitButton).left().row();

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

    public Label getItemPickLabel() {
        return itemPickLabel;
    }

    public Player getPlayer() {
        return player;
    }

    public GameView getGameView() {
        return gameView;
    }


    public void refreshInventoryItems() {
        itemsTable.clear();

        for (BackPackableType backPackableType : player.getBackPack().getBackPackItems().keySet()) {
            if (backPackableType.getInventoryTexture() == null) continue;
            if (backPack.getBackPackItems().get(backPackableType).isEmpty()) continue;

            Texture itemTexture = backPackableType.getInventoryTexture();
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backPack.getBackPackItems().get(backPackableType).size()), labelStyle);
            countLabel.setFontScale(1.3f);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setTouchable(Touchable.disabled);

            Stack itemStack = new Stack();
            itemStack.setSize(64, 64);
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backPackableType, player);
                    itemPickLabel.setText("You picked: %s".formatted(backPackableType.getName()));
                }
            });

            itemsTable.add(itemStack).size(64, 64).pad(5);
        }

        itemsTable.invalidate();
    }

    public void showOnlyTools() {
        itemsTable.clear();

        for (BackPackableType backPackableType : player.getBackPack().getBackPackItems().keySet()) {
            if (backPackableType.getInventoryTexture() == null) continue;
            if (!(backPackableType instanceof ToolType)) continue;
            if (backPack.getBackPackItems().get(backPackableType).isEmpty()) continue;

            Texture itemTexture = backPackableType.getInventoryTexture();
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backPack.getBackPackItems().get(backPackableType).size()), labelStyle);
            countLabel.setFontScale(1.3f);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setTouchable(Touchable.disabled);

            Stack itemStack = new Stack();
            itemStack.setSize(64, 64);
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backPackableType, player);
                    itemPickLabel.setText("You picked: %s".formatted(backPackableType.getName()));
                }
            });

            itemsTable.add(itemStack).size(64, 64).pad(5);
        }

        itemsTable.invalidate();
    }
}
