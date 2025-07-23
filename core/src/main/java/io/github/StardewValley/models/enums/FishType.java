package io.github.StardewValley.models.enums;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.models.BackPackableType;

public enum FishType implements BackPackableType {
    // Regular Fish
    Salmon(75, Season.Fall, false, new Texture("Fish/Salmon.png")),
    Sardine(40, Season.Fall, false, new Texture("Fish/Sardine.png")),
    Shad(60, Season.Fall, false, new Texture("Fish/Shad.png")),
    BlueDiscus(120, Season.Fall, false, new Texture("Fish/Blue_Discus.png")),
    MidnightCarp(150, Season.Winter, false, new Texture("Fish/Midnight_Carp.png")),
    Squid(80, Season.Winter, false, new Texture("Fish/Squid.png")),
    Tuna(100, Season.Winter, false, new Texture("Fish/Tuna.png")),
    Perch(55, Season.Winter, false, new Texture("Fish/Perch.png")),
    Flounder(100, Season.Spring, false, new Texture("Fish/Flounder.png")),
    LionFish(100, Season.Spring, false, new Texture("Fish/Lionfish.png")),
    Herring(30, Season.Spring, false, new Texture("Fish/Herring.png")),
    GhostFish(45, Season.Spring, false, new Texture("Fish/Ghostfish.png")),
    Tilapia(75, Season.Summer, false, new Texture("Fish/Tilapia.png")),
    Dorado(100, Season.Summer, false, new Texture("Fish/Dorado.png")),
    SunFish(30, Season.Summer, false, new Texture("Fish/Sunfish.png")),
    RainbowTrout(65, Season.Summer, false, new Texture("Fish/Rainbow_Trout.png")),

    // Legendary Fish
    Legend(5000, Season.Spring, true, new Texture("Fish/Legend.png")),
    GlacierFish(1000, Season.Winter, true, new Texture("Fish/Glacierfish.png")),
    Angler(900, Season.Fall, true, new Texture("Fish/Angler.png")),
    CrimsonFish(1500, Season.Summer, true, new Texture("Fish/Crimsonfish.png"));

    private final int price;
    private final Season season;
    private final boolean isLegendary;
    private final Texture texture;

    FishType(int price, Season season, boolean isLegendary, Texture texture) {
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
        this.texture = texture;
    }

    public Season getSeason() {
        return season;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public double getEnergy() {
        return 0;
    }

    @Override
    public Texture getInventoryTexture() {
        return texture;
    }
}
