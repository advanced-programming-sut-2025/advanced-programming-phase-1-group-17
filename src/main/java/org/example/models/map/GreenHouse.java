package org.example.models.map;
import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.tools.WaterStorage;

import java.util.ArrayList;

public class GreenHouse implements Placeable {
    private ArrayList<Tile> tiles = new ArrayList<>();
    private static ArrayList<GreenHouse> greenHouse = new ArrayList<GreenHouse>();
    private boolean isActive = false;
    private Player player;
    private WaterStorage waterStorage;
    private boolean offSeasonCultivation = false;
    private boolean need_a_scarecrow = false;
    //TODO
    public GreenHouse(Player player) {
        this.isActive = true;
        greenHouse.add(this);
        this.player = player;
        //TODO
        
    }
    public void changeDay() {}
    public GreenHouse getGreenHouse(Player player) {
        for (GreenHouse greenHouse : greenHouse) {
            if (greenHouse.player.equals(player)) {
                return greenHouse;
            }
        }
        return null;
    }

    public boolean isOffSeasonCultivation() {
        return offSeasonCultivation;
    }

    public void setOffSeasonCultivation(boolean offSeasonCultivation) {
        this.offSeasonCultivation = offSeasonCultivation;
    }

    public boolean isNeed_a_scarecrow() {
        return need_a_scarecrow;
    }

    public void setNeed_a_scarecrow(boolean need_a_scarecrow) {
        this.need_a_scarecrow = need_a_scarecrow;
    }

    public static ArrayList<GreenHouse> getGreenHouse() {
        return greenHouse;
    }

    public static void setGreenHouse(ArrayList<GreenHouse> greenHouse) {
        GreenHouse.greenHouse = greenHouse;
    }
}
