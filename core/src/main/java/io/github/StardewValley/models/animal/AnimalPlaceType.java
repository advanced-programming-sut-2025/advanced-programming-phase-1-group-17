package io.github.StardewValley.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.BackPackableType;

public enum AnimalPlaceType implements BackPackableType {
    Coop(4,4000,new Texture("animalplace/Coop.png")),
    BigCoop(8,10000,new Texture("animalplace/Big_Coop.png")),
    DeluxeCoop(12,20000,new Texture("animalplace/Deluxe_Coop.png")),
    Barn(4,6000,new Texture("animalplace/Barn.png")),
    BigBarn(8,12000,new Texture("animalplace/Big_Barn.png")),
    DeluxeBarn(12,25000,new Texture("animalplace/Deluxe_Barn.png"));
    private final int capacity;
    private final int price;
    private final Texture texture;
    AnimalPlaceType(int capacity, int price,Texture texture) {
        this.capacity = capacity;
        this.price = price;
        this.texture = texture;
    }
    public int getCapacity() {
        return capacity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Texture getInventoryTexture() {
        return texture;
    }
}
