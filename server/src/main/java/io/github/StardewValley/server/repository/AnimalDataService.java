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
    private static final ArrayList<AnimalDTO> animalDatabase = new ArrayList<>();

    public AnimalDataService() {
        // اضافه کردن داده تستی
        AnimalDTO testCow = new AnimalDTO();
        testCow.setId("1");
        testCow.setName("Bessie");
        testCow.setAnimalType(AnimalType.Cow);
        testCow.setX(20);
        testCow.setY(20);
        animalDatabase.add( testCow);
        AnimalDTO testPig = new AnimalDTO("pigg",AnimalType.Pig,200,200);
        testPig.setId("2");
        animalDatabase.add( testPig);
    }

    public static List<AnimalDTO> findAll() {
        return animalDatabase;
    }

    public static AnimalDTO findById(String id) {
        for(AnimalDTO animalDTO:animalDatabase) {
            if(animalDTO.getId().equals(id)) {
                return animalDTO;
            }
        }
        return null;
    }

    public static void save(AnimalDTO animal) {
        animalDatabase.add(animal);
    }
}
