package org.example.controllers.helperControllers;

import org.example.models.*;
import org.example.models.animal.AnimalProduct;
import org.example.models.animal.AnimalProductType;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.cooking.Food;
import org.example.models.cooking.FoodType;
import org.example.models.cooking.Recipe;
import org.example.models.cooking.RecipeType;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;
import org.example.models.crafting.CraftingRecipe;
import org.example.models.crafting.CraftingRecipeType;
import org.example.models.enums.BackPackType;
import org.example.models.enums.FishType;
import org.example.models.foraging.Mineral;
import org.example.models.foraging.MineralType;
import org.example.models.map.Tile;
import org.example.models.market.*;
import org.example.models.plant.*;
import org.example.models.tools.FishingPoleType;
import org.example.models.tools.Tool;
import org.example.models.tools.ToolMaterial;
import org.example.models.tools.ToolType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarketsController {

    public Result showAllProducts() {
        //check is the player in store
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX(), player.getY());
        if (tile.getPlaceable() instanceof Store store) {
            if (!App.getCurrentGame().getStoreManager().isStoreOpen(store.getType()))
                return new Result(false, "Store is open from %d to %d".formatted(
                        store.getType().getOpeningHour(), store.getType().getClosingHour()
                ));
            return new Result(true, App.getCurrentGame().getStoreManager().showAllProducts(store));
        }
        return new Result(false, "You are not in a store");
    }


    public Result showAllAvailableProducts() {
        //check is the player in store
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        Tile tile = App.getCurrentGame().getTileByIndex(player.getX(), player.getY());
        if (tile.getPlaceable() instanceof Store store) {
            if (!App.getCurrentGame().getStoreManager().isStoreOpen(store.getType()))
                return new Result(false, "Store is open from %d to %d".formatted(
                        store.getType().getOpeningHour(), store.getType().getClosingHour()
                ));
            return new Result(true, App.getCurrentGame().getStoreManager().showAllAvailableProducts(store));
        }
        return new Result(false, "You are not in a store");
    }


    public Result purchase(String productName, String count) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        StoreType storeType;
        if (App.getCurrentGame().getTileByIndex(player.getX(), player.getY()).getPlaceable() instanceof Store store) {
            storeType = store.getType();
        } else
            return new Result(false, "The Player is not in a store");

        if (!App.getCurrentGame().getStoreManager().isStoreOpen(store.getType()))
            return new Result(false, "Store is open from %d to %d".formatted(
                    store.getType().getOpeningHour(), store.getType().getClosingHour()
            ));

        ShopItem product = null;
        for (ShopItem shopItem : App.getCurrentGame().getStoreManager().getInventory(storeType).getItems()) {
            if (shopItem.getName().equalsIgnoreCase(productName)) {
                product = shopItem;
            }
        }
        if (product == null)
            return new Result(false, "no product with name %s in store %s".formatted(
                    productName, storeType.name()
            ));

        if (storeType.equals(StoreType.FishShop))
            if (!App.getCurrentGame().getStoreManager().checkFishingSkill(product))
                return new Result(false, "You do not have enough fishing skill to buy this item.");

        int count1;

        if (count == null)
            count1 = 1;
        else
            count1 = Integer.parseInt(count);

        int availableCount = product.getDailyLimit() - product.getSoldToday();
        if (availableCount < count1)
            return new Result(false, "only %d left today".formatted(availableCount));

        if (!App.getCurrentGame().getStoreManager().hasIngredients(product))
            return new Result(false, "You do not have enough ingredients to buy this item");

        App.getCurrentGame().getStoreManager().useIngredients(product);

        double price;
        if (storeType.equals(StoreType.PierresGeneralStore))
            price = App.getCurrentGame().getStoreManager().getSeasonalPrice(product);
        else
            price = product.getPrice();

        price *= count1;
        if (player.getBackPack().getCoin() < price)
            return new Result(false, "you have only %.2f dollars left(not enough money)".formatted(
                    player.getBackPack().getCoin()
            ));

        //purchasing
        product.setSoldToday(product.getSoldToday() + count1);
        player.getBackPack().addcoin(-price);
        if (product.getType().equals(BackPackType.LargeBackPack) || product.getType().equals(BackPackType.DeluxeBackPack))
            return App.getCurrentGame().getStoreManager().purchaseBackpack(product);

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
            for (int i = 0; i < count1; i++) {
                //player.getBackPack().addItemToInventory(product);
                player.getBackPack().addItemToInventory((BackPackable) addItem(product.getName()).get(1));
            }
        }

        return new Result(false, "purhcased successfully");
    }


    public Result cheatAddDollars(String count) {
        double amount;
        try {
            amount = Double.parseDouble(count);
        } catch (Exception e) {
            return new Result(false, "Amount must be number.");
        }

        App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().addCoin(amount);
        return new Result(false, "Your new Balance: %.1f".formatted(
                App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().getCoin()
        ));
    }


    public Result sellProduct(String productName, String count) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

        ShippingBin bin = null;
        for (int i = 0; i < 8; i++) {
            if (App.getCurrentGame().getTileByIndex((player.getX() + dx[i]), (player.getY() + dy[i])).getPlaceable()
                    instanceof ShippingBin shippingBin) {
                if (shippingBin.getTodayItemOwner() != null) {
                    if (!shippingBin.getTodayItemOwner().equals(player))
                        return new Result(false, "this shipping bin is already used by %s today.".formatted(
                                shippingBin.getTodayItemOwner().getUser().getUsername()) +
                                "you may be able to use it tomorrow.");
                }
                bin = shippingBin;
            }
        }
        if (bin == null)
            return new Result(false, "you are not near a shipping bin");

        Optional<BackPackableType> productType = parseBackPackable(productName);
        if (productType.isEmpty())
            return new Result(false, "no product type found with name %s".formatted(productName));

        if (productType.get().getClass().equals(ToolType.class))
            return new Result(false, "Can not sell item of type tool");

        int count1;
        if (count == null)
            count1 = player.getBackPack().getInventorySize(productName);
        else
            count1 = Integer.parseInt(count);

        if (player.getBackPack().getBackPackItems().get(productType.get()) == null)
            return new Result(false, "you do not have item of type %s".formatted(productType.get().getName()));

        int availableCount = player.getBackPack().getBackPackItems().get(productType.get()).size();
        if (availableCount < count1)
            return new Result(false, "not enough count: you only have %d of this item".formatted(availableCount));

        bin.setTodayItemOwner(player);
        for (int i = 0; i < count1; i++) {
            bin.addItem(player.getBackPack().getBackPackItems().get(productType.get()).get(0));
            player.getBackPack().useItem(productType.get());
        }
        return new Result(true, "sold successfully");
    }


    public Optional<BackPackableType> parseBackPackable(String name) {
        List<Class<? extends Enum<?>>> enumClasses = List.of(
                FruitType.class,
                FishType.class,
                AnimalProductType.class,
                CraftingItemType.class,
                SeedType.class,
                CropType.class,
                NormalItemType.class,
                FoodType.class,
                MineralType.class,
                ArtisanProductType.class
                //TODO: Add more if needed
        );

        for (Class<? extends Enum<?>> enumClass : enumClasses) {
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                if (constant.name().equalsIgnoreCase(name)) {
                    return Optional.of((BackPackableType) constant);
                }
            }
        }
        return Optional.empty();
    }

    public ArrayList<Object> addItem(String itemName) {
        BackPackableType type = null;
        BackPackable sampleItem = null;
        try {
            type = CraftingItemType.valueOf(itemName);
            sampleItem = new CraftingItem((CraftingItemType) type);
        } catch (IllegalArgumentException e1) {
            try {
                type = NormalItemType.valueOf(itemName);
                sampleItem = new NormalItem((NormalItemType) type);
            } catch (IllegalArgumentException e2) {
                try {
                    type = MineralType.valueOf(itemName);
                    sampleItem = new Mineral((MineralType) type, true);
                } catch (Exception e3) {
                    try {
                        type = SaplingType.valueOf(itemName);
                        sampleItem = new Sapling((SaplingType) type);
                    } catch (IllegalArgumentException e4) {
                        try {
                            type = SeedType.valueOf(itemName);
                            sampleItem = new Seed((SeedType) type);
                        } catch (IllegalArgumentException e5) {
                            try {
                                type = CropType.valueOf(itemName);
                                sampleItem = new Crop(false, (CropType) type, null, false);
                            } catch (IllegalArgumentException e6) {
                                try {
                                    type = FlowerType.valueOf(itemName);
                                    sampleItem = new Flower((FlowerType) type);
                                } catch (IllegalArgumentException e7) {
                                    {
                                        try {
                                            type = FruitType.valueOf(itemName);
                                            sampleItem = new Fruit((FruitType) type);
                                        } catch (IllegalArgumentException e9) {
                                            try {
                                                type = AnimalProductType.valueOf(itemName);
                                                AnimalProduct a = new AnimalProduct();
                                                a.setAnimalProductType((AnimalProductType) type);
                                                a.setQuality(ItemQuality.Regular);
                                                sampleItem = a;


                                            } catch (IllegalArgumentException e10) {
                                                try {
                                                    type = FishType.valueOf(itemName);
                                                    sampleItem = new Fish((FishType) type, ItemQuality.Regular);
                                                } catch (Exception e11) {
                                                    try {
                                                        type = FertilizerType.valueOf(itemName);
                                                        sampleItem = new Fertilizer((FertilizerType) type);
                                                    } catch (Exception e12) {
                                                        try {
                                                            type = ArtisanProductType.valueOf(itemName);
                                                            sampleItem = new ArtisanProduct((ArtisanProductType) type, null);
                                                        } catch (Exception e13) {
                                                            try{
                                                                if(!itemName.equals("FishingPole")) {
                                                                    type = ToolType.valueOf(itemName);
                                                                    sampleItem = new Tool((ToolType) type, ToolMaterial.Basic,null);


                                                                }

                                                            } catch (Exception e14){
                                                                try{
                                                                    type = FishingPoleType.valueOf(itemName);
                                                                    sampleItem = new Tool(ToolType.FishingPole,ToolMaterial.Basic,(FishingPoleType) type);
                                                                }
                                                                catch(Exception e15){
                                                                    try{
                                                                        type = FoodType.valueOf(itemName);
                                                                        sampleItem = new Food((FoodType) type);

                                                                    } catch (Exception e16){
                                                                        try{
                                                                            type = ArtisanProductType.valueOf(itemName);
                                                                            sampleItem = new ArtisanProduct((ArtisanProductType) type,null);
                                                                        }catch (Exception e17) {
                                                                            sampleItem = null;
                                                                            type = null;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Object> result = new ArrayList<>();
        result.add(type);
        result.add(sampleItem);
        return result;
    }
}
