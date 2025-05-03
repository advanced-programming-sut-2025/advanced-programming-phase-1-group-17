package org.example.models.enums;

public enum AnimalProductType {
    Egg(50),
    BigEgg(95),
    DuckEgg(95),
    DuckFeather(250),
    RabbitWool(340),
    RabbitFood(565),
    DinosaurEgg(350),
    CowMilk(125),
    BigCowMilk(190),
    GoatMilk(225),
    BigGoatMilk(345),
    SheepWool(340),
    PigTruffle(625);
    private final int price;
    AnimalProductType(int price) {
        this.price = price;
    }

}
