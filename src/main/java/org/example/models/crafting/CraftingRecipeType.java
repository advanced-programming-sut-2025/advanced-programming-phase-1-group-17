package org.example.models.crafting;

import org.example.models.BackPackableType;

public enum CraftingRecipeType implements BackPackableType {
    //Crafting Item Recipes
    FishSmokerRecipe(CraftingItemType.FishSmoker, 5000), //In Fish Shop
    DehydratorRecipe(CraftingItemType.Dehydrator, 5000), //In Pierre
    GrassStarterRecipe(CraftingItemType.GrassStarter, 500); //In Pierre


    private final int price;
    private CraftingItemType craftingItemType;
    CraftingRecipeType(CraftingItemType craftingItemType, int price) {
        this.craftingItemType = craftingItemType;
        this.price = price;
    }

    public CraftingItemType getCraftingItemType() {
        return craftingItemType;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }
}
