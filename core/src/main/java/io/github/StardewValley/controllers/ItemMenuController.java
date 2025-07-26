package io.github.StardewValley.controllers;

import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.helperControllers.MarketsController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.cooking.Recipe;
import io.github.StardewValley.shared.models.cooking.RecipeType;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.crafting.CraftingRecipeType;
import io.github.StardewValley.shared.models.backpack.BackPackType;
import io.github.StardewValley.shared.models.market.ShopItem;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolType;
import io.github.StardewValley.views.ItemMenu;

public class ItemMenuController {
    private ItemMenu view;

    public void setView(ItemMenu itemMenu) {
        this.view = itemMenu;
    }

    public void minus() {
        int count = view.getCount();
        if (count > 1) {
            count--;
            view.setCount(count);
        }
        else
            view.getErrorLabel().setText("No items selected.");
        view.getCountLabel().setText("Count: %d".formatted(count));
    }

    public void plus() {
        int count = view.getCount();
        if (count == view.getItem().getDailyLimit())
            view.getErrorLabel().setText("Daily Limit reached");
        else {
            count++;
            view.setCount(count);
        }
        view.getCountLabel().setText("Count: %d".formatted(count));
    }


    public void purchase() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        StoreType storeType = view.getStoreType();


//        if (!App.getCurrentGame().getStoreManager().isStoreOpen(store.getType()))
//            return new Result(false, "Store is open from %d to %d".formatted(
//                store.getType().getOpeningHour(), store.getType().getClosingHour()
//            ));

        if (storeType.equals(StoreType.FishShop))
            if (!App.getCurrentGame().getStoreManager().checkFishingSkill(view.getItem())) {
                view.getErrorLabel().setText("You do not have enough fishing skill to buy this item.");
                return;
            }

        if (!App.getCurrentGame().getStoreManager().hasIngredients(view.getItem())) {
            view.getErrorLabel().setText("You do not have enough ingredients to buy this item");
            return;
        }

        App.getCurrentGame().getStoreManager().useIngredients(view.getItem());

        double price;
        if (storeType.equals(StoreType.PierresGeneralStore))
            price = App.getCurrentGame().getStoreManager().getSeasonalPrice(view.getItem());
        else
            price = view.getItem().getPrice();

        price *= view.getCount();
        if (player.getBackPack().getCoin() < price) {
            view.getErrorLabel().setText("you have only %.2f dollars left(not enough money)".formatted(
                player.getBackPack().getCoin()
            ));
            return;
        }

        //purchasing
        ShopItem product = view.getItem();
        product.setSoldToday(product.getSoldToday() + view.getCount());
        player.getBackPack().addcoin(-price);
        if (product.getType().equals(BackPackType.LargeBackPack) || product.getType().equals(BackPackType.DeluxeBackPack))
            App.getCurrentGame().getStoreManager().purchaseBackpack(product);

        if (product.getType().getClass().equals(RecipeType.class)) {
            player.getRecipes().add(new Recipe((FoodType) ((RecipeType) product.getType()).getFoodType()));
        }else if (product.getType().getClass().equals(CraftingRecipeType.class)) {
            CraftingRecipeType craftingRecipeType = ((CraftingRecipeType) product.getType());
            if (craftingRecipeType.equals(CraftingRecipeType.DehydratorRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Dehydrator));
            } else if (craftingRecipeType.equals(CraftingRecipeType.FishSmokerRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.FishSmoker));
            } else if (craftingRecipeType.equals(CraftingRecipeType.GrassStarterRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.GrassStarter));
            }
        }else if (product.getType().getClass().equals(ToolType.class)) {
            //milkpail and shear in marine's ranch
            player.getBackPack().addItemToInventory(new Tool((ToolType) product.getType(), null,null));
        } else {
            for (int i = 0; i < view.getCount(); i++) {
                //player.getBackPack().addItemToInventory(product);
                player.getBackPack().addItemToInventory((BackPackable) (new MarketsController()).addItem(product.getName()).get(1));
            }
        }
        view.getErrorLabel().setText("Purchased successfully. New Balance: %.0f".formatted(App.getCurrentGame()
            .getCurrentPlayingPlayer().getBackPack().getCoin()));
    }


    public void exit() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(view.getStoreMenu());
    }
}
