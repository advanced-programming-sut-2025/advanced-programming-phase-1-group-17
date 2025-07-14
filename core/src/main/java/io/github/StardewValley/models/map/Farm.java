package io.github.StardewValley.models.map;
import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.models.animal.Animal;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.animal.AnimalPlace;

import java.util.ArrayList;

public class Farm implements Placeable {
    private GreenHouse greenHouse;
    private ArrayList<Lake> lakes = new ArrayList<>();
    private Quarry quarries = new Quarry();
    private Hut hut = new Hut();
    public void randomFill(){}
    private ArrayList<AnimalPlace> animalPlaces = new ArrayList<>();
    private ArrayList<Animal> animals=new ArrayList<>();
    private static Texture farmTexture = new Texture(GameAssetManager.getGameAssetManager().getFarmTexture());

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(ArrayList<Lake> lakes) {
        this.lakes = lakes;
    }

    public Quarry getQuarries() {
        return quarries;
    }

    public void setQuarries(Quarry quarrie) {
        this.quarries = quarrie;
    }

    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }

    public ArrayList<AnimalPlace> getAnimalPlaces() {
        return animalPlaces;
    }

    public void setAnimalPlaces(ArrayList<AnimalPlace> animalPlaces) {
        this.animalPlaces = animalPlaces;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public Texture getTexture() {
        return farmTexture;
    }
}
