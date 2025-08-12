package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO; // فقط از DTO استفاده می‌کند
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.dto.AnimalProductDTO;
import io.github.StardewValley.shared.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {
    private JwtService jwtService;
    public AnimalApiController(JwtService jwtService) {
        this.jwtService = jwtService;
    }




    @GetMapping("/allAnimals")
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {

        return ResponseEntity.ok(AnimalDataService.findAll());
    }
    @PostMapping("/collect")
    // به جای @PathVariable، از @RequestBody استفاده می‌کنیم
    public ResponseEntity<Void> collectProduct(@RequestBody AnimalProductDTO productDto, @RequestHeader("Authorization") String token) {
        // ۲. محصول را از روی زمین حذف کن
        AnimalDataService.deleteProductById(productDto.getId());

        // ۳. محصول معتبر (که از دیتابیس خودمان خواندیم) را به کوله‌پشتی اضافه کن
        Player player = getPlayerFromToken(token);
        player.getBackPack().addItemToInventory(productDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<AnimalProductDTO>> getAllAnimalProducts() {

        return ResponseEntity.ok(AnimalDataService.findAllProductsOnGround());
    }

    @PostMapping("/{id}/pet")
    public ResponseEntity<Void> petAnimal(@PathVariable String id) {
        // ۱. DTO (ظرف داده) را از انبار بگیر
        AnimalDTO animal = AnimalDataService.findById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }

        // ۲. تمام منطق بازی را مستقیماً در کنترلر اجرا کن
        if (!animal.isPettedToday()) {
            animal.setPettedToday(true);
            animal.setFriendship(animal.getFriendship() + 15);
            animal.setShowPetHeart(true);
            animal.setPettingTimer(0);
        }

        // ۳. DTO تغییر کرده را دوباره در انبار ذخیره کن
        AnimalDataService.save(animal);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/feed")
    public ResponseEntity<Void> feedAnimal(@PathVariable String id) {
        // ۱. DTO (ظرف داده) را از انبار بگیر
        AnimalDTO animal = AnimalDataService.findById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }

        // ۲. تمام منطق بازی را مستقیماً در کنترلر اجرا کن
        if (!animal.isFedToday()) {
            animal.setFedToday(true);
            animal.setFriendship(animal.getFriendship() + 10);
            animal.setEating(true);
            animal.setEatingTimer(0);
        }

        // ۳. DTO تغییر کرده را دوباره در انبار ذخیره کن
        AnimalDataService.save(animal);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/allAnimalPlaces")
    public ResponseEntity<List<AnimalPlaceDTO>>getAllAnimalPlaces(){
        return ResponseEntity.ok(AnimalDataService.findAllPlaces());
    }

    // TODO: متد update حیوانات هم باید در یک سرویس مثل GameLoopService
    // و با همین الگو پیاده‌سازی شود.
    public Player getPlayerFromToken(String token) {
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
//        User user = userRepository.findByUsername(username)
//            .orElseThrow(() -> new RuntimeException("User not found"));

//        Game activeGame = user.getActiveGame();
//        if (activeGame == null) {
//            throw new RuntimeException("User is not in an active game");
//        }

        //return activeGame.getPlayerByUsername(username);
        return AppServer.getCurrentGame().getPlayerByUsername(username);
    }
}
