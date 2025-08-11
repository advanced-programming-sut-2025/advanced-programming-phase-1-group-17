package io.github.StardewValley.shared;

import io.github.StardewValley.shared.models.enums.DaysOfTheWeek;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.enums.WeatherType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class TimeAndDateDTO {
    @Enumerated(EnumType.STRING)
    private WeatherType todayWeather;
    @Enumerated(EnumType.STRING)
    private WeatherType tomorrowWeather;
    private int hour, minute;
    private int day, month, year;
    @Enumerated(EnumType.STRING)
    private Season season;
    @Enumerated(EnumType.STRING)
    private DaysOfTheWeek dayOfTheWeek;

    public TimeAndDateDTO() {
    }

    public TimeAndDateDTO(WeatherType todayWeather, WeatherType tomorrowWeather,
                          int hour, int minute, int day, int month, int year,
                          Season season, DaysOfTheWeek dayOfTheWeek) {
        this.todayWeather = todayWeather;
        this.tomorrowWeather = tomorrowWeather;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.season = season;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public WeatherType getTodayWeather() {
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
