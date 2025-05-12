package org.example.models.animal;

import org.example.models.Placeable;
import org.example.models.map.Tile;

import java.util.ArrayList;

public class AnimalPlace implements Placeable {
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private AnimalPlaceType animalPlaceType;
    private int capacity;
    public AnimalPlace(AnimalPlaceType animalPlaceType){
        this.animalPlaceType = animalPlaceType;
        this.capacity = animalPlaceType.getCapacity();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
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
}
