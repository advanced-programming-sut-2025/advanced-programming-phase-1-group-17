package org.example.models.plant;

import org.example.models.enums.Season;

import java.util.*;

public class MixedSeedPossibleCrops {
    private static final Map<Season, List<CropType>> cropsBySeason = new HashMap<>();

    static {
        for (Season season : Season.values()) {
            cropsBySeason.put(season, new ArrayList<>());
        }

        Set<CropType> mixedSeedEligible = Set.of(
                CropType.Cauliflower, CropType.Parsnip, CropType.Potato, CropType.BlueJazz, CropType.Tulip,
                CropType.HotPepper, CropType.Radish, CropType.Wheat, CropType.Poppy, CropType.SummerSpangle,
                CropType.Artichoke, CropType.Eggplant, CropType.Pumpkin, CropType.FairyRose,
                CropType.Corn, CropType.Sunflower,
                CropType.Powdermelon
        );

        for (CropType crop : mixedSeedEligible) {
            for (Season season : crop.getSeasons()) {
                cropsBySeason.get(season).add(crop);
            }
        }
    }

    public static List<CropType> getCropsForSeason(Season season) {
        return cropsBySeason.getOrDefault(season, Collections.emptyList());
    }
}
