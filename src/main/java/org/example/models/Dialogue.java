package org.example.models;

import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;

public class Dialogue {
    private String message;
    private Season season;
    private WeatherType weatherType;
    private int minFriendship;
    private int maxFriendship;
    private int houre;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public int getMinFriendship() {
        return minFriendship;
    }

    public void setMinFriendship(int minFriendship) {
        this.minFriendship = minFriendship;
    }

    public int getMaxFriendship() {
        return maxFriendship;
    }

    public void setMaxFriendship(int maxFriendship) {
        this.maxFriendship = maxFriendship;
    }
}
