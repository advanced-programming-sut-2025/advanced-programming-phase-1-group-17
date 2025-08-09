package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.cooking.Refrigerator;
import io.github.StardewValley.shared.models.savedClasses.HutSave;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;

public class Hut implements Placeable {
    private Refrigerator refrigerator = new Refrigerator();
    private String texture;
    private int x;
    private int y;

    public Hut(String texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public Hut(PlaceableSave dto) {
        HutSave save = dto.getHutSave();

        this.refrigerator = new Refrigerator(save.getRefrigerator());
        this.texture = save.getTexture();
        this.x = save.getX();
        this.y = save.getY();
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    @Override
    public String  getTexture() {
        return texture;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(Hut.class.getSimpleName());
        placeableSave.setHutSave(new HutSave(this));
        return  placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        Hut hut = new Hut(dto);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
