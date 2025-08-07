package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import java.util.ArrayList;
import java.util.List;

public class AnimalPlaceDTO {

    private AnimalPlaceType animalPlaceType;
    private float x, y;
    private boolean isDoorOpen;
    private List<AnimalDTO> animals;

    public AnimalPlaceDTO() {
        this.animals = new ArrayList<>();
    }

    public AnimalPlaceDTO(String id, AnimalPlaceType animalPlaceType, float x, float y, boolean isDoorOpen, List<AnimalDTO> animals) {
        this.animalPlaceType = animalPlaceType;
        this.x = x;
        this.y = y;
        this.isDoorOpen = isDoorOpen;
        this.animals = animals;
    }

    public AnimalPlaceType getAnimalPlaceType() { return animalPlaceType; }
    public void setAnimalPlaceType(AnimalPlaceType animalPlaceType) { this.animalPlaceType = animalPlaceType; }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public boolean isDoorOpen() { return isDoorOpen; }
    public void setDoorOpen(boolean doorOpen) { this.isDoorOpen = doorOpen; }
    public List<AnimalDTO> getAnimals() { return animals; }
    public void setAnimals(List<AnimalDTO> animals) { this.animals = animals; }
}
