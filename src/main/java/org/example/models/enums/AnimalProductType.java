package org.example.models.enums;

import org.example.models.BackPackableType;
import org.example.models.trade.ShippingBinType;

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
}
