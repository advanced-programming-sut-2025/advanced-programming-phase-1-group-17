package org.example.models.plant;

import org.example.models.App;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;

import java.util.ArrayList;
import java.util.Random;

public abstract class PlantGrowthController {
    private static final Random random = new Random();

    public static void growOneDay() {
        for (PlayerMap playerMap : App.getCurrentGame().getGameMap().getPlayerMaps()) {
            ArrayList<Plant> plants = new ArrayList<>();

            for (Tile tile : playerMap.getTiles()) {
                if (tile.getPlaceable() instanceof Tree tree) {
                    tree.goToNextDay();
                    plants.add(tree);
                } else if (tile.getPlaceable() instanceof Crop crop) {
                    crop.goToNextDay();
                    handleCropAdding(plants, crop);
                }
                double probability = Math.floor(plants.size() / 16.0) * 25;

                int randInt = random.nextInt(100);
                if (randInt < probability)
                    crowAttack(plants);
            }
        }
    }

    private static void handleCropAdding(ArrayList<Plant> plants, Crop crop) {
        if (!crop.isGiant())
            plants.add(crop);
        else {
            boolean isAddable = false;
            for (Crop neighborGiantTile : crop.neighborGiantTiles) {
                if (plants.contains(neighborGiantTile)) {
                    isAddable = true;
                    break;
                }
            }
            if (isAddable)
                plants.add(crop);
        }
    }

    private static void crowAttack(ArrayList<Plant> plants) {
        boolean canBreak = false;
        while (!canBreak) {
            int randInt = random.nextInt(plants.size());
            Plant plant = plants.get(randInt);
            if (plant.isInsideGreenhouse)
                continue;

            canBreak = true;
            if (plant instanceof Tree tree) {
                tree.getTile().setPlaceable(null);
                tree.hasFruit = false;
            } else if (plant instanceof Crop crop) {
                if (crop.getType().isOneTime())
                    crop.getTile().setPlaceable(null);
                else {
                    crop.hasFruit = false;
                }
            }
            plant.getTile().setPlaceable(null);
        }
    }

}
