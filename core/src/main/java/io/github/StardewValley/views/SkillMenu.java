package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.controllers.SkillMenuController;
import io.github.StardewValley.models.Player;

public class SkillMenu implements Screen {
    private Stage stage;
    private final GameView gameView;
    private final SkillMenuController controller;

    private final Table mainTable;
    private final Label farmingLabel;
    private final Label miningLabel;
    private final Label foragingLabel;
    private final Label fishingLabel;

    private final TextButton exitButton;


    public SkillMenu(SkillMenuController skillMenuController, Skin skin, Player player, GameView gameView) {
        this.controller = skillMenuController;
        this.gameView = gameView;
        this.controller.setView(this);

        TooltipManager manager = TooltipManager.getInstance();
        manager.initialTime = 0.1f;  // How long to hover before showing
        manager.resetTime = 0.1f;    // Time before showing again
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
        Label titleLabel = new Label("Skill Menu", GameAssetManager.getGameAssetManager().getSkin());
        titleLabel.setFontScale(2f);
        mainTable.add(titleLabel).center().padBottom(60).row();
        mainTable.add(farmingLabel).padBottom(50).row();
        mainTable.add(miningLabel).padBottom(50).row();
        mainTable.add(foragingLabel).padBottom(50).row();
        mainTable.add(fishingLabel).padBottom(50).row();
        mainTable.add(exitButton).row();

        stage.addActor(mainTable);
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
}
