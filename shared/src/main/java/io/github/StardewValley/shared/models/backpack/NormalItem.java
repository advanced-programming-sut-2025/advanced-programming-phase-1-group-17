package io.github.StardewValley.shared.models.backpack;

import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.savedClasses.NormalItemSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

import java.util.Random;

public class NormalItem implements BackPackable, Placeable {
    private NormalItemType type;
    private int grassTextureID;

    public NormalItem(NormalItemType type) {
        this.type = type;
        if (type.equals(NormalItemType.Grass)) {
            Random random = new Random();
            int randInt = random.nextInt(33);
        }
    }


    public NormalItem(PlaceableSave dto) {
        NormalItemSave save = dto.getNormalItemSave();

        this.type = save.getType();
        this.grassTextureID = save.getGrassTextureID();
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
    public String getTexture() {
        return type.getInventoryTexturePath();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(NormalItem.class.getSimpleName());
        placeableSave.setNormalItemSave(new NormalItemSave(this));
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        NormalItem normalItem = new NormalItem(dto);
    }

    public int getGrassTextureID() {
        return grassTextureID;
    }
}
