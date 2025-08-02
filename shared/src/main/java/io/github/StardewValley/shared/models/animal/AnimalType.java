package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.StardewValley.shared.models.animal.AnimalPlaceType.*;
import static io.github.StardewValley.shared.models.animal.AnimalProductType.*;

public enum AnimalType implements BackPackableType {
    Chicken(new ArrayList<>( Arrays.asList(Coop,BigCoop,DeluxeCoop)),new ArrayList<>(Arrays.asList(Egg,LargeEgg)), 800,32),
    Duck(new ArrayList<>(Arrays.asList(BigCoop,DeluxeCoop)),new ArrayList<>(Arrays.asList(DuckEgg,DuckFeather)),1200,32),
    Rabbit(new ArrayList<>(List.of(DeluxeCoop)),new ArrayList<>(Arrays.asList(Wool,RabbitFoot)),8000,32),
    Dinosaur(new ArrayList<>(List.of(BigCoop)),new ArrayList<>(Arrays.asList(DinosaurEgg)),14000,32),
    Cow(new ArrayList<>(Arrays.asList(Barn,BigBarn,DeluxeBarn)),new ArrayList<>(Arrays.asList(Milk,LargeMilk)),1500,64),
    Goat(new ArrayList<>(Arrays.asList(BigBarn,DeluxeBarn)),new ArrayList<>(Arrays.asList(GoatMilk,LargeGoatMilk)),4000,64),
    Sheep(new ArrayList<>(List.of(DeluxeBarn)),new ArrayList<>(Arrays.asList(Wool)),8000,64),
    Pig(new ArrayList<>(List.of(DeluxeBarn)),new ArrayList<>(Arrays.asList(Truffle)),16000,64),;
    private final ArrayList<AnimalPlaceType> placeTypes;
    private final ArrayList<AnimalProductType> productTypes;
    private final int price;
    private final int TexttureSize;
    AnimalType(ArrayList<AnimalPlaceType> animalPlaceTypes,ArrayList<AnimalProductType> productTypes, int price,int TexttureSize) {
        this.placeTypes = animalPlaceTypes;
        this.productTypes = productTypes;
        this.price = price;
        this.TexttureSize = TexttureSize;
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

    @Override
    public String getInventoryTexture() {
        //TODO
        return null;
    }

    public int getTexttureSize() {
        return TexttureSize*2;
    }
}
