package io.github.StardewValley.shared.models;

import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.LightningLogicController;
import io.github.StardewValley.shared.models.NPCS.*;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.cooking.Recipe;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.map.GameMap;
import io.github.StardewValley.shared.models.map.PlayerMap;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Serializable {
    private final Player creator;
    private final TimeAndDate date = new TimeAndDate();
    private final ArrayList<Player> players = new ArrayList<Player>();
    private final GameMap gameMap;
    private final ArrayList<NPC> NPCs = new ArrayList<>();
    private final MarketsController marketsController = new MarketsController();
    private final LightningLogicController lightningLogicController = new LightningLogicController();
    private final ArrayList<NPC> NPCHuts = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private ArrayList<Tile> treeTile = new ArrayList<Tile>();
    private final ArrayList<GreenHouse> greenHouses = new ArrayList<>();
    private final HashMap<GreenHouse, Rectangle> greenHouseBounds = new HashMap<>();


    public Game(UserDTO user1, UserDTO user2, UserDTO user3, UserDTO user4) {
        System.out.println("Game creation started");
        user1.setActiveGame(this);
        user2.setActiveGame(this);
        user3.setActiveGame(this);
        user4.setActiveGame(this);
        players.add(creator = new Player(user1, false));
        players.add(new Player(user2, user2.getUsername().startsWith("guest")));
        players.add(new Player(user3, user3.getUsername().startsWith("guest")));
        players.add(new Player(user4, user4.getUsername().startsWith("guest")));
        addNPCs(new Abigail(true));
        addNPCs(new Harvey(true));
        addNPCs(new Lia(true));
        addNPCs(new Robin(true));
        addNPCs(new Sebastian(true));
        for (Player player : players) {
            for (NPC npc : NPCs) {
                player.setFriendShipsWithNPCs(npc);
                player.setTalkedNPCToday(npc);
                player.setGiftNPCToday(npc);
            }
        }
        for (Player p : players) {
            p.getUser().setNumOfPlay(p.getUser().getNumOfPlay() + 1);
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 0) {
                players.get(0).addGift(players.get(i));
                players.get(0).addTalk(players.get(i), new Talk(players.get(i)));
                players.get(0).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 1) {
                players.get(1).addGift(players.get(i));
                players.get(1).addTalk(players.get(i), new Talk(players.get(i)));
                players.get(1).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 2) {
                players.get(2).addGift(players.get(i));
                players.get(2).addTalk(players.get(i), new Talk(players.get(i)));
                players.get(2).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 3) {
                players.get(3).addGift(players.get(i));
                players.get(3).addTalk(players.get(i), new Talk(players.get(i)));
                players.get(3).addFriendShips(players.get(i), 0);
            }
        }
        //App.setCurrentGame(this);
        this.gameMap = new GameMap(players, this);
        setTiles(new ArrayList<>(Tile.getTiles()));
        setTreeTile(new ArrayList<>(Tile.getTreeTile()));
        Tile.getTiles().clear();
        Tile.getTreeTile().clear();

        for (Player player : players) {
            if (player.getUser().getUsername().equals("NPC")) continue;
            if (player.isGuest()) {
                player.getPlayerMap().setMapType(1, this);
            }
        }
//        this.marketsController.initializeStores();
        giveInitialItems();
        System.out.println("Game creation finished");
    }

    private void giveInitialItems() {
        for (Player player : players) {
            player.getRecipes().add(new Recipe(FoodType.FriedEgg));
            player.getRecipes().add(new Recipe(FoodType.BakedFish));
            player.getRecipes().add(new Recipe(FoodType.Salad));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Furnace));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Scarecrow));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MayonnaiseMachine));

        }
    }

    public Player getPlayerByPlayerMap(PlayerMap playerMap) {
        for (Player player : players) {
            if (player.getPlayerMap().equals(playerMap)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public TimeAndDate getDate() {
        return date;
    }

    public Player getCreator() {
        return creator;
    }

    public MarketsController getMarketsController() {
        return marketsController;
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public ArrayList<NPC> getNPCHuts() {
        return NPCHuts;
    }

    public void addNPCs(NPC npc) {
        this.NPCs.add(npc);
    }

    public NPC getNPC(String npcName) {
        for (NPC npc : NPCs) {
            if (npc.getName().equals(npcName)) {
                return npc;
            }
        }
        return null;
    }

    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUser().getUsername().equals(username))
                return player;
        }
        return null;
    }

    public LightningLogicController getLightningLogicController() {
        return lightningLogicController;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Tile> getTreeTile() {
        return treeTile;
    }

    public void setTreeTile(ArrayList<Tile> treeTile) {
        this.treeTile = treeTile;
    }

    public Tile getTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    public void addGreenHouses(GreenHouse greenHouse) {
        greenHouses.add(greenHouse);
        float tileWidth = GameAssetManager.getGameAssetManager().getTileWidth();
        float tileHeight = GameAssetManager.getGameAssetManager().getTileHeight();
        greenHouseBounds.put(greenHouse, new Rectangle(
            greenHouse.getStarting_x() * tileWidth,
            greenHouse.getStarting_y() * tileHeight,
            greenHouse.getWidth() * tileWidth,
            greenHouse.getHeight() * tileHeight
        ));
    }

    public ArrayList<GreenHouse> getGreenHouses() {
        return greenHouses;
    }

    public HashMap<GreenHouse, Rectangle> getGreenHouseBounds() {
        return greenHouseBounds;
    }
}
