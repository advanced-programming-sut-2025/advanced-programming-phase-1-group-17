package io.github.StardewValley.models.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.views.AnimalPlaceShow;

import java.util.ArrayList;

public class AnimalPlace implements Placeable {
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private AnimalPlaceType animalPlaceType;
    private int capacity;
    private Stage uiStage = new Stage(new ScreenViewport());
    private Skin skin;

    public AnimalPlace(AnimalPlaceType animalPlaceType){
        this.animalPlaceType = animalPlaceType;
        this.capacity = animalPlaceType.getCapacity();
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.animals=new ArrayList<>();
    }
    private float x=1500f;
    private float y=1500f;
    public void render(float delta) {
        Main.getBatch().draw(this.animalPlaceType.getInventoryTexture(),
            x,
            y
        );
        if (Gdx.input.justTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            App.getCamera().unproject(vector3);
            if (this.getHitBox().contains(vector3.x, vector3.y)) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new AnimalPlaceShow(GameAssetManager.getGameAssetManager().getSkin(), App.getGameView(),this));
            }
        }
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public boolean isFull(){
        return animals.size() >= capacity;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public AnimalPlaceType getAnimalPlaceType() {
        return animalPlaceType;
    }

    public void setAnimalPlaceType(AnimalPlaceType animalPlaceType) {
        this.animalPlaceType = animalPlaceType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    @Override
    public Texture getTexture() {
        return this.animalPlaceType.getInventoryTexture();
    }
    public Rectangle getHitBox(){
        return new Rectangle(x,y,this.animalPlaceType.getInventoryTexture().getWidth(),this.animalPlaceType.getInventoryTexture().getHeight());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
