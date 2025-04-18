package models.enums;

public enum WeatherType {
    Sunny(false, 1, false),
    Rainy(true, 1.5, false),
    Storm(true, 1.5, true),
    Snow(false, 2, false);

    private boolean automaticallyWatering;
    private double energyConsume;
    private boolean canBreakTrees;


    WeatherType(boolean automaticallyWatering,
                double energyConsume,
                boolean canBreakTrees) {

    }
}