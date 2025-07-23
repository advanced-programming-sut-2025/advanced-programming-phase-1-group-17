package io.github.StardewValley.models.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.BackPackable;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.market.ItemQuality;

public class AnimalProduct implements BackPackable {
    private Animal animal;
    private String name;
    private AnimalProductType animalProductType;
    private ItemQuality quality = ItemQuality.Regular;
    private float x=100;
    private float y=100;


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    public void setAnimalProductType(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    @Override
    public double getPrice() {
        return animalProductType.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return animalProductType;
    }
    public Rectangle getHitBox(){
        return new Rectangle(x,y,this.animalProductType.getInventoryTexture().getWidth(),this.animalProductType.getInventoryTexture().getHeight());
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
    boolean isRender=true;
    public boolean isRender(){
        return isRender;
    }
    public void render(SpriteBatch batch, float v) {
        if(isRender){
            batch.draw(this.animalProductType.getInventoryTexture(),x,y,this.animalProductType.getInventoryTexture().getWidth(),this.animalProductType.getInventoryTexture().getHeight());
        }
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            App.getCamera().unproject(touchPos);
            if(this.getHitBox().contains(touchPos.x,touchPos.y)){
                isRender =false;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            for(Animal animal1: App.getCurrentGame().getCurrentPlayingPlayer().getAnimals()){
                animal1.produce();
            }
        }
    }
}
