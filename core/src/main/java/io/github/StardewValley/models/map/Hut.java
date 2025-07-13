package io.github.StardewValley.models.map;

import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.cooking.Refrigerator;

import java.util.ArrayList;

public class Hut implements Placeable {
    private Refrigerator refrigerator = new Refrigerator();

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }
}
