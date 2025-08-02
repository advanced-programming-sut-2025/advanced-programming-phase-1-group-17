package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum AnimalProductType implements BackPackableType {
    Egg(50,("Animal_product/Egg.png")),
    LargeEgg(95,("Animal_product/Large_Egg.png")),
    DuckEgg(95,("Animal_product/Duck_Egg.png")),
    DuckFeather(250,("Animal_product/Duck_Feather.png")),
    Wool(340,("Animal_product/Wool.png")),
    RabbitFoot(565,("Animal_product/Rabbit%27s_Foot.png")),
    DinosaurEgg(350,("Animal_product/Dinosaur_Egg.png")),
    Milk(125,("Animal_product/Milk.png")),
    LargeMilk(190,("Animal_product/Large_Milk.png")),
    GoatMilk(225,("Animal_product/Goat_Milk.png")),
    LargeGoatMilk(345,("Animal_product/Large_Goat_Milk.png")),
    Truffle(625,("Animal_product/Truffle.png"));

    private final double price;
    private final String icon;

    AnimalProductType(double price, String icon) {
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
    public String getInventoryTexture() {
        return icon;
    }
}
