package io.github.StardewValley.server.repository;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.dto.AnimalProductDTO;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.enums.Direction;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnimalDataService {
    // برای سادگی، داده‌ها را در حافظه نگه می‌داریم
    private static final ArrayList<AnimalPlaceDTO> animalPlaceDatabase = new ArrayList<>();
    private static final ArrayList<AnimalProductDTO> productsOnGround = new ArrayList<>();

    public AnimalDataService() {

    }
    public static void initial(){
        AnimalPlaceDTO animalPlaceDTO = new AnimalPlaceDTO("1", AnimalPlaceType.Barn,1000,1000,true);
        savePlace(animalPlaceDTO);
        AnimalDTO testCow = new AnimalDTO();
        testCow.setId("1");
        testCow.setName("Bessie");
        testCow.setAnimalType(AnimalType.Cow);
        testCow.setY(100);

        testCow.setDirection(Direction.Up);
        //animalDatabase.add( testCow);
        AnimalDTO testPig = new AnimalDTO("pigg",AnimalType.Pig,200,200);
        testPig.setDirection(Direction.Up);
        testPig.setId("2");
        //animalDatabase.add( testPig);
        animalPlaceDTO.getAnimals().add(testPig);
        animalPlaceDTO.getAnimals().add(testCow);
        testPig.setX(animalPlaceDTO.getSpawnX());
        testPig.setY(animalPlaceDTO.getSpawnY());
        testCow.setX(animalPlaceDTO.getSpawnX());
        testCow.setY(animalPlaceDTO.getSpawnY());
        AnimalProductDTO animalProductDTO = new AnimalProductDTO();
        animalProductDTO.setType(AnimalProductType.Milk);
        animalProductDTO.setX(700);
        animalProductDTO.setY(100);
        animalProductDTO.setId(UUID.randomUUID().toString());
        productsOnGround.add(animalProductDTO);
    }


    public static List<AnimalDTO> findAll() {
        ArrayList<AnimalDTO>animals = new ArrayList<>();
        for(AnimalPlaceDTO animalPlaceDTO : animalPlaceDatabase) {
            animals.addAll(animalPlaceDTO.getAnimals());
        }
        return animals;
    }

    public static AnimalDTO findById(String id) {
        for(AnimalDTO animalDTO:findAll()) {
            if(animalDTO.getId().equals(id)) {
                return animalDTO;
            }
        }
        return null;
    }

    public static void save(AnimalDTO animalToSave) {
        // برای ذخیره، باید حیوان را در لیست تو در تو پیدا و جایگزین کنیم
        for (AnimalPlaceDTO place : animalPlaceDatabase) {
            for (int i = 0; i < place.getAnimals().size(); i++) {
                if (place.getAnimals().get(i).getId().equals(animalToSave.getId())) {
                    place.getAnimals().set(i, animalToSave);
                    return;
                }
            }
        }
        for(AnimalPlaceDTO place : animalPlaceDatabase) {
            if(place.getAnimals().size()<4){
                place.getAnimals().add(animalToSave);
                return;
            }
        }
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
        int y = (int) place.getY()-120;
        while(y>0){
            Tile tile = AppServer.getCurrentGame().getTileFromPixel((int) (place.getX()+200),y);
            if(tile.isWalkAble() && tile != null){
                place.setSpawnX((int) (place.getX()+200));
                place.setSpawnY(y);
                return;
            }
            y-=110;
        }
    }
    public static List<AnimalProductDTO> findAllProductsOnGround() {
        return productsOnGround;
    }

    public static void saveProductOnGround(AnimalProductDTO product) {
        productsOnGround.add(product);
    }

    public static void deleteProductById(String id) {
        productsOnGround.removeIf(a -> a.getId().equals(id));
    }
    public static void buyAnimal(AnimalDTO animal,String animalPlaceId){
        for(AnimalPlaceDTO animalPlaceDTO:animalPlaceDatabase) {
            if(animalPlaceDTO.getId().equals(animalPlaceId)) {
                animalPlaceDTO.getAnimals().add(animal);
            }
        }
    }

}
