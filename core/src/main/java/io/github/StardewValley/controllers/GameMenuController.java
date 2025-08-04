package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.NPCS.Gift;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;
import io.github.StardewValley.shared.models.enums.CheatCodeCommands;
import io.github.StardewValley.shared.models.enums.Season;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.tools.*;
import io.github.StardewValley.views.GameMenu;
import io.github.StardewValley.views.MainMenu;

import java.util.*;

public class GameMenuController {
    private GameMenu view;

    public void setView(GameMenu view) {
        this.view = view;
        setupButtonListener();
    }

    private void setupButtonListener() {
//        view.getBackButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("Entering to MainMenu...");
//                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                    @Override
//                    public void run() {
//                        Main.getMain().getScreen().dispose();
//                        Main.getMain().setScreen(
//                            new MainMenu(
//                                new MainMenuController(),
//                                GameAssetManager.getGameAssetManager().getSkin()
//                            )
//                        );
//                    }
//                }, 2);
//            }
//        });
//        view.getAddUser().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                String username = view.getUserName().getText().trim();
//                if (username.isEmpty()) {
//                    view.setError("Please enter a username");
//                    return;
//                }
//                if (!(view.getUser1().getText().toString().equals("-") || view.getUser2().getLabel().getText().toString().equals("-")
//                    || view.getUser3().getLabel().getText().toString().equals("-") || view.getUser4().getLabel().getText().toString().equals("-"))) {
//                    view.setError("you can only add a maximum of 4 players to the game!");
//                    return;
//                }
//
//                if (username.equals(view.getUser1().getLabel().getText().toString()) ||
//                    username.equals(view.getUser2().getLabel().getText().toString()) ||
//                    username.equals(view.getUser3().getLabel().getText().toString()) ||
//                    username.equals(view.getUser4().getLabel().getText().toString())) {
//                    view.setError("you cannot add repetitive player!");
//                    return;
//                }
//                if (App.getUserWithUsername(username) == null) {
//                    view.setError("no user exists with this username");
//                    return;
//                }
//                if (App.getUserWithUsername(username).getActiveGame() != null) {
//                    view.setError("user with this username has an active game");
//                    return;
//                }
//                if (view.getUser2().getLabel().getText().toString().equals("-")) {
//                    view.getUser2().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//                if (view.getUser3().getLabel().getText().toString().equals("-")) {
//                    view.getUser3().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//                if (view.getUser4().getLabel().getText().toString().equals("-")) {
//                    view.getUser4().getLabel().setText(view.getUserName().getText().trim());
//                    return;
//                }
//
//            }
//        });
//        view.getStartGame().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//
//                String username1 = view.getUser1().getLabel().getText().toString().trim();
//                String username2 = view.getUser2().getLabel().getText().toString().trim();
//                String username3 = view.getUser3().getLabel().getText().toString().trim();
//                String username4 = view.getUser4().getLabel().getText().toString().trim();
//                User user1 = App.getUserWithUsername(username1);
//                User user2 = App.getUserWithUsername(username2);
//                User user3 = App.getUserWithUsername(username3);
//                User user4 = App.getUserWithUsername(username4);
//
//                if (username1.equals("-")) {
//                    view.setError("you must give at least 1 username");
//                    return;
//                }
//                if (username2.equals("-")) {
//                    if (App.getUserWithUsername("guest1") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest1"));
//                    }
//                    user2 = new User();
//                    user2.setUsername("guest1");
//                    App.getUsers().add(user2);
//                } else {
//                    user2 = App.getUserWithUsername(username2);
//                }
//                if (username3.equals("-")) {
//                    if (App.getUserWithUsername("guest2") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest2"));
//                    }
//                    user3 = new User();
//                    user3.setUsername("guest2");
//                    App.getUsers().add(user3);
//                } else {
//                    user3 = App.getUserWithUsername(username3);
//                }
//                if (username4.equals("-")) {
//                    if (App.getUserWithUsername("guest3") != null) {
//                        App.getUsers().remove(App.getUserWithUsername("guest3"));
//                    }
//                    user4 = new User();
//                    user4.setUsername("guest3");
//                    App.getUsers().add(user4);
//                } else {
//                    user4 = App.getUserWithUsername(username4);
//                }
//                Tile.getTiles().clear();
//
//                NPC.setFatherPlayer(null);
//                NPC.setFatherUser(null);
//                Game game = new Game(user2, user3, user4);
//                App.setCurrentGame(game);
//                App.getGames().add(game);
//                view.setError("new game created Successfully");
//
//                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                    @Override
//                    public void run() {
//                        Main.getMain().getScreen().dispose();
//                        Main.getMain().setScreen(new chooseMap(new ChooseMapController(), GameAssetManager.getGameAssetManager().getSkin()));
//                    }
//                }, 2);
//
//
//            }
//        });
//        view.getDeleteUser1().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                String username1 = view.getUser1().getLabel().getText().toString().trim();
//                view.setError("you can not delete the loggedIn user (you)");
//                return;
//            }
//        });
//        view.getDeleteUser2().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser2().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getDeleteUser3().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser3().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getDeleteUser4().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.setError("");
//                view.getUser4().getLabel().setText("-");
//                view.setError("delete user");
//                return;
//            }
//        });
//        view.getLoadGame().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                User user = App.getLoggedInUser();
//                Player currentPlayer = null;
//                if (user.getLastGame() == null) {
//                    view.setError("you have no game to load");
//                } else {
//                    Game game = user.getLastGame();
//                    for (Player player : game.getPlayers()) {
//                        if (!(player.getUser().getLastGame() != null && player.getUser().getLastGame().equals(game))) {
//                            view.setError("your friends have another active game");
//                            return;
//                        }
//                        if (player.getUser().equals(user)) {
//                            currentPlayer = player;
//                        }
//                    }
//                    App.setCurrentGame(game);
//                    App.getCurrentGame().setCurrentPlayingPlayer(currentPlayer);
//                    App.getCurrentGame().setCreator(currentPlayer);
//                    for (PlayerMap pm : game.getGameMap().getPlayerMaps()) {
//                        for (Tile tile : pm.getTiles()) {
//                            Tile.getTiles().add(tile);
//                        }
//                    }
//                    NPC.setFatherPlayer(game.getPlayers().get(4));
//                    NPC.setFatherUser(game.getPlayers().get(4).getUser());
//
//                    com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
//                        @Override
//                        public void run() {
//                            view.setError("you are in Game now");
//                            Main.getMain().getScreen().dispose();
//                            Main.getMain().setScreen(
//                                new GameView(new GameController(App.getCurrentGame()),new GameMenuController())
//                            );
//                        }
//                    }, 2);
//
//                }
//            }
//        });

    }

    public Result exitGame() {
        try {
            if (!GameClient.gameStateApiClient.exitGame())
                return new Result(false, "Only the game creator can exit the game.");
            else{
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                return new Result(true, "Exit the game.");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    public Result getTime() {
        return new Result(true, App.getCurrentGame().getDate().getHour() + " : " +
            App.getCurrentGame().getDate().getMinute());
    }


    private int normalize(int delta) {
        if (delta > 0) return 1;
        if (delta < 0) return -1;
        return 0;
    }

    public List<Tile> aStar(int startX, int startY, int endX, int endY, Player player) {
        int[][] directions = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        Tile start = Tile.getTile(startX, startY);
        Tile end = Tile.getTile(endX, endY);

        if (start == null || end == null || !start.isWalkAble() || !end.isWalkAble()) {
            return null;
        }

        Map<Tile, Tile> parent = new HashMap<>();
        Map<Tile, Integer> gScore = new HashMap<>();
        PriorityQueue<Tile> openSet = new PriorityQueue<>(Comparator.comparingInt(tile -> gScore.get(tile) + heuristic(tile, end)));
        boolean[][] visited = new boolean[200][200];

        gScore.put(start, 0);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Tile current = openSet.poll();

            if (current.getX() == endX && current.getY() == endY) {
                return buildPath(parent, start, end);
            }

            visited[current.getX()][current.getY()] = true;

            for (int[] dir : directions) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];

                if (newX <= 0 || newY <= 0 || newX > 200 || newY > 200) continue;
                Tile neighbor = Tile.getTile(newX, newY);

                if (neighbor == null || visited[newX][newY] || !isValid(neighbor, player)) continue;

                int tentativeG = gScore.get(current) + 1;

                if (!gScore.containsKey(neighbor) || tentativeG < gScore.get(neighbor)) {
                    gScore.put(neighbor, tentativeG);
                    parent.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null;
    }

    private boolean isValid(Tile tile, Player player) {
        return tile != null && tile.isWalkAble() &&
            (tile.getOwner().equals(player) ||
                tile.getOwner().equals(player.getPartner()) ||
                tile.getOwner().equals(NPC.getFatherPlayer()));
    }

    private List<Tile> buildPath(Map<Tile, Tile> parent, Tile start, Tile end) {
        List<Tile> path = new LinkedList<>();
        Tile current = end;
        while (current != null && !current.equals(start)) {
            path.add(0, current);
            current = parent.get(current);
        }
        if (current != null) path.add(0, start);
        return path;
    }

    private int heuristic(Tile a, Tile b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());
        return dx + dy;
    }

    public String friendship(Player player) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        String result = "";
        result += "your friendship amount with " + player.getUser().getUsername() + " : " +
            currentPlayer.getFriendShips().get(player) + "\n" + "your friendship level : "
            + String.valueOf((int) Math.floor(currentPlayer.getFriendShips().get(player) / 100)) + "\n";
        return result;
    }


    public boolean sideBySide(Player currentPlayer, Player player) {
        int x = currentPlayer.getTileX();
        int y = currentPlayer.getTileY();
        int x1 = player.getTileX();
        int y1 = player.getTileY();
        if ((x == x1 && y == y1)
            || (x == x1 + 1 && y == y1)
            || (x == x1 - 1 && y == y1)
            || (x == x1 && y == y1 + 1)
            || (x == x1 - 1 && y == y1 + 1)
            || (x == x1 + 1 && y == y1 + 1)
            || (x == x1 && y == y1 - 1)
            || (x == x1 + 1 && y == y1 - 1)
            || (x == x1 - 1 && y == y1 - 1)) {
            return true;
        } else return false;
    }

    public Result talk(String username, String massage) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getTalk().get(player) != null) {
                    if (sideBySide(currentPlayer, player)) {
                        currentPlayer.getTalk().get(player).addTalk("you" + " : " + massage + "\n");
                        player.getTalk().get(currentPlayer).addTalk(currentPlayer.getUser().getUsername()
                            + " : " + massage + "\n");
                        player.addFriendShips(currentPlayer, player.getFriendShips().get(currentPlayer) + 20);
                        currentPlayer.addFriendShips(player, currentPlayer.getFriendShips().get(player) + 20);
                        message message = new message(currentPlayer, massage);
                        player.addMessage(message);
                        if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                            player.setEnergy(player.getEnergy() + 50);
                            currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                        }
                        return new Result(true, "your message sent to " + player.getUser().getUsername());
                    } else {
                        return new Result(false, "you can't talk from this distance");
                    }
                } else {
                    return new Result(false, "there isn't player in this game with this username");
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public String talkHistory(String username) {
        Player currentPlayer = null;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getTalk().get(player) != null) {
                    return currentPlayer.getTalk().get(player).getTalk();
                }
            }
        }
        return "";
    }

    public Result gift(String username, String item, String amount) {
        int Amount;
        try {
            Amount = Integer.parseInt(amount);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
//        if (App.getUserWithUsername(username) == null) {
//            return new Result(false, "there isn't player in this game with this username");
//        }

        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().get(player) != null) {
                    if (sideBySide(currentPlayer, player)) {
                        if (currentPlayer.getFriendShips().get(player) < 100) {
                            return new Result(false, "your level is less than 1");
                        } else {
                            if (currentPlayer.getBackPack().getInventorySize(item) < Amount) {
                                return new Result(false, "insufficient inventory");
                            } else {
                                for (int i = 0; i < Amount; i++) {
                                    BackPackable backPackable = currentPlayer.getBackPack().useItem(item);
                                    player.getBackPack().addItemToInventory(backPackable);
                                }
                                Gift gift = new Gift(currentPlayer, player, item, Amount);
                                currentPlayer.getGifts().get(player).add(gift);
                                player.getGifts().get(currentPlayer).add(gift);
                                if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                                    player.setEnergy(player.getEnergy() + 50);
                                    currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                                }
                                message message = new message(currentPlayer, player.getUser().getUsername() + ", you have received a gift from " + currentPlayer.getUser().getUsername()
                                    + "\n" + "your gift : " + item + "\n" + "your gift amount : " + amount + "\n"
                                    + "please rate this gift between one and five Whenever you have time ");
                                player.addMessage(message);
                                return new Result(true, "your gift was received by " + player.getUser().getUsername());
                            }
                        }
                    } else {
                        return new Result(false, "you can't gift from this distance");
                    }
                }
            }
        }
        return new Result(false, "there isn't player in this game with this username");
    }

    public Result giftRate(String giftNumber, String rate) {
        if (CheatCodeCommands.Int.getMatcher(rate) == null || CheatCodeCommands.Int.getMatcher(giftNumber) == null) {
            return new Result(true, "your rate or giftNumber is not valid");
        } else if (Integer.parseInt(rate) > 5 || Integer.parseInt(rate) < 1) {
            return new Result(true, "your rate is not valid");
        } else {
            //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
            Player currentPlayer = null;
            for (Player player : currentPlayer.getGifts().keySet()) {
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    if (gift.getGiftNumber() == Integer.parseInt(giftNumber) && gift.getPlayerWhoGetGift() == currentPlayer) {
                        if (!gift.getRateGiven()) {
                            gift.setRateGiven(true);
                            currentPlayer.getFriendShips().put(player, currentPlayer.getFriendShips().get(player) + (Integer.parseInt(rate) - 3) * 30 + 15);
                            player.getFriendShips().put(currentPlayer, currentPlayer.getFriendShips().get(player));
                            return new Result(true, "your rate was recorded as " + Integer.parseInt(rate));
                        } else {
                            return new Result(true, "you rate to this gift previously");
                        }
                    }
                }
            }


        }
        return new Result(false, "you have not received a gift with this giftNumber");
    }

    public String giftHistory(String username) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        if (username.equals(currentPlayer.getUser().getUsername())) {
            return "you can't gift to your self.";
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                String result = "";
                result += player.getUser().getUsername() + "\n";
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    result += "whoGetGift : " + gift.getPlayerWhoGetGift().getUser().getUsername() + "\n" + gift.getItem() + " : (amount:)" + gift.getAmount() + " ---> (gift number:)" + gift.getGiftNumber() + "\n";
                }
                return result;
            }
        }
        return "this username there is not in this game";

    }

    public Result hug(String username) {
        //TODO
        //        if (App.getUserWithUsername(username) == null) {
//            return new Result(false, "there isn't player in this game with this username");
//        }
        Player currentPlayer = null;
        if (currentPlayer.getUser().getUsername().equals(username)) {
            return new Result(false, "you can't hug yourself");
        }
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().get(player) >= 200) {
                    if (sideBySide(player, currentPlayer)) {
                        currentPlayer.getFriendShips().put(
                            player, (currentPlayer.getFriendShips().get(player) + 60));
                        player.getFriendShips().put(currentPlayer,
                            currentPlayer.getFriendShips().get(player));
                        if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                            player.setEnergy(player.getEnergy() + 50);
                            currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                        }
                        return new Result(true, "you hug your friend ^^");
                    } else {
                        return new Result(false, "you can't hug your friend from this distance");
                    }
                } else {
                    return new Result(false, "your level less than 2");
                }
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result flower(String username) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().containsKey(player)) {
                    if (currentPlayer.getFriendShips().get(player) < 200) {
                        return new Result(false, "your friendship level is less than 2");
                    } else {
                        if (currentPlayer.getBackPack().getInventorySize("FLOWER") > 0) {
                            if (currentPlayer.getFriendShips().get(player) < 300) {
                                currentPlayer.getFriendShips().put(player, 300);
                                player.getFriendShips().put(currentPlayer, 300);
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                return new Result(true, "Flower were presented to " + player.getUser().getUsername());
                            } else {
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                                    player.setEnergy(player.getEnergy() + 50);
                                    currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                                }
                                return new Result(true, "Flower were presented to " + player.getUser().getUsername());
                            }
                        } else {
                            return new Result(false, "insufficient inventory");
                        }

                    }
                }
                return new Result(false, "you can't give flower to your self");
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result askMarriage(String username, String ring) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (sideBySide(player, currentPlayer)) {
                    if (currentPlayer.getFriendShips().containsKey(player)) {
                        if (currentPlayer.getFriendShips().get(player) < 300) {
                            return new Result(false, "your friendship level is less than 3");
                        } else {
                            if (currentPlayer.getUser().getGender().equals(Gender.Female)) {
                                return new Result(false, "you can't ask marriage");
                            } else if (player.getUser().getGender() == currentPlayer.getUser().getGender()) {
                                return new Result(false, "khejalat bekesh dadash (abjy)");
                            } else if (currentPlayer.getBackPack().getInventorySize(ring) < 1) {
                                return new Result(false, "you haven't Ring for ask marriage");
                            } else {
                                message message = new message(currentPlayer, "ask for marriage with "
                                    + currentPlayer.getUser().getUsername());
                                player.getMessage().add(message);
                                return new Result(true, "your marriage request has been sent");
                            }
                        }
                    } else {
                        return new Result(false, "you can't ask marriage to your self");
                    }
                } else {
                    return new Result(false, "you can't ask marriage from this distance");
                }
            }
        }
        return new Result(false, "this username does not exist in this game");
    }

    public Result respond(String accept, String username) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        for (message m : currentPlayer.getMessage()) {
            if (m.getMessage().startsWith("ask for marriage")) {
                for (Player player : App.getCurrentGame().getPlayers()) {
                    if (player.getUser().getUsername().equals(username)) {
                        if (m.getSender().equals(player)) {
                            if (accept.trim().equals("accept")) {
                                BackPackable b = player.getBackPack().useItem("Ring");
                                currentPlayer.getBackPack().addItemToInventory(b);
                                ArrayList<message> temp = new ArrayList<message>();
                                for (message message : player.getMessage()) {
                                    if (m.getMessage().startsWith("ask for marriage")) {
                                        temp.add(message);
                                    }
                                }
                                for (message message : temp) {
                                    player.getMessage().remove(message);
                                }
                                if (player.getFriendShips().get(currentPlayer) < 400) {
                                    player.getFriendShips().put(currentPlayer, 400);
                                    currentPlayer.getFriendShips().put(player, 400);
                                }
                                player.getBackPack().addCoin(currentPlayer.getBackPack().getCoin());
                                currentPlayer.getBackPack().addCoin(player.getBackPack().getCoin());
                                player.setPartner(currentPlayer);
                                currentPlayer.setPartner(player);
                                message m1 = new message(currentPlayer
                                    , "oh my God, I was taken by surprise. I thought about it. I accept");
                                player.addMessage(m1);
                                return new Result(true, "Congratulations, you got married");
                            } else {
                                player.setIsbrokenUp(7);
                                player.getFriendShips().put(currentPlayer, 0);
                                currentPlayer.getFriendShips().put(player, 0);
                                ArrayList<message> temp = new ArrayList<message>();
                                for (message message : player.getMessage()) {
                                    if (m.getMessage().startsWith("ask for marriage with ")) {
                                        temp.add(message);
                                    }
                                }
                                for (message message : temp) {
                                    player.getMessage().remove(message);
                                }
                                message m1 = new message(currentPlayer, "i do not intend to marry");
                                player.addMessage(m1);
                                return new Result(true, "request was rejected");
                            }
                        } else {
                            return new Result(false, "this username did not request marriage to you");
                        }
                    }
                }
                return new Result(false, "this username does not exist in this game");
            }
        }

        return new Result(false, "this username did not request marriage to you");
    }

    public String tradeHistory() {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        String result = "on going trades : \n";
        for (Trade trade : currentPlayer.getTrades()) {
            if (trade.getTradeType().equals("byMoney")) {
                result += (trade.getType() + " : " + "\n"
                    + "tradeId : " + trade.getId() + "\n"
                    + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                    + "tradeType : " + "byMoney" + "\n"
                    + "tradeItem : " + trade.getItem() + "\n"
                    + "amount : " + trade.getAmount() + "\n"
                    + "price : " + trade.getPrice() + "\n"
                    + "--------------------------------------------------\n");
            } else {
                result += (trade.getType() + " : " + "\n"
                    + "tradeId : " + trade.getId() + "\n"
                    + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                    + "tradeType : " + "byItem" + "\n"
                    + "item : " + trade.getItem() + "\n"
                    + "amount : " + trade.getAmount() + "\n"
                    + "targetItem : " + trade.getTargetItem() + "\n"
                    + "targetAmount : " + trade.getTargetAmount() + "\n"
                    + "--------------------------------------------------\n");
            }
        }
        result += "\nprevious trades (accepted): \n";
        for (Trade trade : currentPlayer.getTradeHistory()) {
            if (trade.getTradeType().equals("byMoney")) {
                result += (trade.getType() + " : " + "\n"
                    + "tradeId : " + trade.getId() + "\n"
                    + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                    + "tradeType : " + "byMoney" + "\n"
                    + "tradeItem : " + trade.getItem() + "\n"
                    + "amount : " + trade.getAmount() + "\n"
                    + "price : " + trade.getPrice() + "\n"
                    + "--------------------------------------------------\n");
            } else {
                result += (trade.getType() + " : " + "\n"
                    + "tradeId : " + trade.getId() + "\n"
                    + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                    + "tradeType : " + "byItem" + "\n"
                    + "item : " + trade.getItem() + "\n"
                    + "amount : " + trade.getAmount() + "\n"
                    + "targetItem : " + trade.getTargetItem() + "\n"
                    + "targetAmount : " + trade.getTargetAmount() + "\n"
                    + "--------------------------------------------------\n");
            }
        }
        return result;
    }

    public String tradeList() {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        if (currentPlayer.getTrades() == null) {
            return "there are nothing trade for you";
        } else {
            String result = "";
            for (Trade trade : currentPlayer.getTrades()) {
                if (!trade.getSender().equals(currentPlayer)) {
                    if (trade.getTradeType().equals("byMoney")) {
                        result += (trade.getType() + " : " + "\n"
                            + "tradeId : " + trade.getId() + "\n"
                            + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                            + "tradeType : " + "byMoney" + "\n"
                            + "tradeItem : " + trade.getItem() + "\n"
                            + "amount : " + trade.getAmount() + "\n"
                            + "price : " + trade.getPrice() + "\n"
                            + "--------------------------------------------------\n");
                    } else {
                        result += (trade.getType() + " : " + "\n"
                            + "tradeId : " + trade.getId() + "\n"
                            + "Sender : " + trade.getSender().getUser().getUsername() + "\n"
                            + "tradeType : " + "byItem" + "\n"
                            + "item : " + trade.getItem() + "\n"
                            + "amount : " + trade.getAmount() + "\n"
                            + "targetItem : " + trade.getTargetItem() + "\n"
                            + "targetAmount : " + trade.getTargetAmount() + "\n"
                            + "--------------------------------------------------\n");
                    }
                }
            }
            if (result.isEmpty()) {
                return "there are nothing trade for you";
            } else {
                return result;
            }
        }
    }

    public boolean sideBySide(Player currentPlayer, NPC npc) {
        int x = currentPlayer.getTileX();
        int y = currentPlayer.getTileY();
        int x1 = npc.getTile_x();
        int y1 = npc.getTile_y();
        if ((x == x1 && y == y1)
            || (x == x1 + 1 && y == y1)
            || (x == x1 - 1 && y == y1)
            || (x == x1 && y == y1 + 1)
            || (x == x1 - 1 && y == y1 + 1)
            || (x == x1 + 1 && y == y1 + 1)
            || (x == x1 && y == y1 - 1)
            || (x == x1 + 1 && y == y1 - 1)
            || (x == x1 - 1 && y == y1 - 1)) {
            return true;
        } else return false;
    }

    public Result meetNPC(String npcName, Scanner scanner) {
        if (!(npcName.equals("Abigail")
            || npcName.equals("Harvey")
            || npcName.equals("Lia")
            || npcName.equals("Robin")
            || npcName.equals("Sebastian"))) {
            return new Result(false, "there isn't this NPC");
        } else {
            //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
            Player currentPlayer = null;
            NPC npc = null;
            for (NPC n : App.getCurrentGame().getNPCs()) {
                if (n.getName().equals(npcName)) {
                    npc = n;
                }
            }
            if (npc != null) {
                if (sideBySide(currentPlayer, npc)) {
                    String input;
                    do {
                        input = scanner.nextLine();
                        if (npc.getDialogue().get(input) != null) {
                            if (App.getCurrentGame().getDate().getSeason().equals(Season.Spring)
                                || App.getCurrentGame().getDate().getSeason().equals(Season.Summer)) {
                                System.out.println(npc.getDialogue().get(input));
                            } else {
                                System.out.println(npc.getDialogue2().get(input));
                            }
                        } else {
                            System.out.println("Please enter a valid dialogue");
                        }
                    } while (!input.equals("goodbye"));
                    if (!currentPlayer.getTalkedNPCToday().get(npc)) {
                        currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 20));
                        currentPlayer.getTalkedNPCToday().put(npc, true);
                        return new Result(true, "your friendship level with " + npcName + " increased by 20 points.");
                    } else {
                        return new Result(true, "you talked with " + npcName);
                    }

                } else {
                    return new Result(false, "you can not talk NPC from this distance");
                }
            } else {
                return new Result(false, "there isn't this NPC");
            }

        }
    }

    public Result giftNPC(NPC npc, String item,String amount) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        if (currentPlayer.getBackPack().getInventorySize(item) == 0) {
            return new Result(false, "your inventory is empty");
        } else {
            int Amount;
            try {
                Amount = Integer.parseInt(amount);
            }catch (NumberFormatException e) {
                return new Result(false, "amount is not a number");
            }
            for (int i = 0; i < Amount ; i++) {
                currentPlayer.getBackPack().useItem(item);
            }
            if (!currentPlayer.getGiftNPCToday().get(npc)) {
                if (npc.getFavorites().contains(item)) {
                    currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 200));
                    currentPlayer.getGiftNPCToday().put(npc, true);
                    return new Result(true, "your beautiful gift was received by  " + npc.getName());
                } else {
                    currentPlayer.getGiftNPCToday().put(npc, true);
                    currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 50));
                    return new Result(true, "your gift was received by  " + npc.getName());
                }
            } else {
                return new Result(true, "your gift was received by  " + npc.getName());
            }

        }
    }

    public String friendshipNPCList(NPC npc) {
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        String result = "";
        result += ("friendship score with " + npc.getName()
            + " : " + currentPlayer.getFriendShipsWithNPCs().get(npc)
            + "\n" + "friendship level with " + npc.getName() + " : "
            + currentPlayer.getFriendShipsWithNPCs().get(npc) / 200 + "\n" + "-------------" + "\n");
        return result;
    }

    public Result questFinish(String index) {
        int i = Integer.parseInt(index);
        if (i < 1 || i > 3) {
            return new Result(false, "invalid index");
        }
        //Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
        Player currentPlayer = null;
        NPC npc = null;
        for (NPC npc2 : App.getCurrentGame().getNPCs()) {
            if (sideBySide(currentPlayer, npc2)) {
                npc = npc2;
                break;
            }
        }
        if (npc == null) {
            return new Result(false, "you must be next to the NPC to complete the mission");
        } else {
            Quest quest = npc.getRequests().get(i - 1);
            if (quest.isCompleted()) {
                return new Result(false, "quest already completed");
            } else {
                if (quest.getLevel() <= currentPlayer.getFriendShipsWithNPCs().get(npc) / 200
                    && quest.isActive()) {
                    String item = quest.getItem();
                    int amount = quest.getAmount();
                    if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
                        for (int j = 0; j < amount; j++) {
                            currentPlayer.getBackPack().useItem(item);
                        }
                        if (2 < currentPlayer.getFriendShipsWithNPCs().get(npc) / 200) {
                            npc.giveReward(currentPlayer, Integer.parseInt(index) - 1);
                            npc.giveReward(currentPlayer, Integer.parseInt(index) - 1);
                        } else {
                            npc.giveReward(currentPlayer, Integer.parseInt(index) - 1);
                        }
                        quest.setCompleted(true);
                        return new Result(true, "the mission was successfully completed.\n" +
                            "your reward has been added to your backpack");
                    } else {
                        return new Result(false, "you can't finish quest because you do not have a the required item");
                    }
                } else {
                    return new Result(false, "you can't finish quest because you do not have a the required level");
                }
            }
        }
    }

}
