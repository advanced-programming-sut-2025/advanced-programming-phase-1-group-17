package org.example.models.animal;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.market.ItemQuality;

public class AnimalProduct implements BackPackable {
    private Animal animal;
    private String name;
    private AnimalProductType animalProductType;
    private ItemQuality quality = ItemQuality.Regular;


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

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    @Override
    public double getPrice() {
        return animalProductType.getPrice();
    }

    @Override
    public BackPackableType getType() {
        return animalProductType;
    }
}
