package org.example.foraging;

import org.example.models.App;
import org.example.models.enums.Season;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;
import org.example.models.plant.Crop;
import org.example.models.plant.SeedType;
import org.example.models.plant.Tree;
import org.example.models.plant.TreeType;

import java.util.List;
import java.util.Random;

public abstract class ForagingController {
    public static void setForagingForNextDay(){
        for (PlayerMap playerMap : App.getCurrentGame().getGameMap().getPlayerMaps()) {
            for (Tile tile : playerMap.getTiles()) {
                Random random = new Random();
                int randInt = random.nextInt(100) + 1;

                if (randInt == 1) {
                    if (tile.isPlowed() && tile.getPlaceable() == null) {
                        setSeedForaging(tile);
                        return;
                    }
                    randInt = random.nextInt(3) + 1;
                    if (randInt == 1) {
                        setCropForaging(tile);
                        return;
                    }
                    else if (randInt == 2) {
                        setTreeForaging(tile);
                        return;
                    }
                    setMineralForaging(tile);
                }
            }
        }
    }

    public static void setMineralForaging(Tile tile) {
        //TODO
    }

    public static void setTreeForaging(Tile tile) {
        Random random = new Random();
        ForagingTree randomForagingTree = ForagingTree.values()[random.nextInt(ForagingTree.values().length)];

        TreeType treeType = randomForagingTree.getTreeType();
        tile.setPlaceable(new Tree(true, treeType, false, tile));
    }


    public static void setCropForaging(Tile tile) {
        Random random = new Random();
        ForagingCrop foragingCrop;
        while (true){
            int randInt = random.nextInt(ForagingCrop.values().length);
            foragingCrop = ForagingCrop.values()[randInt];
            if (foragingCrop.getSeasons().contains(App.getCurrentGame().getDate().getSeason()))
                break;
        }
        tile.setPlaceable(new Crop(true, foragingCrop.getCropType(), false, tile));
    }


    public static void setSeedForaging(Tile tile) {
        Season currentSeason = App.getCurrentGame().getDate().getSeason();
        List<SeedType> validSeeds = ForagingSeed.getSeedTypesBySeason(currentSeason);

        Random random = new Random();
        SeedType chosenSeed = validSeeds.get(random.nextInt(validSeeds.size()));

        Crop crop = new Crop(false, chosenSeed.getCropType(), false, tile);
        tile.setPlowed(false);
        tile.setPlaceable(crop);
    }

}
