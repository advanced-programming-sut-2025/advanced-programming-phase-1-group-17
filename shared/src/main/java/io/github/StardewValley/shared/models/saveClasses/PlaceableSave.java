package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.Fence;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.greenhouse.GreenHouseFence;
import io.github.StardewValley.shared.models.greenhouse.GreenHouseLake;
import io.github.StardewValley.shared.models.map.Hut;
import io.github.StardewValley.shared.models.map.Lake;
import io.github.StardewValley.shared.models.map.Quarry;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.plant.Seed;

public class PlaceableSave {
    private String type;

    private CropSave cropSave;
    private TreeSave treeSave;
    private CraftingItemSave craftingItemSave;
    private AnimalSave animalSave;
    private AnimalPlaceSave animalPlaceSave;
    private Fence fence;
    private GreenHouseSave greenHouseSave;
    private GreenHouseLake greenHouseLake;
    private GreenHouseFence greenHouseFence;
    private Hut hut;
    private NormalItem normalItem;
    private Mineral mineral;
    private Lake lake;
    private Quarry quarry;
    private Seed seed;
    private ShippingBinSave shippingBinSave;
    private Store store;
    private NPCSave NPCSave;

    public PlaceableSave(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CropSave getCropSave() {
        return cropSave;
    }

    public void setCropSave(CropSave cropSave) {
        this.cropSave = cropSave;
    }

    public TreeSave getTreeSave() {
        return treeSave;
    }

    public void setTreeSave(TreeSave treeSave) {
        this.treeSave = treeSave;
    }

    public CraftingItemSave getCraftingItemSave() {
        return craftingItemSave;
    }

    public void setCraftingItemSave(CraftingItemSave craftingItemSave) {
        this.craftingItemSave = craftingItemSave;
    }

    public AnimalSave getAnimalSave() {
        return animalSave;
    }

    public void setAnimalSave(AnimalSave animalSave) {
        this.animalSave = animalSave;
    }

    public AnimalPlaceSave getAnimalPlaceSave() {
        return animalPlaceSave;
    }

    public void setAnimalPlaceSave(AnimalPlaceSave animalPlaceSave) {
        this.animalPlaceSave = animalPlaceSave;
    }

    public GreenHouseSave getGreenHouseSave() {
        return greenHouseSave;
    }

    public void setGreenHouseSave(GreenHouseSave greenHouseSave) {
        this.greenHouseSave = greenHouseSave;
    }

    public GreenHouseLake getGreenHouseLake() {
        return greenHouseLake;
    }

    public void setGreenHouse(GreenHouseLake greenHouseLake) {
        this.greenHouseLake = greenHouseLake;
    }

    public ShippingBinSave getShippingBinSave() {
        return shippingBinSave;
    }

    public void setShippingBinSave(ShippingBinSave shippingBinSave) {
        this.shippingBinSave = shippingBinSave;
    }

    public NPCSave getNPCSave() {
        return NPCSave;
    }

    public void setNPCSave(NPCSave NPCSave) {
        this.NPCSave = NPCSave;
    }

    public Fence getFence() {
        return fence;
    }

    public void setFence(Fence fence) {
        this.fence = fence;
    }

    public void setGreenHouseLake(GreenHouseLake greenHouseLake) {
        this.greenHouseLake = greenHouseLake;
    }

    public GreenHouseFence getGreenHouseFence() {
        return greenHouseFence;
    }

    public void setGreenHouseFence(GreenHouseFence greenHouseFence) {
        this.greenHouseFence = greenHouseFence;
    }

    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }

    public NormalItem getNormalItem() {
        return normalItem;
    }

    public void setNormalItem(NormalItem normalItem) {
        this.normalItem = normalItem;
    }

    public Mineral getMineral() {
        return mineral;
    }

    public void setMineral(Mineral mineral) {
        this.mineral = mineral;
    }

    public Lake getLake() {
        return lake;
    }

    public void setLake(Lake lake) {
        this.lake = lake;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public void setQuarry(Quarry quarry) {
        this.quarry = quarry;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
