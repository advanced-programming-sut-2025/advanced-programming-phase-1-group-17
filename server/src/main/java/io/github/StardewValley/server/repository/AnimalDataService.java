package io.github.StardewValley.server.repository;

import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
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
    private static final ArrayList<AnimalPlaceDTO> animalPlaceDatabase = new ArrayList<>();

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
        animalPlaceDatabase.add(new AnimalPlaceDTO("1", AnimalPlaceType.Barn,1000,1000,true));
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
    public static List<AnimalPlaceDTO> findAllPlaces() {
        return animalPlaceDatabase;
    }
    public static AnimalPlaceDTO findPlaceById(String id) {
        for(AnimalPlaceDTO animalPlaceDTO:animalPlaceDatabase) {
            if(animalPlaceDTO.getId().equals(id)) {
                return animalPlaceDTO;
            }
        }
        return null;
    }
    public static void savePlace(AnimalPlaceDTO place) {
        animalPlaceDatabase.add(place);
    }
}
