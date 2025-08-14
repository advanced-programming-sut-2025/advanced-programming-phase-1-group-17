package io.github.StardewValley.shared.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.dto.TradeRequestDto;
import io.github.StardewValley.shared.models.NPCS.Gift;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Talk;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.cooking.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.backpack.BackPackType;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.map.PlayerMap;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.saveClasses.*;
import io.github.StardewValley.shared.models.tools.*;

import java.util.*;

public class Player {
    private PlayerMap playerMap;
    private UUID activeGameID;
    private UserDTO user;
    private boolean isGuest = false;
    private boolean isPassedOut = false;
    private int x;
    private int y;
    private Buff buff;
    private float speed = 1000f;
    private double coin = 550;
    private transient Animation<TextureRegion> walkUpAnimation;
    private transient Animation<TextureRegion> walkDownAnimation;
    private transient Animation<TextureRegion> walkLeftAnimation;
    private transient Animation<TextureRegion> walkRightAnimation;
    private TextureRegion currentFrame;
    private float animationTimer = 0f;
    private float passOutTimer = 0f;
    private boolean moved;
    private Direction lastDirection = Direction.DOWN;
    private Direction currentDirection = Direction.IDLE;
    private String targetPlayerToTrade = null;
    private HashMap<String, Integer> suggestions = new HashMap<>();
    private HashMap<String, Integer> requierd = new HashMap<>();
    private ArrayList<Food>refrigerator = new ArrayList<>();


    //For friendShip
    private final HashMap<Player, Integer> friendShips = new HashMap<Player, Integer>();
    private final HashMap<Player, Talk> talk = new HashMap<Player, Talk>();
    private boolean newMessage = false;

    private HashMap<Player, ArrayList<Gift>> gifts = new HashMap<Player, ArrayList<Gift>>();
    private ArrayList<Message> Messages = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();
    private Player partner = this;
    private boolean interactionWithPartner = false;
    private int isbrokenUp = 0;

    //For NPC
    private HashMap<NPC, Integer> friendShipsWithNPCs = new HashMap<>();
    private HashMap<NPC, Boolean> talkedNPCToday = new HashMap<>();
    private HashMap<NPC, Boolean> giftNPCToday = new HashMap<>();

    //For Energy
    private double energy;
    private double maxEnergy = 200;
    private boolean isEnergyUnlimited = false;
    private boolean hasPassedOutToday = false;

    //For BackPack
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack, this);

    //For TrashCan & WaterStorage
    private Tool trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Basic, null);
    private Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic, null);
    private Tool currentTool;
    private BackPackable equippedItem;
    private Ability abilities = new Ability(this);
    private HashSet<Recipe> recipes = new HashSet<>();
    private HashSet<CraftingRecipe> craftingRecipes = new HashSet<>();

    private int daysSinceBrakeUp = 0;

    public void initializeFromSave(PlayerSave playerSave, List<Player> players, List<NPC> npcs) {
        this.friendShips.clear();
        if (playerSave.getFriendShips() != null) {
            for (String playerUsername : playerSave.getFriendShips().keySet()) {
                for (Player player : players) {
                    if (player.getUser().getUsername().equals(playerUsername))
                        this.friendShips.put(player, playerSave.getFriendShips().get(playerUsername));
                }
            }
        }

        // Talks
        this.talk.clear();
        if (playerSave.getTalk() != null) {
            for (String playerUsername : playerSave.getTalk().keySet()) {
                for (Player player : players) {
                    if (player.getUser().getUsername().equals(playerUsername))
                        this.talk.put(player, new Talk(player, playerSave.getTalk().get(playerUsername).getTalk()));
                }
            }
        }
        // Gifts
        this.gifts.clear();
        Gift.setCounter(0);
        if (playerSave.getGifts() != null) {
            for (String playerUsername : playerSave.getGifts().keySet()) {
                for (Player player : players) {
                    if (player.getUser().getUsername().equals(playerUsername)) {
                        ArrayList<Gift> gifts = new ArrayList<>();
                        playerSave.getGifts().forEach((playerUsername1, gifts2) -> {
                            gifts2.forEach(gift -> gifts.add(new Gift(gift, this, player)));
                        });
                        this.gifts.put(player, gifts);
                    }
                }
            }
        }

        // Messages
        this.Messages.clear();
        if (playerSave.getMessages() != null) {
            for (MessageSave messageSave : playerSave.getMessages()) {
                for (Player player : players) {
                    if (player.getUser().getUsername().equals(messageSave.getSenderName()))
                        this.Messages.add(new Message(player, messageSave.getMessage()));
                }
            }
        }

        // Trades
        this.trades = playerSave.getTrades();

        for (Player player : players) {
            if (player.getUser().getUsername().equals(playerSave.getPartnerUsername()))
                this.partner = player;
        }

        // NPC relationships
        this.friendShipsWithNPCs.clear();
        if (playerSave.getFriendShipsWithNPCs() != null) {
            for (NPCSave npcSave : playerSave.getFriendShipsWithNPCs().keySet()) {
                for (NPC npc : npcs) {
                    if (npc.getName().equals(npcSave.getName())) {
                        this.friendShipsWithNPCs.put(npc, playerSave.getFriendShipsWithNPCs().get(npcSave));
                        break;
                    }
                }
            }
        }

        this.talkedNPCToday.clear();
        if (playerSave.getTalkedNPCToday() != null) {
            for (NPCSave npcSave : playerSave.getTalkedNPCToday().keySet()) {
                for (NPC npc : npcs) {
                    if (npc.getName().equals(npcSave.getName())) {
                        this.talkedNPCToday.put(npc, playerSave.getTalkedNPCToday().get(npcSave));
                        break;
                    }
                }
            }
        }

        this.giftNPCToday.clear();
        if (playerSave.getGiftNPCToday() != null) {
            for (NPCSave npcSave : playerSave.getGiftNPCToday().keySet()) {
                for (NPC npc : npcs) {
                    if (npc.getName().equals(npcSave.getName())) {
                        this.giftNPCToday.put(npc, playerSave.getGiftNPCToday().get(npcSave));
                        break;
                    }
                }
            }
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public Player(UserDTO user, boolean isGuest) {
        this.user = user;
        this.isGuest = isGuest;
        this.energy = maxEnergy;
        Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic, null);
        backPack.addItemToInventory(wateringCan);
        backPack.addItemToInventory(new Tool(ToolType.Scythe, null, null));
        backPack.addItemToInventory(new Tool(ToolType.Hoe, ToolMaterial.Basic, null));
        backPack.addItemToInventory(new Tool(ToolType.Pickaxe, ToolMaterial.Basic, null));
        backPack.addItemToInventory(new Tool(ToolType.Axe, ToolMaterial.Basic, null));
        this.currentTool = wateringCan;
        this.equippedItem = wateringCan;
        this.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MegaBomb));
        backPack.addItemToInventory(new Tool(ToolType.FishingPole, null, FishingPoleType.IridiumFishingPole));
        this.getRecipes().add(new Recipe(FoodType.MakiRoll));
        this.getRecipes().add(new Recipe(FoodType.FarmersLunch));
        this.buff = new Buff(BuffType.None, 0);

        //test
        this.refrigerator.add(new Food(FoodType.Bread));
        this.refrigerator.add(new Food(FoodType.FriedEgg));
    }

    public Player(PlayerSave playerSave, Game game) {
        this.user = playerSave.getUser();
        this.isGuest = playerSave.isGuest();
        this.isPassedOut = playerSave.isPassedOut();
        this.x = playerSave.getX();
        this.y = playerSave.getY();
        this.buff = playerSave.getBuff();
        this.speed = playerSave.getSpeed();
        this.coin = playerSave.getCoin();
        this.animationTimer = playerSave.getAnimationTimer();
        this.passOutTimer = playerSave.getPassOutTimer();
        this.moved = playerSave.isMoved();
        this.lastDirection = playerSave.getLastDirection();
        this.currentDirection = playerSave.getCurrentDirection();
        this.newMessage = playerSave.isNewMessage();
        this.interactionWithPartner = playerSave.isInteractionWithPartner();
        this.isbrokenUp = playerSave.getIsbrokenUp();

        // Energy
        this.energy = playerSave.getEnergy();
        this.maxEnergy = playerSave.getMaxEnergy();
        this.isEnergyUnlimited = playerSave.isEnergyUnlimited();
        this.hasPassedOutToday = playerSave.isHasPassedOutToday();

        // BackPack
        this.backPack = new BackPack(playerSave.getBackPack().getType(), this);
        this.backPack.getBackPackItems().clear();
        getBackPackItemsFromSave(playerSave, game);

        // Tools
        this.trashCan = playerSave.getTrashCan();
        this.wateringCan = playerSave.getWateringCan();
        this.currentTool = playerSave.getCurrentTool();
        this.equippedItem = backPack.getBackPackItems().get(ToolType.WateringCan).get(0);

        // Recipes & Abilities
        this.recipes = new HashSet<>(playerSave.getRecipes() != null ? playerSave.getRecipes() : Set.of());
        this.abilities = playerSave.getAbilities() != null ? playerSave.getAbilities() : new Ability(this);
        this.craftingRecipes = new HashSet<>(playerSave.getCraftingRecipes() != null ? playerSave.getCraftingRecipes() : Set.of());

        // Misc
        this.daysSinceBrakeUp = playerSave.getDaysSinceBrakeUp();
    }

    private void getBackPackItemsFromSave(PlayerSave playerSave, Game game) {
        playerSave.getBackPack().getBackPackItems().forEach((typePair, saves) -> {
            String name = typePair.getFirst();
            String className = typePair.getSecond();
            BackPackableType type;
            try {
                Class<?> clazz = Class.forName(className);
                type = (BackPackableType) clazz.getConstructor(String.class).newInstance(name);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load backpack type: " + name + " / " + className, e);
            }

            ArrayList<BackPackable> backpackables = new ArrayList<>();
            for (BackPackableSave save : saves) {
                backpackables.add(fromBackPackableSave(save, game));
            }

            backPack.getBackPackItems().put(type, backpackables);
        });
    }

    private BackPackable fromBackPackableSave(BackPackableSave save, Game game) {
        return switch (save.getType()) {
            case "Crop" -> new Crop(save.getCropSave());
            case "Fruit" -> save.getFruit();
            case "Tool" -> save.getTool();
            case "Fish" -> save.getFish();
            case "Seed" -> save.getSeed();
            case "ArtisanProduct" -> save.getArtisanProduct();
            case "ShopItem" -> save.getShopItem();
            case "Fertilizer" -> save.getFertilizer();
            case "Flower" -> save.getFlower();
            case "Food" -> save.getFood();
            case "CraftingItem" -> new CraftingItem(save.getCraftingItemSave(), game.getPlayers());
            case "Mineral" -> save.getMineral();
            case "Ring" -> save.getRing();
            case "ShippingBinSave" -> new ShippingBin(save.getShippingBinSave(), game);
            case "NormalItem" -> save.getNormalItem();
            case "Sapling" -> save.getSapling();

            default -> throw new IllegalArgumentException("Unknown backpackable type: " + save.getType());
        };
    }



    public void setInitialEnergyForTomorrow(boolean isPassedOut) {
        if (isEnergyUnlimited)
            maxEnergy = 200;
        isEnergyUnlimited = false;
        if (isPassedOut) {
            energy = maxEnergy * 0.75;
        } else {
            energy = maxEnergy;
        }
    }

    public void passOut() {
        if (isPassedOut) return;
        hasPassedOutToday = true;
        isPassedOut = true;
        passOutTimer = 3f;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y * (int) GameAssetManager.getGameAssetManager().getTileHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x * (int) GameAssetManager.getGameAssetManager().getTileWidth();
    }

    public PlayerMap getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    //For Energy
    private int temporaryMaxEnergyBoost = 0;
    private int temporaryBoostRemainingHours = 0;

    public double getMaxEnergy() {
        return maxEnergy + temporaryMaxEnergyBoost;
    }

    public void applyTemporaryMaxEnergyBoost(int boostAmount, int hours) {
        this.temporaryMaxEnergyBoost = boostAmount;
        this.temporaryBoostRemainingHours = hours;
    }

    public void updateTemporaryBoostTimer() {
        if (temporaryBoostRemainingHours > 0) {
            temporaryBoostRemainingHours--;
            if (temporaryBoostRemainingHours == 0) {
                temporaryMaxEnergyBoost = 0;

            }
        }
    }


    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double amount) {
        if (amount < this.energy) {
            this.energy = amount;
        }
        if (amount > this.getMaxEnergy()) {
            return;
        }
        amount = Math.min(amount, this.getMaxEnergy());
        this.energy = amount;
    }

    public boolean isHasPassedOutToday() {
        return hasPassedOutToday;
    }

    public void setHasPassedOutToday(boolean hasPassedOutToday) {
        this.hasPassedOutToday = hasPassedOutToday;
    }

    //For BackPack
    public BackPack getBackPack() {
        return backPack;
    }

    public void setBackPack(BackPack backPack) {
        this.backPack = backPack;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }


    //For TrashCan
    public Tool getTrashCan() {
        return trashCan;
    }

    public void setTrashCan(Tool trashCan) {
        this.trashCan = trashCan;
    }

    public Tool getWateringCan() {
        return wateringCan;
    }

    public void setWateringCan(Tool wateringCan) {
        this.wateringCan = wateringCan;
    }

    public HashMap<Player, Integer> getFriendShips() {
        return friendShips;
    }

    public void addFriendShips(Player player, int friendShip) {
        this.friendShips.put(player, friendShip);
    }

    public HashMap<Player, Talk> getTalk() {
        return talk;
    }

    public void addTalk(Player player, Talk talk) {
        this.talk.put(player, talk);
    }

    public void addGift(Player player) {
        this.gifts.put(player, new ArrayList<>());
    }

    public HashMap<Player, ArrayList<Gift>> getGifts() {
        return gifts;
    }

    public int getIsbrokenUp() {
        return isbrokenUp;
    }

    public void setIsbrokenUp(int isbrokenUp) {
        this.isbrokenUp = isbrokenUp;
    }

    public void setGifts(HashMap<Player, ArrayList<Gift>> gifts) {
        this.gifts = gifts;
    }

    public void addMessage(Message message) {
        this.Messages.add(message);
        newMessage = true;
    }

    public ArrayList<Message> getMessage() {
        return this.Messages;
    }


    public String getStringMessage() {
        String message = "";
        for (int i = 0; i < Messages.size(); i++) {
            if (Messages.get(i).getSender() != null) {
                message += (i + "- " + "SENDER" + " : " + Messages.get(i).getSender().getUser().getUsername()
                    + "\n" + "message : " + Messages.get(i).getMessage() + "\n");
            } else {
                message += (i + "- " + "SENDER(NPC)" + " : " + Messages.get(i).getSenderNPC().getName()
                    + "\n" + "message : " + Messages.get(i).getMessage() + "\n");
            }
        }
        return message;
    }

    public Player getPartner() {
        return partner;
    }

    public void setPartner(Player partner) {
        this.partner = partner;
    }

    public boolean isInteractionWithPartner() {
        return interactionWithPartner;
    }

    public void setInteractionWithPartner(boolean interactionWithPartner) {
        this.interactionWithPartner = interactionWithPartner;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void addTrades(Trade trade) {
        this.trades.add(trade);
    }


    public HashMap<NPC, Integer> getFriendShipsWithNPCs() {
        return friendShipsWithNPCs;
    }

    public void setFriendShipsWithNPCs(NPC npc) {
        this.friendShipsWithNPCs.put(npc, 0);
    }

    public HashMap<NPC, Boolean> getTalkedNPCToday() {
        return talkedNPCToday;
    }

    public void setTalkedNPCToday(NPC npc) {
        this.talkedNPCToday.put(npc, false);
    }

    public HashMap<NPC, Boolean> getGiftNPCToday() {
        return giftNPCToday;
    }

    public void setGiftNPCToday(NPC npc) {
        this.giftNPCToday.put(npc, false);
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public void updateBuff() {
        if (buff != null && buff.getBuffType() != BuffType.None) {
            if (buff.getDuration() > 0) buff.setDuration(buff.getDuration() - 1);
            if (buff.getDuration() <= 0) {
                buff = new Buff(BuffType.None, 0);
            }
        }
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void upgradeTrashCan() {
        ToolMaterial material = trashCan.getMaterial();
        if (material.equals(ToolMaterial.Basic))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Copper, null);
        else if (material.equals(ToolMaterial.Copper))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Steel, null);
        else if (material.equals(ToolMaterial.Steel))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Gold, null);
        else if (material.equals(ToolMaterial.Gold))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Iridium, null);
    }

    public void update(Game game, float delta, boolean up, boolean down, boolean left, boolean right) {
        if (isPassedOut) {
            passOutTimer -= delta;
            if (passOutTimer <= 0) {
                isPassedOut = false;
                hasPassedOutToday = false;
                this.energy = getMaxEnergy();
                this.x = (this.playerMap.getRow() + 1) * 120;
                this.y = (this.playerMap.getCol() + 1) * 120;
            }
            return;
        }
        float newX = x;
        float newY = y;


        if (up) {
            newY += speed * delta;
            this.currentDirection = Direction.UP;
            lastDirection = currentDirection;
            energy -= energy * 0.0005;
            if (energy < 0) {
                energy = 0;
                passOut();
            }
        } else if (down) {
            newY -= speed * delta;
            this.currentDirection = Direction.DOWN;
            lastDirection = currentDirection;

            energy -= energy * 0.0005;
            if (energy < 0) {
                energy = 0;
                passOut();
            }
        } else if (left) {
            newX -= speed * delta;

            this.currentDirection = Direction.LEFT;
            lastDirection = currentDirection;
            energy -= energy * 0.0005;
            if (energy < 0) {
                energy = 0;
                passOut();
            }
        } else if (right) {
            newX += speed * delta;

            this.currentDirection = Direction.RIGHT;
            lastDirection = currentDirection;
            energy -= energy * 0.0005;
            if (energy < 0) {
                energy = 0;
                passOut();
            }
        } else {
            this.currentDirection = Direction.IDLE;
        }


        if (this.currentDirection != Direction.IDLE) {
            this.animationTimer += delta;
        } else {
            this.animationTimer = 0f;
        }

        boolean isOky = true;
        int playerWidth = (int) GameAssetManager.getGameAssetManager().getTileWidth();
        ;
        int playerHeight = (int) GameAssetManager.getGameAssetManager().getTileHeight();
        ;
        try {
            float centerX = newX + playerWidth / 2f;
            float centerY = newY + playerHeight / 2f;

            int tileX = (int) (centerX / (int) GameAssetManager.getGameAssetManager().getTileWidth());
            int tileY = (int) (centerY / (int) GameAssetManager.getGameAssetManager().getTileHeight());

            if (tileX == 0) tileX = 1;
            if (tileY == 0) tileY = 1;

            Tile destination = game.getTile(tileX, tileY);
            if (destination != null) {
                if (!(destination.getOwner().equals(this.getPartner())
                    || destination.getOwner().equals(this)
                    || destination.getOwner().equals(NPC.getFatherPlayer()))) {
                    isOky = false;
                } else if (!destination.isWalkAble()) {
                    isOky = false;
                }
                if (isOky) {
                    moved = true;
                    int mapWidth = (int) GameAssetManager.getGameAssetManager().getTileWidth() * 300;
                    int mapHeight = (int) GameAssetManager.getGameAssetManager().getTileHeight() * 300;

//                    x = tileX;
//                    y = tileY;

                    x = (int) Math.max(1, Math.min(newX, mapWidth - playerWidth));
                    y = (int) Math.max(1, Math.min(newY, mapHeight - playerHeight));


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        if (isPassedOut) {
            batch.draw(
                new Texture(user.getGender().equals(Gender.Male) ? "Alex.png" : "Emily.png"),
                getX() == 0 ? 1 : getX(),
                getY() == 0 ? 1 : getY(),
                (int) GameAssetManager.getGameAssetManager().getTileWidth() / 1.5f,
                (int) GameAssetManager.getGameAssetManager().getTileHeight() / 1.5f
            );
            return;
        }
        switch (this.currentDirection) {
            case UP:
                this.currentFrame = walkUpAnimation.getKeyFrame(animationTimer);
                break;
            case DOWN:
                this.currentFrame = walkDownAnimation.getKeyFrame(animationTimer);
                break;
            case LEFT:
                this.currentFrame = walkLeftAnimation.getKeyFrame(animationTimer);
                break;
            case RIGHT:
                this.currentFrame = walkRightAnimation.getKeyFrame(animationTimer);
                break;
            case IDLE:
                switch (this.lastDirection) {
                    case UP:
                        this.currentFrame = walkUpAnimation.getKeyFrame(0);
                        break;
                    case DOWN:
                        this.currentFrame = walkDownAnimation.getKeyFrame(0);
                        break;
                    case LEFT:
                        this.currentFrame = walkLeftAnimation.getKeyFrame(0);
                        break;
                    case RIGHT:
                        this.currentFrame = walkRightAnimation.getKeyFrame(0);
                        break;
                    default:
                        this.currentFrame = walkDownAnimation.getKeyFrame(0);
                        break;
                }
                break;
        }
    }

    public BackPackable getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(BackPackable equippedItem) {
        this.equippedItem = equippedItem;
    }

    public int getTileX() {
        int playerWidth = (int) GameAssetManager.getGameAssetManager().getTileWidth();
        ;
        float centerX = x + playerWidth / 2f;
        return (int) (centerX / (int) GameAssetManager.getGameAssetManager().getTileHeight());


    }

    public int getTileY() {
        int playerHeight = (int) GameAssetManager.getGameAssetManager().getTileHeight();
        ;
        float centerY = y + playerHeight / 2f;
        return (int) (centerY / (int) GameAssetManager.getGameAssetManager().getTileHeight());

    }

    public boolean isMoved() {
        return moved;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }



    public boolean isEnergyUnlimited() {
        return isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        isEnergyUnlimited = energyUnlimited;
    }

    public boolean isPassedOut() {
        return isPassedOut;
    }

    public float getSpeed() {
        return speed;
    }

    public Animation<TextureRegion> getWalkUpAnimation() {
        return walkUpAnimation;
    }

    public Animation<TextureRegion> getWalkDownAnimation() {
        return walkDownAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation<TextureRegion> getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public float getAnimationTimer() {
        return animationTimer;
    }

    public float getPassOutTimer() {
        return passOutTimer;
    }

    public ArrayList<Message> getMessages() {
        return Messages;
    }

    public int getDaysSinceBrakeUp() {
        return daysSinceBrakeUp;
    }

    public int getTemporaryMaxEnergyBoost() {
        return temporaryMaxEnergyBoost;
    }

    public int getTemporaryBoostRemainingHours() {
        return temporaryBoostRemainingHours;
    }

    public void toolEquip(ToolType toolType) {
        BackPack backPack = this.backPack;
        currentTool = (Tool) backPack.getBackPackItems().get(toolType).get(0);
    }

    public void fishingPoleEquip(FishingPoleType fishingPoleType) {
        BackPack backPack = this.backPack;
        currentTool = (Tool) backPack.getBackPackItems().get(fishingPoleType).get(0);
    }

    public Ability getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability abilities) {
        this.abilities = abilities;
    }

    public HashSet<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(HashSet<CraftingRecipe> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public HashSet<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashSet<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addCoin(double coin) {
        if (!this.getPartner().equals(this)) {
            addcoin(coin);
            this.getPartner().addcoin(coin);
        } else {
            this.addcoin(coin);
        }
    }

    public void addcoin(double coin) {
        this.coin += coin;
    }

    public UUID getActiveGameID() {
        return activeGameID;
    }

    public void setActiveGameID(UUID activeGameID) {
        this.activeGameID = activeGameID;
    }

    public String getTargetPlayerToTrade() {
        return targetPlayerToTrade;
    }

    public void setTargetPlayerToTrade(String targetPlayerToTrade) {
        this.targetPlayerToTrade = targetPlayerToTrade;
    }

    public HashMap<String, Integer> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(HashMap<String, Integer> suggestions) {
        this.suggestions = suggestions;
    }

    public HashMap<String, Integer> getRequierd() {
        return requierd;
    }

    public void setRequierd(HashMap<String, Integer> requierd) {
        this.requierd = requierd;
    }

    public ArrayList<Food> getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(ArrayList<Food> refrigerator) {
        this.refrigerator = refrigerator;
    }
}
