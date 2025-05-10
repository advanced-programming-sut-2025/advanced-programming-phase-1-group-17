package org.example.models;

import org.example.models.foraging.ForagingController;
import org.example.models.NPCS.NPC;
import org.example.models.enums.DaysOfTheWeek;
import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;
import org.example.models.plant.PlantGrowthController;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimeAndDate {
    private WeatherType todayWeather;
    private WeatherType tomorrowWeather;
    private int hour, minute;
    private int day, month, year;
    private Season season = Season.Spring;
    private DaysOfTheWeek dayOfTheWeek = DaysOfTheWeek.Saturday;

    public TimeAndDate(){
        setTodayWeather(getRandomWeather());
        setTomorrowWeather(getRandomWeather());
        hour = 9;
        minute = 0;
        day = 1;
        month = 1;
        year = 1;
    }

    public void increaseHour() {
        hour++;
        if (hour >= 22) {
            hour = 9;
            minute = 0;
            goToNextDay();
        }
    }

    public void goToNextDay() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            player.setEnergy(player.getMaxEnergy());
            player.setInteractionWithPartner(false);
            if(player.getIsbrokenUp() > 0){
                player.setEnergy(player.getMaxEnergy()/2);
                player.setIsbrokenUp(player.getIsbrokenUp()-1);
            }
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC")){
                continue;
            }
            for (NPC npc : App.getCurrentGame().getNPCs()) {
                player.getTalkedNPCToday().put(npc, false);
                player.getGiftNPCToday().put(npc, true);
            }
        }
        // fifty percent chance of receiving a gift from an NPC
        int a = ThreadLocalRandom.current().nextInt(1, 3);
        if (a == 2) {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals("NPC")){
                    continue;
                }
                for (NPC npc : App.getCurrentGame().getNPCs()) {
                    if (player.getFriendShipsWithNPCs().get(npc) >= 600) {
                        Flower flower = new Flower();
                        message message = new message(npc, "you received a flower");
                        player.getBackPack().addItemToInventory(flower);
                    }
                }
            }
        }

        todayWeather = tomorrowWeather;
        setTomorrowWeather(getRandomWeather());
        PlantGrowthController.growOneDay();
        ForagingController.setForagingForNextDay();


        changeDayOfTheWeek();
        day++;
        if (day >= 28) {
            changeSeason();
            day = 1;
        }
    }

    public void changeSeason() {
        Season[] seasons = Season.values();
        int currentIndex = season.ordinal();
        int nextIndex = (currentIndex + 1) % seasons.length;

        season = seasons[nextIndex];

        if (nextIndex == 0) {
            year++;
        }
    }

    public void changeDayOfTheWeek() {
        DaysOfTheWeek[] daysOfTheWeek = DaysOfTheWeek.values();
        int currentDayIndex = dayOfTheWeek.ordinal();
        dayOfTheWeek = daysOfTheWeek[(currentDayIndex + 1) % daysOfTheWeek.length];
    }

    public WeatherType getRandomWeather(){
        Random rand = new Random();
        int randInt = rand.nextInt(4) + 1;
        if (randInt == 1) return WeatherType.Snow;
        else if (randInt == 2) return WeatherType.Rainy;
        else if (randInt == 3) return WeatherType.Storm;
        return WeatherType.Sunny;
    }

    public WeatherType getTodayWeatherType() {
        return todayWeather;
    }

    public void setTodayWeather(WeatherType todayWeather) {
        this.todayWeather = todayWeather;
    }

    public WeatherType getTomorrowWeather() {
        return tomorrowWeather;
    }

    public void setTomorrowWeather(WeatherType tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public DaysOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(DaysOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

}
