package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum AnimalPlaceType implements BackPackableType {
    Coop(4,4000,("animalplace/Coop.png")),
    BigCoop(8,10000,("animalplace/Big_Coop.png")),
    DeluxeCoop(12,20000,("animalplace/Deluxe_Coop.png")),
    Barn(4,6000,("animalplace/Barn.png")),
    BigBarn(8,12000,("animalplace/Big_Barn.png")),
    DeluxeBarn(12,25000,("animalplace/Deluxe_Barn.png"));
    private final int capacity;
    private final int price;
    private final String texture;
    AnimalPlaceType(int capacity, int price,String texture) {
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
    public String getInventoryTexturePath() {
        return texture;
    }
}
