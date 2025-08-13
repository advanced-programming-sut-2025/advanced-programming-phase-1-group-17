package io.github.StardewValley.shared.models.foraging;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.plant.*;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.map.Quarry;
import io.github.StardewValley.shared.models.map.Tile;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class ForagingController {
    public static void setForagingForNextDay(Game game) {
        Random random = new Random();
        for (Tile tile : game.getTiles()) {
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
                    setSeedForaging(tile, game);
                    continue;
                }
                randInt = random.nextInt(5) + 1;
                if (randInt == 1) {
                    setCropForaging(tile, game);
                } else if (randInt == 2) {
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

    private static void setGrassForaging(Tile tile) {
        Random random = new Random();
        int randInt = random.nextInt(2);
        if (randInt == 0)
            tile.setPlaceable(new NormalItem(NormalItemType.Fiber));
        else
            tile.setPlaceable(new NormalItem(NormalItemType.Grass));
        tile.setWalkAble(false);
    }

    private static void setWoodForaging(Tile tile) {
        tile.setPlaceable(new NormalItem(NormalItemType.Wood));
        tile.setWalkAble(false);
    }

    private static void setStoneForaging(Tile tile) {
        tile.setPlaceable(new Mineral(MineralType.Stone, true));
        tile.setWalkAble(false);
    }

    public static void setMineralForaging(Tile tile) {
        tile.setPlaceable(new Mineral(getRandomMineralType(), true));
        tile.setWalkAble(false);
    }

    public static boolean canBreakMineral(ToolMaterial toolMaterial, MineralType mineralType) {
        if (mineralType.equals(MineralType.Stone))
            return true;
        if (toolMaterial.equals(ToolMaterial.Basic)) {
            return mineralType.equals(MineralType.CopperOre);
        } else if (toolMaterial.equals(ToolMaterial.Copper)) {
            return mineralType.equals(MineralType.IronOre) || mineralType.equals(MineralType.CopperOre);
        } else if (toolMaterial.equals(ToolMaterial.Steel)) {
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
        tile.setWalkAble(false);
    }


    public static void setCropForaging(Tile tile, Game game) {
        Random random = new Random();
        ForagingCropType foragingCrop;
        do {
            int randInt = random.nextInt(ForagingCropType.values().length);
            foragingCrop = ForagingCropType.values()[randInt];
        } while (!foragingCrop.getSeasons().contains(game.getDate().getSeason()));
        tile.setPlaceable(new Crop(true, foragingCrop.getCropType(), tile, false));
        tile.setWalkAble(false);
    }


    public static void setSeedForaging(Tile tile, Game game) {
        Season currentSeason = game.getDate().getSeason();
        List<SeedType> validSeeds = ForagingSeed.getSeedTypesBySeason(currentSeason);

        Random random = new Random();
        SeedType chosenSeed = validSeeds.get(random.nextInt(validSeeds.size()));

        if (CropType.getCropTypeBySeedType(chosenSeed, game) == null)
            return;
        Crop crop = new Crop(false, Objects.requireNonNull(CropType.getCropTypeBySeedType(chosenSeed, game)), tile, false);
        tile.setPlowed(false);
        tile.setPlaceable(crop);
        tile.setWalkAble(false);
    }


    public static void pickForaging(int dx, int dy, Player player, Game game) {
        int x = player.getX() / GameAssetManager.getGameAssetManager().getTileWidth() + dx;
        int y = player.getY() / GameAssetManager.getGameAssetManager().getTileHeight() + dy;
        Tile tile = game.getTile(x, y);

        Placeable placeable = tile.getPlaceable();
        if (placeable instanceof Crop crop) {
            if (crop.isForaging()) {
                player.getBackPack().addItemToInventory(crop);
                tile.setPlaceable(null);
                tile.setWalkAble(true);
            }
        } else if (placeable instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Wood)) {
                player.getBackPack().addItemToInventory(normalItem);
                tile.setPlaceable(null);
                tile.setWalkAble(true);
            }
        }
    }
}
