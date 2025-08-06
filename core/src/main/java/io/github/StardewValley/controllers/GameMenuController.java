package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.Store;
import io.github.StardewValley.shared.models.market.StoreType;
import io.github.StardewValley.shared.models.tools.*;
import io.github.StardewValley.views.GameMenu;
import io.github.StardewValley.views.MainMenu;

import java.util.*;
import java.util.regex.Matcher;

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
//    public Result walk(int x, int y, Scanner scanner) {
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

    public String friendship(String player) {
        return GameClient.getGameStateApiClient().friendship(player);
    }

    public Result talk(String username, String massage) {
        return GameClient.gameStateApiClient.talk(username, massage);
    }

    public String talkHistory(String username) {
        return GameClient.getGameStateApiClient().talkHistory(username);
    }

    public Result gift(String username, String item, String amount) {
        return GameClient.gameStateApiClient.gift(username, item, amount);
    }

    public Result giftList() {
        return GameClient.gameStateApiClient.giftList();
    }

    public Result giftRate(String giftNumber, String rate) {
        return GameClient.gameStateApiClient.giftRate(giftNumber, rate);
    }

    public String giftHistory(String username) {
        return GameClient.getGameStateApiClient().giftHistory(username);
    }

    public Result hug(String username) {
        return GameClient.gameStateApiClient.hug(username);
    }

    public Result flower(String username) {
        return GameClient.gameStateApiClient.flower(username);
    }

    public Result askMarriage(String username, String ring) {
        return GameClient.gameStateApiClient.askMarriage(username, ring);
    }

    public Result respond(String accept, String username) {
        return GameClient.gameStateApiClient.respond(accept, username);
    }



//    public Result startTrade() {
//        String result = "";
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        for (message m : currentPlayer.getMessage()) {
//            if (m.getMessage().startsWith("you have a trade")) {
//                result += (m.getMessage() + "\n");
//            }
//        }
//        return new Result(false, "you are now in trade menu \nlist of players : \n"
//            + App.getCurrentGame().getPlayers().get(1).getUser().getUsername() + "\n"
//            + App.getCurrentGame().getPlayers().get(2).getUser().getUsername() + "\n"
//            + App.getCurrentGame().getPlayers().get(3).getUser().getUsername() + "\n"
//            + App.getCurrentGame().getPlayers().get(0).getUser().getUsername() +
//            "\nnew trade request or offer : \n"
//            + result);
//    }
//
//    public Result tradeByMoney(Matcher matcher, String type, int enable) {
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        if (type.trim().equals("offer")) {
//            for (Player player : App.getCurrentGame().getPlayers()) {
//                if (player.getUser().getUsername().equals(matcher.group("username"))) {
//                    String item = matcher.group("item");
//                    int amount = Integer.parseInt(matcher.group("amount"));
//                    double price = Float.parseFloat(matcher.group("price"));
//                    if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
//                        if (enable == 0) {
//                            Trade trade = new Trade(currentPlayer, type, item, amount, price, "", 0, "byMoney", matcher);
//                            message message = new message(currentPlayer, "you have a trade offer from "
//                                + currentPlayer.getUser().getUsername());
//                            player.addMessage(message);
//                            player.addTrades(trade);
//                            currentPlayer.addTrades(trade);
//                            return new Result(true, "yor offer for trade has been registered");
//                        } else if (enable == 1) {
//                            player.getBackPack().addcoin(-1 * price);
//                            currentPlayer.getBackPack().addcoin(price);
//                            for (int i = 0; i < amount; i++) {
//                                BackPackable b = currentPlayer.getBackPack().useItem(item);
//                                player.getBackPack().addItemToInventory(b);
//                            }
//                        }
//                    } else {
//                        return new Result(false, "you have not enough items in your inventory");
//                    }
//                }
//            }
//            return new Result(false, "this username does not exist in this game");
//        } else {
//            for (Player player : App.getCurrentGame().getPlayers()) {
//                if (player.getUser().getUsername().equals(matcher.group("username"))) {
//                    String item = matcher.group("item");
//                    int amount = Integer.parseInt(matcher.group("amount"));
//                    double price = Float.parseFloat(matcher.group("price"));
//                    if (currentPlayer.getBackPack().getCoin() < price) {
//                        return new Result(false, "you have not enough coins");
//                    }
//                    if (player.getBackPack().getInventorySize(item) >= amount) {
//                        if (enable == 0) {
//                            Trade trade = new Trade(currentPlayer, type, item, amount, price, "", 0, "byMoney", matcher);
//                            message message = new message(currentPlayer, "you have a trade offer from "
//                                + currentPlayer.getUser().getUsername());
//                            player.addMessage(message);
//                            player.addTrades(trade);
//                            currentPlayer.addTrades(trade);
//                            return new Result(true, "yor request for trade has been registered");
//                        } else if (enable == 1) {
//                            player.getBackPack().addcoin(price);
//                            currentPlayer.getBackPack().addcoin(-1 * price);
//                            for (int i = 0; i < amount; i++) {
//                                BackPackable b = player.getBackPack().useItem(item);
//                                currentPlayer.getBackPack().addItemToInventory(b);
//                            }
//
//                        }
//                    } else {
//                        return new Result(false, "this player have not enough items in her/his inventory");
//                    }
//                }
//            }
//            return new Result(false, "this username does not exist in this game");
//        }
//
//    }
//
//    public Result tradeByItem(Matcher matcher, String type, int enable) {
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        if (type.trim().equals("offer")) {
//            for (Player player : App.getCurrentGame().getPlayers()) {
//                if (player.getUser().getUsername().equals(matcher.group("username"))) {
//                    String item = matcher.group("item");
//                    int amount = Integer.parseInt(matcher.group("amount"));
//                    String targetItem = matcher.group("targetItem");
//                    int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
//                    if (player.getBackPack().getInventorySize(targetItem) >= targetAmount) {
//                        if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
//                            if (enable == 0) {
//                                Trade trade = new Trade(currentPlayer, type, item, amount, 0, targetItem, targetAmount, "byItem", matcher);
//                                message message = new message(currentPlayer, "you have a trade offer from "
//                                    + currentPlayer.getUser().getUsername());
//                                player.addMessage(message);
//                                player.addTrades(trade);
//                                currentPlayer.addTrades(trade);
//                                return new Result(true, "yor offer for trade has been registered");
//                            } else if (enable == 1) {
//                                for (int i = 0; i < amount; i++) {
//                                    BackPackable b = currentPlayer.getBackPack().useItem(item);
//                                    player.getBackPack().addItemToInventory(b);
//                                }
//                                for (int i = 0; i < targetAmount; i++) {
//                                    BackPackable b = player.getBackPack().useItem(targetItem);
//                                    currentPlayer.getBackPack().addItemToInventory(b);
//                                }
//                            }
//                        } else {
//                            return new Result(false, "you have not enough item in your inventory");
//                        }
//                    } else {
//                        return new Result(false, "this player have not enough targetItems in her/his inventory");
//                    }
//                }
//            }
//        } else {
//            for (Player player : App.getCurrentGame().getPlayers()) {
//                if (player.getUser().getUsername().equals(matcher.group("username"))) {
//                    String item = matcher.group("item");
//                    int amount = Integer.parseInt(matcher.group("amount"));
//                    String targetItem = matcher.group("targetItem");
//                    int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
//                    if (player.getBackPack().getInventorySize(item) >= amount) {
//                        if (currentPlayer.getBackPack().getInventorySize(targetItem) >= targetAmount) {
//                            if (enable == 0) {
//                                Trade trade = new Trade(currentPlayer, type, item, amount, 0, targetItem, targetAmount, "byItem", matcher);
//                                message message = new message(currentPlayer, "you have a trade request from "
//                                    + currentPlayer.getUser().getUsername());
//                                player.addMessage(message);
//                                player.addTrades(trade);
//                                currentPlayer.addTrades(trade);
//                                return new Result(true, "yor request for trade has been registered");
//                            } else if (enable == 1) {
//                                for (int i = 0; i < amount; i++) {
//                                    BackPackable b = player.getBackPack().useItem(item);
//                                    currentPlayer.getBackPack().addItemToInventory(b);
//                                }
//                                for (int i = 0; i < targetAmount; i++) {
//                                    BackPackable b = currentPlayer.getBackPack().useItem(targetItem);
//                                    player.getBackPack().addItemToInventory(b);
//                                }
//                            }
//                        } else {
//                            return new Result(false, "this player have not enough item in your inventory");
//                        }
//                    } else {
//                        return new Result(false, "this player have not enough targetItems in her/his inventory");
//                    }
//                }
//            }
//        }
//        return new Result(false, "this username does not exist in this game");
//    }
//
//    public Result tradeResponse(Matcher matcher) {
//        String accept = matcher.group("accept");
//        int id = Integer.parseInt(matcher.group("id"));
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        Trade trade = null;
//        for (Trade trade1 : currentPlayer.getTrades()) {
//            if (trade1.getId() == id && !trade1.getSender().equals(currentPlayer)) {
//                trade = trade1;
//            }
//        }
//        if (trade == null) {
//            return new Result(false, "invalid id");
//        } else if (accept.equals("-accept")) {
//            if (trade.getTradeType().equals("byMoney")) {
//                tradeByMoney2(trade.getSender(), trade.getMatcher(), trade.getMatcher().group("type"));
//                trade.getSender().addTradeHistory(trade);
//                currentPlayer.addTradeHistory(trade);
//                trade.getSender().getTrades().remove(trade);
//                currentPlayer.getTrades().remove(trade);
//                return new Result(true, "the operation was successful");
//            } else {
//                tradeByItem2(trade.getSender(), trade.getMatcher(), trade.getMatcher().group("type"));
//                trade.getSender().addTradeHistory(trade);
//                currentPlayer.addTradeHistory(trade);
//                trade.getSender().getTrades().remove(trade);
//                currentPlayer.getTrades().remove(trade);
//                return new Result(true, "the operation was successful");
//            }
//        } else {
//            trade.getSender().getTrades().remove(trade);
//            currentPlayer.getTrades().remove(trade);
//            return new Result(false, "the operation was successful");
//        }
//    }
//
//    public Result tradeByMoney2(Player player, Matcher matcher, String type) {
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        if (type.trim().equals("offer")) {
//            String item = matcher.group("item");
//            int amount = Integer.parseInt(matcher.group("amount"));
//            double price = Float.parseFloat(matcher.group("price"));
//            if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
//                player.getBackPack().addcoin(-1 * price);
//                currentPlayer.getBackPack().addcoin(price);
//                for (int i = 0; i < amount; i++) {
//                    BackPackable b = currentPlayer.getBackPack().useItem(item);
//                    player.getBackPack().addItemToInventory(b);
//                }
//            } else {
//                return new Result(false, "you have not enough items in your inventory");
//            }
//            return new Result(false, "this username does not exist in this game");
//        } else {
//            String item = matcher.group("item");
//            int amount = Integer.parseInt(matcher.group("amount"));
//            double price = Float.parseFloat(matcher.group("price"));
//            if (currentPlayer.getBackPack().getCoin() < price) {
//                return new Result(false, "you have not enough coins");
//            }
//            if (player.getBackPack().getInventorySize(item) >= amount) {
//                player.getBackPack().addcoin(price);
//                currentPlayer.getBackPack().addcoin(-1 * price);
//                for (int i = 0; i < amount; i++) {
//                    BackPackable b = player.getBackPack().useItem(item);
//                    currentPlayer.getBackPack().addItemToInventory(b);
//                }
//            } else {
//                return new Result(false, "this player have not enough items in her/his inventory");
//            }
//            return new Result(false, "this username does not exist in this game");
//        }
//
//    }
//
//    public Result tradeByItem2(Player player, Matcher matcher, String type) {
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();
//        if (type.trim().equals("offer")) {
//            String item = matcher.group("item");
//            int amount = Integer.parseInt(matcher.group("amount"));
//            String targetItem = matcher.group("targetItem");
//            int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
//            if (player.getBackPack().getInventorySize(targetItem) >= targetAmount) {
//                if (currentPlayer.getBackPack().getInventorySize(item) >= amount) {
//                    for (int i = 0; i < amount; i++) {
//                        BackPackable b = currentPlayer.getBackPack().useItem(item);
//                        player.getBackPack().addItemToInventory(b);
//                    }
//                    for (int i = 0; i < targetAmount; i++) {
//                        BackPackable b = player.getBackPack().useItem(targetItem);
//                        currentPlayer.getBackPack().addItemToInventory(b);
//                    }
//                } else {
//                    return new Result(false, "you have not enough item in your inventory");
//                }
//            } else {
//                return new Result(false, "this player have not enough targetItems in her/his inventory");
//            }
//
//        } else {
//            String item = matcher.group("item");
//            int amount = Integer.parseInt(matcher.group("amount"));
//            String targetItem = matcher.group("targetItem");
//            int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
//            if (player.getBackPack().getInventorySize(item) >= amount) {
//                if (currentPlayer.getBackPack().getInventorySize(targetItem) >= targetAmount) {
//                    for (int i = 0; i < amount; i++) {
//                        BackPackable b = player.getBackPack().useItem(item);
//                        currentPlayer.getBackPack().addItemToInventory(b);
//                    }
//                    for (int i = 0; i < targetAmount; i++) {
//                        BackPackable b = currentPlayer.getBackPack().useItem(targetItem);
//                        player.getBackPack().addItemToInventory(b);
//                    }
//                } else {
//                    return new Result(false, "this player have not enough item in your inventory");
//                }
//            } else {
//                return new Result(false, "this player have not enough targetItems in her/his inventory");
//            }
//        }
//        return new Result(false, "this username does not exist in this game");
//    }

    public String tradeHistory() {
        return GameClient.getGameStateApiClient().tradeHistory();
    }

    public String tradeList() {
       return GameClient.getGameStateApiClient().tradeList();
    }


    public Result giftNPC(String npc, String item,String amount) {
        return GameClient.gameStateApiClient.giftNPC(npc, item, amount);
    }

    public String friendshipNPCList(String npc) {
        return GameClient.gameStateApiClient.friendshipNPCList(npc);
    }

    public Result questsList() {
        return GameClient.gameStateApiClient.questsList();
    }

    public Result questFinish(String index) {
        return GameClient.gameStateApiClient.questFinish(index);
    }

    public Result showMessage() {
        return GameClient.gameStateApiClient.showMessage();
    }

    public Result deleteMessage(int index) {
        return GameClient.gameStateApiClient.deleteMessage(index);
    }

}
