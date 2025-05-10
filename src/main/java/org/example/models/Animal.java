package org.example.models;

import org.example.models.enums.AnimalHabitat;
import org.example.models.enums.AnimalType;
import org.example.models.map.AnimalPlace;

import java.util.ArrayList;
import java.util.HashMap;

public class Animal implements Placeable {
    private String name;
    private AnimalPlace animalPlace;
    private AnimalType animalType;
    private AnimalHabitat type;
    private int price;
    private HashMap<String, Integer> products = new HashMap<>();
    private int friendship;
    private ArrayList<AnimalProduct> animalProduct=new ArrayList<>();
    private boolean isPettedToday=false;
    private boolean isFedToday = false;
    private boolean isFedOutside = false;
    private boolean isOutside=false;
    public Animal(String name, AnimalType animalType){
        this.name=name;
        this.animalType=animalType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalPlace getAnimalPlace() {
        return animalPlace;
    }

    public void setAnimalPlace(AnimalPlace animalPlace) {
        this.animalPlace = animalPlace;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public AnimalHabitat getType() {
        return type;
    }

    public void setType(AnimalHabitat type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public ArrayList<AnimalProduct> getAnimalProduct() {
        return animalProduct;
    }

    public void setAnimalProduct(ArrayList<AnimalProduct> animalProduct) {
        this.animalProduct = animalProduct;
    }
    public static Animal findAnimalByName(String name){
        for(Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getFarm().getAnimals()){
            if(animal.getName().equals(name)){
                return animal;
            }
        }
        return null;
    }

    public boolean isPettedToday() {
        return isPettedToday;
    }

    public void setPettedToday(boolean pettedToday) {
        isPettedToday = pettedToday;
    }

    public boolean isFedToday() {
        return isFedToday;
    }

    public void setFedToday(boolean fedToday) {
        isFedToday = fedToday;
    }

    public boolean isFedOutside() {
        return isFedOutside;
    }

    public void setFedOutside(boolean fedOutside) {
        isFedOutside = fedOutside;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }
}
