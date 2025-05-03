package org.example.models.enums;

public enum AnimalType {
    Chicken(AnimalPlace.Coop,800),
    Duck(AnimalPlace.Coop,1200),
    Rabbit(AnimalPlace.Coop,8000),
    Dinosaur(AnimalPlace.Coop,14000),
    Cow(AnimalPlace.Barn,1500),
    Goat(AnimalPlace.Barn,4000),
    Sheep(AnimalPlace.Barn,8000),
    Pig(AnimalPlace.Barn,16000),;
    private AnimalPlace animalPlace;
    private final int price;
    AnimalType(AnimalPlace animalPlace, int price) {
        this.animalPlace = animalPlace;
        this.price = price;
    }


}
