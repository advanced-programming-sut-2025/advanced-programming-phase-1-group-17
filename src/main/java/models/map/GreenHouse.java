package models.map;
import models.Placeable;
import models.Player;
import models.tools.WaterStorage;

import java.util.ArrayList;

public class GreenHouse implements Placeable {
    private ArrayList<Tile> tiles = new ArrayList<>();
    private static ArrayList<GreenHouse> greenHouse = new ArrayList<GreenHouse>();
    private boolean isActive = false;
    private Player player;
    private WaterStorage waterStorage;

    public GreenHouse(Player player) {
        this.isActive = true;
        greenHouse.add(this);
        this.player = player;
        //

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

}
