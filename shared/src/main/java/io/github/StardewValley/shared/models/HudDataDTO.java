package io.github.StardewValley.shared.models;

// این کلاس به سازنده خالی برای Jackson deserialization نیاز دارد
public class HudDataDTO {
    private String timeString;
    private String dateString;
    private String season; // "SPRING", "SUMMER", "FALL", "WINTER"
    private String weather; // "SUNNY", "RAINY", "STORM", "SNOW"
    private int money;
    private double playerEnergy;
    private double playerMaxEnergy;
    private boolean playerEnergyUnlimited;
    private float timeAngle; // زاویه عقربه ساعت که در سرور محاسبه شده

    // Constructor خالی برای Jackson
    public HudDataDTO() {}

    // Constructor اصلی
    public HudDataDTO(String timeString, String dateString, String season, String weather, int money, double playerEnergy, double playerMaxEnergy, boolean playerEnergyUnlimited, float timeAngle) {
        this.timeString = timeString;
        this.dateString = dateString;
        this.season = season;
        this.weather = weather;
        this.money = money;
        this.playerEnergy = playerEnergy;
        this.playerMaxEnergy = playerMaxEnergy;
        this.playerEnergyUnlimited = playerEnergyUnlimited;
        this.timeAngle = timeAngle;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public double getPlayerEnergy() {
        return playerEnergy;
    }

    public void setPlayerEnergy(double playerEnergy) {
        this.playerEnergy = playerEnergy;
    }

    public double getPlayerMaxEnergy() {
        return playerMaxEnergy;
    }

    public void setPlayerMaxEnergy(double playerMaxEnergy) {
        this.playerMaxEnergy = playerMaxEnergy;
    }

    public boolean isPlayerEnergyUnlimited() {
        return playerEnergyUnlimited;
    }

    public void setPlayerEnergyUnlimited(boolean playerEnergyUnlimited) {
        this.playerEnergyUnlimited = playerEnergyUnlimited;
    }

    public float getTimeAngle() {
        return timeAngle;
    }

    public void setTimeAngle(float timeAngle) {
        this.timeAngle = timeAngle;
    }
}
