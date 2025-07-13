package org.example.models.market;

public enum StoreType {
    Blacksmith(9, 16, StoreOwnerName.Clint),
    JojaMart(9, 23, StoreOwnerName.Morris),
    PierresGeneralStore(9, 17, StoreOwnerName.Pierre),
    CarpentersShop(9, 20, StoreOwnerName.Robin),
    FishShop(9, 17, StoreOwnerName.Willy),
    Ranch(9, 16, StoreOwnerName.Marnie),
    StardropSaloon(12, 24, StoreOwnerName.Gus);

    private final int openingHour;
    private final int closingHour;
    private final StoreOwnerName ownerName;

    StoreType(int openingHour, int closingHour, StoreOwnerName ownerName) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.ownerName = ownerName;
    }

    public int getOpeningHour() { return openingHour; }
    public int getClosingHour() { return closingHour; }
    public StoreOwnerName getOwnerName() { return ownerName; }
}

