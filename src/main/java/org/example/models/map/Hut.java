package org.example.models.map;

import org.example.models.Placeable;
import org.example.models.cooking.Refrigerator;

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
