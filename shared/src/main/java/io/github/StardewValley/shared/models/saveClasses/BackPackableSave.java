package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.NPCS.Flower;
import io.github.StardewValley.shared.models.NPCS.Ring;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.market.ShopItem;
import io.github.StardewValley.shared.models.plant.Fertilizer;
import io.github.StardewValley.shared.models.plant.Fruit;
import io.github.StardewValley.shared.models.plant.Sapling;
import io.github.StardewValley.shared.models.plant.Seed;
import io.github.StardewValley.shared.models.tools.Tool;

public class BackPackableSave {
    private String type;

    private ArtisanProduct artisanProduct;
    private CraftingItemSave craftingItemSave;
    private CropSave cropSave;
    private Fertilizer fertilizer;
    private Fish fish;
    private Flower flower;
    private Food food;
    private Fruit fruit;
    private Mineral mineral;
    private NormalItem normalItem;
    private Ring ring;
    private Sapling sapling;
    private Seed seed;
    private ShippingBinSave shippingBinSave;
    private ShopItem shopItem;
    private Tool tool;

    public BackPackableSave() {
    }

    public BackPackableSave(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArtisanProduct getArtisanProduct() {
        return artisanProduct;
    }

    public void setArtisanProduct(ArtisanProduct artisanProductSave) {
        this.artisanProduct = artisanProductSave;
    }

    public CraftingItemSave getCraftingItemSave() {
        return craftingItemSave;
    }

    public void setCraftingItemSave(CraftingItemSave craftingItemSave) {
        this.craftingItemSave = craftingItemSave;
    }

    public CropSave getCropSave() {
        return cropSave;
    }

    public void setCropSave(CropSave cropSave) {
        this.cropSave = cropSave;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Mineral getMineral() {
        return mineral;
    }

    public void setMineral(Mineral mineral) {
        this.mineral = mineral;
    }

    public NormalItem getNormalItem() {
        return normalItem;
    }

    public void setNormalItem(NormalItem normalItem) {
        this.normalItem = normalItem;
    }

    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    public Sapling getSapling() {
        return sapling;
    }

    public void setSapling(Sapling sapling) {
        this.sapling = sapling;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public ShippingBinSave getShippingBinSave() {
        return shippingBinSave;
    }

    public void setShippingBinSave(ShippingBinSave shippingBinSave) {
        this.shippingBinSave = shippingBinSave;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}
