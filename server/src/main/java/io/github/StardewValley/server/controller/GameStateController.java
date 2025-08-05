package io.github.StardewValley.server.controller;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.controller.logicControllers.CheatCodeHandler;
import io.github.StardewValley.server.controller.logicControllers.FarmingController;
import io.github.StardewValley.server.controller.logicControllers.GameWorldController;
import io.github.StardewValley.server.controller.logicControllers.ToolController;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.LightningLogicController;
import io.github.StardewValley.shared.dto.*;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.artisan.ArtisanProduct;
import io.github.StardewValley.shared.models.artisan.ArtisanProductType;
import io.github.StardewValley.shared.models.backpack.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.enums.CheatCodeCommands;
import io.github.StardewValley.shared.models.foraging.ForagingController;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.market.MarketsController;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.shared.models.market.StoreInventory;
import io.github.StardewValley.shared.models.plant.Fertilizer;
import io.github.StardewValley.shared.models.plant.Sapling;
import io.github.StardewValley.shared.models.plant.Seed;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/api/gameState")
public class GameStateController {
    private final ToolController toolController = new ToolController();
    private final FarmingController farmingController = new FarmingController();
    private final GameWorldController gameWorldController = new GameWorldController();

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GameStateController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

//    @GetMapping("/game/map")
//    public ResponseEntity<List<TileDTO>> getGameMap(
//        @RequestHeader("Authorization") String token,
//        @RequestParam int minX,
//        @RequestParam int maxX,
//        @RequestParam int minY,
//        @RequestParam int maxY
//    ) {
//        List<TileDTO> tileDTOs = new ArrayList<>();
//        for (int i = minX - 1; i < maxX; i++) {
//            for (int j = minY - 1; j < maxY; j++) {
//                tileDTOs.add(new TileDTO(Objects.requireNonNull(Tile.getTile(i + 1, j + 1))));
//            }
//        }
//        return ResponseEntity.ok(tileDTOs);
//    }

    @GetMapping("/game/map")
    public ResponseEntity<GetGameStateResponse> getGameMap(
        @RequestHeader("Authorization") String token,
        @RequestParam int minX,
        @RequestParam int maxX,
        @RequestParam int minY,
        @RequestParam int maxY
    ) {
        Game game = getGameFromToken(token);
        List<TileDTO> tileDTOs = new ArrayList<>();
        List<CraftingItemDTO> craftingItemDTOs = new ArrayList<>();
        for (int i = minX - 1; i < maxX; i++) {
            for (int j = minY - 1; j < maxY; j++) {
                Tile tile = Tile.getTile(i + 1, j + 1);
                tileDTOs.add(new TileDTO(Objects.requireNonNull(tile)));
                if (tile.getPlaceable() instanceof CraftingItem craftingItem) {
                    craftingItemDTOs.add(CraftingItem.getCraftingItemDTO(craftingItem));
                }
            }
        }
        return ResponseEntity.ok(new GetGameStateResponse(
            craftingItemDTOs,
            tileDTOs,
            game.getLightningLogicController().getLightningStateDTO()
        ));
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
    public ResponseEntity<HandleWorldClickResponse> handleClick(@RequestBody HandleWorldClickRequest request, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token); // Authenticate and get the correct Player
        float x = request.getX();
        float y = request.getY();
        int button = request.getButton();

        HandleWorldClickResponse response = gameWorldController.checkBounds(x, y, button, player);
        if (!response.isSuccessful() || !response.getActionType().equals(HandleWorldClickResponse.ActionType.NONE))
            return ResponseEntity.ok(response);

        // Convert world coordinates to tile positions
        int clickedTileX = (int) (x / GameAssetManager.getGameAssetManager().getTileWidth());
        int clickedTileY = (int) (y / GameAssetManager.getGameAssetManager().getTileHeight());
        int dx = clickedTileX - player.getTileX();
        int dy = clickedTileY - player.getTileY();

        Result result = new Result(false, "");
        if (Math.abs(dx) + Math.abs(dy) == 1) {
            if (player.getEquippedItem() instanceof Tool)
                result = toolController.toolUse(dx, dy, player);
            else if (player.getEquippedItem() instanceof CraftingItem)
                result = toolController.placeCraftingItem(dx, dy, player);
            else if (player.getEquippedItem() instanceof Seed seed)
                result = farmingController.plantSeed(seed, dx, dy, player);
            else if (player.getEquippedItem() instanceof Sapling sapling)
                result = farmingController.plantSapling(sapling, dx, dy, player);
            else if (player.getEquippedItem() instanceof Fertilizer fertilizer)
                result = farmingController.fertilize(fertilizer, dx, dy, player);
        }
        return ResponseEntity.ok(new HandleWorldClickResponse(result.isSuccessful(), result.getMessage(), HandleWorldClickResponse.ActionType.NONE));
    }


    @PostMapping("/game/cheatCode/handleCheatCode")
    public ResponseEntity<Result> handleCheatCode(@RequestBody String command, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        String result = "invalid Command";
        Matcher matcher;

        if ((matcher = CheatCodeCommands.CheatAdvanceTime.getMatcher(command)) != null) {
            result = CheatCodeHandler.changeTime(
                matcher.group("hour")
            );
        } else if ((matcher = CheatCodeCommands.CheatAdvanceDate.getMatcher(command)) != null) {
            result = CheatCodeHandler.changeDate(
                matcher.group("day")
            );
        } else if ((matcher = CheatCodeCommands.CheatThor.getMatcher(command)) != null) {
            result = CheatCodeHandler.cheatThor(
                Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y")),
                getGameFromToken(token)
            );
        } else if ((matcher = CheatCodeCommands.CheatWeatherSet.getMatcher(command)) != null) {
            result = CheatCodeHandler.changeWeather(
                matcher.group("type")
            );
        } else if ((matcher = CheatCodeCommands.EnergyUnlimited.getMatcher(command)) != null) {
            result = CheatCodeHandler.energyUnlimited(player);
        } else if ((matcher = CheatCodeCommands.CheatAddItem.getMatcher(command)) != null) {
            result = CheatCodeHandler.addItem(matcher.group("itemName"), matcher.group("count"), player);
        } else if ((matcher = CheatCodeCommands.CheatSetFriendshipWithAnimal.getMatcher(command)) != null) {
            result = CheatCodeHandler.setFriendship(matcher.group("animalName"),
                matcher.group("amount"));
        } else if ((matcher = CheatCodeCommands.CheatAddDollars.getMatcher(command)) != null) {
            result = CheatCodeHandler.cheatAddDollars(
                matcher.group("count"), player
            );
        }
        return ResponseEntity.ok(new Result(true, result));
    }


    @PostMapping("/game/Foraging/pickForaging")
    public void pickForaging(@RequestBody PickForaingRequest request, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        ForagingController.pickForaging(request.getDx(), request.getDy(), player);
    }


    @PostMapping("/game/market/purchase")
    public ResponseEntity<Result> purchaseItem(@RequestBody PurchaseRequest request, @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        Game game = player.getUser().getActiveGame();
        MarketsController marketsController = player.getUser().getActiveGame().getMarketsController();
        return ResponseEntity.ok(
            marketsController.purchase(request.getShopItemDTO(), request.getCount(),
                request.getStoreType(), player, game.getDate().getSeason())
        );
    }

    @PostMapping("/game/market/getInventory")
    public ResponseEntity<GetMarketInventoryResponse> getMarketInventory(@RequestBody GetMarketInventoryRequest request,
                                                                         @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        Game game = player.getUser().getActiveGame();
        MarketsController marketsController = player.getUser().getActiveGame().getMarketsController();
        StoreInventory inventory = marketsController.getInventory(request.getStoreType());
        return ResponseEntity.ok(new GetMarketInventoryResponse(
            inventory.getItemDTOs(game.getDate().getSeason(), request.getStoreType()),
            inventory.getUpgradeServiceDTOs()
        ));
    }

    @PostMapping("/game/backpack/equipItem")
    public void equipItem(@RequestBody String request,
                          @RequestHeader("Authorization") String token){
        //TODO: maybe we can delete player.currentTool
        Player player = getPlayerFromToken(token);
        BackPackable backPackable = player.getBackPack().getFromDTO(request);
        player.setEquippedItem(backPackable);
        player.setCurrentTool(null);
        if (backPackable.getType() instanceof ToolType toolType)
            player.toolEquip(toolType);
        else if (backPackable.getType() instanceof FishingPoleType fishingPoleType)
            player.fishingPoleEquip(fishingPoleType);
    }


    @PostMapping("/game/backpack/trashItem")
    public ResponseEntity<String> trashItem(@RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        if (player.getEquippedItem() == null) {
            return ResponseEntity.ok("You haven't picked any item.");
        } else {
            BackPackable backPackable = player.getEquippedItem();
            if (player.getEquippedItem() instanceof Tool tool)
                player.setCurrentTool(null);
            player.setEquippedItem(null);

            if (player.getBackPack().getInventorySize(backPackable.getType().getName()) == 1)
                player.getBackPack().getBackPackItems().remove(backPackable.getType());
            else
                player.getBackPack().getBackPackItems().get(backPackable.getType()).remove(0);

            toolController.handleRefund(backPackable, player);
            return ResponseEntity.ok("Item deleted from Inventory");
        }
    }


    @PostMapping("/game/greenhouse/buildGreenhouse")
    public ResponseEntity<Result> buildGreenhouse(@RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        if (player.getBackPack().getCoin() < 1000) {
            return ResponseEntity.ok(new Result(false, "You only have %.2f coin. (not enough)".formatted(
                player.getBackPack().getCoin())));
        }

        int woodCount = player.getBackPack().getInventorySize(NormalItemType.Wood.getName());
        if (woodCount < 500) {
            return ResponseEntity.ok(new Result(false, "You only have %d wood. (not enough wood)".formatted(woodCount)));
        }

        player.getBackPack().addCoin(-1000);
        for (int i = 0; i < 500; i++)
            player.getBackPack().useItem(NormalItemType.Wood);

        player.getPlayerMap().getGreenHouse().setActive(true);
        return ResponseEntity.ok(new Result(true, "Greenhouse built successfully!"));
    }

    @PostMapping("/game/shippingBin/sellItem")
    public ResponseEntity<Boolean> sellItem(@RequestBody SellItemRequest request,
                                            @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        BackPack backPack = player.getBackPack();
        Tile shippingBinTile = Tile.getTile(request.getShippingBinTile().getX(), request.getShippingBinTile().getY());
        ShippingBin shippingBin = (ShippingBin) shippingBinTile.getPlaceable();

        BackpackableTypeDTO itemType = request.getItem();
        for (int i = 0; i < request.getQuantity(); i++) {
            BackPackable backPackable = backPack.getFromDTO(itemType.getName());
            shippingBin.addItem(backPackable);
            backPack.useItem(backPackable.getType());
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/game/artisanProduct/takeProduct")
    public ResponseEntity<Boolean> takeArtisanProduct(@RequestBody CraftingItemDTO craftingItemDTO,
                                                      @RequestHeader("Authorization") String token) {

        Player player = getPlayerFromToken(token);
        Tile craftingItemTile = Tile.getTile(craftingItemDTO.getTileX(), craftingItemDTO.getTileY());
        if (craftingItemTile == null)
            return ResponseEntity.ok(false);
        CraftingItem craftingItem = (CraftingItem) craftingItemTile.getPlaceable();
        player.getBackPack().addItemToInventory(craftingItem.getArtisanProductInProgress());
        craftingItem.setArtisanProductInProgress(null);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/game/artisanProduct/cancelProduction")
    public ResponseEntity<Boolean> cancelArtisanProduction(@RequestBody CraftingItemDTO craftingItemDTO,
                                                           @RequestHeader("Authorization") String token) {
        Tile craftingItemTile = Tile.getTile(craftingItemDTO.getTileX(), craftingItemDTO.getTileY());
        if (craftingItemTile == null)
            return ResponseEntity.ok(false);
        CraftingItem craftingItem = (CraftingItem) craftingItemTile.getPlaceable();
        craftingItem.setArtisanProductInProgress(null);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/game/artisanProduct/craftArtisan")
    public ResponseEntity<Result> craftArtisan(@RequestBody CraftArtisanRequest request,
                                               @RequestHeader("Authorization") String token) {
        Player player = getPlayerFromToken(token);
        Tile craftingItemTile = Tile.getTile(request.getCraftingItemDTO().getTileX(), request.getCraftingItemDTO().getTileY());
        if (craftingItemTile == null)
            return ResponseEntity.ok(new Result(false, "Crafting Item not found."));
        CraftingItem artisan = (CraftingItem) craftingItemTile.getPlaceable();
        if (!artisan.getOwner().equals(player))
            return ResponseEntity.ok(new Result(false, "This crafting Item is not yours."));

        if (artisan.getArtisanProductInProgress() != null) {
            return ResponseEntity.ok(new Result(false, "Artisan is already crafting a product!"));
        }

        // Try to match an ArtisanProductType with given artisan and ingredients
        for (ArtisanProductType product : ArtisanProductType.values()) {
            if (!product.getArtisan().equals(artisan.getType())) continue;

            boolean matched = true;
            for (BackpackableTypeDTO backPackableTypeDTO : request.getSelectedItems().keySet()) {
                if (!product.containsDTO(backPackableTypeDTO)) {
                    matched = false;
                    break;
                } else if (request.getSelectedItems().get(backPackableTypeDTO) < product.getIngredients().get(backPackableTypeDTO)) {
                    matched = false;
                    break;
                }
            }
            if (!matched || (product.getIngredients().size() != request.getSelectedItems().size()))
                continue;

            ArrayList<BackPackableType> provided = new ArrayList<>();
            for (BackpackableTypeDTO backpackableTypeDTO : request.getSelectedItems().keySet()) {
                try {
                    BackPackableType item = AppServer.getEnumInstance(backpackableTypeDTO.getClassName(), backpackableTypeDTO.getName());
                    provided.add(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ArtisanProduct artisanProduct = new ArtisanProduct(product, ArtisanProduct.getIngredient(product, provided));
            artisan.setArtisanProductInProgress(artisanProduct);

            for (BackpackableTypeDTO backPackableTypeDTO : request.getSelectedItems().keySet()) {
                try {
                    BackPackableType backPackableType = AppServer.getEnumInstance(backPackableTypeDTO.getClassName(), backPackableTypeDTO.getName());
                    if (request.getSelectedItems().get(backPackableTypeDTO) > 0) {
                        player.getBackPack().getBackPackItems().get(backPackableType).subList(0, request.getSelectedItems().get(backPackableTypeDTO)).clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return ResponseEntity.ok(new Result(true, "%s is now being crafted".formatted(product.getName())));
        }
        return ResponseEntity.ok(new Result(false, "Items given do not match any of the artisan product ingredients."));
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
}
