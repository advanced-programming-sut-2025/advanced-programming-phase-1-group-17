package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum AnimalProductType implements BackPackableType {
    Egg(50),
    LargeEgg(95),
    DuckEgg(95),
    DuckFeather(250),
    Wool(340),
    RabbitFoot(565),
    DinosaurEgg(350),
    Milk(125),
    LargeMilk(190),
    GoatMilk(225),
    LargeGoatMilk(345),
    Truffle(625);

    private final double price;

    AnimalProductType(double price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return name(); // Uses enum constant name
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Texture getInventoryTexture() {
        //TODO
        return null;
    }
}
