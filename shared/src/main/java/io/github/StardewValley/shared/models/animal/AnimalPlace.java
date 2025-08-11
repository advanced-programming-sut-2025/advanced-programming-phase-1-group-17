package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.ArrayList;
import java.util.List;

public class AnimalPlace implements Placeable {
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private AnimalPlaceType animalPlaceType;
    private int capacity;
    //private Stage uiStage = new Stage(new ScreenViewport());
    private Stage uiStage = new Stage(new FitViewport(1920, 1080));
    private String  skin;
    private boolean isOpen=true;
    private boolean justPlaced = false;

    public AnimalPlace(AnimalPlaceType animalPlaceType){
        this.animalPlaceType = animalPlaceType;
        this.capacity = animalPlaceType.getCapacity();
        //TODO
        //this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.animals=new ArrayList<>();
    }
    private float x=1500f;
    private float y=1500f;
    public void render(float delta) {
//        Main.getBatch().draw(this.animalPlaceType.getInventoryTexture(),
//            x,
//            y
//        );
        if (Gdx.input.justTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //TODO
            //App.getCamera().unproject(vector3);
//            if (this.getHitBox().contains(vector3.x, vector3.y)) {
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new AnimalPlaceShow(GameAssetManager.getGameAssetManager().getSkin(), App.getGameView(),this));

//            }
        }
        justPlaced = true;
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
    //TODO
//    public Rectangle getHitBox(){
//        return new Rectangle(x,y,this.animalPlaceType.getInventoryTexture().getWidth(),this.animalPlaceType.getInventoryTexture().getHeight());
//    }

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
        //TODO
//        for(int i=(int)x;i<x+this.animalPlaceType.getInventoryTexture().getWidth();i+=110){
//            for (int j=(int)y;j<y + animalPlaceType.getInventoryTexture().getHeight();j+=110){
//                Tile.getTileByClick(i,j).setWalkAble(false);
//            }
//        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
//    public static void animalHouseBuy( SpriteBatch batch,AnimalPlace animalPlace,GameView gameView) {
//
//                Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//                Vector3 worldPos = App.getCamera().unproject(mousePos);
//
//                float width = animalPlace.animalPlaceType.getInventoryTexture().getWidth();
//                float height = animalPlace.animalPlaceType.getInventoryTexture().getHeight();
//                batch.setColor(1, 1, 1, 0.5f); //
//                batch.draw(animalPlace.animalPlaceType.getInventoryTexture(), worldPos.x - width / 2, worldPos.y - height / 2, width, height);
//                batch.setColor(1, 1, 1, 1f);
//                if (Gdx.input.justTouched()) {
//                    for (int i = (int) (worldPos.x - width/2); i < worldPos.x + width/2; i += 110) {
//                        for (int j = (int) (worldPos.y - height/2); j < worldPos.y + height/2; j += 110) {
//                            Tile tile = Tile.getTileFromPixel(i, j);
//                            if (tile == null || !tile.isWalkAble()) {
//                                gameView.setSthBuilding(false);
//                                if(tile == null) System.out.println("tile is null!");
//                                if(!tile.isWalkAble()) System.out.println("tile is not walkable!");
//                                return;
//                            }
//
//                        }
//                    }
//                    animalPlace.setX(worldPos.x - width / 2);
//                    animalPlace.setY(worldPos.y - height / 2);
//                    animalPlace.justPlaced = true;
//                    App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimalPlaces().add(animalPlace);
//                    gameView.setSthBuilding(false);
//                }
//    }
    //TODO
    @Override
    public String getTexture() {
        return "";
    }

    @Override
    public PlaceableSave toDTO() {
        return null;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return null;
    }
}
