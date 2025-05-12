package org.example.models;

import org.example.models.enums.AnimalType;
import org.example.models.map.AnimalPlace;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.models.market.ItemQuality.*;

public class Animal implements Placeable {
    private String name;
    private AnimalPlace animalPlace;
    private AnimalType animalType;
    private int price;
    private HashMap<String, Integer> products = new HashMap<>();
    private int friendship;
    private ArrayList<AnimalProduct> animalProducts =new ArrayList<>();
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

    public ArrayList<AnimalProduct> getAnimalProducts() {
        return animalProducts;
    }

    public void setAnimalProducts(ArrayList<AnimalProduct> animalProducts) {
        this.animalProducts = animalProducts;
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
    public void produce(){
        double randomNumber = 0.5 + Math.random();
        double chance = (double)(friendship + 150*randomNumber)/1500;
        double r = Math.random();
        double quality= ((double) friendship /1000)*(0.5 + r/2);
        AnimalProduct animalProduct=new AnimalProduct();
        if(Math.random()<=chance){
            if(this.animalType.getProductTypes().size()==2) {
                animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(1));
            }
        }
        else{
            animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(0));
        }
        if(quality<0.5){
            animalProduct.setShippingBinType(Regular);
        }
        else if(quality<0.7){
            animalProduct.setShippingBinType(Silver);
        }
        else if(quality<0.9){
            animalProduct.setShippingBinType(Gold);
        }
        else{
            animalProduct.setShippingBinType(Iridium);
        }
        animalProduct.setAnimal(this);
        addProduct(animalProduct);


    }
    public void addProduct(AnimalProduct animalProduct){
        if(this.getAnimalProducts().contains(animalProduct)){
            animalProduct.setCount(animalProduct.getCount()+1);
        }
        else{
            this.getAnimalProducts().add(animalProduct);
        }
    }
    public void sell(){
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        player.getPlayerMap().getFarm().getAnimals().remove(this);
        for(AnimalPlace animalPlace : player.getPlayerMap().getFarm().getAnimalPlaces()){
            animalPlace.getAnimals().remove(this);
        }
        double price = this.animalType.getPrice() * (((double) friendship /1000) + 0.3);
        player.getBackPack().addcoin(price);
    }
}
