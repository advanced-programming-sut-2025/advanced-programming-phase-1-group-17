package org.example.models.artisan;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;
import org.example.models.enums.AnimalProductType;
import org.example.models.foraging.ForagingCropType;
import org.example.models.plant.CropType;
import org.example.models.plant.FruitType;
import org.example.models.tools.BackPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum IngredientGroup {
    AnyMushroom(Set.of(MushroomType.Red, MushroomType.Morel, MushroomType.Common, MushroomType.Chanterelle, MushroomType.Purple)),
    AnyFruit(Set.of(FruitType.values())),
    AnyVegetable(Set.of(CropType.Carrot, CropType.Potato, CropType.Tomato)), // example subset
    MilkOrLargeMilk(Set.of(AnimalProductType.Milk, AnimalProductType.LargeMilk)),
    GoatMilkOrLargeGoatMilk(Set.of(AnimalProductType.GoatMilk, AnimalProductType.LargeGoatMilk)),
    EggOrLargeEgg(Set.of(AnimalProductType.Egg, AnimalProductType.LargeEgg)),
    Grapes(Set.of(CropType.Grape, ForagingCropType.GRAPE));

    private final Set<BackPackableType> members;

    IngredientGroup(Set<BackPackableType> options) {
        this.members = options;
    }

    public Set<BackPackableType> getMembers() {
        return members;
    }

    public boolean matches(BackPackableType type) {
        return members.contains(type);
    }

    public int countInBackPack(BackPack backPack) {
        int total = 0;
        for (BackPackableType type : members) {
            List<BackPackable> items = backPack.getBackPackItems().getOrDefault(type, new ArrayList<>());
            total += items.size();
        }
        return total;
    }


    public void removeFromBackPack(int count, BackPack backPack) {
        for (BackPackableType type : members) {
            List<BackPackable> items = backPack.getBackPackItems().getOrDefault(type, new ArrayList<>());
            while (!items.isEmpty() && count > 0) {
                items.remove(0); // remove one item
                count--;
            }
            if (count == 0) break;
        }

        if (count > 0) {
            throw new IllegalStateException("Not enough items to remove from group: " + this.name);
        }
    }

}
