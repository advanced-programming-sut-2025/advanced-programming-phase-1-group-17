package org.example.models.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.example.models.enums.AnimalPlaceType.*;

public enum AnimalType {
    Chicken(new ArrayList<>(Arrays.asList(NormalCoop, BigCoop,DeluxeCoop)), 800),
    Duck(new ArrayList<>(Arrays.asList( BigCoop,DeluxeCoop)), 1200), // اضافه شد
    Rabbit(new ArrayList<>(List.of(DeluxeCoop)), 8000), // اضافه شد
    Dinosaur(new ArrayList<>(List.of(BigCoop)), 14000),         // اضافه شد (فرضاً فقط BigCoop)
    Cow(new ArrayList<>(Arrays.asList(NormalBarn, BigBarn,DeluxeBarn)), 1500),       // اضافه شد
    Goat(new ArrayList<>(Arrays.asList(BigBarn,DeluxeBarn)), 4000),      // اضافه شد
    Sheep(new ArrayList<>(List.of(DeluxeBarn)), 8000),     // اضافه شد
    Pig(new ArrayList<>(List.of(DeluxeBarn)), 16000);      // اضافه شد و ویرگول انتهایی حذف شد

    private final List<AnimalPlaceType> animalPlace;
    private final int price;

    AnimalType(List<AnimalPlaceType> animalPlace, int price) {
        this.animalPlace = animalPlace;
        this.price = price;
    }

    public List<AnimalPlaceType> getAnimalPlace() {
        return animalPlace;
    }

    public int getPrice() {
        return price;
    }
}