package org.example.models.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.models.enums.AnimalPlaceType.*;

public enum AnimalType {
    Chicken(new ArrayList<>(Arrays.asList(Coop,BigCoop,DeluxeCoop)), 800),
    Duck(new ArrayList<>(Arrays.asList(BigCoop,DeluxeCoop)),1200),
    Rabbit(new ArrayList<>(List.of(DeluxeCoop)),8000),
    Dinosaur(new ArrayList<>(List.of(BigCoop)),14000),
    Cow(new ArrayList<>(Arrays.asList(Barn,BigBarn,DeluxeBarn)),1500),
    Goat(new ArrayList<>(Arrays.asList(BigBarn,DeluxeBarn)),4000),
    Sheep(new ArrayList<>(List.of(DeluxeBarn)),8000),
    Pig(new ArrayList<>(List.of(DeluxeBarn)),16000),;
    private final ArrayList<AnimalPlaceType> placeTypes;
    private final int price;
    AnimalType(ArrayList<AnimalPlaceType> animalPlaceTypes, int price) {
        this.placeTypes = animalPlaceTypes;
        this.price = price;
    }
    public ArrayList<AnimalPlaceType> getAnimalPlaceTypes() {
        return placeTypes;
    }

    public int getPrice() {
        return price;
    }


}