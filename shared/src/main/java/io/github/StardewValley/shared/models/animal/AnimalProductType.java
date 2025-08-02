package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum AnimalProductType implements BackPackableType {
    Egg(50,new Texture("Animal_product/Egg.png")),
    LargeEgg(95,new Texture("Animal_product/Large_Egg.png")),
    DuckEgg(95,new Texture("Animal_product/Duck_Egg.png")),
    DuckFeather(250,new Texture("Animal_product/Duck_Feather.png")),
    Wool(340,new Texture("Animal_product/Wool.png")),
    RabbitFoot(565,new Texture("Animal_product/Rabbit%27s_Foot.png")),
    DinosaurEgg(350,new Texture("Animal_product/Dinosaur_Egg.png")),
    Milk(125,new Texture("Animal_product/Milk.png")),
    LargeMilk(190,new Texture("Animal_product/Large_Milk.png")),
    GoatMilk(225,new Texture("Animal_product/Goat_Milk.png")),
    LargeGoatMilk(345,new Texture("Animal_product/Large_Goat_Milk.png")),
    Truffle(625,new Texture("Animal_product/Truffle.png"));

    private final double price;
    private final Texture icon;

    AnimalProductType(double price, Texture icon) {
        this.price = price;
        this.icon = icon;
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
        return icon;
    }
}
