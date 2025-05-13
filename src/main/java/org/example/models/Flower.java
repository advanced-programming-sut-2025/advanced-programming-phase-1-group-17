package org.example.models;

public class Flower implements BackPackable {

    private FlowerType flowerType = FlowerType.FLOWER;

    public Flower(FlowerType flowerType) {
        this.flowerType = flowerType;
    }

    public String getName(){
        return FlowerType.FLOWER.getName();
    }
    public double getPrice(){
        return 0;
    }
    public BackPackableType getType(){
        return FlowerType.FLOWER;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(FlowerType flowerType) {
        this.flowerType = flowerType;
    }
}
