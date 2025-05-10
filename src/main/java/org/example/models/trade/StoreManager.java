package org.example.models.trade;

import org.example.models.App;
import org.example.models.NormalItemType;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.cooking.FoodType;
import org.example.models.cooking.RecipeType;
import org.example.models.crafting.CraftingItemType;
import org.example.models.enums.AnimalType;
import org.example.models.enums.Season;
import org.example.models.tools.ToolType;
import org.example.models.foraging.MineralType;
import org.example.models.plant.FertilizerType;
import org.example.models.plant.SeedType;
import org.example.models.plant.SaplingType;
import org.example.models.tools.FishingPoleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreManager {
    private final Map<StoreType, StoreInventory> shopInventories = new HashMap<>();

    public void registerShop(StoreInventory inventory) {
        shopInventories.put(inventory.getStoreType(), inventory);
    }

    public StoreInventory getInventory(StoreType shopType) {
        return shopInventories.get(shopType);
    }

    public void initializeStores() {
        // === Blacksmith ===
        StoreInventory blacksmith = new StoreInventory(StoreType.Blacksmith);
        blacksmith.addItem(new ShopItem(CraftingItemType.CopperOre, 75, Integer.MAX_VALUE, "A common ore that can be smelted into bars."));
        blacksmith.addItem(new ShopItem(CraftingItemType.IronOre, 150, Integer.MAX_VALUE, "A fairly common ore that can be smelted into bars."));
        blacksmith.addItem(new ShopItem(MineralType.Coal, 150, Integer.MAX_VALUE, "A combustible rock that is useful for crafting and smelting."));
        blacksmith.addItem(new ShopItem(CraftingItemType.GoldOre, 400, Integer.MAX_VALUE, "A precious ore that can be smelted into bars."));

        blacksmith.addUpgradeService(new UpgradeService("Copper Tool", CraftingItemType.CopperBar, 5, 2000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Steel Tool", CraftingItemType.IronBar, 5, 5000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Gold Tool", CraftingItemType.GoldBar, 5, 10000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Iridium Tool", CraftingItemType.IridiumBar, 5, 25000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Copper Trash Can", CraftingItemType.CopperBar, 5, 1000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Steel Trash Can", CraftingItemType.IronBar, 5, 2500, 1));
        blacksmith.addUpgradeService(new UpgradeService("Gold Trash Can", CraftingItemType.GoldBar, 5, 5000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Iridium Trash Can", CraftingItemType.IridiumBar, 5, 12500, 1));
        registerShop(blacksmith);

        // === Ranch ===
        StoreInventory ranch = new StoreInventory(StoreType.Ranch);
        ranch.addItem(new ShopItem(ToolType.MilkPail, 1000, 1, "Gather milk from your animals."));
        ranch.addItem(new ShopItem(ToolType.Shear, 1000, 1, "Use this to collect wool from sheep."));

        ranch.addAnimal(new AnimalItem(AnimalType.Chicken, "Well cared-for chickens lay eggs every day. Lives in the coop.", 800, AnimalHabitat.Coop, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Cow, "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn.", 1500, AnimalHabitat.Barn, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Goat, "Happy goats provide goat milk every other day. Requires a milk pail. Lives in the barn.", 4000, AnimalHabitat.Barn, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Duck, "Happy ducks lay duck eggs every other day. Lives in the coop.", 1200, AnimalHabitat.Coop, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Sheep, "Can be shorn for wool. Shears are required. Lives in the barn.", 8000, AnimalHabitat.Barn, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Rabbit, "These are wooly rabbits! They shed precious wool every few days. Lives in the coop.", 8000, AnimalHabitat.Coop, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Dinosaur, "The Dinosaur is a farm animal that lives in a Big Coop.", 14000, AnimalHabitat.Coop, 2));
        ranch.addAnimal(new AnimalItem(AnimalType.Pig, "These pigs are trained to find truffles! Lives in the barn.", 16000, AnimalHabitat.Barn, 2));
        registerShop(ranch);

        // === Stardrop Saloon ===
        StoreInventory saloon = new StoreInventory(StoreType.StardropSaloon);
        saloon.addItem(new ShopItem(ArtisanProductType.Beer, 400, Integer.MAX_VALUE, "Drink in moderation."));
        saloon.addItem(new ShopItem(FoodType.Salad, 220, Integer.MAX_VALUE, "A healthy garden salad."));
        saloon.addItem(new ShopItem(FoodType.Bread, 120, Integer.MAX_VALUE, "A crusty baguette."));
        saloon.addItem(new ShopItem(FoodType.Spaghetti, 240, Integer.MAX_VALUE, "An old favorite."));
        saloon.addItem(new ShopItem(FoodType.Pizza, 600, Integer.MAX_VALUE, "It's popular for all the right reasons."));
        saloon.addItem(new ShopItem(ArtisanProductType.Coffee, 300, Integer.MAX_VALUE, "It smells delicious. This is sure to give you a boost."));

        saloon.addItem(new ShopItem(RecipeType.HashBrowns, 50, 1, "A recipe to make Hashbrowns"));
        saloon.addItem(new ShopItem(RecipeType.Olmelet, 100, 1, "A recipe to make Omelet"));
        saloon.addItem(new ShopItem(RecipeType.Pancakes, 100, 1, "A recipe to make Pancakes"));
        saloon.addItem(new ShopItem(RecipeType.Bread, 100, 1, "A recipe to make Bread"));
        saloon.addItem(new ShopItem(RecipeType.Tortilla, 100, 1, "A recipe to make Tortilla"));
        saloon.addItem(new ShopItem(RecipeType.Pizza, 150, 1, "A recipe to make Pizza"));
        saloon.addItem(new ShopItem(RecipeType.MakiRoll, 300, 1, "A recipe to make Maki Roll"));
        saloon.addItem(new ShopItem(RecipeType.TripleShotEspresso, 5000, 1, "A recipe to make Triple Shot Espresso"));
        saloon.addItem(new ShopItem(RecipeType.Cookie, 300, 1, "A recipe to make Cookie"));
        registerShop(saloon);

        // === Carpenter's Shop ===
        StoreInventory carpenterInventory = new StoreInventory(StoreType.CarpentersShop);
        carpenterInventory.addItem(new ShopItem(NormalItemType.Wood, 10, Integer.MAX_VALUE, "A sturdy, yet flexible plant material with a wide variety of uses."));
        carpenterInventory.addItem(new ShopItem(MineralType.Stone, 20, Integer.MAX_VALUE, "A common material with many uses in crafting and building."));
        registerShop(carpenterInventory);
        //TODO: Add Barn and Coop

        // === JojaMart ===
        StoreInventory jojaInventory = new StoreInventory(StoreType.JojaMart);

        // Permanent Stock
        //TODO: find out what these are
        //jojaInventory.addItem(new ShopItem(FoodType.JojaCola, 75, Integer.MAX_VALUE, "The flagship product of Joja corporation."));
        jojaInventory.addItem(new ShopItem(SeedType.AncientSeeds, 500, 1, "Could these still grow?"));
        //jojaInventory.addItem(new ShopItem(CraftingItemType.GrassStarter, 125, Integer.MAX_VALUE, "Place this on your farm to start a new patch of grass."));
        //jojaInventory.addItem(new ShopItem(CraftingItemType.Sugar, 125, Integer.MAX_VALUE, "Adds sweetness to pastries and candies. Too much can be unhealthy."));
        //jojaInventory.addItem(new ShopItem(CraftingItemType.WheatFlour, 125, Integer.MAX_VALUE, "A common cooking ingredient made from crushed wheat seeds."));
        //jojaInventory.addItem(new ShopItem(CraftingItemType.Rice, 250, Integer.MAX_VALUE, "A basic grain often served under vegetables."));

        // Spring Stock
        jojaInventory.addItem(new ShopItem(SeedType.ParsnipSeeds, 25, 5, "Plant these in the spring. Takes 4 days to mature.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.BeanStarter, 75, 5, "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.CauliflowerSeeds, 100, 5, "Plant these in the spring. Takes 12 days to produce a large cauliflower.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.PotatoSeeds, 62, 5, "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.StrawberrySeeds, 100, 5, "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.TulipBulb, 25, 5, "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.KaleSeeds, 87, 5, "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.CoffeeBean, 200, 1, "Plant in summer or spring. Takes 10 days to grow, then produces coffee beans every other day.", List.of(Season.Spring, Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.CarrotSeeds, 5, 10, "Plant in the spring. Takes 3 days to grow.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.RhubarbSeeds, 100, 5, "Plant these in the spring. Takes 13 days to mature.", List.of(Season.Spring)));
        jojaInventory.addItem(new ShopItem(SeedType.JazzSeeds, 37, 5, "Plant in spring. Takes 7 days to produce a blue puffball flower.", List.of(Season.Spring)));

        // Summer Stock
        jojaInventory.addItem(new ShopItem(SeedType.TomatoSeeds, 62, 5, "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.PepperSeeds, 50, 5, "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.WheatSeeds, 12, 10, "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.", List.of(Season.Summer, Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.SummerSquashSeeds, 10, 10, "Plant in the summer. Takes 6 days to grow, and continues to produce after first harvest.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.RadishSeeds, 50, 5, "Plant these in the summer. Takes 6 days to mature.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.MelonSeeds, 100, 5, "Plant these in the summer. Takes 12 days to mature.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.HopsStarter, 75, 5, "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.PoppySeeds, 125, 5, "Plant in summer. Produces a bright red flower in 7 days.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.SpangleSeeds, 62, 5, "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.StarfruitSeeds, 400, 5, "Plant these in the summer. Takes 13 days to mature.", List.of(Season.Summer)));
        jojaInventory.addItem(new ShopItem(SeedType.SunflowerSeeds, 125, 5, "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", List.of(Season.Summer, Season.Fall)));

        // Fall Stock
        jojaInventory.addItem(new ShopItem(SeedType.CornSeeds, 187, 5, "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.", List.of(Season.Summer, Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.EggplantSeeds, 25, 5, "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.PumpkinSeeds, 125, 5, "Plant these in the fall. Takes 13 days to mature.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.BroccoliSeeds, 15, 5, "Plant in the fall. Takes 8 days to mature, and continues to produce after first harvest.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.AmaranthSeeds, 87, 5, "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.GrapeStarter, 75, 5, "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.BeetSeeds, 20, 5, "Plant these in the fall. Takes 6 days to mature.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.YamSeeds, 75, 5, "Plant these in the fall. Takes 10 days to mature.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.BokChoySeeds, 62, 5, "Plant these in the fall. Takes 4 days to mature.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.CranberrySeeds, 300, 5, "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.FairySeeds, 250, 5, "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted colors.", List.of(Season.Fall)));
        jojaInventory.addItem(new ShopItem(SeedType.RareSeed, 1000, 1, "Sow in fall. Takes all season to grow.", List.of(Season.Fall)));

        // Winter Stock
        jojaInventory.addItem(new ShopItem(SeedType.PowdermelonSeeds, 20, 10, "This special melon grows in the winter. Takes 7 days to grow.", List.of(Season.Winter)));

        registerShop(jojaInventory);

        // === Pierre's General Store ===
        StoreInventory pierre = new StoreInventory(StoreType.PierresGeneralStore);
        // Year-Round Stock

        //TODO: find out what these are
        //pierre.addItem(new ShopItem(CraftingItemType.Rice, 200, Integer.MAX_VALUE, "A basic grain often served under vegetables."));
        //pierre.addItem(new ShopItem(CraftingItemType.WheatFlour, 100, Integer.MAX_VALUE, "A common cooking ingredient made from crushed wheat seeds."));
        //pierre.addItem(new ShopItem(CraftingItemType.Bouquet, 1000, 2, "A gift that shows your romantic interest.\n(Unlocked after reaching level 2 friendship with a player)"));
        //pierre.addItem(new ShopItem(CraftingItemType.WeddingRing, 10000, 2, "It's used to ask for another farmer's hand in marriage.\n(Unlocked after reaching level 3 friendship with a player)"));
        pierre.addItem(new ShopItem(CraftingItemType.Dehydrator, 10000, 1, "A recipe to make Dehydrator"));
        //pierre.addItem(new ShopItem(RecipeType.GrassStarter, 1000, 1, "A recipe to make Grass Starter"));
        //pierre.addItem(new ShopItem(CraftingItemType.Sugar, 100, Integer.MAX_VALUE, "Adds sweetness to pastries and candies. Too much can be unhealthy."));
        pierre.addItem(new ShopItem(ArtisanProductType.Oil, 200, Integer.MAX_VALUE, "All purpose cooking oil."));
        //pierre.addItem(new ShopItem(CraftingItemType.Vinegar, 200, Integer.MAX_VALUE, "An aged fermented liquid used in many cooking recipes."));
        //pierre.addItem(new ShopItem(CraftingItemType.DeluxeRetainingSoil, 150, Integer.MAX_VALUE, "This soil has a 100% chance of staying watered overnight. Mix into tilled soil."));
        //pierre.addItem(new ShopItem(CraftingItemType.GrassStarter, 100, Integer.MAX_VALUE, "Place this on your farm to start a new patch of grass."));
        pierre.addItem(new ShopItem(FertilizerType.SpeedGro, 100, Integer.MAX_VALUE, "Makes the plants grow 1 day earlier."));

        pierre.addItem(new ShopItem(SaplingType.AppleSapling, 4000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.ApricotSapling, 2000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.CherrySapling, 3400, Integer.MAX_VALUE, "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.OrangeSapling, 4000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.PeachSapling, 6000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.PomegranateSapling, 6000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty."));

        //pierre.addItem(new ShopItem(CraftingItemType.BasicRetainingSoil, 100, Integer.MAX_VALUE, "This soil has a chance of staying watered overnight. Mix into tilled soil."));
        //pierre.addItem(new ShopItem(CraftingItemType.QualityRetainingSoil, 150, Integer.MAX_VALUE, "This soil has a good chance of staying watered overnight. Mix into tilled soil."));

        registerShop(pierre);

        // === Fish Shop ===
        StoreInventory fishShopInventory = new StoreInventory(StoreType.FishShop);

        fishShopInventory.addItem(new ShopItem(CraftingItemType.FishSmoker, 10000, 1, "A recipe to make Fish Smoker"));
        //fishShopInventory.addItem(new ShopItem(FoodType.TroutSoup, 250, 1, "Pretty salty."));
        fishShopInventory.addItem(new ShopItem(FishingPoleType.Bamboo, 500, 1, "Use in the water to catch fish."));
        fishShopInventory.addItem(new ShopItem(FishingPoleType.Training, 25, 1, "It's a lot easier to use than other rods, but can only catch basic fish."));
        fishShopInventory.addItem(new ShopItem(FishingPoleType.Fiberglass, 1800, 1, "Use in the water to catch fish.")); // TODO: Fishing skill level 2
        fishShopInventory.addItem(new ShopItem(FishingPoleType.Iridium, 7500, 1, "Use in the water to catch fish.")); // TODO: Fishing skill level 4
    }

    public String showAllProducts(Store store) {
        StringBuilder result = new StringBuilder();
        StoreInventory inventory = shopInventories.get(store.getType());

        result.append("Store Name: %s".formatted(store.getType().name()));
        int rank = 1;

        for (ShopItem item : inventory.getItems()) {
            result.append("\n%d- %s(%.2f)".formatted(
                    rank++,
                    item.getType().getName(),
                    item.getType().getPrice()
            ));
        }

        if (store.getType().equals(StoreType.Blacksmith)) {
            rank = 1;
            for (UpgradeService upgradeService : inventory.getUpgradeServices()) {
                result.append("\n%d- %s(%d)".formatted(
                        rank++,
                        upgradeService.getName(),
                        upgradeService.getCost()
                ));
            }
        }

        if (store.getType().equals(StoreType.Ranch)) {
            rank = 1;
            for (AnimalItem animalItem : inventory.getAnimals()) {
                result.append("\n%d- %s(%.2f)".formatted(
                        rank++,
                        animalItem.getAnimalType().name(),
                        animalItem.getPrice()
                ));
            }
        }

        return result.toString();
    }

    public String showAllAvailableProducts(Store store) {
        StringBuilder result = new StringBuilder();
        StoreInventory inventory = shopInventories.get(store.getType());
        Season season = App.getCurrentGame().getDate().getSeason();

        result.append("Store Name: %s".formatted(store.getType().name()));
        int rank = 1;

        for (ShopItem item : inventory.getItems()) {
            if (!item.isAvailableInSeason(season) ||
                    (item.getSoldToday() >= item.getDailyLimit()))
                continue;

            result.append("\n%d- %s(%.2f)".formatted(
                    rank++,
                    item.getType().getName(),
                    item.getType().getPrice()
            ));
        }

        if (store.getType().equals(StoreType.Blacksmith)) {
            rank = 1;
            for (UpgradeService upgradeService : inventory.getUpgradeServices()) {
                if (upgradeService.getSoldToday() >= upgradeService.getDailyLimit())
                    continue;

                result.append("\n%d- %s(%d)".formatted(
                        rank++,
                        upgradeService.getName(),
                        upgradeService.getCost()
                ));
            }
        }

        if (store.getType().equals(StoreType.Ranch)) {
            rank = 1;
            for (AnimalItem animalItem : inventory.getAnimals()) {
                if (animalItem.getSoldToday() >= animalItem.getDailyLimit())
                    continue;

                result.append("\n%d- %s(%.2f)".formatted(
                        rank++,
                        animalItem.getAnimalType().name(),
                        animalItem.getPrice()
                ));
            }
        }

        return result.toString();
    }

    public void resetDailyLimits() {
        for (StoreType storeType : shopInventories.keySet()) {
            StoreInventory inventory = shopInventories.get(storeType);
            for (ShopItem item : inventory.getItems()) {
                item.setSoldToday(0);
            }

            for (AnimalItem animal : inventory.getAnimals()) {
                animal.setSoldToday(0);
            }

            for (UpgradeService upgradeService : inventory.getUpgradeServices()) {
                upgradeService.setSoldToday(0);
            }
        }
    }
}
