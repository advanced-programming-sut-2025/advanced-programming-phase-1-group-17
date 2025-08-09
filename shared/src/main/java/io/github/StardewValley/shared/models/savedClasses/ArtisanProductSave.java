package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;

public class ArtisanProductSave {
    private ArtisanProductType type;
    private boolean isReady ;
    private int hoursInProgress;
    private int daysInProgress;
    private double price;
    private double energy;

    public ArtisanProductSave() {
    }

    public ArtisanProductSave(ArtisanProduct artisanProduct) {
        this.type = artisanProduct.getType();
        this.isReady = artisanProduct.isReady();
        this.hoursInProgress = artisanProduct.getHoursInProgress();
        this.daysInProgress = artisanProduct.getDaysInProgress();
        this.price = artisanProduct.getPrice();
        this.energy = artisanProduct.getEnergy();
    }

    public ArtisanProductType getType() {
        return type;
    }

    public boolean isReady() {
        return isReady;
    }

    public int getHoursInProgress() {
        return hoursInProgress;
    }

    public int getDaysInProgress() {
        return daysInProgress;
    }

    public double getPrice() {
        return price;
    }

    public double getEnergy() {
        return energy;
    }
}
