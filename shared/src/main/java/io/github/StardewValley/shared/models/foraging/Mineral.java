package io.github.StardewValley.shared.models.foraging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.BackPackSave;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Mineral implements BackPackable, Placeable {
    MineralType type;
    boolean isForaging;

    public Mineral() {
    }

    public Mineral(MineralType type, boolean isForaging) {
        this.type = type;
        this.isForaging = isForaging;
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
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(Mineral.class.getSimpleName());
        backPackableSave.setMineral(this);
        return backPackableSave;
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
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(Mineral.class.getSimpleName());
        placeableSave.setMineral(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }

    public void setType(MineralType type) {
        this.type = type;
    }

    public void setForaging(boolean foraging) {
        isForaging = foraging;
    }
}
