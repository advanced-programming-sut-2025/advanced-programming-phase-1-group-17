package org.example.models.map;
import org.example.models.Placeable;
import org.example.models.Player;

import java.util.ArrayList;

public class GreenHouse implements Placeable {
    //TODO: handle sprinkler
    private static ArrayList<GreenHouse> greenHouse = new ArrayList<GreenHouse>();
    private boolean isActive = false;
    private GreenHouseFence fence;


    public GreenHouse(Player player) {
        this.fence = new GreenHouseFence();
        this.isActive = true;
        greenHouse.add(this);
    }
    public void changeDay() {}

    public static ArrayList<GreenHouse> getGreenHouse() {
        return greenHouse;
    }

    public static void setGreenHouse(ArrayList<GreenHouse> greenHouse) {
        GreenHouse.greenHouse = greenHouse;
    }

    public GreenHouseFence getFence() {
        return fence;
    }

    public void setFence(GreenHouseFence fence) {
        this.fence = fence;
    }
}
