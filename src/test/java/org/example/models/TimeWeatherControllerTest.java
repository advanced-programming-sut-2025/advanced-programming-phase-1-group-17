package org.example.models;

import org.example.controllers.GameMenuController;
import org.example.models.enums.Season;
import org.example.models.enums.WeatherType;
import org.example.models.foraging.Mineral;
import org.example.models.foraging.MineralType;
import org.example.models.map.GreenHouse;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;
import org.example.models.plant.Crop;
import org.example.models.plant.Tree;
import org.example.models.tools.BackPack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TimeWeatherControllerTest {
    GameMenuController controller;
    Game mockGame;
    TimeAndDate mockTimeAndDate;

    @BeforeEach
    void setUp() {
        controller = new GameMenuController();
        mockGame = mock(Game.class);
        mockTimeAndDate = mock(TimeAndDate.class);
    }

    @Test
    void testGetTime() {
        when(mockTimeAndDate.getHour()).thenReturn(14);
        when(mockTimeAndDate.getMinute()).thenReturn(30);
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);

        try (MockedStatic<App> app = Mockito.mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);
            Result result = controller.getTime();
            assertTrue(result.isSuccessful());
            assertEquals("14 : 30", result.getMessage());
        }
    }

    @Test
    void testGetDate() {
        when(mockTimeAndDate.getDay()).thenReturn(12);
        when(mockTimeAndDate.getMonth()).thenReturn(4);
        when(mockTimeAndDate.getYear()).thenReturn(2025);
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);
            Result result = controller.getDate();
            assertEquals("12/4/2025", result.getMessage());
        }
    }

    @Test
    void testChangeTime() {
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeTime("3");
            verify(mockTimeAndDate, times(3)).increaseHour();
            assertTrue(result.isSuccessful());
            assertEquals("added successfully", result.getMessage());
        }
    }

    @Test
    void testChangeTimeGoesToNextDay() {
        TimeAndDate realTime = new TimeAndDate();
        realTime.setHour(22); // Start at 10PM
        Game mockGame = mock(Game.class);
        when(mockGame.getDate()).thenReturn(realTime);

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeTime("1"); // Advance 1 hour, triggers next day

            assertEquals(9, realTime.getHour()); // Day resets to 9AM
            assertEquals(2, realTime.getDay()); // Day increased
            assertTrue(result.isSuccessful());
        }
    }


    @Test
    void testChangeTimeTriggersNextSeason() {
        TimeAndDate realTime = new TimeAndDate();
        realTime.setHour(22);
        realTime.setDay(28); // Last day of season

        Game mockGame = mock(Game.class);
        when(mockGame.getDate()).thenReturn(realTime);
        when(mockGame.getNPCs()).thenReturn(new ArrayList<>()); // prevent NPE

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeTime("1"); // Go to next day

            assertEquals(9, realTime.getHour());
            assertEquals(1, realTime.getDay()); // Day resets to 1
            assertEquals(Season.Summer, realTime.getSeason()); // Example: if it was Spring

            assertTrue(result.isSuccessful());
        }
    }


    @Test
    void testChangeDate() {
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeDate("2");
            verify(mockTimeAndDate, times(2)).goToNextDay();
            assertTrue(result.isSuccessful());
            assertEquals("2 days added successfully", result.getMessage());
        }
    }


    @Test
    void testChangeDateTriggersNextSeason() {
        TimeAndDate time = new TimeAndDate();
        time.setDay(28);
        time.setHour(22); // to trigger daily reset
        time.setSeason(Season.Spring);

        Game mockGame = mock(Game.class);
        when(mockGame.getDate()).thenReturn(time);
        when(mockGame.getPlayers()).thenReturn(new ArrayList<>());
        when(mockGame.getNPCs()).thenReturn(new ArrayList<>());

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeDate("1");

            assertEquals(Season.Summer, time.getSeason());
            assertEquals(1, time.getDay());
            assertTrue(result.isSuccessful());
        }
    }


    @Test
    void testLightningStrike_TreeOutsideGreenhouse_ReplacedWithCoal() {
        Tile tile = new Tile(10, 10, new Player(new User(), false));
        Tree tree = mock(Tree.class);
        when(tree.isInsideGreenhouse()).thenReturn(false);
        tile.setPlaceable(tree);

        tile.lightningStrike();

        assertInstanceOf(Mineral.class, tile.getPlaceable());
        assertEquals(MineralType.Coal, ((Mineral) tile.getPlaceable()).getType());
    }


    @Test
    void testLightningStrike_TreeInsideGreenhouse_Untouched() {
        Tile tile = new Tile(10, 10, new Player(new User(), false));
        Tree tree = mock(Tree.class);
        when(tree.isInsideGreenhouse()).thenReturn(true);
        tile.setPlaceable(tree);

        tile.lightningStrike();

        assertSame(tree, tile.getPlaceable()); // Still the tree
    }


    @Test
    void testLightningStrike_CropOutsideGreenhouse_Removed() {
        Tile tile = new Tile(10, 10, new Player(new User(), false));
        Crop crop = mock(Crop.class);
        when(crop.isInsideGreenhouse()).thenReturn(false);
        when(crop.getTile()).thenReturn(tile);
        tile.setPlaceable(crop);

        tile.lightningStrike();

        assertNull(tile.getPlaceable());
    }


    @Test
    void testLightningStrike_CropInsideGreenhouse_Untouched() {
        Tile tile = new Tile(10, 10, new Player(new User(), false));
        Crop crop = mock(Crop.class);
        when(crop.isInsideGreenhouse()).thenReturn(true);
        tile.setPlaceable(crop);

        tile.lightningStrike();

        assertSame(crop, tile.getPlaceable());
    }


    @Test
    void testGetWeather() {
        when(mockTimeAndDate.getTodayWeatherType()).thenReturn(WeatherType.Sunny);
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.getWeather();
            assertEquals("Sunny", result.getMessage());
        }
    }

    @Test
    void testWeatherForecast() {
        when(mockTimeAndDate.getTomorrowWeather()).thenReturn(WeatherType.Storm);
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.weatherForeCast();
            assertEquals("Storm", result.getMessage());
        }
    }

    @Test
    void testChangeWeatherWithValidInput() {
        when(mockGame.getDate()).thenReturn(mockTimeAndDate);
        when(mockTimeAndDate.getTomorrowWeather()).thenReturn(WeatherType.Rainy);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeWeather("Rainy");
            verify(mockTimeAndDate).setTomorrowWeather(WeatherType.Rainy);
            assertTrue(result.getMessage().contains("tomorrow weather changed to Rainy successfully"));
        }
    }

    @Test
    void testChangeWeatherWithInvalidInput() {
        TimeAndDate realTime = new TimeAndDate();
        when(mockGame.getDate()).thenReturn(realTime);
        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.changeWeather("Foggy");
            assertFalse(result.isSuccessful());
            assertTrue(result.getMessage().contains("valid options"));
        }
    }

    @Test
    void testCheatThorValidTile() {
        Tile tile = mock(Tile.class);
        when(mockGame.getTileByIndex(1, 1)).thenReturn(tile);

        try (MockedStatic<App> app = Mockito.mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);
            Result result = controller.cheatThor(1, 1);
            verify(tile).lightningStrike();
            assertTrue(result.isSuccessful());
        }
    }

    @Test
    void testCheatThorInvalidTile() {
        when(mockGame.getTileByIndex(5, 5)).thenReturn(null);

        try (MockedStatic<App> app = Mockito.mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);
            Result result = controller.cheatThor(5, 5);
            assertFalse(result.isSuccessful());
            assertEquals("tile not found", result.getMessage());
        }
    }


    @Test
    void testBuildGreenHouse_NotEnoughCoins() {
        Player mockPlayer = mock(Player.class);
        BackPack mockBackPack = mock(BackPack.class);

        when(mockPlayer.getBackPack()).thenReturn(mockBackPack);
        when(mockBackPack.getCoin()).thenReturn(500.0); // Less than 1000

        when(mockGame.getCurrentPlayingPlayer()).thenReturn(mockPlayer);

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.buildGreenHouse();

            assertFalse(result.isSuccessful());
            assertTrue(result.getMessage().contains("not enough"));
        }
    }


    @Test
    void testBuildGreenHouse_NotEnoughWood() {
        Player mockPlayer = mock(Player.class);
        BackPack mockBackPack = mock(BackPack.class);

        when(mockPlayer.getBackPack()).thenReturn(mockBackPack);
        when(mockBackPack.getCoin()).thenReturn(1500.0); // Enough coin
        when(mockBackPack.getInventorySize("Wood")).thenReturn(300); // Not enough wood

        when(mockGame.getCurrentPlayingPlayer()).thenReturn(mockPlayer);

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.buildGreenHouse();

            assertFalse(result.isSuccessful());
            assertTrue(result.getMessage().contains("not enough wood"));
        }
    }


    @Test
    void testBuildGreenHouse_Success() {
        Player mockPlayer = mock(Player.class);
        BackPack mockBackPack = mock(BackPack.class);
        PlayerMap mockMap = mock(PlayerMap.class);
        GreenHouse mockGreenHouse = mock(GreenHouse.class);

        when(mockPlayer.getBackPack()).thenReturn(mockBackPack);
        when(mockPlayer.getPlayerMap()).thenReturn(mockMap);
        when(mockMap.getGreenHouse()).thenReturn(mockGreenHouse);

        when(mockBackPack.getCoin()).thenReturn(1200.0);
        when(mockBackPack.getInventorySize("Wood")).thenReturn(600);

        when(mockGame.getCurrentPlayingPlayer()).thenReturn(mockPlayer);

        try (MockedStatic<App> app = mockStatic(App.class)) {
            app.when(App::getCurrentGame).thenReturn(mockGame);

            Result result = controller.buildGreenHouse();

            verify(mockBackPack).addCoin(-1000);
            verify(mockBackPack, times(500)).useItem(NormalItemType.Wood);
            verify(mockGreenHouse).setActive(true);

            assertTrue(result.isSuccessful());
            assertEquals("GreenHouse created Successfully", result.getMessage());
        }
    }
}

