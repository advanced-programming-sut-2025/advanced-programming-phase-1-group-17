package io.github.StardewValley.server.controller;


import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/gameState")
public class GameStateController {
    private final JwtService jwtService = new JwtService();
    private final UserRepository userRepository;

    public GameStateController(UserRepository userRepository) {
        this.userRepository = userRepository;
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




}
