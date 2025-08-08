package io.github.StardewValley.shared.models.market;

public enum StoreType {
    Blacksmith(9, 16, StoreOwnerName.Clint, 60, 130, 8, 8),
    JojaMart(9, 23, StoreOwnerName.Morris, 230, 180, 12, 8),
    PierresGeneralStore(9, 17, StoreOwnerName.Pierre, 110, 230, 8, 8),
    CarpentersShop(9, 20, StoreOwnerName.Robin, 180, 80, 8, 8),
    FishShop(9, 17, StoreOwnerName.Willy, 130, 280, 8, 8),
    Ranch(9, 16, StoreOwnerName.Marnie, 80, 180, 8, 8),
    StardropSaloon(12, 24, StoreOwnerName.Gus, 160, 30, 8, 8);

    private final int openingHour;
    private final int closingHour;
    private final StoreOwnerName ownerName;

    //Coordinates
    private final int start_x;
    private final int start_y;
    private final int width;
    private final int height;

    StoreType(int openingHour, int closingHour, StoreOwnerName ownerName,
              int start_x, int start_y, int width, int height) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.ownerName = ownerName;
        this.start_x = start_x;
        this.start_y = start_y;
        this.width = width;
        this.height = height;
    }

    public int getOpeningHour() { return openingHour; }
    public int getClosingHour() { return closingHour; }
    public StoreOwnerName getOwnerName() { return ownerName; }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

