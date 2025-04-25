package org.example.models;

import org.example.foraging.ForagingController;
import org.example.models.enums.DaysOfTheWeek;
import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;
import org.example.models.plant.PlantGrowthController;

import java.util.Random;

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
