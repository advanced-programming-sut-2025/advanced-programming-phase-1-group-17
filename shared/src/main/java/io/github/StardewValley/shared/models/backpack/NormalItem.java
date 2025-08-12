package io.github.StardewValley.shared.models.backpack;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;
import java.util.Random;

public class NormalItem implements BackPackable, Placeable {
    private NormalItemType type;
    private int grassTextureID;

    public NormalItem() {}

    public NormalItem(NormalItemType type) {
        this.type = type;
        if (type.equals(NormalItemType.Grass)) {
            Random random = new Random();
            int randInt = random.nextInt(33);
        }
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    @Override
    public NormalItemType getType() {
        return type;
    }

    @Override
    public BackPackableSave toBackpackableSave() {
        BackPackableSave backPackableSave = new BackPackableSave(NormalItem.class.getSimpleName());
        backPackableSave.setNormalItem(this);
        return backPackableSave;
    }

    @Override
    public String getTexture() {
        return type.getInventoryTexturePath();
    }

    @Override
    public PlaceableSave toDTO(int x, int y, String ownerUsername) {
        PlaceableSave placeableSave = new PlaceableSave(NormalItem.class.getSimpleName());
        placeableSave.setNormalItem(this);
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }

    public int getGrassTextureID() {
        return grassTextureID;
    }

    public void setType(NormalItemType type) {
        this.type = type;
    }

    public void setGrassTextureID(int grassTextureID) {
        this.grassTextureID = grassTextureID;
    }
}
