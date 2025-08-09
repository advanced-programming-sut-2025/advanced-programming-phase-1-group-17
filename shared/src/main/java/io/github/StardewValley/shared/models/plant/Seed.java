package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;
import io.github.StardewValley.shared.models.savedClasses.SeedSave;

public class Seed implements BackPackable, Placeable {
    private SeedType type;
    public Seed(SeedType type) {
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }

    public void setType(SeedType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public String getTexture() {
        return type.getInventoryTexturePath();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Seed.class.getSimpleName());
        placeableSave.setSeedSave(new SeedSave(type));
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Seed seed = new Seed(dto.getSeedSave().getType());
    }
}
