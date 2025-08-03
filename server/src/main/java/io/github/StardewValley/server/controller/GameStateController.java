package io.github.StardewValley.server.controller;


import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.controller.logicControllers.FarmingController;
import io.github.StardewValley.server.controller.logicControllers.ToolController;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.dto.HandleClickRequest;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.plant.Fertilizer;
import io.github.StardewValley.shared.models.plant.Sapling;
import io.github.StardewValley.shared.models.plant.Seed;
import io.github.StardewValley.shared.models.tools.Tool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
