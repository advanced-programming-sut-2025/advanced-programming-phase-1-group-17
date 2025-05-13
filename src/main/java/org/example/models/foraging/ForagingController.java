package org.example.models.foraging;

import org.example.models.App;
import org.example.models.NormalItem;
import org.example.models.NormalItemType;
import org.example.models.enums.Season;
import org.example.models.tools.ToolMaterial;
import org.example.models.map.PlayerMap;
import org.example.models.map.Quarry;
import org.example.models.map.Tile;
import org.example.models.plant.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class ForagingController {
    public static void setForagingForNextDay() {
        Random random = new Random();
        for (PlayerMap playerMap : App.getCurrentGame().getGameMap().getPlayerMaps()) {
            for (Tile tile : playerMap.getTiles()) {

                if (tile.getPlaceable() instanceof Quarry) {
                    if (random.nextInt(100) == 1)
                        setMineralForaging(tile);
                    continue;
                }

                if (tile.getPlaceable() != null)
                    continue;
                int randInt = random.nextInt(100) + 1;

                if (randInt == 1) {
                    if (tile.isPlowed()) {
                        setSeedForaging(tile);
                        continue;
                    }
                    randInt = random.nextInt(5) + 1;
                    if (randInt == 1) {
                        setCropForaging(tile);
                    } else if (randInt == 2){
                        setTreeForaging(tile);
                    } else if (randInt == 3) {
                        setStoneForaging(tile);
                    } else if (randInt == 4) {
                        setWoodForaging(tile);
                    } else
                        setGrassForaging(tile);
                }
            }
        }
    }

    private static void setGrassForaging(Tile tile) {
        Random random = new Random();
        int randInt = random.nextInt(2);
        if (randInt == 0)
            tile.setPlaceable(new NormalItem(NormalItemType.Fibre));
        else
            tile.setPlaceable(new NormalItem(NormalItemType.Grass));
    }

    private static void setWoodForaging(Tile tile) {
        tile.setPlaceable(new NormalItem(NormalItemType.Wood));
    }

    private static void setStoneForaging(Tile tile) {
        tile.setPlaceable(new Mineral(MineralType.Stone, true));
    }

    public static void setMineralForaging(Tile tile) {
        tile.setPlaceable(new Mineral(getRandomMineralType(), true));
    }

    public static boolean canBreakMineral(ToolMaterial toolMaterial, MineralType mineralType) {
        if (mineralType.equals(MineralType.Stone))
            return true;
        if (toolMaterial.equals(ToolMaterial.Basic)) {
            return mineralType.equals(MineralType.CopperOre);
        } else if (toolMaterial.equals(ToolMaterial.Copper)) {
            return mineralType.equals(MineralType.IronOre) || mineralType.equals(MineralType.CopperOre);
        } else if (toolMaterial.equals(ToolMaterial.Iron)) {
            return !mineralType.equals(MineralType.IridiumOre);
        }
        return true;
    }

    public static MineralType getRandomMineralType() {
        Random random = new Random();
        return MineralType.values()[random.nextInt(MineralType.values().length)];
    }

    public static TreeType getRandomTreeType() {
        Random random = new Random();
        ForagingTree randomForagingTree = ForagingTree.values()[random.nextInt(ForagingTree.values().length)];
        return randomForagingTree.getTreeType();
    }

    public static void setTreeForaging(Tile tile) {
        TreeType treeType = getRandomTreeType();
        tile.setPlaceable(new Tree(true, treeType, tile, false));
    }


    public static void setCropForaging(Tile tile) {
        Random random = new Random();
        ForagingCropType foragingCrop;
        do {
            int randInt = random.nextInt(ForagingCropType.values().length);
            foragingCrop = ForagingCropType.values()[randInt];
        } while (!foragingCrop.getSeasons().contains(App.getCurrentGame().getDate().getSeason()));
        tile.setPlaceable(new Crop(true, foragingCrop.getCropType(), tile, false));
    }


    public static void setSeedForaging(Tile tile) {
        Season currentSeason = App.getCurrentGame().getDate().getSeason();
        List<SeedType> validSeeds = ForagingSeed.getSeedTypesBySeason(currentSeason);

        Random random = new Random();
        SeedType chosenSeed = validSeeds.get(random.nextInt(validSeeds.size()));

        Crop crop = new Crop(false, Objects.requireNonNull(CropType.getCropTypeBySeedType(chosenSeed)), tile, false);
        tile.setPlowed(false);
        tile.setPlaceable(crop);

    }

}
