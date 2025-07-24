package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.controllers.StoreMenuController;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.animal.Animal;
import io.github.StardewValley.models.animal.AnimalPlace;
import io.github.StardewValley.models.animal.AnimalPlaceType;
import io.github.StardewValley.models.animal.AnimalType;
import io.github.StardewValley.models.crafting.CraftingItem;
import io.github.StardewValley.models.market.StoreType;

import java.util.Map;

import io.github.StardewValley.models.tools.Tool;

import static io.github.StardewValley.models.animal.AnimalType.*;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final GameController controller;
    private final GameMenuController menuController;
    private HUD hud;
    private AnimalPlace animalPlace;
    private AnimalPlace animalPlace1;


    public GameView(GameController controller, GameMenuController menuController) {
        App.setGameView(this);
        this.animalPlace = new AnimalPlace(AnimalPlaceType.BigBarn);
        animalPlace.getAnimals().add(new Animal("test",Cow));
        this.animalPlace1 = new AnimalPlace(AnimalPlaceType.Barn);
        animalPlace1.setX(2000);
        animalPlace1.setY(2000);
//        App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(animalPlace);
//        App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(animalPlace1);
        int i=0;
        for(AnimalPlaceType animalPlaceType : AnimalPlaceType.values()) {
            AnimalPlace ap = new AnimalPlace(animalPlaceType);
            ap.setX(1000+500*i);
            ap.setY(1000+500*i);
            i++;
            App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(ap);
            Animal animal = new Animal("test" + i,AnimalType.values()[i]);
            animal.setX(100+20*i);
            animal.setY(100+20*i);
            ap.getAnimals().add(animal);

        }



        this.controller = controller;
        this.menuController = menuController;
//        display.run(1,1,300);
        this.controller.setView(this);
        Main.setGameView(this);



    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        this.hud = new HUD();

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);

        controller.getCamera().update();

        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);
        Main.getBatch().begin();

        controller.updateGame(v);
        controller.handlePlayerInput();
        for(AnimalPlace animalPlace : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces()) {
            animalPlace.render(v);
            for(Animal animal:animalPlace.getAnimals()){
                animal.render(Main.getBatch(),v);
                animal.update(v);
            }
        }

        Main.getBatch().end();

        hud.render(Main.getBatch(),v);

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


    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = controller.getCamera().unproject(new Vector3(screenX, screenY, 0));
        if(handleToolUse(worldCoordinates))
            return true;
        return checkStoreBounds(worldCoordinates);
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        controller.setKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        controller.setKey(keycode, false);
        return true;
    }

    private boolean checkStoreBounds(Vector3 worldCoordinates) {
        for (Map.Entry<StoreType, Rectangle> entry : controller.getStoreBounds().entrySet()) {
            if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StoreMenu(new StoreMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin(),
                    this, entry.getKey()));
                return true;
            }
        }
        return false;
    }

    private boolean handleToolUse(Vector3 worldCoordinates) {
        float tileWidth = controller.getWorldController().getTileWidth();
        float tileHeight = controller.getWorldController().getTileHeight();
        int clickedTileX = (int)(worldCoordinates.x / tileWidth);
        int clickedTileY = (int)(worldCoordinates.y / tileHeight);

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        int dx = clickedTileX - player.getTileX();
        int dy = clickedTileY - player.getTileY();

        if (Math.abs(dx) + Math.abs(dy) == 1) {
            if (player.getEquippedItem() instanceof Tool)
                controller.getToolController().toolUse(dx, dy);
            else if (player.getEquippedItem() instanceof CraftingItem)
                controller.placeItem(dx, dy);
            return true;
        }
        return false;
    }


    public GameController getController() {
        return controller;
    }

    public GameMenuController getMenuController() {
        return menuController;
    }
}
