package io.github.StardewValley.controllers.helperControllers;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.Result;
import io.github.StardewValley.models.enums.Season;
import io.github.StardewValley.models.map.GreenHouse;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.models.plant.*;
import io.github.StardewValley.models.tools.BackPack;
import io.github.StardewValley.models.tools.Tool;
import io.github.StardewValley.models.tools.ToolType;

public class FarmingController {
    public Result fertilize(Fertilizer fertilizer, int dx, int dy) {
        FertilizerType fertilizerType = fertilizer.getType();
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;
        Tile tile = Tile.getTile(newX, newY);

        if (tile == null) {
            return new Result(false, "Tile out of map.");
        }

        if (player.getBackPack().getInventorySize(fertilizerType.getName()) == 0) {
            return new Result(false, "You do not have fertilizer of type %s".formatted(fertilizerType.getName()));
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
            return new Result(true, "Fertilized successfully");
        }

        return new Result(false, "No plant in this tile");
    }


    public Result plantSeed(Seed seed, int dx, int dy) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;

        Tile tile = Tile.getTile(newX, newY);
        if (tile == null) {
            return new Result(false, "Tile out of map");
        }

        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse greenHouse))
            return new Result(false, "Specified tile is already occupied");


        if (!tile.isPlowed())
            return new Result(false, "The Specified tile is not Plowed");

        SeedType seedType = seed.getType();
        if (player.getBackPack().getBackPackItems().get(seedType) == null ||
            player.getBackPack().getBackPackItems().get(seedType).isEmpty())
            return new Result(false, "You do not have any seed of this type");

        if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
            if (!greenHouse.isActive())
                return new Result(false, "You need to Build the Greenhouse first");
            tile.setPlaceable(new Crop(false, CropType.getCropTypeBySeedType(seedType), tile, true));
        } else if (tile.getPlaceable() == null) {
            CropType cropType = CropType.getCropTypeBySeedType(seedType);
            if (!cropType.getSeasons().contains(App.getCurrentGame().getDate().getSeason()))
                return new Result(false, "Can not plant crop of type %s in season %s outside the greenhouse.".formatted(
                    cropType.name(), App.getCurrentGame().getDate().getSeason()
                ));
            tile.setPlaceable(new Crop(false, cropType, tile, false));
            ((Crop) tile.getPlaceable()).checkCouldBeGiant();
        }

        tile.setWalkAble(false);
        player.getBackPack().useItem(seedType);
        return new Result(true,
            "Successfully planted a plant of type %s in (%d,%d)".formatted(
                seedType.name(), newX, newY
            ));
    }


    public Result plantSapling(Sapling sapling, int dx, int dy) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int newX = player.getTileX() + dx;
        int newY = player.getTileY() + dy;

        Tile tile = Tile.getTile(newX, newY);
        if (tile == null) {
            return new Result(false, "TIle out of map");
        }

        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse greenHouse))
            return new Result(false, "Specified tile is already occupied");


        if (!tile.isPlowed())
            return new Result(false, "The Specified tile is not Plowed");

        SaplingType saplingType = sapling.getType();
        if (player.getBackPack().getBackPackItems().get(saplingType) == null ||
            player.getBackPack().getBackPackItems().get(saplingType).isEmpty())
            return new Result(false, "You do not have any sapling of this type");


        if (tile.getPlaceable() instanceof GreenHouse greenHouse) {
            if (!greenHouse.isActive())
                return new Result(false, "You need to Build the Greenhouse first");
            tile.setPlaceable(new Tree(false, TreeType.getTreeTypeBySaplingType(saplingType), tile, true));
        } else if (tile.getPlaceable() == null) {
            TreeType treeType = TreeType.getTreeTypeBySaplingType(saplingType);
            if (!treeType.getSeasons().contains(App.getCurrentGame().getDate().getSeason()))
                return new Result(false, "Can not plant tree of type %s in season %s outside the greenhouse.".formatted(
                    treeType.name(), App.getCurrentGame().getDate().getSeason()
                ));
            tile.setPlaceable(new Tree(false, treeType, tile, false));
        }

        tile.setWalkAble(false);
        player.getBackPack().useItem(saplingType);
        return new Result(true,
            "Successfully planted a plant of type %s in (%d,%d)".formatted(
                saplingType.name(), newX, newY
            ));
    }
}
