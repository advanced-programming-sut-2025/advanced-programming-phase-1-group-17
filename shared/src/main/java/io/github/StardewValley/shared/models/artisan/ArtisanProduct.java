package io.github.StardewValley.shared.models.artisan;

import io.github.StardewValley.shared.models.BackPackable;
import io.github.StardewValley.shared.models.BackPackableType;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.plant.CropType;
import io.github.StardewValley.shared.models.plant.FruitType;

import java.util.ArrayList;

public class ArtisanProduct implements BackPackable {
    private ArtisanProductType type;
    private boolean isReady = false;
    private int hoursInProgress = 0;
    private int daysInProgress = 0;
    private double price;
    private double energy;

    public ArtisanProduct(ArtisanProductType type, BackPackableType ingredientUsed) {
        this.type = type;
        this.price = type.getPrice();
        this.energy = type.getEnergy();
        //setPrice(ingredientUsed);
        //setEnergy(ingredientUsed);
        //setProgressTime(ingredientUsed);
    }

    private void setProgressTime(BackPackableType ingredientUsed) {
        if (type.equals(ArtisanProductType.Oil)) {
            if (ingredientUsed.equals(CropType.Corn)) {
                daysInProgress = 1;
                hoursInProgress = 18;
            } else if (ingredientUsed.equals(CropType.Sunflower)) {
                daysInProgress = 1;
                hoursInProgress = 23;
            }
        }
    }

    public static BackPackableType getIngredient(ArtisanProductType product, ArrayList<BackPackableType> provided) {
        if (product.equals(ArtisanProductType.Juice)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.Wine)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.Dried_Mushrooms)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.Dried_Fruit)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.Pickles)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.Jelly)) {
            return provided.get(0);
        } else if (product.equals(ArtisanProductType.SmokedFish)) {
            for (BackPackableType backPackableType : provided) {
                if (backPackableType.getClass().equals(FishType.class))
                    return backPackableType;
            }
        }
        return null;
    }

    private void setEnergy(BackPackableType ingredientUsed) {
        if (type.equals(ArtisanProductType.Juice)) {
            energy = 2 * ((CropType)ingredientUsed).getEnergy();
        } else if (type.equals(ArtisanProductType.Wine)) {
            energy = 1.75 * ((FruitType)ingredientUsed).getEnergy();
        } else if (type.equals(ArtisanProductType.Pickles)) {
            energy = 1.75 * ((CropType)ingredientUsed).getEnergy();
        } else if (type.equals(ArtisanProductType.Jelly)) {
            energy = 2 * ((FruitType)ingredientUsed).getEnergy();
        } else if (type.equals(ArtisanProductType.SmokedFish)) {
            energy = 1.5 * ((FishType)ingredientUsed).getEnergy();
        }
    }

    private void setPrice(BackPackableType ingredientUsed) {
        if (type.equals(ArtisanProductType.Juice))
            price = ingredientUsed.getPrice() * 2.25;
        else if (type.equals(ArtisanProductType.Wine))
            price = ingredientUsed.getPrice() * 3;
        else if (type.equals(ArtisanProductType.Dried_Mushrooms))
            price = ingredientUsed.getPrice() * 7.5 + 25;
        else if (type.equals(ArtisanProductType.Dried_Fruit))
            price = ingredientUsed.getPrice() * 7.5 + 25;
        else if (type.equals(ArtisanProductType.Pickles))
            price = ingredientUsed.getPrice() * 2 + 50;
        else if (type.equals(ArtisanProductType.Jelly))
            price = ingredientUsed.getPrice() * 2 + 50;
        else if (type.equals(ArtisanProductType.SmokedFish))
            price = ingredientUsed.getPrice() * 2;
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
        return price;
    }

    public void goToNextHour() {
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

    public double getEnergy() {
        return energy;
    }

    public int getHoursInProgress() {
        return hoursInProgress;
    }

    public int getDaysInProgress() {
        return daysInProgress;
    }

    public void goToNextDay(int hours) {
        if (type.equals(ArtisanProductType.Dried_Mushrooms) || type.equals(ArtisanProductType.Dried_Fruit)
            || type.equals(ArtisanProductType.Raisins)) {
            isReady = true;
        } else {
            for (int i = 0; i < hours; i++)
                goToNextHour();
        }
    }
}
