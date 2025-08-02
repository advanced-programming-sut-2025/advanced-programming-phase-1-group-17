package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.controllers.SkillMenuController;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;

public class SkillMenu implements Screen {
    private Stage stage;
    private final GameView gameView;
    private final SkillMenuController controller;

    private final Table mainTable;
    private final Label farmingLabel;
    private final Label miningLabel;
    private final Label foragingLabel;
    private final Label fishingLabel;

    private final Player player = App.getCurrentGame().getCurrentPlayingPlayer();
    private final TextButton exitButton;


    public SkillMenu(SkillMenuController skillMenuController, Skin skin, Player player, GameView gameView) {
        this.controller = skillMenuController;
        this.gameView = gameView;
        this.controller.setView(this);

        TooltipManager manager = TooltipManager.getInstance();
        manager.initialTime = 0f;
        manager.subsequentTime = 0.1f;
        manager.resetTime = 0.1f;
        manager.hideAll();

        this.farmingLabel = new Label("Your farming Ability is Level %d".formatted(player.getAbilities().getFarmingLevel()), skin);
        this.farmingLabel.setFontScale(1.6f); this.farmingLabel.setFontScale(1.6f);
        Tooltip<Label> tooltip = new Tooltip<>(new Label("Upgrading this ability increases the probability of harvesting high quality crops and fruits", skin));
        this.farmingLabel.setAlignment(Align.center);
        this.farmingLabel.addListener(tooltip);

        this.miningLabel = new Label("Your mining Ability is Level %d".formatted(player.getAbilities().getMiningLevel()), skin);
        this.miningLabel.setFontScale(1.6f);
        Tooltip<Label> tooltip2 = new Tooltip<>(new Label("Upgrading this ability increases the chance of getting one additional stone", skin));
        this.miningLabel.setAlignment(Align.center);
        this.miningLabel.addListener(tooltip2);

        this.foragingLabel = new Label("Your foraging Ability is Level %d".formatted(player.getAbilities().getForagingLevel()), skin);
        this.foragingLabel.setFontScale(1.6f);
        Tooltip<Label> tooltip3 = new Tooltip<>(new Label("Upgrading this ability will decrease the energy needed to use Axe.", skin));
        this.foragingLabel.setAlignment(Align.center);
        this.foragingLabel.addListener(tooltip3);

        this.fishingLabel = new Label("Your fishing Ability is Level %d".formatted(player.getAbilities().getFishingLevel()), skin);
        this.fishingLabel.setFontScale(1.6f);
        Tooltip<Label> tooltip4= new Tooltip<>(new Label("Upgrading this ability will increase the chance to catch a legendary fish.", skin));
        this.fishingLabel.setAlignment(Align.center);
        this.fishingLabel.addListener(tooltip4);

        this.exitButton = new TextButton("Exit", skin);
        this.exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToGameView();
            }
        });

        this.mainTable = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable.setFillParent(true);
        mainTable.top().pad(20);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        TooltipManager tooltipManager = TooltipManager.getInstance();

        Table titleTable = new Table();
        titleTable.setFillParent(true);
        Label titleLabel = new Label("Skill Menu", skin);
        titleLabel.setFontScale(2f);
        titleTable.top().add(titleLabel).expandX().padTop(20);

        stage.addActor(titleTable);

        // Right side description display
        final Label rightSideDescription = new Label("", skin);
        rightSideDescription.setWrap(true);
        rightSideDescription.setAlignment(Align.topLeft);
        rightSideDescription.setWidth(300);
        rightSideDescription.setVisible(true);

        GameAssetManager gameAssetManager = GameAssetManager.getGameAssetManager();

        // Skill rows
        SkillRow farming = new SkillRow(
            new TextureRegionDrawable(gameAssetManager.getAbilityTextures("Farming")),
            player.getAbilities().getFarmingLevel(),
            "Increases crop quality chance",
            skin,
            tooltipManager
        );

        SkillRow mining = new SkillRow(
            new TextureRegionDrawable(gameAssetManager.getAbilityTextures("Mining")),
            player.getAbilities().getMiningLevel(),
            "Chance to get extra stones when mining",
            skin,
            tooltipManager
        );

        SkillRow foraging = new SkillRow(
            new TextureRegionDrawable(gameAssetManager.getAbilityTextures("Foraging")),
            player.getAbilities().getForagingLevel(),
            "Reduces Axe energy cost",
            skin,
            tooltipManager
        );

        SkillRow fishing = new SkillRow(
            new TextureRegionDrawable(gameAssetManager.getAbilityTextures("Fishing")),
            player.getAbilities().getFishingLevel(),
            "Increases legendary fish catch rate",
            skin,
            tooltipManager
        );

        Table leftColumn = new Table().top();
        leftColumn.add(farming).padBottom(30).row();
        leftColumn.add(mining).padBottom(30).row();
        leftColumn.add(foraging).padBottom(30).row();
        leftColumn.add(fishing).padBottom(30).row();
        leftColumn.add(exitButton).padTop(40).row();

        farming.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                rightSideDescription.setText(farming.getHoverDescription().getText());
            }
        });
        mining.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                rightSideDescription.setText(mining.getHoverDescription().getText());
            }
        });
        foraging.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                rightSideDescription.setText(foraging.getHoverDescription().getText());
            }
        });
        fishing.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                rightSideDescription.setText(fishing.getHoverDescription().getText());
            }
        });

        // Create a right side table with "Description" title and description label
        Table rightColumn = new Table().top().padTop(50).padRight(60);
        Label descriptionTitle = new Label("Description", skin);
        descriptionTitle.setFontScale(1.6f);
        rightColumn.add(descriptionTitle).left().padBottom(20).row();
        rightColumn.add(rightSideDescription).width(300).left().top().row();

        // Create root layout: left = skills, right = description
        Table root = new Table();
        root.setFillParent(true);
        root.add(leftColumn).left().expandY().padLeft(40).padTop(50).expandX();
        root.add(rightColumn).width(350).top().right().padRight(60);
        stage.addActor(root);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        controller.handlePlayerInput();
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

    public GameView getGameView() {
        return gameView;
    }

    class SkillRow extends Table {
        private final Image icon;
        private final Table levelIcons;
        private final Label levelNumber;
        private final Label hoverDescription;

        public SkillRow(TextureRegionDrawable skillIcon, int level, String description, Skin skin, TooltipManager manager) {
            this.icon = new Image(skillIcon);
            this.levelIcons = new Table();
            this.levelNumber = new Label("Level " + level, skin);
            this.hoverDescription = new Label(description, skin);
            this.hoverDescription.setWrap(true);
            this.hoverDescription.setVisible(false);
            this.hoverDescription.setWidth(300);

            for (int i = 1; i <= 5; i++) {
                boolean filled = i <= level;
                TextureRegionDrawable dotDrawable = new TextureRegionDrawable(generateDotPixmap(filled));
                Image levelDot = new Image(dotDrawable);
                levelDot.setSize(20, 20);
                levelIcons.add(levelDot).pad(5);
            }


            add(icon).width(40).height(40).padRight(20);
            add(levelIcons).expandX().left();
            add(levelNumber).padLeft(20);

            Tooltip<Label> tooltip = new Tooltip<>(hoverDescription, manager);
            this.addListener(tooltip);
        }

        public Label getHoverDescription() {
            return hoverDescription;
        }

        private Texture generateDotPixmap(boolean filled) {
            int size = 20;
            Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
            pixmap.setBlending(Pixmap.Blending.None);
            pixmap.setColor(filled ? Color.RED : Color.LIGHT_GRAY);
            pixmap.fillCircle(size / 2, size / 2, size / 2 - 2);
            Texture texture = new Texture(pixmap);
            pixmap.dispose();
            return texture;
        }

    }

}
