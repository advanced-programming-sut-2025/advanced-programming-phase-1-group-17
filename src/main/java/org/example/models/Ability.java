package org.example.models;

import org.example.models.cooking.FoodType;
import org.example.models.cooking.Recipe;
import org.example.models.crafting.CraftingItemType;
import org.example.models.crafting.CraftingRecipe;

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

    Ability(Player player) {
        this.player = player;
    }

    public void increaseFarmingAbility(int abilityAdded) {
        farmingAbility += abilityAdded;

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



    public void increaseMiningAbility(int abilityAdded) {
        miningAbility += abilityAdded;

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


    public void increaseForagingAbility(int abilityAdded) {
        foragingAbility += abilityAdded;

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


    public void increaseFishingAbility(int abilityAdded) {
        //TODO: with every fish fished, you gain 5 points

        fishingAbility += abilityAdded;

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
}
