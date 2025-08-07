package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.shared.dto.HandleWorldClickResponse;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.plant.*;

public class FarmingController {
    public HandleWorldClickResponse fertilize(Fertilizer fertilizer, int dx, int dy, Player player) {
        FertilizerType fertilizerType = fertilizer.getType();

        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;
        Tile tile = Tile.getTile(newX, newY);

        if (tile == null) {
            return new HandleWorldClickResponse(false, "Tile out of map.",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }

        if (player.getBackPack().getInventorySize(fertilizerType.getName()) == 0) {
            return new HandleWorldClickResponse(false, "You do not have fertilizer of type %s".formatted(fertilizerType.getName()),
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }

        if (tile.getPlaceable() instanceof Plant plant) {
            player.getBackPack().useItem(fertilizerType);
            plant.setFertilizerType(fertilizerType);
            if (plant instanceof Crop crop) {
                if (crop.isGiant()) {
                    for (Crop neighborGiantTile : crop.getNeighborGiantTiles()) {
                        neighborGiantTile.setFertilizerType(fertilizerType);
                    }
                }
            }
            return new HandleWorldClickResponse(true, "Fertilized successfully", HandleWorldClickResponse.ActionType.NONE);
        }

        return new HandleWorldClickResponse(false, "No plant in this tile", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
    }


    public HandleWorldClickResponse plantSeed(Seed seed, int dx, int dy, Player player) {
        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;

        Tile tile = Tile.getTile(newX, newY);
        if (tile == null) {
            return new HandleWorldClickResponse(false, "Tile out of map",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }

        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse greenHouse))
            return new HandleWorldClickResponse(false, "Specified tile is already occupied",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);


        if (!tile.isPlowed())
            return new HandleWorldClickResponse(false, "The Specified tile is not Plowed",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);

        SeedType seedType = seed.getType();
        if (player.getBackPack().getBackPackItems().get(seedType) == null ||
            player.getBackPack().getBackPackItems().get(seedType).isEmpty())
            return new HandleWorldClickResponse(false, "You do not have any seed of this type",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);

        if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
            if (!greenHouse.isActive())
                return new HandleWorldClickResponse(false, "You need to Build the Greenhouse first",
                    HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            tile.setPlaceable(new Crop(false, CropType.getCropTypeBySeedType(seedType), tile, true));
        } else if (tile.getPlaceable() == null) {
            CropType cropType = CropType.getCropTypeBySeedType(seedType);
            if (!cropType.getSeasons().contains(App.getCurrentGame().getDate().getSeason()))
                return new HandleWorldClickResponse(false, "Can not plant crop of type %s in season %s outside the greenhouse.".formatted(
                    cropType.name(), App.getCurrentGame().getDate().getSeason()
                ), HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            tile.setPlaceable(new Crop(false, cropType, tile, false));
            ((Crop) tile.getPlaceable()).checkCouldBeGiant();
        }

        tile.setWalkAble(false);
        player.getBackPack().useItem(seedType);
        return new HandleWorldClickResponse(true,
            "Successfully planted a plant of type %s in (%d,%d)".formatted(
                seedType.name(), newX, newY
            ), HandleWorldClickResponse.ActionType.NONE);
    }


    public HandleWorldClickResponse plantSapling(Sapling sapling, int dx, int dy, Player player) {
        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;

        Tile tile = Tile.getTile(newX, newY);
        if (tile == null) {
            return new HandleWorldClickResponse(false, "Tile out of map",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
        }

        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse greenHouse))
            return new HandleWorldClickResponse(false, "Specified tile is already occupied",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);


        if (!tile.isPlowed())
            return new HandleWorldClickResponse(false, "The Specified tile is not Plowed",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);

        SaplingType saplingType = sapling.getType();
        if (player.getBackPack().getBackPackItems().get(saplingType) == null ||
            player.getBackPack().getBackPackItems().get(saplingType).isEmpty())
            return new HandleWorldClickResponse(false, "You do not have any sapling of this type",
                HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);


        if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
            if (!greenHouse.isActive())
                return new HandleWorldClickResponse(false, "You need to Build the Greenhouse first",
                    HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            tile.setPlaceable(new Tree(false, TreeType.getTreeTypeBySaplingType(saplingType), tile, true));
        } else if (tile.getPlaceable() == null) {
            TreeType treeType = TreeType.getTreeTypeBySaplingType(saplingType);
            if (!treeType.getSeasons().contains(App.getCurrentGame().getDate().getSeason()))
                return new HandleWorldClickResponse(false, "Can not plant tree of type %s in season %s outside the greenhouse.".formatted(
                    treeType.name(), App.getCurrentGame().getDate().getSeason()
                ), HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
            tile.setPlaceable(new Tree(false, treeType, tile, false));
        }

        tile.setWalkAble(false);
        player.getBackPack().useItem(saplingType);
        return new HandleWorldClickResponse(true,
            "Successfully planted a plant of type %s in (%d,%d)".formatted(
                saplingType.name(), newX, newY
            ), HandleWorldClickResponse.ActionType.NONE);
    }
}
