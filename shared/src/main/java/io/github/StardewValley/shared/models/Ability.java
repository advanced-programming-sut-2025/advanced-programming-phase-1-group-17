package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.cooking.Recipe;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;

public class Ability {
    private int farmingAbility = 0;
    private int miningAbility = 0;
    private int foragingAbility = 0;
    private int fishingAbility = 0;
    private int farmingLevel = 0;
    private int miningLevel = 0;
    private int foragingLevel = 0;
    private int fishingLevel = 0;
    private Player player;

    public Ability() {
    }

    public Ability(AbilityDTO dto) {
        this.farmingLevel = dto.getFarmingLevel();
        this.fishingLevel = dto.getFishingLevel();
        this.foragingLevel = dto.getForagingLevel();
        this.miningLevel = dto.getMiningLevel();
    }

    Ability(Player player) {
        this.player = player;
    }

    public void increaseFarmingAbility() {
        //TODO: in higher levels, you can gain better products
        farmingAbility += 5;

        if (farmingAbility < 150) return;
        else if (farmingAbility < 250) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Sprinkler));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.BeeHouse));

            player.getRecipes().add(new Recipe(FoodType.FarmersLunch));
            farmingLevel = 1;
            return;
        }
        else if (farmingAbility < 350) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.QualitySprinkler));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.DeluxeScarecrow));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.CheesePress));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.PreservesJar));
            farmingLevel = 2;
            return;
        }
        else if (farmingAbility < 450) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.IridiumSprinkler));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Keg));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Loom));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.OilMaker));
            farmingLevel = 3;
            return;
        }
        farmingLevel = 4;
    }


    public void increaseMiningAbility() {
        miningAbility += 10;

        if (miningAbility < 150) return;
        else if (miningAbility < 250) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.CherryBomb));

            player.getRecipes().add(new Recipe(FoodType.MinersTreat));
            miningLevel = 1;
            return;
        }
        else if (miningAbility < 350) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Bomb));
            miningLevel = 2;
            return;
        }
        else if (miningAbility < 450) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MegaBomb));
            miningLevel = 3;
            return;
        }
        miningLevel = 4;
    }

    public void increaseForagingAbility() {
        foragingAbility += 10;

        if (foragingAbility < 150) return;
        else if (foragingAbility < 250) {
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.CharcoalKlin));
            foragingLevel = 1;
            return;
        }
        else if (foragingAbility < 350) {
            player.getRecipes().add(new Recipe(FoodType.VegetableMedley));
            foragingLevel = 2;
            return;
        }
        else if (foragingAbility < 450) {
            player.getRecipes().add(new Recipe(FoodType.SurvivalBurger));
            foragingLevel = 3;
            return;
        }
        player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MysticTreeSeed));
        foragingLevel = 4;
    }

    public void increaseFishingAbility() {
        //TODO: with every fish fished, you gain 5 points

        fishingAbility += 5;

        if (fishingAbility < 150) return;
        else if (fishingAbility < 250){
            fishingLevel = 1;
            return;
        }
        else if (fishingAbility < 350) {
            player.getRecipes().add(new Recipe(FoodType.DishOTheSea));
            fishingLevel = 2;
            return;
        }
        else if (fishingAbility < 450){
            player.getRecipes().add(new Recipe(FoodType.SeafoamPudding));
            fishingLevel = 3;
            return;
        }
        fishingLevel = 4;
    }


    public int getFarmingLevel() {
        return farmingLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

    public static AbilityDTO getDTO(Ability ability) {
        return new AbilityDTO(
            ability.farmingLevel,
            ability.miningLevel,
            ability.foragingLevel,
            ability.fishingLevel
        );
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }

    public void setFarmingAbility(int farmingAbility) {
        this.farmingAbility = farmingAbility;
    }

    public int getMiningAbility() {
        return miningAbility;
    }

    public void setMiningAbility(int miningAbility) {
        this.miningAbility = miningAbility;
    }

    public int getForagingAbility() {
        return foragingAbility;
    }

    public void setForagingAbility(int foragingAbility) {
        this.foragingAbility = foragingAbility;
    }

    public int getFishingAbility() {
        return fishingAbility;
    }

    public void setFishingAbility(int fishingAbility) {
        this.fishingAbility = fishingAbility;
    }

    public void setFarmingLevel(int farmingLevel) {
        this.farmingLevel = farmingLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public void setForagingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
    }

    public void setFishingLevel(int fishingLevel) {
        this.fishingLevel = fishingLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
