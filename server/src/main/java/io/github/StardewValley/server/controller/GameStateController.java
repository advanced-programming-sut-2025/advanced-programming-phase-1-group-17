package io.github.StardewValley.server.controller;


import ch.qos.logback.core.subst.Token;
import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.controller.logicControllers.FarmingController;
import io.github.StardewValley.server.controller.logicControllers.ToolController;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.dto.HandleClickRequest;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.Gift;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.enums.CheatCodeCommands;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.foraging.ForagingController;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.plant.Fertilizer;
import io.github.StardewValley.shared.models.plant.Sapling;
import io.github.StardewValley.shared.models.plant.Seed;
import io.github.StardewValley.shared.models.tools.Tool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/gameState")
public class GameStateController {
    private final ToolController toolController = new ToolController();
    private final FarmingController farmingController = new FarmingController();

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GameStateController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/game/map")
    public ResponseEntity<List<TileDTO>> getGameMap(
        @RequestHeader("Authorization") String token,
        @RequestParam int minX,
        @RequestParam int maxX,
        @RequestParam int minY,
        @RequestParam int maxY
    ) {
        List<TileDTO> tileDTOs = new ArrayList<>();
        for (int i = minX - 1; i < maxX; i++) {
            for (int j = minY - 1; j < maxY; j++) {
                tileDTOs.add(new TileDTO(Objects.requireNonNull(Tile.getTile(i + 1, j + 1))));
            }
        }
        return ResponseEntity.ok(tileDTOs);
    }

    @PostMapping("/game/player/update")
    public ResponseEntity<PlayerDto> updatePlayer(
        @RequestHeader("Authorization") String token,
        @RequestBody Map<String, Object> body) {
        String token1 = token.substring(7);
        String username = jwtService.extractUsername(token1);

        float delta = ((Number) body.get("delta")).floatValue();
        boolean up = (Boolean) body.get("upPressed");
        boolean down = (Boolean) body.get("downPressed");
        boolean left = (Boolean) body.get("leftPressed");
        boolean right = (Boolean) body.get("rightPressed");
        Player player = null;
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            if (p.getUser().getUsername().equals(username)) {
                player = p;
                break;
            }
        }
        if (player != null)
            player.update(delta, up, down, left, right);

        PlayerDto pd = new PlayerDto(player.isPassedOut()
            , player.getEnergy()
            , player.getMaxEnergy()
            , player.isEnergyUnlimited()
            , player.isHasPassedOutToday()
            , player.getX(), player.getY(), player.getCurrentDirection()
            , player.getSpeed(), player.getLastDirection()
            , player.getCoin(), player.getAnimationTimer()
            , player.getPassOutTimer());

        return ResponseEntity.ok(pd);
    }

    @PostMapping("/selectMap")
    public ResponseEntity<Void> selectMap(@RequestHeader("Authorization") String authHeader, @RequestParam int type) {
        String token = authHeader.substring(7);
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            if (p.getUser().getUsername().equals(jwtService.extractUsername(token))) {
                p.getPlayerMap().setType(type);
                break;
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUserNameByToken")
    public ResponseEntity<String> getUserNameByToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(username);
    }
    @PostMapping("/exitGame")
    public ResponseEntity<Boolean> exitGame(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        if (!username.equals(AppServer.getCurrentGame().getCreator().getUser().getUsername()))
            return ResponseEntity.ok(false);
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            User user = userRepository.findByUsername(p.getUser().getUsername()).get();
            p.getUser().setLastGame(AppServer.getCurrentGame());
            p.getUser().setActiveGame(null);
            if (p.isGuest()) continue;
            p.getUser().setTheMostMoneyInGame(Math.max(p.getUser().getTheMostMoneyInGame(), p.getBackPack().getCoin()));
            UserDTO userDTO = p.getUser();
            user.setEmail(userDTO.getEmail());
            user.setAvatar(userDTO.getAvatar());
            user.setUsername(userDTO.getUsername());
            user.setNickName(userDTO.getNickname());
            user.setTheMostMoneyInGame(userDTO.getTheMostMoneyInGame());
            user.setSecurityQuestion(userDTO.getSecurityQuestion());
            user.setSecurityAnswer(userDTO.getSecurityAnswer());
            user.setNumOfPlay(userDTO.getNumOfPlay());
            user.setPasswordHash(userDTO.getPasswordHash());
            userRepository.save(user);
        }
        AppServer.setCurrentGame(null);
        return ResponseEntity.ok(true);
    }



    @PostMapping("/game/handleClick")
    public ResponseEntity<Result> handleClick(@RequestBody HandleClickRequest request, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token); // Authenticate and get the correct Player
        Result result = null;
        if (player.getEquippedItem() instanceof Tool)
            result = toolController.toolUse(request.getDx(), request.getDy(), player);
        else if (player.getEquippedItem() instanceof CraftingItem)
            result = toolController.placeCraftingItem(request.getDx(), request.getDy(), player);
        else if (player.getEquippedItem() instanceof Seed seed)
            result = farmingController.plantSeed(seed, request.getDx(), request.getDy(), player);
        else if (player.getEquippedItem() instanceof Sapling sapling)
            result = farmingController.plantSapling(sapling, request.getDx(), request.getDy(), player);
        else if (player.getEquippedItem() instanceof Fertilizer fertilizer)
            result = farmingController.fertilize(fertilizer, request.getDx(), request.getDy(), player);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/game/Foraging/pickForaging")
    public void pickForaging(@RequestBody HandleClickRequest request, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        ForagingController.pickForaging(request.getDx(), request.getDy(), player);
    }

    public Player getPlayerFromToken(String token) {
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Game activeGame = user.getActiveGame();
        if (activeGame == null) {
            throw new RuntimeException("User is not in an active game");
        }

        return activeGame.getPlayerByUsername(username);
    }

    public Game getGameFromToken(String token) {
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Game activeGame = user.getActiveGame();
        if (activeGame == null) {
            throw new RuntimeException("No active game found for user");
        }

        return activeGame;
    }
    @PostMapping("/questFinish")
    public  ResponseEntity<Result> questFinish(@RequestHeader("Authorization") String token,@RequestParam String index) {
        int i = Integer.parseInt(index);
        if (i < 1 || i > 3) {
            return ResponseEntity.ok(new Result(false, "invalid index"));
        }

        Player currentPlayer = getPlayerFromToken(token);
        NPC npc = null;
        for (NPC npc2 : AppServer.getCurrentGame().getNPCs()) {
            if (sideBySide(currentPlayer, npc2)) {
                npc = npc2;
                break;
            }
        }
        if (npc == null) {
            return ResponseEntity.ok(new Result(false, "you must be next to the NPC to complete the mission"));
        } else {
            Quest quest = npc.getRequests().get(i - 1);
            if (quest.isCompleted()) {
                return ResponseEntity.ok(new Result(false, "quest already completed"));
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
                        return ResponseEntity.ok(new Result(true, "the mission was successfully completed.\n" +
                            "your reward has been added to your backpack"));
                    } else {
                        return ResponseEntity.ok(new Result(false, "you can't finish quest because you do not have a the required item"));
                    }
                } else {
                    return ResponseEntity.ok(new Result(false, "you can't finish quest because you do not have a the required level"));
                }
            }
        }
    }
    @PostMapping("/friendship")
    public ResponseEntity<Result> friendship(@RequestHeader("Authorization") String token, @RequestParam String userNameOfPlayer) {
        Player currentPlayer = getPlayerFromToken(token);
        Player player = null;
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            if (p.getUser().getUsername().equals(userNameOfPlayer)) {
                player = p;
                break;
            }

        }
        String result = "";
        result += "your friendship amount with " + player.getUser().getUsername() + " : " +
            currentPlayer.getFriendShips().get(player) + "\n" + "your friendship level : "
            + String.valueOf((int) Math.floor(currentPlayer.getFriendShips().get(player) / 100)) + "\n";
        return ResponseEntity.ok(new Result(true, result));
    }
    @PostMapping("/talk")
    public ResponseEntity<Result> talk(@RequestHeader("Authorization") String token,@RequestParam String username, @RequestParam String massage) {
        Player currentPlayer = getPlayerFromToken(token);
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
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
                        return ResponseEntity.ok(new Result(true, "your message sent to " + player.getUser().getUsername()));
                    } else {
                        return ResponseEntity.ok(new Result(false, "you can't talk from this distance"));
                    }
                } else {
                    return ResponseEntity.ok(new Result(false, "there isn't player in this game with this username"));
                }
            }
        }
        return ResponseEntity.ok(new Result(false, "there isn't player in this game with this username"));
    }
    @PostMapping("/talkHistory")
    public ResponseEntity<Result> talkHistory(@RequestHeader("Authorization") String token,@RequestParam String username) {
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (getPlayerFromToken(token).getTalk().get(player) != null) {
                    return ResponseEntity.ok(new Result(true,getPlayerFromToken(token).getTalk().get(player).getTalk()));
                }
            }
        }
        return ResponseEntity.ok(new Result(false,""));
    }
    @PostMapping("/gift")
    public ResponseEntity<Result> gift(@RequestHeader("Authorization") String token,@RequestParam String username, @RequestParam String item,@RequestParam String amount) {
        int Amount;
        try {
            Amount = Integer.parseInt(amount);
        } catch (Exception e) {
            return ResponseEntity.ok(new Result(false, e.getMessage()));
        }
        Player currentPlayer = getPlayerFromToken(token);

        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().get(player) != null) {
                    if (sideBySide(currentPlayer, player)) {
                        if (currentPlayer.getFriendShips().get(player) < 100) {
                            return ResponseEntity.ok(new Result(false, "your level is less than 1"));
                        } else {
                            if (currentPlayer.getBackPack().getInventorySize(item) < Amount) {
                                return ResponseEntity.ok(new Result(false, "insufficient inventory"));
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
                                return ResponseEntity.ok(new Result(true, "your gift was received by " + player.getUser().getUsername()));
                            }
                        }
                    } else {
                        return ResponseEntity.ok(new Result(false, "you can't gift from this distance"));
                    }
                }
            }
        }
        return ResponseEntity.ok(new Result(false, "there isn't player in this game with this username"));
    }
    @PostMapping("/giftList")
    public ResponseEntity<Result> giftList(@RequestHeader("Authorization") String token) {
        Player currentPlayer = getPlayerFromToken(token);
        String result = "";
        for (Player player : currentPlayer.getGifts().keySet()) {
            result += player.getUser().getUsername() + "\n";
            for (Gift gift : currentPlayer.getGifts().get(player)) {
                if (currentPlayer.equals(gift.getPlayerWhoGetGift())) {
                    result += gift.getItem() + " : (amount:)" + gift.getAmount() + " ---> (gift number:)" + gift.getGiftNumber() + "\n";
                }
            }
        }
        return ResponseEntity.ok(new Result(true, result));
    }

    @PostMapping("/giftRate")
    public  ResponseEntity<Result> giftRate(@RequestHeader("Authorization") String token,@RequestParam String giftNumber,@RequestParam String rate) {
        if (CheatCodeCommands.Int.getMatcher(rate) == null || CheatCodeCommands.Int.getMatcher(giftNumber) == null) {
            return ResponseEntity.ok(new Result(true, "your rate or giftNumber is not valid"));
        } else if (Integer.parseInt(rate) > 5 || Integer.parseInt(rate) < 1) {
            return ResponseEntity.ok(new Result(true, "your rate is not valid"));
        } else {
            Player currentPlayer = getPlayerFromToken(token);
            for (Player player : currentPlayer.getGifts().keySet()) {
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    if (gift.getGiftNumber() == Integer.parseInt(giftNumber) && gift.getPlayerWhoGetGift() == currentPlayer) {
                        if (!gift.getRateGiven()) {
                            gift.setRateGiven(true);
                            currentPlayer.getFriendShips().put(player, currentPlayer.getFriendShips().get(player) + (Integer.parseInt(rate) - 3) * 30 + 15);
                            player.getFriendShips().put(currentPlayer, currentPlayer.getFriendShips().get(player));
                            return ResponseEntity.ok(new Result(true, "your rate was recorded as " + Integer.parseInt(rate)));
                        } else {
                            return ResponseEntity.ok(new Result(true, "you rate to this gift previously"));
                        }
                    }
                }
            }


        }
        return ResponseEntity.ok(new Result(false, "you have not received a gift with this giftNumber"));
    }


    @PostMapping("/giftHistory")
    public  ResponseEntity<Result> giftHistory(@RequestHeader("Authorization") String token,@RequestParam String username) {
        Player currentPlayer = getPlayerFromToken(token);
        if (username.equals( getPlayerFromToken(token).getUser().getUsername())) {
            return ResponseEntity.ok(new Result(false,"you can't gift to your self."));
        }
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                String result = "";
                result += player.getUser().getUsername() + "\n";
                for (Gift gift : currentPlayer.getGifts().get(player)) {
                    result += "whoGetGift : " + gift.getPlayerWhoGetGift().getUser().getUsername() + "\n" + gift.getItem() + " : (amount:)" + gift.getAmount() + " ---> (gift number:)" + gift.getGiftNumber() + "\n";
                }
                return ResponseEntity.ok(new Result(true, result));
            }
        }
        return ResponseEntity.ok(new Result(false,"this username there is not in this game"));

    }
    @PostMapping("/hug")
    public  ResponseEntity<Result> hug(@RequestHeader("Authorization") String token,@RequestParam String username) {
        if (getPlayerFromToken(token).getUser().getUsername().equals(username)) {
            return ResponseEntity.ok(new Result(false, "you can't hug yourself"));
        }
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (getPlayerFromToken(token).getFriendShips().get(player) >= 200) {
                    if (sideBySide(player, getPlayerFromToken(token))) {
                        getPlayerFromToken(token).getFriendShips().put(
                            player, (getPlayerFromToken(token).getFriendShips().get(player) + 60));
                        player.getFriendShips().put(getPlayerFromToken(token),
                            getPlayerFromToken(token).getFriendShips().get(player));
                        if (player.getPartner().equals(getPlayerFromToken(token)) && !player.isInteractionWithPartner()) {
                            player.setEnergy(player.getEnergy() + 50);
                            getPlayerFromToken(token).setEnergy(getPlayerFromToken(token).getEnergy() + 50);
                        }
                        return ResponseEntity.ok(new Result(true, "you hug your friend ^^"));
                    } else {
                        return ResponseEntity.ok(new Result(false, "you can't hug your friend from this distance"));
                    }
                } else {
                    return ResponseEntity.ok( new Result(false, "your level less than 2"));
                }
            }
        }
        return ResponseEntity.ok(new Result(false, "this username does not exist in this game"));
    }
    @PostMapping("/flower")
    public  ResponseEntity<Result> flower(@RequestHeader("Authorization") String token,@RequestParam String username) {
        Player currentPlayer = getPlayerFromToken(token);
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (currentPlayer.getFriendShips().containsKey(player)) {
                    if (currentPlayer.getFriendShips().get(player) < 200) {
                        return ResponseEntity.ok(new Result(false, "your friendship level is less than 2"));
                    } else {
                        if (currentPlayer.getBackPack().getInventorySize("FLOWER") > 0) {
                            if (currentPlayer.getFriendShips().get(player) < 300) {
                                currentPlayer.getFriendShips().put(player, 300);
                                player.getFriendShips().put(currentPlayer, 300);
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                return ResponseEntity.ok(new Result(true, "Flower were presented to " + player.getUser().getUsername()));
                            } else {
                                BackPackable b = currentPlayer.getBackPack().useItem("FLOWER");
                                player.getBackPack().addItemToInventory(b);
                                if (player.getPartner().equals(currentPlayer) && !player.isInteractionWithPartner()) {
                                    player.setEnergy(player.getEnergy() + 50);
                                    currentPlayer.setEnergy(currentPlayer.getEnergy() + 50);
                                }
                                return  ResponseEntity.ok(new Result(true, "Flower were presented to " + player.getUser().getUsername()));
                            }
                        } else {
                            return  ResponseEntity.ok(new Result(false, "insufficient inventory"));
                        }

                    }
                }
                return  ResponseEntity.ok(new Result(false, "you can't give flower to your self"));
            }
        }
        return  ResponseEntity.ok(new Result(false, "this username does not exist in this game"));
    }
    @PostMapping("/askMarriage")
    public  ResponseEntity<Result> askMarriage(@RequestHeader("Authorization") String token,@RequestParam String username,@RequestParam String ring) {
        Player currentPlayer = getPlayerFromToken(token);
        for (Player player : AppServer.getCurrentGame().getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                if (sideBySide(player, currentPlayer)) {
                    if (currentPlayer.getFriendShips().containsKey(player)) {
                        if (currentPlayer.getFriendShips().get(player) < 300) {
                            return  ResponseEntity.ok(new Result(false, "your friendship level is less than 3"));
                        } else {
                            if (currentPlayer.getUser().getGender().equals(Gender.Female)) {
                                return  ResponseEntity.ok(new Result(false, "you can't ask marriage"));
                            } else if (player.getUser().getGender() == currentPlayer.getUser().getGender()) {
                                return ResponseEntity.ok( new Result(false, "khejalat bekesh dadash (abjy)"));
                            } else if (currentPlayer.getBackPack().getInventorySize(ring) < 1) {
                                return  ResponseEntity.ok(new Result(false, "you haven't Ring for ask marriage"));
                            } else {
                                message message = new message(currentPlayer, "ask for marriage with "
                                    + getPlayerFromToken(token).getUser().getUsername());
                                player.getMessage().add(message);
                                return  ResponseEntity.ok(new Result(true, "your marriage request has been sent"));
                            }
                        }
                    } else {
                        return  ResponseEntity.ok(new Result(false, "you can't ask marriage to your self"));
                    }
                } else {
                    return  ResponseEntity.ok(new Result(false, "you can't ask marriage from this distance"));
                }
            }
        }
        return  ResponseEntity.ok(new Result(false, "this username does not exist in this game"));
    }
    @PostMapping("/respond")
    public  ResponseEntity<Result> respond(@RequestHeader("Authorization") String token,@RequestParam String accept,@RequestParam String username) {
        Player currentPlayer = getPlayerFromToken(token);
        for (message m : currentPlayer.getMessage()) {
            if (m.getMessage().startsWith("ask for marriage")) {
                for (Player player : AppServer.getCurrentGame().getPlayers()) {
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
                                message m1 = new message(getPlayerFromToken(token)
                                    , "oh my God, I was taken by surprise. I thought about it. I accept");
                                player.addMessage(m1);
                                return  ResponseEntity.ok(new Result(true, "Congratulations, you got married"));
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
                                message m1 = new message(getPlayerFromToken(token), "i do not intend to marry");
                                player.addMessage(m1);
                                return  ResponseEntity.ok(new Result(true, "request was rejected"));
                            }
                        } else {
                            return  ResponseEntity.ok(new Result(false, "this username did not request marriage to you"));
                        }
                    }
                }
                return  ResponseEntity.ok(new Result(false, "this username does not exist in this game"));
            }
        }

        return  ResponseEntity.ok(new Result(false, "this username did not request marriage to you"));
    }

    @PostMapping("/giftNPC")
    public ResponseEntity<Result> giftNPC(@RequestHeader("Authorization") String token,@RequestParam String npc1,@RequestParam String item,@RequestParam String amount) {
        NPC npc = AppServer.getCurrentGame().getNPC(npc1);
        Player currentPlayer = getPlayerFromToken(token);
        if (currentPlayer.getBackPack().getInventorySize(item) == 0) {
            return  ResponseEntity.ok(new Result(false, "your inventory is empty"));
        } else {
            int Amount;
            try {
                Amount = Integer.parseInt(amount);
            }catch (NumberFormatException e) {
                return  ResponseEntity.ok(new Result(false, "amount is not a number"));
            }
            for (int i = 0; i < Amount ; i++) {
                currentPlayer.getBackPack().useItem(item);
            }
            if (!currentPlayer.getGiftNPCToday().get(npc)) {
                if (npc.getFavorites().contains(item)) {
                    currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 200));
                    currentPlayer.getGiftNPCToday().put(npc, true);
                    return  ResponseEntity.ok(new Result(true, "your beautiful gift was received by  " + npc.getName()));
                } else {
                    currentPlayer.getGiftNPCToday().put(npc, true);
                    currentPlayer.getFriendShipsWithNPCs().put(npc, Math.min(799, currentPlayer.getFriendShipsWithNPCs().get(npc) + 50));
                    return  ResponseEntity.ok(new Result(true, "your gift was received by  " + npc.getName()));
                }
            } else {
                return  ResponseEntity.ok(new Result(true, "your gift was received by  " + npc.getName()));
            }

        }
    }
    @PostMapping("/friendshipNPCList")
    public ResponseEntity<Result> friendshipNPCList(@RequestHeader("Authorization") String token,@RequestParam String npc1) {
        NPC npc = AppServer.getCurrentGame().getNPC(npc1);
        Player currentPlayer =  getPlayerFromToken(token);
        String result = "";
        result += ("friendship score with " + npc.getName()
            + " : " + currentPlayer.getFriendShipsWithNPCs().get(npc)
            + "\n" + "friendship level with " + npc.getName() + " : "
            + currentPlayer.getFriendShipsWithNPCs().get(npc) / 200 + "\n" + "-------------" + "\n");
        return  ResponseEntity.ok(new Result(true  ,result));
    }
    @PostMapping("/questsList")
    public ResponseEntity<Result> questsList(@RequestHeader("Authorization") String token) {
        String result = "";
        Player currentPlayer = getPlayerFromToken(token);
        result += "Only missions with your level are active for you.\n";
        for (NPC npc : currentPlayer.getFriendShipsWithNPCs().keySet()) {
            int temp = currentPlayer.getFriendShipsWithNPCs().get(npc) / 200;
            result += (npc.getName() + "   your friendship level: " + temp + "\n"
                + "1- questLeve: " + npc.getRequests().get(0).getLevel() + "\n quest explanation: "
                + npc.getRequests().get(0).getQuestExplanation() + "\n" +
                (npc.getRequests().get(0).isCompleted() ? " is completed" : " not completed")
                + "\n2- questLeve: "
                + npc.getRequests().get(1).getLevel()
                + "\n quest explanation: " + npc.getRequests().get(1).getQuestExplanation() + "\n" +
                (npc.getRequests().get(1).isCompleted() ? " is completed" : " not completed")
                + "\n3- questLeve: " + npc.getRequests().get(2).getLevel()
                + "\n quest explanation: " + npc.getRequests().get(2).getQuestExplanation() + "\n" +
                (npc.getRequests().get(2).isCompleted() ? " is completed" : " not completed")
                + "\n" + "------------------------------------" + "\n");
        }
        return  ResponseEntity.ok(new Result(false, result));
    }
    @PostMapping("/showMessage")
    public ResponseEntity<Result> showMessage(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok( new Result(true,  getPlayerFromToken(token).getStringMessage()));
    }
    @PostMapping("/deleteMessage")
    public ResponseEntity<Result> deleteMessage(@RequestHeader("Authorization") String token,@RequestParam int index) {
        if (index >= getPlayerFromToken(token).getMessage().size()) {
            return  ResponseEntity.ok(new Result(false, "there are no messages with this index"));
        } else {
            message message = getPlayerFromToken(token).getMessage().get(index);
            getPlayerFromToken(token).getMessage().remove(message);
            return  ResponseEntity.ok(new Result(true, "message delete successfully"));
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

}
