package io.github.StardewValley.shared.models.market;

import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.NPCS.Flower;
import io.github.StardewValley.shared.models.NPCS.FlowerType;
import io.github.StardewValley.shared.models.NPCS.Ring;
import io.github.StardewValley.shared.models.NPCS.RingType;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.animal.AnimalProduct;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.backpack.*;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.cooking.Recipe;
import io.github.StardewValley.shared.models.cooking.RecipeType;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.crafting.CraftingRecipeType;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.plant.*;
import io.github.StardewValley.shared.models.saveClasses.MarketsControllerSave;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;
import io.github.StardewValley.shared.models.foraging.MineralType;
import io.github.StardewValley.shared.models.tools.FishingPoleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketsController {
    private final Map<StoreType, StoreInventory> shopInventories = new HashMap<>();
    private final HashMap<StoreType, Store> stores = new HashMap<>();
    private final HashMap<StoreType, Rectangle> storeBounds = new HashMap<>();

    public MarketsController() {}

    public MarketsController(MarketsControllerSave save) {
        int tileWidth = GameAssetManager.getGameAssetManager().getTileWidth();
        int tileHeight = GameAssetManager.getGameAssetManager().getTileHeight();
        save.getShopInventories().forEach((inventory) -> this.shopInventories.put(inventory.getStoreType(), inventory));
        save.getStores().forEach((store -> {
            this.stores.put(store.getType(), store);
            this.storeBounds.put(store.getType(), new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
                store.getWidth() * tileWidth, store.getHeight() * tileHeight));
        }));
    }

    public void registerShop(StoreInventory inventory) {
        shopInventories.put(inventory.getStoreType(), inventory);
    }
    public StoreInventory getInventory(StoreType shopType) {
        return shopInventories.get(shopType);
    }

    public void initializeStores(Game game) {
        // === Blacksmith ===
        initializeBlackSmith();

        // === Ranch ===
        initializeRanch();

        // === Stardrop Saloon ===
        initializeStarDropSalon();

        // === Carpenter's Shop ===
        initializeCarpenter();

        // === JojaMart ===
        initializeJoja();

        // === Pierre's General Store ===
        initializePierre();

        // === Fish Shop ===
        initializeFishShop();

        initializeStoreBounds(game);
    }

    private void initializeFishShop() {
        StoreInventory fishShopInventory = new StoreInventory(StoreType.FishShop);

        //TODO: test fishing poles
        fishShopInventory.addItem(new ShopItem(CraftingRecipeType.FishSmokerRecipe, 10000, 1, "A recipe to make Fish Smoker"));

        fishShopInventory.addItem(new ShopItem(NormalItemType.TroutSoup, 250, 1, "Pretty salty."));

        fishShopInventory.addItem(new ShopItem(FishingPoleType.BambooFishingPole, 500, 1, "Use in the water to catch fish."));
        fishShopInventory.addItem(new ShopItem(FishingPoleType.TrainingFishingPole, 25, 1, "It's a lot easier to use than other rods, but can only catch basic fish."));
        fishShopInventory.addItem(new ShopItem(FishingPoleType.FiberglassFishingPole, 1800, 1, "Use in the water to catch fish.")); // TODO: Fishing skill level 2
        fishShopInventory.addItem(new ShopItem(FishingPoleType.IridiumFishingPole, 7500, 1, "Use in the water to catch fish.")); // TODO: Fishing skill level 4
        registerShop(fishShopInventory);
    }

    private void initializePierre() {
        StoreInventory pierre = new StoreInventory(StoreType.PierresGeneralStore);
        // Year-Round Stock

        pierre.addItem(new ShopItem(NormalItemType.Rice, 200, Integer.MAX_VALUE, "A basic grain often served under vegetables."));
        pierre.addItem(new ShopItem(NormalItemType.WheatFlour, 100, Integer.MAX_VALUE, "A common cooking ingredient made from crushed wheat seeds."));
        pierre.addItem(new ShopItem(FlowerType.FLOWER, 1000, 2, "A gift that shows your romantic interest.\n(Unlocked after reaching level 2 friendship with a player)"));
        pierre.addItem(new ShopItem(RingType.Ring, 10000, 2, "It's used to ask for another farmer's hand in marriage.\n(Unlocked after reaching level 3 friendship with a player)"));
        pierre.addItem(new ShopItem(CraftingRecipeType.DehydratorRecipe, 10000, 1, "A recipe to make Dehydrator"));
        pierre.addItem(new ShopItem(CraftingRecipeType.GrassStarterRecipe, 1000, 1, "A recipe to make Grass Starter"));
        pierre.addItem(new ShopItem(NormalItemType.Sugar, 100, Integer.MAX_VALUE, "Adds sweetness to pastries and candies. Too much can be unhealthy."));
        pierre.addItem(new ShopItem(ArtisanProductType.Oil, 200, Integer.MAX_VALUE, "All purpose cooking oil."));
        pierre.addItem(new ShopItem(ArtisanProductType.Vinegar, 200, Integer.MAX_VALUE, "An aged fermented liquid used in many cooking recipes."));
        pierre.addItem(new ShopItem(FertilizerType.DeluxeRetainingSoil, 150, Integer.MAX_VALUE, "This soil has a 100% chance of staying watered overnight. Mix into tilled soil."));
        pierre.addItem(new ShopItem(NormalItemType.GrassStarter, 100, Integer.MAX_VALUE, "Place this on your farm to start a new patch of grass."));
        pierre.addItem(new ShopItem(FertilizerType.SpeedGro, 100, Integer.MAX_VALUE, "Makes the plants grow 1 day earlier."));

        pierre.addItem(new ShopItem(SaplingType.AppleSapling, 4000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.ApricotSapling, 2000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.CherrySapling, 3400, Integer.MAX_VALUE, "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.OrangeSapling, 4000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.PeachSapling, 6000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty."));
        pierre.addItem(new ShopItem(SaplingType.PomegranateSapling, 6000, Integer.MAX_VALUE, "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty."));

        pierre.addItem(new ShopItem(FertilizerType.BasicRetainingSoil, 100, Integer.MAX_VALUE, "This soil has a chance of staying watered overnight. Mix into tilled soil."));
        pierre.addItem(new ShopItem(FertilizerType.QualityRetainingSoil, 150, Integer.MAX_VALUE, "This soil has a good chance of staying watered overnight. Mix into tilled soil."));

        // Backpack Upgrades
        pierre.addItem(new ShopItem(BackPackType.LargeBackPack, 2000, 1, ""));
        ShopItem deluxe = new ShopItem(BackPackType.DeluxeBackPack, 10000, 1, "");
        deluxe.setAvailable(false);
        pierre.addItem(deluxe);

        // Seeds (base in-season price, out-of-season pricing handled at purchase)
        pierre.addItem(new ShopItem(SeedType.ParsnipSeeds, 20, 5, ""));
        pierre.addItem(new ShopItem(SeedType.BeanStarter, 60, 5, ""));
        pierre.addItem(new ShopItem(SeedType.CauliflowerSeeds, 80, 5, ""));
        pierre.addItem(new ShopItem(SeedType.PotatoSeeds, 50, 5, ""));
        pierre.addItem(new ShopItem(SeedType.TulipBulb, 20, 5, ""));
        pierre.addItem(new ShopItem(SeedType.KaleSeeds, 70, 5, ""));
        pierre.addItem(new ShopItem(SeedType.BlueJazzSeeds, 30, 5, ""));
        pierre.addItem(new ShopItem(SeedType.GarlicSeeds, 40, 5, ""));
        pierre.addItem(new ShopItem(SeedType.RiceShoot, 40, 5, ""));

        pierre.addItem(new ShopItem(SeedType.MelonSeeds, 80, 5, ""));
        pierre.addItem(new ShopItem(SeedType.TomatoSeeds, 50, 5, ""));
        pierre.addItem(new ShopItem(SeedType.BlueberrySeeds, 80, 5, ""));
        pierre.addItem(new ShopItem(SeedType.PepperSeeds, 40, 5, ""));
        pierre.addItem(new ShopItem(SeedType.WheatSeeds, 10, 5, ""));
        pierre.addItem(new ShopItem(SeedType.RadishSeeds, 40, 5, ""));
        pierre.addItem(new ShopItem(SeedType.PoppySeeds, 100, 5, ""));
        pierre.addItem(new ShopItem(SeedType.SpangleSeeds, 50, 5, ""));
        pierre.addItem(new ShopItem(SeedType.HopsStarter, 60, 5, ""));
        pierre.addItem(new ShopItem(SeedType.CornSeeds, 150, 5, ""));
        pierre.addItem(new ShopItem(SeedType.SunflowerSeeds, 200, 5, ""));
        pierre.addItem(new ShopItem(SeedType.RedCabbageSeeds, 100, 5, ""));

        pierre.addItem(new ShopItem(SeedType.EggplantSeeds, 20, 5, ""));
        pierre.addItem(new ShopItem(SeedType.PumpkinSeeds, 100, 5, ""));
        pierre.addItem(new ShopItem(SeedType.BokChoySeeds, 50, 5, ""));
        pierre.addItem(new ShopItem(SeedType.YamSeeds, 60, 5, ""));
        pierre.addItem(new ShopItem(SeedType.CranberrySeeds, 240, 5, ""));
        pierre.addItem(new ShopItem(SeedType.FairySeeds, 200, 5, ""));
        pierre.addItem(new ShopItem(SeedType.AmaranthSeeds, 70, 5, ""));
        pierre.addItem(new ShopItem(SeedType.GrapeStarter, 60, 5, ""));
        pierre.addItem(new ShopItem(SeedType.ArtichokeSeeds, 30, 5, ""));

        registerShop(pierre);

    }

    private void initializeJoja() {
        StoreInventory jojaInventory = new StoreInventory(StoreType.JojaMart);

        // Permanent Stock
        jojaInventory.addItem(new ShopItem(NormalItemType.JojaCola, 75, Integer.MAX_VALUE, "The flagship product of Joja corporation."));
        jojaInventory.addItem(new ShopItem(SeedType.AncientSeeds, 500, 1, "Could these still grow?"));
        jojaInventory.addItem(new ShopItem(NormalItemType.GrassStarter, 125, Integer.MAX_VALUE, "Place this on your farm to start a new patch of grass."));
        jojaInventory.addItem(new ShopItem(NormalItemType.Sugar, 125, Integer.MAX_VALUE, "Adds sweetness to pastries and candies. Too much can be unhealthy."));
        jojaInventory.addItem(new ShopItem(NormalItemType.WheatFlour, 125, Integer.MAX_VALUE, "A common cooking ingredient made from crushed wheat seeds."));
        jojaInventory.addItem(new ShopItem(NormalItemType.Rice, 250, Integer.MAX_VALUE, "A basic grain often served under vegetables."));

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
        jojaInventory.addItem(new ShopItem(SeedType.BlueJazzSeeds, 37, 5, "Plant in spring. Takes 7 days to produce a blue puffball flower.", List.of(Season.Spring)));

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

    }

    private void initializeCarpenter() {
        StoreInventory carpenterInventory = new StoreInventory(StoreType.CarpentersShop);
        carpenterInventory.addItem(new ShopItem(NormalItemType.Wood, 10, Integer.MAX_VALUE, "A sturdy, yet flexible plant material with a wide variety of uses."));
        carpenterInventory.addItem(new ShopItem(MineralType.Stone, 20, Integer.MAX_VALUE, "A common material with many uses in crafting and building."));

        // Farm buildings
        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.Barn,
            6000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 350,
                MineralType.Stone, 150
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.BigBarn,
            12000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 450,
                MineralType.Stone, 200
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.DeluxeBarn,
            25000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 550,
                MineralType.Stone, 300
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.Coop,
            4000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 300,
                MineralType.Stone, 100
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.BigCoop,
            10000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 400,
                MineralType.Stone, 150
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            AnimalPlaceType.DeluxeCoop,
            20000,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 500,
                MineralType.Stone, 200
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            NormalItemType.Well,
            1000,
            new HashMap<>(Map.of(
                MineralType.Stone, 75
            )),
            1
        ));

        carpenterInventory.addItem(new ShopItem(
            ShippingBinType.Basic,
            250,
            new HashMap<>(Map.of(
                NormalItemType.Wood, 150
            )),
            Integer.MAX_VALUE
        ));

        registerShop(carpenterInventory);
    }

    private void initializeStarDropSalon() {
        StoreInventory saloon = new StoreInventory(StoreType.StardropSaloon);
        saloon.addItem(new ShopItem(ArtisanProductType.Beer, 400, Integer.MAX_VALUE, "Drink in moderation."));
        saloon.addItem(new ShopItem(FoodType.Salad, 220, Integer.MAX_VALUE, "A healthy garden salad."));
        saloon.addItem(new ShopItem(FoodType.Bread, 120, Integer.MAX_VALUE, "A crusty baguette."));
        saloon.addItem(new ShopItem(FoodType.Spaghetti, 240, Integer.MAX_VALUE, "An old favorite."));
        saloon.addItem(new ShopItem(FoodType.Pizza, 600, Integer.MAX_VALUE, "It's popular for all the right reasons."));
        saloon.addItem(new ShopItem(ArtisanProductType.Coffee, 300, Integer.MAX_VALUE, "It smells delicious. This is sure to give you a boost."));

        saloon.addItem(new ShopItem(RecipeType.HashBrownsRecipe, 50, 1, "A recipe to make Hashbrowns"));
        saloon.addItem(new ShopItem(RecipeType.OmeletRecipe, 100, 1, "A recipe to make Omelet"));
        saloon.addItem(new ShopItem(RecipeType.PancakesRecipe, 100, 1, "A recipe to make Pancakes"));
        saloon.addItem(new ShopItem(RecipeType.BreadRecipe, 100, 1, "A recipe to make Bread"));
        saloon.addItem(new ShopItem(RecipeType.TortillaRecipe, 100, 1, "A recipe to make Tortilla"));
        saloon.addItem(new ShopItem(RecipeType.PizzaRecipe, 150, 1, "A recipe to make Pizza"));
        saloon.addItem(new ShopItem(RecipeType.MakiRollRecipe, 300, 1, "A recipe to make Maki Roll"));
        saloon.addItem(new ShopItem(RecipeType.TripleShotEspressoRecipe, 5000, 1, "A recipe to make Triple Shot Espresso"));
        saloon.addItem(new ShopItem(RecipeType.CookieRecipe, 300, 1, "A recipe to make Cookie"));
        registerShop(saloon);
    }

    private void initializeRanch() {
        StoreInventory ranch = new StoreInventory(StoreType.Ranch);
        ranch.addItem(new ShopItem(NormalItemType.Hay, 50, Integer.MAX_VALUE, "Dried grass used as animal food."));
        ranch.addItem(new ShopItem(ToolType.MilkPail, 1000, 1, "Gather milk from your animals."));
        ranch.addItem(new ShopItem(ToolType.Shear, 1000, 1, "Use this to collect wool from sheep."));

        //TODO: bring back AnimalItem Class
        ranch.addItem(new ShopItem(AnimalType.Chicken, 800, 2, "Well cared-for chickens lay eggs every day. Lives in the coop."));
        ranch.addItem(new ShopItem(AnimalType.Cow, 1500, 2, "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn."));
        ranch.addItem(new ShopItem(AnimalType.Goat, 4000, 2, "Happy goats provide goat milk every other day. A milk pail is required. Lives in the big barn."));
        ranch.addItem(new ShopItem(AnimalType.Duck, 1200, 2, "Happy ducks lay duck eggs every other day. Lives in the big coop."));
        ranch.addItem(new ShopItem(AnimalType.Sheep, 8000, 2, "Can be shorn for wool. Shears are required. Lives in the deluxe barn."));
        ranch.addItem(new ShopItem(AnimalType.Rabbit, 8000, 2, "These are wooly rabbits! They shed precious wool every few days. Lives in the deluxe coop."));
        ranch.addItem(new ShopItem(AnimalType.Dinosaur, 14000, 2, "The Dinosaur is a farm animal that lives in a big coop."));
        ranch.addItem(new ShopItem(AnimalType.Pig, 16000, 2, "These pigs are trained to find truffles! Lives in the deluxe barn."));
        registerShop(ranch);
    }

    private void initializeBlackSmith() {
        StoreInventory blacksmith = new StoreInventory(StoreType.Blacksmith);
        blacksmith.addItem(new ShopItem(MineralType.CopperOre, 75, Integer.MAX_VALUE, "A common ore that can be smelted into bars."));
        blacksmith.addItem(new ShopItem(MineralType.IronOre, 150, Integer.MAX_VALUE, "A fairly common ore that can be smelted into bars."));
        blacksmith.addItem(new ShopItem(MineralType.Coal, 150, Integer.MAX_VALUE, "A combustible rock that is useful for crafting and smelting."));
        blacksmith.addItem(new ShopItem(MineralType.GoldOre, 400, Integer.MAX_VALUE, "A precious ore that can be smelted into bars."));

        blacksmith.addUpgradeService(new UpgradeService("Copper Tool", ArtisanProductType.CopperBar, 5, 2000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Steel Tool", ArtisanProductType.IronBar, 5, 5000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Gold Tool", ArtisanProductType.GoldBar, 5, 10000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Iridium Tool", ArtisanProductType.IridiumBar, 5, 25000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Copper Trash Can", ArtisanProductType.CopperBar, 5, 1000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Steel Trash Can", ArtisanProductType.IronBar, 5, 2500, 1));
        blacksmith.addUpgradeService(new UpgradeService("Gold Trash Can", ArtisanProductType.GoldBar, 5, 5000, 1));
        blacksmith.addUpgradeService(new UpgradeService("Iridium Trash Can", ArtisanProductType.IridiumBar, 5, 12500, 1));
        registerShop(blacksmith);

    }


    private void initializeStoreBounds(Game game) {
        int tileWidth = GameAssetManager.getGameAssetManager().getTileWidth();
        int tileHeight = GameAssetManager.getGameAssetManager().getTileHeight();

        Store store = game.getMarketsController().getStore(StoreType.Blacksmith);
        storeBounds.put(StoreType.Blacksmith, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.Ranch);
        storeBounds.put(StoreType.Ranch, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.StardropSaloon);
        storeBounds.put(StoreType.StardropSaloon, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.CarpentersShop);
        storeBounds.put(StoreType.CarpentersShop, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.JojaMart);
        storeBounds.put(StoreType.JojaMart, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.PierresGeneralStore);
        storeBounds.put(StoreType.PierresGeneralStore, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));

        store = game.getMarketsController().getStore(StoreType.FishShop);
        storeBounds.put(StoreType.FishShop, new Rectangle(store.getStart_x() * tileWidth, store.getStart_y() * tileHeight,
            store.getWidth() * tileWidth, store.getHeight() * tileHeight));
    }



    public void resetDailyLimits() {
        for (StoreType storeType : shopInventories.keySet()) {
            StoreInventory inventory = shopInventories.get(storeType);
            for (ShopItem item : inventory.getItems()) {
                item.setSoldToday(0);
            }

            for (UpgradeService upgradeService : inventory.getUpgradeServices()) {
                upgradeService.setSoldToday(0);
            }
        }
    }

    public boolean isStoreOpen(StoreType storeType, Game game) {
        int hour = game.getDate().getHour();
        return hour >= storeType.getOpeningHour() && hour <= storeType.getClosingHour();
    }

    public boolean hasIngredients(ShopItem product, Player player) {
        if (product.getCost().isEmpty())
            return true;

        for (BackPackableType backPackableType : product.getCost().keySet()) {
            if (product.getCost().get(backPackableType) >
                player.getBackPack().getInventorySize(backPackableType.getName()))
                return false;
        }
        return true;
    }

    public void useIngredients(ShopItem product, Player player) {
        BackPack backPack = player.getBackPack();
        for (BackPackableType backPackableType : product.getCost().keySet()) {
            for (int i = 0; i < product.getCost().get(backPackableType); i++) {
                backPack.useItem(backPackableType);
            }
        }
    }

    public boolean checkFishingSkill(ShopItem item, Player player) {
        int fishingLevel = player.getAbilities().getFishingLevel();
        if (item.getType().equals(FishingPoleType.IridiumFishingPole)) {
            return fishingLevel >= 4;
        } else if (item.getType().equals(FishingPoleType.FiberglassFishingPole)) {
            return fishingLevel >= 2;
        }
        return true;
    }

    public void purchaseBackpack(ShopItem product, Player player) {
        if (product.getType().equals(BackPackType.LargeBackPack)) {
            for (ShopItem item : shopInventories.get(StoreType.PierresGeneralStore).getItems()) {
                if (item.getType().equals(BackPackType.DeluxeBackPack)){
                    item.setAvailable(true);
                    break;
                }
            }
            BackPack backPack = new BackPack(BackPackType.LargeBackPack,
                player.getBackPack().getPlayer());
            backPack.setBackPackItems(player.getBackPack().getBackPackItems());
            player.setBackPack(backPack);
        } else {
            BackPack backPack = new BackPack(BackPackType.DeluxeBackPack,
                player.getBackPack().getPlayer());
            backPack.setBackPackItems(player.getBackPack().getBackPackItems());
            player.setBackPack(backPack);
        }
    }

    public static double getSeasonalPrice(ShopItem product, Season season) {
        BackPackableType type = product.getType();

        // Spring Seeds
        if (type.equals(SeedType.ParsnipSeeds)) return season == Season.Spring ? 20 : 30;
        if (type.equals(SeedType.BeanStarter)) return season == Season.Spring ? 60 : 90;
        if (type.equals(SeedType.CauliflowerSeeds)) return season == Season.Spring ? 80 : 120;
        if (type.equals(SeedType.PotatoSeeds)) return season == Season.Spring ? 50 : 75;
        if (type.equals(SeedType.TulipBulb)) return season == Season.Spring ? 20 : 30;
        if (type.equals(SeedType.KaleSeeds)) return season == Season.Spring ? 70 : 105;
        if (type.equals(SeedType.BlueJazzSeeds)) return season == Season.Spring ? 30 : 45;
        if (type.equals(SeedType.GarlicSeeds)) return season == Season.Spring ? 40 : 60;
        if (type.equals(SeedType.RiceShoot)) return season == Season.Spring ? 40 : 60;

        // Summer Seeds
        if (type.equals(SeedType.MelonSeeds)) return season == Season.Summer ? 80 : 120;
        if (type.equals(SeedType.TomatoSeeds)) return season == Season.Summer ? 50 : 75;
        if (type.equals(SeedType.BlueberrySeeds)) return season == Season.Summer ? 80 : 120;
        if (type.equals(SeedType.PepperSeeds)) return season == Season.Summer ? 40 : 60;
        if (type.equals(SeedType.WheatSeeds)) return (season == Season.Summer || season == Season.Fall) ? 10 : 15;
        if (type.equals(SeedType.RadishSeeds)) return season == Season.Summer ? 40 : 60;
        if (type.equals(SeedType.PoppySeeds)) return season == Season.Summer ? 100 : 150;
        if (type.equals(SeedType.SpangleSeeds)) return season == Season.Summer ? 50 : 75;
        if (type.equals(SeedType.HopsStarter)) return season == Season.Summer ? 60 : 90;
        if (type.equals(SeedType.CornSeeds)) return (season == Season.Summer || season == Season.Fall) ? 150 : 225;
        if (type.equals(SeedType.SunflowerSeeds)) return (season == Season.Summer || season == Season.Fall) ? 200 : 300;
        if (type.equals(SeedType.RedCabbageSeeds)) return season == Season.Summer ? 100 : 150;

        // Fall Seeds
        if (type.equals(SeedType.EggplantSeeds)) return season == Season.Fall ? 20 : 30;
        if (type.equals(SeedType.PumpkinSeeds)) return season == Season.Fall ? 100 : 150;
        if (type.equals(SeedType.BokChoySeeds)) return season == Season.Fall ? 50 : 75;
        if (type.equals(SeedType.YamSeeds)) return season == Season.Fall ? 60 : 90;
        if (type.equals(SeedType.CranberrySeeds)) return season == Season.Fall ? 240 : 360;
        if (type.equals(SeedType.FairySeeds)) return season == Season.Fall ? 200 : 300;
        if (type.equals(SeedType.AmaranthSeeds)) return season == Season.Fall ? 70 : 105;
        if (type.equals(SeedType.GrapeStarter)) return season == Season.Fall ? 60 : 90;
        if (type.equals(SeedType.ArtichokeSeeds)) return season == Season.Fall ? 30 : 45;

        return product.getPrice(); // Default to item's base price
    }

    public Store getStore(StoreType storeType) {
        return stores.get(storeType);
    }

    public void addStore(StoreType storeType, Store store) {
        stores.put(storeType, store);
    }

    public static ArrayList<Object> addItem(String itemName, Player player, Game game) {
        BackPackableType type = null;
        BackPackable sampleItem = null;
        try {
            type = CraftingItemType.valueOf(itemName);
            sampleItem = new CraftingItem((CraftingItemType) type, player, game);
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
                                                                    sampleItem = new Tool(ToolType.FishingPole, ToolMaterial.Basic,(FishingPoleType) type);
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
                                                                            try {
                                                                                type = RingType.valueOf(itemName);
                                                                                sampleItem = new Ring();
                                                                            } catch (Exception e18) {
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
        }
        ArrayList<Object> result = new ArrayList<>();
        result.add(type);
        result.add(sampleItem);
        return result;
    }

    public Result purchase(ShopItemDTO shopItem2, int count, StoreType storeType, Player player, Season season, Game game) {
        ShopItem shopItem = null;
        for (ShopItem item : getInventory(storeType).getItems()) {
            if (item.getType().getName().equals(shopItem2.getType())) {
                shopItem = item;
                break;
            }
        }
        if (!isStoreOpen(storeType, game))
            return new Result(false, "Store is open from %d to %d".formatted(
                storeType.getOpeningHour(), storeType.getClosingHour()
            ));

        if (storeType.equals(StoreType.FishShop))
            if (!checkFishingSkill(shopItem, player)) {
                return new Result(false, "You do not have enough fishing skill to buy this item.");
            }

        if (!hasIngredients(shopItem, player)) {
            return new Result(false, "You do not have enough ingredients to buy this item");
        }

        useIngredients(shopItem, player);

        double price = calculatePrice(shopItem, count, storeType, season);
        if (player.getCoin() < price) {
            return new Result(false, "you have only %.2f dollars left(not enough money)".formatted(
                player.getCoin()));
        }

        //purchasing
        shopItem.setSoldToday(shopItem.getSoldToday() + count);
        player.addcoin(-price);
        if (shopItem.getType().equals(BackPackType.LargeBackPack) || shopItem.getType().equals(BackPackType.DeluxeBackPack))
            purchaseBackpack(shopItem, player);

        if (shopItem.getType().getClass().equals(RecipeType.class)) {
            player.getRecipes().add(new Recipe((FoodType) ((RecipeType) shopItem.getType()).getFoodType()));
        }else if (shopItem.getType().getClass().equals(CraftingRecipeType.class)) {
            CraftingRecipeType craftingRecipeType = ((CraftingRecipeType) shopItem.getType());
            if (craftingRecipeType.equals(CraftingRecipeType.DehydratorRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Dehydrator));
            } else if (craftingRecipeType.equals(CraftingRecipeType.FishSmokerRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.FishSmoker));
            } else if (craftingRecipeType.equals(CraftingRecipeType.GrassStarterRecipe)) {
                player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.GrassStarter));
            }
        }else if (shopItem.getType().getClass().equals(ToolType.class)) {
            //milkpail and shear in marine's ranch
            player.getBackPack().addItemToInventory(new Tool((ToolType) shopItem.getType(), null,null));
        } else {
            for (int i = 0; i < count; i++) {
                //player.getBackPack().addItemToInventory(product);
                player.getBackPack().addItemToInventory((BackPackable) addItem(shopItem.getName(), player, game).get(1));
            }
        }
        return new Result(true, "Purchased successfully. New Balance: %.0f".formatted(player.getCoin()));
    }

    private double calculatePrice(ShopItem shopItem, int count, StoreType storeType, Season season) {
        double price;
        if (storeType.equals(StoreType.PierresGeneralStore))
            price = getSeasonalPrice(shopItem, season);
        else
            price = shopItem.getPrice();

        price *= count;
        return price;
    }


    public HashMap<StoreType, Rectangle> getStoreBounds() {
        return storeBounds;
    }

    public Map<StoreType, StoreInventory> getShopInventories() {
        return shopInventories;
    }

    public HashMap<StoreType, Store> getStores() {
        return stores;
    }
}
