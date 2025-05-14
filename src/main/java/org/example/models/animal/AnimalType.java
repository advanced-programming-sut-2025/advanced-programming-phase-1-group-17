package org.example.models.animal;

import org.example.models.BackPackableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.models.animal.AnimalPlaceType.*;
import static org.example.models.animal.AnimalProductType.*;

public enum AnimalType implements BackPackableType {
    Chicken(new ArrayList<>( Arrays.asList(Coop,BigCoop,DeluxeCoop)),new ArrayList<>(Arrays.asList(Egg,LargeEgg)), 800),
    Duck(new ArrayList<>(Arrays.asList(BigCoop,DeluxeCoop)),new ArrayList<>(Arrays.asList(DuckEgg,DuckFeather)),1200),
    Rabbit(new ArrayList<>(List.of(DeluxeCoop)),new ArrayList<>(Arrays.asList(Wool,RabbitFoot)),8000),
    Dinosaur(new ArrayList<>(List.of(BigCoop)),new ArrayList<>(Arrays.asList(DinosaurEgg)),14000),
    Cow(new ArrayList<>(Arrays.asList(Barn,BigBarn,DeluxeBarn)),new ArrayList<>(Arrays.asList(Milk,LargeMilk)),1500),
    Goat(new ArrayList<>(Arrays.asList(BigBarn,DeluxeBarn)),new ArrayList<>(Arrays.asList(GoatMilk,LargeGoatMilk)),4000),
    Sheep(new ArrayList<>(List.of(DeluxeBarn)),new ArrayList<>(Arrays.asList(Wool)),8000),
    Pig(new ArrayList<>(List.of(DeluxeBarn)),new ArrayList<>(Arrays.asList(Truffle)),16000),;
    private final ArrayList<AnimalPlaceType> placeTypes;
    private final ArrayList<AnimalProductType> productTypes;
    private final int price;
    AnimalType(ArrayList<AnimalPlaceType> animalPlaceTypes,ArrayList<AnimalProductType> productTypes, int price) {
        this.placeTypes = animalPlaceTypes;
        this.productTypes = productTypes;
        this.price = price;
    }
    public ArrayList<AnimalPlaceType> getAnimalPlaceTypes() {
        return placeTypes;
    }

    public double getPrice() {
        return price;
    }
    public ArrayList<AnimalProductType> getProductTypes() {
        return productTypes;
    }

    public ArrayList<AnimalPlaceType> getPlaceTypes() {
        return placeTypes;
    }

    @Override
    public String getName() {
        return name();
    }
}