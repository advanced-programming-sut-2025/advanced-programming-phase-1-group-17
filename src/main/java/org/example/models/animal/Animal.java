package org.example.models.animal;

import org.example.models.App;
import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.map.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.models.market.ItemQuality.*;

public class Animal implements Placeable {
    private String name;
    private AnimalPlace animalPlace;
    private AnimalType animalType;
    private int price;
    private int friendship;
    private ArrayList<AnimalProduct> animalProducts =new ArrayList<>();
    private boolean isPettedToday=false;
    private boolean isFedToday = false;
    private boolean isFedOutside = false;
    private boolean isOutside=false;
    private Tile tile=null;
    public Animal(String name, AnimalType animalType){
        this.name=name;
        this.animalType=animalType;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
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

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        if(friendship>1000){
            if(friendship<this.friendship){
                this.friendship = friendship;
            }
        }
        else if(friendship<0){
            if(friendship>this.friendship){
                this.friendship = friendship;
            }
        }
        else {
            this.friendship = friendship;
        }
    }
    public void cheatSetFriendship(int friendship){
        this.friendship=friendship;
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
        isFedToday = fedOutside;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }
    public void produce(){
        if(!isFedToday){
            return;
        }
        double randomNumber = 0.5 + Math.random();
        double chance = (double)(friendship + 150*randomNumber)/1500;
        double r = Math.random();
        double quality= ((double) friendship /1000)*(0.5 + r/2);
        AnimalProductType animalProductType;
        AnimalProduct animalProduct=new AnimalProduct();
        if(Math.random()<=chance && friendship>100){
            if(this.animalType.getProductTypes().size()==2) {
                animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(1));
            }
        }
        else{
            animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(0));
        }
        if(quality<0.5){
            animalProduct.setQuality(Regular);
        }
        else if(quality<0.7){
            animalProduct.setQuality(Silver);
        }
        else if(quality<0.9){
            animalProduct.setQuality(Gold);
        }
        else{
            animalProduct.setQuality(Iridium);
        }
        animalProduct.setAnimal(this);
        addProduct(animalProduct);


    }
    public void addProduct(AnimalProduct animalProduct){
            this.getAnimalProducts().add(animalProduct);
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
    public static void goToNextDay(){
        for(Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getFarm().getAnimals()) {
            if(animal.isFedToday){
                animal.produce();
                animal.setFedToday(false);
            }
            else{
                animal.setFriendship(animal.getFriendship()-20);
            }
            if(!animal.isPettedToday){
                animal.setFriendship(animal.getFriendship() - 10);
            }
            if(animal.isOutside){
                animal.setFriendship(animal.getFriendship() - 20);
            }
        }
    }
    public static Map<AnimalProduct,Integer> getMapListOfAnimalProducts(ArrayList<AnimalProduct> animalProducts){
        Map<AnimalProduct,Integer> productIntegerMap= new HashMap<>();
        for(AnimalProduct animalProduct : animalProducts){
            if(productIntegerMap.containsKey(animalProduct)){
                productIntegerMap.put(animalProduct,productIntegerMap.get(animalProduct)+1);
            }
            else{
                productIntegerMap.put(animalProduct,1);
            }
        }
        return productIntegerMap;
    }
}
