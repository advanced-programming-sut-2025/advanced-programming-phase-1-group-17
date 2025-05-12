package org.example.models.animal;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.market.ShippingBinType;

public class AnimalProduct implements BackPackable {
    private Animal animal;
    private String name;
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
        return animalProductType;
    }
}
