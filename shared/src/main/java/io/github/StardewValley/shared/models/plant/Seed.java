package io.github.StardewValley.shared.models.plant;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

public class Seed implements BackPackable, Placeable {
    private SeedType type;
    public Seed(SeedType type) {
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Seed.class.getSimpleName());
        backPackableSave.setSeed(this);
        return backPackableSave;
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
        placeableSave.setSeed(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }
}
