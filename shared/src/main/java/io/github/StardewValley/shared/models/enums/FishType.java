package io.github.StardewValley.shared.models.enums;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public enum FishType implements BackPackableType {
    // Regular Fish
    Salmon(75, Season.Fall, false, ("Fish/Salmon.png")),
    Sardine(40, Season.Fall, false,("Fish/Sardine.png")),
    Shad(60, Season.Fall, false, ("Fish/Shad.png")),
    BlueDiscus(120, Season.Fall, false, ("Fish/Blue_Discus.png")),
    MidnightCarp(150, Season.Winter, false, ("Fish/Midnight_Carp.png")),
    Squid(80, Season.Winter, false, ("Fish/Squid.png")),
    Tuna(100, Season.Winter, false, ("Fish/Tuna.png")),
    Perch(55, Season.Winter, false, ("Fish/Perch.png")),
    Flounder(100, Season.Spring, false, ("Fish/Flounder.png")),
    LionFish(100, Season.Spring, false, ("Fish/Lionfish.png")),
    Herring(30, Season.Spring, false, ("Fish/Herring.png")),
    GhostFish(45, Season.Spring, false, ("Fish/Ghostfish.png")),
    Tilapia(75, Season.Summer, false, ("Fish/Tilapia.png")),
    Dorado(100, Season.Summer, false, ("Fish/Dorado.png")),
    SunFish(30, Season.Summer, false, ("Fish/Sunfish.png")),
    RainbowTrout(65, Season.Summer, false, ("Fish/Rainbow_Trout.png")),

    // Legendary Fish
    Legend(5000, Season.Spring, true,("Fish/Legend.png")),
    GlacierFish(1000, Season.Winter, true, ("Fish/Glacierfish.png")),
    Angler(900, Season.Fall, true, ("Fish/Angler.png")),
    CrimsonFish(1500, Season.Summer, true,("Fish/Crimsonfish.png"));

    private final int price;
    private final Season season;
    private final boolean isLegendary;
    private final String texture;

    FishType(int price, Season season, boolean isLegendary, String texture) {
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
    public String getInventoryTexture() {
        return texture;
    }
}
