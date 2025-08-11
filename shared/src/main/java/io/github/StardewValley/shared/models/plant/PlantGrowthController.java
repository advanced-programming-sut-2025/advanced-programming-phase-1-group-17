package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.map.PlayerMap;
import io.github.StardewValley.shared.models.map.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class PlantGrowthController {
    private static final Random random = new Random();

    public static void growOneDay(Game game) {
        HashMap<Player, ArrayList<Plant>> plants = new HashMap<>();
        for (Player player : game.getPlayers()) {
            plants.put(player, new ArrayList<>());
        }
        for (Tile tile : game.getTiles()) {
            if (tile.getPlaceable() instanceof Tree tree) {
                tree.goToNextDay();
                if (!tree.isForaging)
                    plants.get(tree.getTile().getOwner()).add(tree);
            } else if (tile.getPlaceable() instanceof Crop crop) {
                crop.goToNextDay();
                handleCropAdding(plants, crop);
            }
        }
        plants.forEach(((player, plants1) -> {
            double probability = Math.floor(plants.size() / 16.0) * 25;

            int randInt = random.nextInt(100);
            if (randInt < probability)
                crowAttack(plants1);
        }));
    }

    private static void handleCropAdding(HashMap<Player, ArrayList<Plant>> plants, Crop crop) {
        if (crop.isForaging)
            return;
        if (!crop.isGiant())
            plants.get(crop.getTile().getOwner()).add(crop);
        else {
            boolean isAddable = false;
            for (Crop neighborGiantTile : crop.neighborGiantTiles) {
                if (plants.get(crop.getTile().getOwner()).contains(neighborGiantTile)) {
                    isAddable = true;
                    break;
                }
            }
            if (isAddable)
                plants.get(crop.getTile().getOwner()).add(crop);
        }
    }

    private static Result crowAttack(ArrayList<Plant> plants) {
        boolean canBreak = true;
        for (Plant plant : plants) {
            if (!plant.isInsideGreenhouse) {
                canBreak = false;
                break;
            }
        }
        while (!canBreak) {
            int randInt = random.nextInt(plants.size());
            Plant plant = plants.get(randInt);
            CropType cropType = null;
            TreeType treeType = null;
            if (plant.isInsideGreenhouse)
                continue;
            if (plant instanceof Crop crop) {
                cropType = crop.getType();
            } else if (plant instanceof Tree tree) {
                treeType = tree.getType();
            }

            canBreak = true;
            if (plant.getTile().isCrowImmunity())
                return new Result(false,
                    "Crows wanted to attack tile <%d, %d> (%s).\nBut, because of Scarecrow, there was no attack."
                        .formatted(plant.tile.getX(), plant.tile.getY(), (plant instanceof Tree) ? treeType.name() : cropType.getName()));

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
            //TODo
            //.trigger(plant.tile.getX(), plant.tile.getY());
//            return new Result(true,
//                "Crows attacked tile <%d, %d> (%s) last night."
//                    .formatted(plant.tile.getX(), plant.tile.getY(), (plant instanceof Tree) ? treeType.name() : cropType.getName()));
        }
        return new Result(false, "There were no crow attacks last night.");
    }

}
