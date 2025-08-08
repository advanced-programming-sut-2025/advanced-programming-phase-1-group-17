package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO; // فقط از DTO استفاده می‌کند
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {

    private final AnimalDataService animalDataService;

    @Autowired
    public AnimalApiController(AnimalDataService animalDataService) {
        this.animalDataService = animalDataService;
    }

    @GetMapping("/allAnimals")
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {

        return ResponseEntity.ok(animalDataService.findAll());
    }

    @PostMapping("/{id}/pet")
    public ResponseEntity<Void> petAnimal(@PathVariable String id) {
        // ۱. DTO (ظرف داده) را از انبار بگیر
        AnimalDTO animal = animalDataService.findById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }

        // ۲. تمام منطق بازی را مستقیماً در کنترلر اجرا کن
        if (!animal.isPettedToday()) {
            animal.setPettedToday(true);
            animal.setFriendship(animal.getFriendship() + 15);
        }

        // ۳. DTO تغییر کرده را دوباره در انبار ذخیره کن
        animalDataService.save(animal);

        return ResponseEntity.ok().build();
    }

    // TODO: متد update حیوانات هم باید در یک سرویس مثل GameLoopService
    // و با همین الگو پیاده‌سازی شود.
}
