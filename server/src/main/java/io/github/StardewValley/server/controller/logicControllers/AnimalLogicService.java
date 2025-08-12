package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.dto.AnimalProductDTO;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.enums.Direction;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.github.StardewValley.shared.models.market.ItemQuality.*;

@Service
public class AnimalLogicService {

    private float timeSinceLastDirectionChange = 0f;
    private float directionChangeInterval = 3f;
    private float stuckingTime=0f;

    /**
     * این متد یک DTO حیوان را می‌گیرد، تمام منطق بازی را روی آن اعمال می‌کند،
     * و وضعیت DTO را مستقیماً آپدیت می‌کند.
     * @param animal DTO حیوانی که باید آپدیت شود.
     * @param delta مدت زمان گذشته از آخرین فریم.
     */
    public void updateAnimalState(AnimalDTO animal, float delta) {
        //if (!animal.isOutside()) return;

        // TODO: منطق مسیر یابی (Pathfinding) شما هم باید به اینجا منتقل شود
        // if (animal.isFollowingPath()) { ... }

        // --- منطق حرکت تصادفی که از کلاس Animal قدیمی آمده ---
        // از تایمرهای خود حیوان استفاده کن
        animal.setTimeSinceLastDirectionChange(animal.getTimeSinceLastDirectionChange() + delta);

        if (!animal.isEating() && animal.getTimeSinceLastDirectionChange() >= animal.getDirectionChangeInterval()) {
            animal.setDirection(getRandomDirection());
            animal.setDirectionChangeInterval((float) (Math.random() * 4 + 1));
            animal.setTimeSinceLastDirectionChange(0);
        }
        if (animal.isEating()) {
            animal.setDirection(Direction.Eating);
            // تایمر را جلو ببر
            animal.setEatingTimer(animal.getEatingTimer() + delta);

            // اگر زمان خوردن تمام شده
            if (animal.getEatingTimer() >= 3) {
                // فرآیند را تمام کن
                animal.setEating(false);
                animal.setEatingTimer(0f); // تایمر را برای دفعه بعد ریست کن
                animal.setDirection(getRandomDirection());
            }
        }
        if (animal.isShowPetHeart()) {
            // تایمر را جلو ببر
            animal.setPettingTimer(animal.getPettingTimer() + delta);

            // اگر زمان خوردن تمام شده
            if (animal.getPettingTimer() >= 3) {
                // فرآیند را تمام کن
                animal.setShowPetHeart(false);
                animal.setPettingTimer(0f); // تایمر را برای دفعه بعد ریست کن
            }
        }

        float newX = animal.getX();
        float newY = animal.getY();
        float speed = 70; // TODO: سرعت را از DTO یا AnimalType بگیرید

        switch (animal.getDirection()) {
            case Up -> newY += speed * delta;
            case Down -> newY -= speed * delta;
            case Left -> newX -= speed * delta;
            case Right -> newX += speed * delta;
        }

        // TODO: منطق بررسی برخورد با موانع
        Tile tile = AppServer.getCurrentGame().getTileFromPixel((int)newX,(int)newY);
        if(tile == null) {
            //System.out.println("tile is null");
            return;
        }
        if(!tile.isWalkAble()) {
            //System.out.println("tile is not walkable");
            return;
        }
//        for(AnimalPlaceDTO animalPlaceDTO:AnimalDataService.findAllPlaces()){
//            if(animalPlaceDTO.getHitBox().contains(newX,newY)){
//                return;
//            }
//        }
        animal.setX(newX);
        animal.setY(newY);

    }

    private Direction getRandomDirection() {
        return Direction.values()[(int)(Math.random() * 4)];
    }
    public static Map<AnimalProductDTO, Integer> getMapListOfAnimalProducts(ArrayList<AnimalProductDTO> animalProducts) {
        Map<AnimalProductDTO, Integer> productIntegerMap = new HashMap<>();
        for (AnimalProductDTO animalProduct : animalProducts) {
            if (productIntegerMap.containsKey(animalProduct)) {
                productIntegerMap.put(animalProduct, productIntegerMap.get(animalProduct) + 1);
            } else {
                productIntegerMap.put(animalProduct, 1);
            }
        }
        return productIntegerMap;
    }
    public void produce(AnimalDTO animalDTO) {
        if (!animalDTO.isFedToday()) {
            return;
        }
        if(animalDTO.getAnimalType().equals(AnimalType.Pig) && !animalDTO.isOutside()){
            return ;
        }
        double randomNumber = 0.5 + Math.random();
        double chance = (double) (animalDTO.getFriendship() + 150 * randomNumber) / 1500;
        double r = Math.random();
        double quality = ((double) animalDTO.getFriendship() / 1000) * (0.5 + r / 2);
        AnimalProductType animalProductType;
        AnimalProductDTO animalProduct = new AnimalProductDTO();
        if (Math.random() <= chance && animalDTO.getFriendship() > 100) {
            if (animalDTO.getAnimalType().getProductTypes().size() == 2) {
                animalProduct.setType(animalDTO.getAnimalType().getProductTypes().get(1));
            }
        } else {
            animalProduct.setType(animalDTO.getAnimalType().getProductTypes().get(0));
        }
        if (quality < 0.5) {
            animalProduct.setQuality(Regular);
        } else if (quality < 0.7) {
            animalProduct.setQuality(Silver);
        } else if (quality < 0.9) {
            animalProduct.setQuality(Gold);
        } else {
            animalProduct.setQuality(Iridium);
        }
        animalProduct.setAnimalDTO(animalDTO);
        animalDTO.getAnimalProductDTOS().add(animalProduct);
    }
    public void animalGoToNextDay(){
        for(AnimalDTO animal : AnimalDataService.findAll()) {
            if(animal.isFedToday() ){
                produce(animal);
                animal.setFedToday(false);

            }

            else{
                animal.setFriendship(animal.getFriendship()-20);
            }
            if (!animal.isPettedToday()) {
                animal.setFriendship(animal.getFriendship() - 10);
            }
            else{
                animal.setFriendship(animal.getFriendship() + 15);
            }
            if(animal.isOutside()){
                animal.setFriendship(animal.getFriendship() - 20);
                animal.setOutside(false);
            }

        }
    }

}
