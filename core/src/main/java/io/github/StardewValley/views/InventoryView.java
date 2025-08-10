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
import io.github.StardewValley.controllers.InventoryController;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.tools.ToolType;
import kotlin.reflect.KClassesImplKt;

public class InventoryView implements Screen {
    private Stage stage;
    private final InventoryController controller;

    private final Label titleLabel;
    private final Table mainTable;
    private final Table itemsTable;
    private final ScrollPane itemsPane;
    private final Label itemPickLabel;
    private final Label inventoryLabel;
    private final TextButton trashButton;
    private final TextButton skillMenuButton;
    private final TextButton socialMenuButton;
    private final TextButton startVotingButton;
    private final TextButton mapButton;
    private final TextButton exitButton;
    private final TextButton saveAndExitButton;
    private final TextButton forceTerminateButton;

    private java.util.List<BackpackableTypeDTO> backPackItems;

    public InventoryView(InventoryController controller, Skin skin) {
        this.controller = controller;
        this.controller.setView(this);
        this.mainTable = new Table();

        this.titleLabel = new Label("Inventory", skin);

        this.itemPickLabel = new Label("", skin);
        this.inventoryLabel = new Label("Inventory", skin);
        this.itemsTable = new Table();
        refreshInventoryItems();
        this.itemsPane = new ScrollPane(itemsTable, skin);

        this.trashButton = new TextButton("Trash picked Item", skin);
        this.trashButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleItemTrash();
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

        this.startVotingButton = new TextButton("Start Voting", skin);
        this.startVotingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startVoting();
            }
        });

        this.mapButton = new TextButton("Map", skin);
        this.mapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleMap();
            }
        });

        this.exitButton = new TextButton("Exit Menu", skin);
        this.exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToGameView();
            }
        });
        this.saveAndExitButton = new TextButton("Save and Exit Game", skin);
        this.saveAndExitButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    controller.saveAndExitButton();
                }
        });

        this.forceTerminateButton = new TextButton("Force Terminate", skin);
        this.forceTerminateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.forceTerminate();
            }
        });
    }

    @Override
    public void show() {
        //this.stage = new Stage(new ScreenViewport());
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(this.stage);

        mainTable.setFillParent(true);
        mainTable.top().pad(10);

        // Outer table layout: 3 columns -> [Left Buttons] [Inventory] [Right Buttons]
        Table leftButtonTable = new Table();
        leftButtonTable.add(trashButton).pad(5).row();
        leftButtonTable.add(skillMenuButton).pad(5).row();
        leftButtonTable.add(socialMenuButton).pad(5).row();

        Table rightButtonTable = new Table();
        rightButtonTable.add(mapButton).pad(5).row();
        rightButtonTable.add(exitButton).pad(5).row();
        rightButtonTable.add(saveAndExitButton).pad(5).row();

        Table inventoryTable = new Table();
        inventoryTable.add(titleLabel).padBottom(10).row();
        inventoryTable.add(inventoryLabel).padBottom(5).row();
        inventoryTable.add(itemsPane).width(500).height(300).row();
        inventoryTable.add(itemPickLabel).padTop(5).left();

        mainTable.add(leftButtonTable).top().padRight(20);
        mainTable.add(inventoryTable).top().expandX();
        mainTable.add(rightButtonTable).top().padLeft(20);

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

    public void refreshInventoryItems() {
        itemsTable.clear();
        backPackItems = GameClient.getGameStateApiClient().getBackpackItems().getItems();

        for (BackpackableTypeDTO backpackableTypeDTO : backPackItems) {
            // 1. Prepare image button style:
            Texture itemTexture = GameAssetManagerClient.getGameAssetManager().getTexture(backpackableTypeDTO.getInventoryTexturePath());
            if (itemTexture == null) {
                System.out.println(backpackableTypeDTO.getInventoryTexturePath() + " " + backpackableTypeDTO.getName());
                continue;
            }
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            // 2. Prepare label for count:
            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backpackableTypeDTO.getCountInBackPack()),
                labelStyle);
            countLabel.setTouchable(Touchable.disabled);
            countLabel.setFontScale(1.3f); // Adjust size
            countLabel.setAlignment(Align.bottomRight);

            // 3. Use a Stack:
            Stack itemStack = new Stack();
            itemStack.setSize(64, Math.min(64, backpackableTypeDTO.getCountInBackPack()));
            itemStack.add(itemButton);
            itemStack.add(countLabel);

            // 4. Handle click logic:
            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleItemClick(backpackableTypeDTO);
                    itemPickLabel.setText("You picked: %s".formatted(backpackableTypeDTO.getName()));
                }
            });

            // 5. Add to inventory layout:
            itemsTable.add(itemStack).size(64, 64).pad(5);
        }
        itemsTable.invalidate();
    }

    public void showOnlyTools() {
        itemsTable.clear();

        for (BackpackableTypeDTO backPackableTypeDTO : backPackItems) {
            if (backPackableTypeDTO.getInventoryTexturePath() == null) continue;

            boolean found = false;
            for (ToolType toolType : ToolType.values()) {
                if (toolType.name().equals(backPackableTypeDTO.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                continue;

            if (backPackableTypeDTO.getCountInBackPack() == 0) continue;

            Texture itemTexture = GameAssetManagerClient.getGameAssetManager().getTexture(backPackableTypeDTO.getInventoryTexturePath());
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(itemTexture));

            ImageButton itemButton = new ImageButton(style);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Label countLabel = new Label("%d".formatted(backPackableTypeDTO.getCountInBackPack()), labelStyle);
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
                    controller.handleItemClick(backPackableTypeDTO);
                    itemPickLabel.setText("You picked: %s".formatted(backPackableTypeDTO.getName()));
                }
            });

            itemsTable.add(itemStack).size(64, 64).pad(5);
        }

        itemsTable.invalidate();
    }
}
