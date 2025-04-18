package models;

import models.enums.DaysOfTheWeek;
import models.enums.Season;
import models.enums.WeatherType;

public class TimeAndDate {
    private WeatherType today;
    private WeatherType tomorrow;
    private int hour, minute;
    private String day, month, year;
    private Season season;
    private DaysOfTheWeek dayOfTheWeek;
    public void increaseHour() {}
    public void goToNextDay() {}
    public void changeTomorrowWeatherType() {}

    public WeatherType getToday() {
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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
