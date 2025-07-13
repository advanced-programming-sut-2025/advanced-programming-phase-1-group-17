package org.example.controllers;

import org.example.models.*;
import org.example.models.NPCS.NPC;
import org.example.models.enums.MainMenuCommands;
import org.example.models.enums.Menu;
import org.example.models.map.GreenHouse;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;

public class MainMenuController {
    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        App.setLoggedInUser(null);
        return new Result(true, "you are logged out");
    }
    public Result changeMenu(String input) {
        String menu = MainMenuCommands.changeMenu.getMatcher(input).group("menu").trim();
        if (menu.equals("game menu")) {
            App.setCurrentMenu(Menu.GameMenu);
            return new Result(true,"successfully changeMenu. you are now in gameMenu");
        }
        else if (menu.equals("profile menu")) {
            App.setCurrentMenu(Menu.ProfileMenu);
            return new Result(true,"successfully changeMenu. you are now in profileMenu");
        }
        else if (menu.equals("avatar menu")) {
            App.setCurrentMenu(Menu.AvatarMenu);
            return new Result(true,"successfully changeMenu. you are now in avatarMenu");
        }
        else{
            return new Result(true,"invalid menu");
        }
    }
    public Result loadGame() {
        User user = App.getLoggedInUser();
        Player currentPlayer = null;
        if (user.getLastGame() == null) {
            return new Result(false, "you have no game to load");
        }else {
            Game game = user.getLastGame();
            for (Player player : game.getPlayers()) {
                if (!(player.getUser().getLastGame() != null && player.getUser().getLastGame().equals(game)))
                    return new Result(false, "your friends have another active game");
                if(player.getUser().equals(user)) {
                    currentPlayer = player;
                }
            }
            App.setCurrentGame(game);
            App.getCurrentGame().setCurrentPlayingPlayer(currentPlayer);
            App.getCurrentGame().setCreator(currentPlayer);
            for (PlayerMap pm : game.getGameMap().getPlayerMaps()) {
                for (Tile tile : pm.getTiles()) {
                    Tile.getTiles().add(tile);
                }
            }
            NPC.setFatherPlayer(game.getPlayers().get(4));
            NPC.setFatherUser(game.getPlayers().get(4).getUser());
            GreenHouse.setGreenHouse(App.getCurrentGame().getGreenHouses());
            App.setCurrentMenu(Menu.GameMenu);
            return new Result(false, "you are in GameMenu now");
        }
    }
}
