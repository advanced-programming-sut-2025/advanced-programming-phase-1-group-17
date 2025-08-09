package io.github.StardewValley.shared.models.foraging;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.MineralSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class Mineral implements BackPackable, Placeable {
    MineralType type;
    boolean isForaging;

    public Mineral(MineralType type, boolean isForaging) {
        this.type = type;
        this.isForaging = isForaging;
    }

    public Mineral(PlaceableSave dto) {
        MineralSave save = dto.getMineralSave();

        this.type = save.getType();
        this.isForaging = save.isForaging();
    }

    @Override
    public String getName() {
        return type.getName();
    }

    public boolean isForaging() {
        return isForaging;
    }

    @Override
    public MineralType getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public String getTexture() {
        return MineralAssetManager.getMineralAssetManager().getTexture(type);
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Mineral.class.getSimpleName());
        placeableSave.setMineralSave(new MineralSave(this));
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Mineral mineral = new Mineral(dto);
    }
}
