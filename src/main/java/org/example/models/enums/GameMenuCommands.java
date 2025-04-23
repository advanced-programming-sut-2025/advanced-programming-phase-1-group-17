package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    startNewGame("\\s*game\\s+new\\s+-u(\\s+(?<username1>\\S+))?(\\s+(?<username2>\\S+))?(\\s+(?<username3>\\S+))?\\s*"),

    //For Energy
    EnergyShow("\\s*energy\\s+show\\s*"),
    EnergySet("\\s*energy\\s+set\\s+-v+(?<value>\\d+)\\s*"),
    EnergyUnlimited("\\s*energy\\s+unlimited\\s+"),

    //For Inventory
    InventoryShow("\\s*inventory\\s+show\\s*"),
    InventoryTrash("\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>\\S+)(\\s+-n\\s+(?<number>\\d+))?\\s*"),
    Walk("\\s*walk -l <(?<x>\\d+), (?<y>\\d+)>\\s*"),
    ChooseGameMap("\\s*game\\s+map\\s+(?<mapNumber>\\S+)\\s*"),
    Int("\\d+"),
    printMap("\\s*print\\s+map\\s+-l\\s+<(?<x>\\d+),\\s*(?<y>\\d+)>\\s+-s\\s+(?<size>\\d+)\\s*");



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
