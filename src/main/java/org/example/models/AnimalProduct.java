package org.example.models;

import org.example.models.enums.AnimalProductType;
import org.example.models.trade.ShippingBinType;

public class AnimalProduct implements BackPackable{
    private Animal animal;
    private String name;
    private int count=1;
    private AnimalProductType animalProductType;
    private ShippingBinType shippingBinType;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    public void setAnimalProductType(AnimalProductType animalProductType) {
        this.animalProductType = animalProductType;
    }

    public ShippingBinType getShippingBinType() {
        return shippingBinType;
    }

    public void setShippingBinType(ShippingBinType shippingBinType) {
        this.shippingBinType = shippingBinType;
    }
    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public BackPackableType getType() {
        return null;
    }
}
