package org.example.models;

import org.example.models.animal.Animal;
import org.example.models.crafting.CraftingItem;
import org.example.models.foraging.ForagingController;
import org.example.models.NPCS.NPC;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.enums.DaysOfTheWeek;
import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;
import org.example.models.foraging.Mineral;
import org.example.models.foraging.MineralType;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;
import org.example.models.plant.*;
import org.example.models.market.ShippingBin;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimeAndDate {
    private WeatherType todayWeather;
    private WeatherType tomorrowWeather;
    private int hour, minute;
    private int day, month, year;
    private Season season = Season.Spring;
    private DaysOfTheWeek dayOfTheWeek = DaysOfTheWeek.Saturday;

    public TimeAndDate() {
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
        for (Player player : App.getCurrentGame().getPlayers()) {
            for (ArtisanProduct artisanItemsInProgress : CraftingItem.getAllArtisanProductsInProgress()) {
                artisanItemsInProgress.goToNextHour();
            }
        }

        if (hour > 22) {
            for (Player player : App.getCurrentGame().getPlayers()) {
                for (ArtisanProduct artisanItemsInProgress : CraftingItem.getAllArtisanProductsInProgress()) {
                    artisanItemsInProgress.goToNextDay();
                }
            }
            hour = 9;
            minute = 0;
            goToNextDay();
        }
    }

    public void goToNextDay() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC")) continue;
            Tile.getTile(player.getX(), player.getY()).setWhoIsHere(null);
            Tile.getTile(player.getPlayerMap().getX_start(), player.getPlayerMap().getY_start()).setWhoIsHere(player);
            player.setX(player.getPlayerMap().getX_start());
            player.setY(player.getPlayerMap().getY_start());
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            player.setEnergy(player.getMaxEnergy());
            player.setInteractionWithPartner(false);
            if (player.getIsbrokenUp() > 0) {
                player.setEnergy(player.getMaxEnergy() / 2);
                player.setIsbrokenUp(player.getIsbrokenUp() - 1);
            }
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals("NPC")) {
                continue;
            }
            for (NPC npc : App.getCurrentGame().getNPCs()) {
                player.getTalkedNPCToday().put(npc, false);
                player.getGiftNPCToday().put(npc, false);
            }
        }
        // fifty percent chance of receiving a gift from an NPC
        int a = ThreadLocalRandom.current().nextInt(1, 3);
        if (a == 2) {
            for (Player player : App.getCurrentGame().getPlayers()) {
                if (player.getUser().getUsername().equals("NPC")) {
                    continue;
                }
                for (NPC npc : App.getCurrentGame().getNPCs()) {
                    if (player.getFriendShipsWithNPCs().get(npc) >= 600) {
                        Flower flower = new Flower(FlowerType.FLOWER);
                        //TODO flower
                        message message = new message(npc, "you received a flower");
                        player.getBackPack().addItemToInventory(flower);
                    }
                }
            }
        }
        //Animal
        Animal.goToNextDay();

        normalizeMaxEnergies();

        todayWeather = tomorrowWeather;
        setTomorrowWeather(getRandomWeather());

        //Actions Needed to be done every day
        weatherEffect();
        PlantGrowthController.growOneDay();
        ForagingController.setForagingForNextDay();
        ShippingBin.goToNextDay();
        App.getCurrentGame().getStoreManager().resetDailyLimits();

        changeDayOfTheWeek();
        day++;
        if (day > 28) {
            changeSeason();
            // active quest 3
            for (NPC npc : App.getCurrentGame().getNPCs()) {
                npc.getRequests().get(2).setActive(true);
            }
            day = 1;
        }
    }

    private void weatherEffect() {
        if (todayWeather.equals(WeatherType.Rainy)) {
            for (Tile tile : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getTiles()) {
                if (tile.getPlaceable() instanceof Plant plant) {
                    if (!plant.isInsideGreenhouse())
                        plant.wateringPlant();
                }
            }
        } else if (todayWeather.equals(WeatherType.Storm)) {
            int counter = 3;
            for (Tile tile : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getTiles()) {
                double rand = Math.random();
                if (rand <= 0.01) {
                    if (counter > 0) {
                        tile.lightningStrike();
                        counter--;
                    }
                }
                if (tile.getPlaceable() instanceof Plant plant) {
                    plant.wateringPlant();
                }
            }
        }
    }

    private void normalizeMaxEnergies() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            player.setEnergy(player.getMaxEnergy());
            player.setInteractionWithPartner(false);
            if (player.getIsbrokenUp() > 0) {
                player.setEnergy(player.getMaxEnergy() / 2);
                player.setIsbrokenUp(player.getIsbrokenUp() - 1);
            }
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

        //removing all crops that are not compatible with this season
        //handleIncompatiblePlants();
    }

    private void handleIncompatiblePlants() {
        for (PlayerMap playerMap : App.getCurrentGame().getGameMap().getPlayerMaps()) {
            for (Tile tile : playerMap.getTiles()) {
                if (tile.getPlaceable() instanceof Tree tree) {
                    if (tree.isInsideGreenhouse())
                        continue;
                    if (!tree.getType().getSeasons().contains(season))
                        tree.getTile().setPlaceable(null);
                } else if (tile.getPlaceable() instanceof Crop crop) {
                    if (crop.isInsideGreenhouse())
                        continue;
                    if (!crop.getType().getSeasons().contains(season))
                        crop.getTile().setPlaceable(null);
                }
            }
        }
    }

    public void changeDayOfTheWeek() {
        DaysOfTheWeek[] daysOfTheWeek = DaysOfTheWeek.values();
        int currentDayIndex = dayOfTheWeek.ordinal();
        dayOfTheWeek = daysOfTheWeek[(currentDayIndex + 1) % daysOfTheWeek.length];
    }

    public WeatherType getRandomWeather() {
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
        return season.ordinal() + 1;
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
