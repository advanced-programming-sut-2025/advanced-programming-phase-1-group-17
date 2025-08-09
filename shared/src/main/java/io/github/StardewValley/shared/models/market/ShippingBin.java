package io.github.StardewValley.shared.models.market;

import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.animal.AnimalProduct;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.Fruit;
import io.github.StardewValley.shared.models.savedClasses.PlaceableSave;
import io.github.StardewValley.shared.models.savedClasses.ShippingBinSave;

import java.util.ArrayList;

public class ShippingBin implements Placeable, BackPackable {
    private ShippingBinType type = ShippingBinType.Basic;
    private ArrayList<BackPackable> items = new ArrayList<>();
    //only one player can have items in a shipping bin each day
    private Player todayItemOwner = null;
    private int tileX;
    private int tileY;

    public ShippingBin(int x, int y, Game game) {
        game.getShippingBinBounds().put(this, new Rectangle(
                x * GameAssetManager.getGameAssetManager().getTileWidth(),
                y * GameAssetManager.getGameAssetManager().getTileHeight(),
                GameAssetManager.getGameAssetManager().getTileWidth(),
                GameAssetManager.getGameAssetManager().getTileHeight())
        );
        this.tileX = x;
        this.tileY = y;
    }

    //Just For Loading
    private ShippingBin(int x, int y) {
        this.tileX = x;
        this.tileY = y;
    }

    public void addItem(BackPackable backPackable) {
        items.add(backPackable);
    }

    public static void goToNextDay(Game game) {
        for (ShippingBin shippingBin : game.getShippingBinBounds().keySet()) {
            double total = 0;
            for (BackPackable item : shippingBin.items) {
                if (item.getClass().equals(Crop.class)) {
                    ItemQuality quality = ((Crop)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(AnimalProduct.class)) {
                    ItemQuality quality = ((AnimalProduct)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(Fish.class)) {
                    ItemQuality quality = ((Fish)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else if (item.getClass().equals(Fruit.class)) {
                    ItemQuality quality = ((Fruit)item).getQuality();
                    total += (item.getPrice() * quality.getLeverage());
                } else
                    total += item.getPrice();
            }
            if (shippingBin.todayItemOwner == null)
                continue;

            //TODO
//            Main.getGameView().showNotification("Player %s earned %.0f coins from selling the items from shipping bin."
//                .formatted(shippingBin.todayItemOwner.getUser().getUsername(), total));
            shippingBin.todayItemOwner.getBackPack().addCoin(
                Math.floor(total));
            shippingBin.items = new ArrayList<>();
            shippingBin.todayItemOwner = null;
        }
    }

    public Player getTodayItemOwner() {
        return todayItemOwner;
    }

    public void setTodayItemOwner(Player todayItemOwner) {
        this.todayItemOwner = todayItemOwner;
    }

    @Override
    public String getTexture() {
        return GameAssetManager.getGameAssetManager().getShippingBinTexture();
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(ShippingBin.class.getSimpleName());
        placeableSave.setShippingBinSave(new ShippingBinSave(this));
        return placeableSave;
    }

    @Override
    public void loadFromDTO(PlaceableSave dto) {
        ShippingBinSave save = dto.getShippingBinSave();
        ShippingBin shippingBin = new ShippingBin(save.getTileX(), save.getTileY());
        //TODO shippingBinBounds
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public double getPrice() {
        return 125;
    }

    @Override
    public BackPackableType getType() {
        return type;
    }

    public ArrayList<BackPackable> getItems() {
        return items;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }
}
