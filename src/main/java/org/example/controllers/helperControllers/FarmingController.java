package org.example.controllers.helperControllers;

import org.example.models.App;
import org.example.models.BackPackableType;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.Season;
import org.example.models.map.GreenHouse;
import org.example.models.map.Tile;
import org.example.models.plant.*;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;
import org.example.models.tools.ToolType;

public class FarmingController {

    public Result showPlant(String x, String y) {
        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        Tile tile = Tile.getTile(x1, y1);
        if (tile == null) {
            return new Result(false, "Tile out of map.");
        }

        if (tile.getPlaceable() instanceof Tree tree) {
            return new Result(true, """
                    Name: %s
                    Days Left Till Full Growth: %d
                    Current Stage: %d
                    Fertilizer: %s""".formatted(tree.getType().name(), tree.getDaysTillFullGrowth(),
                    tree.getCurrentStageIndex() + 1, tree.getFertilizerType()));
        } else if (tile.getPlaceable() instanceof Crop crop) {
            return new Result(true, """
                    Name: %s
                    Days Left Till Full Growth: %d
                    Current Stage: %d
                    Fertilizer: %s""".formatted(crop.getName(), crop.getDaysTillFullGrowth(),
                    crop.getCurrentStageIndex() + 1, crop.getFertilizerType()));
        }
        return new Result(false, "There is no plant in this tile");
    }


    public Result fertilize(String fertilizer, String direction) {
        FertilizerType fertilizerType = FertilizerType.getFertilizerTypeByName(fertilizer);
        if (fertilizerType == null) {
            return new Result(false, "No fertilizer with name %s".formatted(fertilizer));
        }

        int[] directions = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int newX = player.getX() + directions[0];
        int newY = player.getY() + directions[1];
        Tile tile = App.getCurrentGame().getTileByIndex(newX, newY);

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


    public Result plantSeed(String source, String direction) {
        int[] directions = App.handleDirection(Integer.parseInt(direction));
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        SeedType seedType = SeedType.getSeedTypeByName(source);
        SaplingType saplingType = SaplingType.getTypeByName(source);

        int newX = player.getX() + directions[0];
        int newY = player.getY() + directions[1];

        Tile tile = App.getCurrentGame().getTileByIndex(newX, newY);
        if (tile == null) {
            return new Result(false, "TIle out of map");
        }

        if (tile.getPlaceable() != null && !(tile.getPlaceable() instanceof GreenHouse greenHouse))
            return new Result(false, "Specified tile is already occupied");


        if (!tile.isPlowed())
            return new Result(false, "The Specified tile is not Plowed");

        else if (seedType == null && saplingType == null)
            return new Result(false, "No SeedType with this Name");

        if (seedType != null) {
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
                ((Crop)tile.getPlaceable()).checkCouldBeGiant();
            }

            player.getBackPack().useItem(seedType);
            return new Result(true,
                    "Successfully planted a plant of type %s in (%d,%d)".formatted(
                            seedType.name(), newX, newY
                    ));
        }
        //if saplingType != null
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

        player.getBackPack().useItem(saplingType);
        return new Result(true,
                "Successfully planted a plant of type %s in (%d,%d)".formatted(
                        saplingType.name(), newX, newY
                ));
    }


    public Result craftInfo(String name) {
        StringBuilder result = new StringBuilder();
        CropType cropType = CropType.getCropTypeByName(name);
        FruitType fruitType = FruitType.getFruitTypeByName(name);

        if (cropType != null) {
            result.append("""
                    Name: %s
                    Source: %s""".formatted(cropType.name(),
                    (cropType.getSource() == null) ? "Foraging" : cropType.getSource().name()));
            result.append("\nStages: ");
            int counter = 1;
            for (Integer stage : cropType.getStages()) {
                result.append("%s".formatted(stage));
                if (counter != cropType.getStages().size())
                    result.append("-");
                counter++;
            }
            result.append("\n");
            result.append("""
                    Total Harvest Time: %d
                    One Time: %s
                    Regrowth Time: %s
                    Base Sell Price: %s
                    Is Edible: %s
                    Base Energy: %s
                    Base Health:
                    Season: """.formatted(
                    cropType.getTotalGrowthTime(),
                    cropType.isOneTime(),
                    (cropType.getRegrowthTime() == -1) ? "" : cropType.getRegrowthTime(),
                    cropType.getBaseSellPrice(),
                    cropType.isEdible(),
                    (cropType.getEnergy() == -1) ? "" : cropType.getEnergy()
            ));


            counter = 1;
            for (Season season : cropType.getSeasons()) {
                result.append("%s".formatted(season));
                if (counter != cropType.getSeasons().size())
                    result.append("-");
                counter++;
            }

            result.append("\nCan Become Giant: %s".formatted(cropType.isCanBecomeGiant()));
            return new Result(true, result.toString());
        }


        if (fruitType != null) {
            TreeType tree = TreeType.getTreeTypeByFruitType(fruitType);
            result.append("""
                    Name: %s
                    Source: %s""".formatted(fruitType.name(), tree.getSapling().name()));
            result.append("\nStages: ");
            int counter = 1;
            for (Integer stage : tree.getStages()) {
                result.append("%s".formatted(stage));
                if (counter != tree.getStages().size())
                    result.append("-");
                counter++;
            }
            result.append("\n");
            result.append("""
                    Total Harvest Time: %d
                    One Time: False
                    Regrowth Time: %s
                    Base Sell Price: %.0f
                    Is Edible: %s
                    Base Energy: %s
                    Base Health:
                    Season: """.formatted(
                    tree.getTotalGrowthTime(),
                    (tree.getFruitHarvestCycle() == -1) ? "" : tree.getFruitHarvestCycle(),
                    fruitType.getBaseSellPrice(), fruitType.isEdible(),
                    (fruitType.isEdible()) ? "" : fruitType.getEnergy()
            ));

            counter = 1;
            for (Season season : tree.getSeasons()) {
                result.append("%s".formatted(season));
                if (counter != tree.getSeasons().size())
                    result.append("-");
                counter++;
            }

            result.append("\nCan Become Giant: False");
            return new Result(true, result.toString());
        }

        return new Result(false, "No Crop or Fruit Found with this Name");
    }


    public Result howMuchWater() {
        BackPack backPack = App.getCurrentGame().getCreator().getBackPack();
        for (BackPackableType backPackableType : backPack.getBackPackItems().keySet()) {
            if (backPackableType.getClass().equals(ToolType.class)) {
                Tool tool = (Tool) backPack.getBackPackItems().get(backPackableType).get(0);
                if (tool.getToolType().equals(ToolType.WateringCan))
                    return new Result(true, "%d".formatted(tool.getWateringCanStorage()));
            }
        }
        return new Result(false, "You do not have any watering can."); //Must never happen
    }



}
