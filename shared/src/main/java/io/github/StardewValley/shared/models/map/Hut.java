package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.Refrigerator;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.List;

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
        placeableSave.setHut(this);
        return  placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
