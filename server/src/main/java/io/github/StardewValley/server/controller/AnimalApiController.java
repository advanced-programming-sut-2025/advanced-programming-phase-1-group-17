package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO; // فقط از DTO استفاده می‌کند
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {




    @GetMapping("/allAnimals")
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {

        return ResponseEntity.ok(AnimalDataService.findAll());
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
}
