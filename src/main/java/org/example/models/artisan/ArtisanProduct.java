package org.example.models.artisan;

import org.example.models.BackPackable;

public class ArtisanProduct implements BackPackable {
    private ArtisanProductType type;
    private boolean isReady = false;
    private int hoursInProgress = 0;
    private int daysInProgress = 0;

    public ArtisanProduct(ArtisanProductType type) {
        this.type = type;
    }

    public ArtisanProductType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }

    public void goToNextHour(){
        if (isReady)
            return;
        this.hoursInProgress++;
        if (this.hoursInProgress == 24) {
            this.daysInProgress++;
            this.hoursInProgress = 0;
        }
        if (this.hoursInProgress == type.getProcessingHours() && this.daysInProgress == type.getProcessingDays())
            isReady = true;
    }

    public void setType(ArtisanProductType type) {
        this.type = type;
    }

    public boolean isReady() {
        return isReady;
    }
}
