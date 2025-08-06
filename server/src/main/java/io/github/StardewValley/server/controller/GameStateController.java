package io.github.StardewValley.server.controller;


import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.cooking.CookResponseDTO;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    @GetMapping("/hudData")
    public ResponseEntity<HudDataDTO> getHudData(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token.substring(7));
        Game game = AppServer.getCurrentGame();
        Player player = null;
        for (Player p : game.getPlayers()) {
            if (p.getUser().getUsername().equals(username)) {
                player = p;
                break;
            }
        }

        if (player == null) {
            return ResponseEntity.status(404).build(); // یا هر خطای مناسب دیگر
        }

        TimeAndDate date = game.getDate();


        StringBuilder time = new StringBuilder();
        int hour = date.getHour();
        int displayHour = hour % 12 == 0 ? 12 : hour % 12; // نمایش 12 به جای 0
        time.append(displayHour).append(":");
        time.append(String.format("%02d", date.getMinute())); // همیشه دو رقمی
        time.append(hour < 12 ? " am" : " pm");

        String dateString = date.getDayOfTheWeek() + ". " + date.getDay(); // مثال

        float timeAngle = ((float) ((date.getHour() - 9) * 180) /13 + date.getMinute()) *3/13;


        // ساخت DTO
        HudDataDTO hudData = new HudDataDTO(
            time.toString(),
            dateString,
            game.getDate().getSeason().toString(), // فرض می‌کنیم Season یک enum است
            game.getDate().getTodayWeatherType().toString(), // فرض می‌کنیم Weather یک enum است
            (int) player.getBackPack().getCoin(),
            player.getEnergy(),
            player.getMaxEnergy(),
            player.isEnergyUnlimited(),
            timeAngle
        );

        return ResponseEntity.ok(hudData);
    }
    @PostMapping("/craft")
    public ResponseEntity<CraftResponseDTO> attemptToCraft(
        @RequestHeader("Authorization") String token,
        @RequestParam String itemTypeName) { // نام آیتم را از کلاینت می‌گیریم

        String username = jwtService.extractUsername(token.substring(7));
        Player player = null;
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            if (p.getUser().getUsername().equals(username)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return ResponseEntity.status(401).body(new CraftResponseDTO(false, "Player not found."));
        }

        CraftingItemType typeToCraft;
        try {
            typeToCraft = CraftingItemType.valueOf(itemTypeName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CraftResponseDTO(false, "Invalid item type."));
        }

        // --- تمام منطق از CraftingController به اینجا منتقل می‌شود ---
        BackPack backPack = player.getBackPack();
        Map<BackPackableType, Integer> ingredients = typeToCraft.getIngredients();

        // 1. بررسی موجودی (در سرور!)
        for (Map.Entry<BackPackableType, Integer> entry : typeToCraft.getIngredients().entrySet()) {
            if (!(player.getBackPack().getBackPackItems().containsKey(entry.getKey())
                && player.getBackPack().getBackPackItems().get(entry.getKey()).size() >= entry.getValue())) {
                String message = "Not enough " + entry.getKey().getName() + ".";
                return ResponseEntity.ok(new CraftResponseDTO(false, message));
            }
        }

        // 2. کم کردن آیتم‌ها (در سرور!)
        for (Map.Entry<BackPackableType, Integer> entry : ingredients.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                backPack.useItem(entry.getKey());
            }
        }

        // 3. اضافه کردن آیتم جدید (در سرور!)
        CraftingItem craftedItem = new CraftingItem(typeToCraft, player);
        backPack.addItemToInventory(craftedItem);

        // 4. ارسال پاسخ موفقیت‌آمیز به کلاینت
        return ResponseEntity.ok(new CraftResponseDTO(true, "Crafted successfully!"));
    }
    @PostMapping("/cook")
    public ResponseEntity<CookResponseDTO> attemptToCook(
        @RequestHeader("Authorization") String token,
        @RequestParam String itemTypeName) { // نام آیتم را از کلاینت می‌گیریم

        String username = jwtService.extractUsername(token.substring(7));
        Player player = null;
        for (Player p : AppServer.getCurrentGame().getPlayers()) {
            if (p.getUser().getUsername().equals(username)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return ResponseEntity.status(401).body(new CookResponseDTO(false, "Player not found."));
        }

        FoodType type;
        try {
            type = FoodType.valueOf(itemTypeName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CookResponseDTO(false, "Invalid item type."));
        }

        // --- تمام منطق از CraftingController به اینجا منتقل می‌شود ---
        BackPack backPack = player.getBackPack();
        Map<BackPackableType, Integer> ingredients = type.getIngredients();

        // 1. بررسی موجودی (در سرور!)
        for (Map.Entry<BackPackableType, Integer> entry : type.getIngredients().entrySet()) {
            if (!(player.getBackPack().getBackPackItems().containsKey(entry.getKey())
                && player.getBackPack().getBackPackItems().get(entry.getKey()).size() >= entry.getValue())) {
                String message = "Not enough " + entry.getKey().getName() + ".";
                return ResponseEntity.ok(new CookResponseDTO(false, message));
            }
        }

        // 2. کم کردن آیتم‌ها (در سرور!)
        for (Map.Entry<BackPackableType, Integer> entry : ingredients.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                backPack.useItem(entry.getKey());
            }
        }

        // 3. اضافه کردن آیتم جدید (در سرور!)
        Food food = new Food(type);
        backPack.addItemToInventory(food);

        // 4. ارسال پاسخ موفقیت‌آمیز به کلاینت
        return ResponseEntity.ok(new CookResponseDTO(true, "Cooked successfully!"));
    }
    @Scheduled(fixedRate = 100) // 10 بار در ثانیه
    public void serverGameLoop() {
        if (AppServer.getCurrentGame() != null) {
            // delta time در سرور حدود 0.1 ثانیه است
            float serverDelta = 0.1f;
            AppServer.getCurrentGame().getDate().increaseMinute(serverDelta * 5);
        }
    }





}
