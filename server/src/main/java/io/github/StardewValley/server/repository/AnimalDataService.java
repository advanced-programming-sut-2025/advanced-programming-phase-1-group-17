package io.github.StardewValley.server.repository;

import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.animal.AnimalType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnimalDataService {
    // برای سادگی، داده‌ها را در حافظه نگه می‌داریم
    private final Map<String, AnimalDTO> animalDatabase = new ConcurrentHashMap<>();

    public AnimalDataService() {
        // اضافه کردن داده تستی
        AnimalDTO testCow = new AnimalDTO();
        testCow.setId("1");
        testCow.setName("Bessie");
        testCow.setAnimalType(AnimalType.Cow);
        testCow.setX(100);
        testCow.setY(100);
        animalDatabase.put("1", testCow);
    }

    public List<AnimalDTO> findAll() {
        return new ArrayList<>(animalDatabase.values());
    }

    public AnimalDTO findById(String id) {
        return animalDatabase.get(id);
    }

    public void save(AnimalDTO animal) {
        animalDatabase.put(animal.getId(), animal);
    }
}
