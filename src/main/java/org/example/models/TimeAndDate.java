package org.example.models;

import org.example.models.enums.DaysOfTheWeek;
import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;

public class TimeAndDate {
    private WeatherType today;
    private WeatherType tomorrow;
    private int hour, minute;
    private int day, month, year;
    private Season season=Season.Spring;
    private DaysOfTheWeek dayOfTheWeek=DaysOfTheWeek.Saturday;
    public void increaseHour() {
        //TODO when all players playes hour ++;
        hour++;
        if(hour >= 22){
            hour = 9;
            minute = 0;
            goToNextDay();
        }
    }
    public void goToNextDay() {
        changeTomorrowWeatherType();
        changeDayOfTheWeek();
        day++;
        if(day>=28){
            changeSeason();
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
    public void changeTomorrowWeatherType() {}

    public WeatherType getTodayWeatherType() {
        return today;
    }

    public void setToday(WeatherType today) {
        this.today = today;
    }

    public WeatherType getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(WeatherType tomorrow) {
        this.tomorrow = tomorrow;
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
