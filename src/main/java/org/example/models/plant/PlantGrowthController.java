package org.example.models.plant;

import org.example.models.App;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;

public abstract class PlantGrowthController {
    public static void growOneDay() {
        for (PlayerMap playerMap : App.getCurrentGame().getGameMap().getPlayerMaps()) {
            for (Tile tile : playerMap.getTiles()) {
                if (tile.getPlaceable() instanceof Tree tree)
                    tree.goToNextDay();
                else if (tile.getPlaceable() instanceof Crop crop)
                    crop.goToNextDay();
            }
        }
    }
}
