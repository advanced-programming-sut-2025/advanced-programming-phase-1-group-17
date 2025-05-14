package org.example.models.plant;

import com.sun.source.tree.UsesTree;
import org.example.models.App;
import org.example.models.BackPackable;
import org.example.models.market.ItemQuality;
import org.example.models.tools.ToolMaterial;

import java.util.Random;

public class Fruit implements BackPackable {
    private FruitType type;
    private ItemQuality quality;

    public Fruit(FruitType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public FruitType getType() {
        return type;
    }

    public void setType(FruitType type) {
        this.type = type;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }

    public void setItemQuality() {
        Random random = new Random();
        int randInt = random.nextInt(100);

        ToolMaterial toolMaterial = App.getCurrentGame().getCurrentPlayingPlayer().getCurrentTool().getMaterial();
        if (toolMaterial.equals(ToolMaterial.Basic)) {
            if (randInt< 25)
                quality = ItemQuality.Regular;
            else if (randInt < 50)
                quality = ItemQuality.Silver;
            else if (randInt < 75)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (toolMaterial.equals(ToolMaterial.Copper)) {
            if (randInt< 20)
                quality = ItemQuality.Regular;
            else if (randInt < 40)
                quality = ItemQuality.Silver;
            else if (randInt < 60)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (toolMaterial.equals(ToolMaterial.Iron)) {
            if (randInt< 15)
                quality = ItemQuality.Regular;
            else if (randInt < 30)
                quality = ItemQuality.Silver;
            else if (randInt < 45)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (toolMaterial.equals(ToolMaterial.Gold)) {
            if (randInt< 10)
                quality = ItemQuality.Regular;
            else if (randInt < 20)
                quality = ItemQuality.Silver;
            else if (randInt < 30)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        } else if (toolMaterial.equals(ToolMaterial.Iridium)) {
            if (randInt< 5)
                quality = ItemQuality.Regular;
            else if (randInt < 10)
                quality = ItemQuality.Silver;
            else if (randInt < 15)
                quality = ItemQuality.Gold;
            else
                quality = ItemQuality.Iridium;
        }
    }

}
