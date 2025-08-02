package io.github.StardewValley.shared.models.backpack;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.GameAssetManager;

public enum NormalItemType implements BackPackableType{
    Wood(10, "Resource/Wood.png"),
    Fiber(0, "Resource/Fiber.png"),
    Hay(50, "Hay.png"),
    Well(500, ""),
    JojaCola(37.5, "Concessions/Joja_Cola_%28large%29.png"),
    GrassStarter(62.5, "Crafting/Grass_Starter.png"),
    Sugar(50, "Ingredient/Sugar.png"),
    WheatFlour(50, "Ingredient/Wheat_Flour.png"),
    Rice(100, "Ingredient/Rice.png"),
    TroutSoup(125, "Recipe/Trout_Soup.png"),
    Grass(0, "sprites/Grass.png"); //TODO: Make sure it is never added to backpack

    private final double price;
    private final String texturePath;

    NormalItemType(double price, String texturePath) {
        this.price = price;
        this.texturePath = texturePath;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public Texture getInventoryTexture() {
        return GameAssetManager.getGameAssetManager().getNormalItemTexture(this);
    }
}
