package org.example.models.foraging;

import org.example.models.enums.Season;
import org.example.models.plant.SeedType;

import java.util.*;

public abstract class ForagingSeed {

    private static final Map<Season, List<SeedType>> seasonalSeedMap = new HashMap<>();

    static {
        for (Season season : Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter)) {
            seasonalSeedMap.put(season, new ArrayList<>());
        }

        // Spring seeds
        Collections.addAll(seasonalSeedMap.get(Season.Spring),
                SeedType.JazzSeeds, SeedType.CarrotSeeds, SeedType.CauliflowerSeeds,
                SeedType.CoffeeBean, SeedType.GarlicSeeds, SeedType.BeanStarter,
                SeedType.KaleSeeds, SeedType.ParsnipSeeds, SeedType.PotatoSeeds,
                SeedType.RhubarbSeeds, SeedType.StrawberrySeeds, SeedType.TulipBulb,
                SeedType.RiceShoot
        );

        // Summer seeds
        Collections.addAll(seasonalSeedMap.get(Season.Summer),
                SeedType.BlueberrySeeds, SeedType.CornSeeds, SeedType.HopsStarter,
                SeedType.PepperSeeds, SeedType.MelonSeeds, SeedType.PoppySeeds,
                SeedType.RadishSeeds, SeedType.RedCabbageSeeds, SeedType.StarfruitSeeds,
                SeedType.SpangleSeeds, SeedType.SummerSquashSeeds, SeedType.SunflowerSeeds,
                SeedType.TomatoSeeds, SeedType.WheatSeeds
        );

        // Fall seeds
        Collections.addAll(seasonalSeedMap.get(Season.Fall),
                SeedType.AmaranthSeeds, SeedType.ArtichokeSeeds, SeedType.BeetSeeds,
                SeedType.BokChoySeeds, SeedType.BroccoliSeeds, SeedType.CranberrySeeds,
                SeedType.EggplantSeeds, SeedType.FairySeeds, SeedType.GrapeStarter,
                SeedType.PumpkinSeeds, SeedType.YamSeeds, SeedType.RareSeed
        );

        // Winter seeds
        seasonalSeedMap.get(Season.Winter).add(SeedType.PowdermelonSeeds);

        // Special seeds go in ALL seasons
        List<SeedType> specialSeeds = Arrays.asList(SeedType.AncientSeeds, SeedType.Mixed);
        for (Season season : Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter)) {
            seasonalSeedMap.get(season).addAll(specialSeeds);
        }
    }

    public static List<SeedType> getSeedTypesBySeason(Season season) {
        return seasonalSeedMap.getOrDefault(season, Collections.emptyList());
    }
}
