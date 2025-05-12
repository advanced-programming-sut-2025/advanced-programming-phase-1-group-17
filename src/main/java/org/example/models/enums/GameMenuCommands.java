package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    StartNewGame("\\s*game\\s+new\\s+-u(\\s+(?<username1>\\S+))?(\\s+(?<username2>\\S+))?(\\s+(?<username3>\\S+))?(\\s+(?<rest>\\S+))?\\s*"),

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

    //Tool
    ToolsEquip("\\s*tools\\s+equip\\s+(?<toolName>\\S+)\\s*"),
    ToolsShowCurrent("\\s*tools\\s+show\\s+current\\s*"),
    ToolsShowAvailable("\\s*tools\\s+show\\s+available\\s*"),
    ToolsUpgrade("\\s*tools\\s+upgrade\\s+(?<toolName>\\S+)\\s*"),
    ToolsUse("\\s*tools\\s+use\\s+-d\\s+(?<direction>\\d+)\\s*"),
    //For Inventory
    InventoryShow("\\s*inventory\\s+show\\s*"),
    InventoryTrash("\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>\\S+)(\\s+-n\\s+(?<number>\\d+))?\\s*"),
    Walk("\\s*walk -l <(?<x>\\d+), (?<y>\\d+)>\\s*"),
    ChooseGameMap("\\s*game\\s+map\\s+(?<mapNumber>\\S+)\\s*"),
    Int("\\d+"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+<(?<x>\\d+),\\s*(?<y>\\d+)>\\s+-s\\s+(?<size>\\d+)\\s*"),

    //For Plants
    CraftInfo("\\s*craftinfo\\s+-n\\s+(?<craftName>\\S+)\\s*"),
    Plant("\\s*plant\\s+-s\\s+(?<seed>\\S+)\\s+-d\\s+(?<direction>\\d+)\\s*"),
    ShowPlant("\\s*showplant\\s+-l\\s+(?<x>\\d+), (?<y>\\d+)\\s*"),
    Fertilize("\\s*fertilize\\s+-f\\s+(?<fertilizer>\\S+)\\s+-d\\s+(?<direction>\\d+)\\s*"),
    HowMuchWater("\\s*howmuch\\s+water\\s*"),
    //For Crafting
    CraftingShowRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    CraftingCraft("\\s*crafting\\s+craft\\s+(?<itemName>\\S+)\\s*"),


    //For Cooking
    CookingRefrigerator("\\s*cooking\\s+refrigerator\\s+(?<mod>put|pick)\\s+(?<item>\\S+)\\s*"),
    CookingShowRecipes("\\s*cooking\\s+show\\s+recipe\\s*"),
    CookingPrepare("\\s*cooking\\s+prepare\\s+(?<recipeName>\\S+)\\s*"),
    Eat("\\s*eat\\s+(?<foodName>\\S+)\\s*"),

    //For artisan
    ArtisanUse("\\s*artisan\\s+use\\s+(?<artisanName>\\S+)(\\s+(?<itemNames>.+))?\\s*"),
    ArtisanGet("\\s*artisan\\s+get\\s+(?<artisanName>\\S+)\\s*"),

    //For Trade
    ShowAllProducts("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    Purchase("\\s*purchase\\s+(?<productName>\\S+)(\\s+-n\\s+(?<count>\\d+))?\\s*"),
    CheatAddDollars("\\s*cheat\\s+add\\s+(?<count>\\S+)\\s+dollars\\s*"),
    Sell("\\s*sell\\s+(?<productName>\\S+)(\\s+-n\\s+(?<count>\\d+))?\\s*"),

    //For friendships
    talk("\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>[\\S ]+)\\s*"),
    talkHistory("\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    gift("\\s*gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*"),
    hug("\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*"),
    giftRate("\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>\\S+)\\s+-r\\s+(?<rate>\\S+)\\s*"),
    giftHistory("\\s*gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    flower("flower\\s+-u\\s+(?<username>\\S+)\\s*"),
    askMarriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\d+)\\s*"),
    respond("\\s*respond\\s+(?<accept>accept|reject)\\s+-u\\s+(?<username>\\S+)\\s*"),
    deleteMessage("\\s*delete\\s+message\\s+(?<index>\\d+)\\s*"),
    startTrade("\\s*start\\s+trade\\s*"),
    //for NPC
    meetNPC("\\s*meet\\s+NPC\\s+(?<npcName>\\S+)\\s*"),
    giftNPC("\\s*gift\\s+NPC\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>\\S+)\\s*"),
    questFinish("\\s*quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*");


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
