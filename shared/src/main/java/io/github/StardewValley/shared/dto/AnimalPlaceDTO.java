package io.github.StardewValley.shared.dto;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.map.Tile;

import java.util.ArrayList;
import java.util.List;

public class AnimalPlaceDTO {
    private String id;
    private AnimalPlaceType animalPlaceType;
    private float x, y;
    private boolean isDoorOpen;
    private List<AnimalDTO> animals; // لیستی از DTO های حیوانات داخل این ساختمان
    private int spawnX, spawnY;
    /**
     * کانستراکتور خالی برای کتابخانه Jackson.
     */
    public AnimalPlaceDTO() {
        this.animals = new ArrayList<>();
    }

    /**
     * کانستراکتور کامل برای ساخت راحت DTO در سرور.
     */
    public AnimalPlaceDTO(String id, AnimalPlaceType type, float x, float y, boolean isOpen) {
        this.id = id;
        this.animalPlaceType = type;
        this.x = x;
        this.y = y;
        this.isDoorOpen = isOpen;
        this.animals = new ArrayList<>();
    }

    // Getters and Setters for all fields...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AnimalPlaceType getAnimalPlaceType() { return animalPlaceType; }
    public void setAnimalPlaceType(AnimalPlaceType animalPlaceType) { this.animalPlaceType = animalPlaceType; }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public boolean isDoorOpen() { return isDoorOpen; }
    public void setDoorOpen(boolean doorOpen) { isDoorOpen = doorOpen; }
    public List<AnimalDTO> getAnimals() { return animals; }
    public void setAnimals(List<AnimalDTO> animals) { this.animals = animals; }
    @JsonIgnore
    public Rectangle getHitBox(){
        return new Rectangle(x,y, new Texture(this.animalPlaceType.getInventoryTexturePath()).getWidth(),
            new Texture(this.animalPlaceType.getInventoryTexturePath()).getHeight());
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }
}
