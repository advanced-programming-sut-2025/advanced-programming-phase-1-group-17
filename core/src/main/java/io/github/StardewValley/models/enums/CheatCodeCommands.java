package io.github.StardewValley.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCodeCommands {
    CheatAdvanceTime("\\s*cheat\\s+advance\\s+time\\s+(?<hour>\\d+)h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s+advance\\s+date\\s+(?<day>\\d+)d\\s*"),
    CheatThor("\\s*cheat\\s+Thor\\s+-l\\s+<(?<x>\\d+), (?<y>\\d+)>\\d*"),
    CheatWeatherSet("\\s*cheat\\s+weather\\s+set\\s+(?<type>\\S+)\\s*"),
    EnergyUnlimited("\\s*energy\\s+unlimited\\s*"),
    Int("\\d+"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    CheatSetFriendshipWithAnimal("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>\\S+)\\s+-c\\s+(?<amount>\\S+)\\s*"),
    CheatAddDollars("\\s*cheat\\s+add\\s+(?<count>\\S+)\\s+dollars\\s*");

    private final String regex;

    CheatCodeCommands(String regex) {
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
