package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    StartNewGame("\\s*game\\s+new\\s+-u(\\s+(?<username1>\\S+))?(\\s+(?<username2>\\S+))?(\\s+(?<username3>\\S+))?\\s*"),

    ExitGame("\\s*exit\\s+game\\s*"),
    LoadGame("\\s*load\\s+game\\s*"),
    NextTurn("\\s*next\\s+turn\\s*"),
    Time("\\s*time\\s*"),
    Date("\\s*date\\s*"),
    DateTime("\\s*datetime\\s*"),
    DayOfTheWeek("\\s*day\\s+of\\s+the\\s+week\\s*"),
    CheatAdvanceTime("\\s*cheat\\s+advance\\s+time\\s+(?<hour>\\d+)h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s+advance\\s+date\\s+(?<day>\\d+)d\\s*"),
    Season("\\s*season\\s*"),
    CheatThor("\\s*cheat\\s+Thor\\s+-l\\s+<(?<x>\\d+), (?<y>\\d+)>\\d*"),
    Weather("\\s*weather\\s*"),
    WeatherForecast("\\s*weather\\s+forecast\\s*"),
    CheatWeatherSet("\\s*cheat\\s+weather\\s+set\\s+(?<type>\\S+)\\s*"),
    GreenhouseBuild("\\s*greenhouse\\s+build\\s*"),

    //For Energy
    EnergyShow("\\s*energy\\s+show\\s*"),
    EnergySet("\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*"),
    EnergyUnlimited("\\s*energy\\s+unlimited\\s*"),

    //For Inventory
    InventoryShow("\\s*inventory\\s+show\\s*"),
    InventoryTrash("\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>\\S+)(\\s+-n\\s+(?<number>\\d+))?\\s*"),
    Walk("\\s*walk -l <(?<x>\\d+), (?<y>\\d+)>\\s*"),
    ChooseGameMap("\\s*game\\s+map\\s+(?<mapNumber>\\S+)\\s*"),
    Int("\\d+"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+<(?<x>\\d+),\\s*(?<y>\\d+)>\\s+-s\\s+(?<size>\\d+)\\s*");



    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }

}
