package org.example.models;

public enum FlowerType implements BackPackableType {
    FLOWER;
    public String getName(){
        return name();
    }
    public double getPrice(){
        return 0;
    }
}
