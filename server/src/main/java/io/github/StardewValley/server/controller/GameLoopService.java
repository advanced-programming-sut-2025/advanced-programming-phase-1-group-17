package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.controller.logicControllers.AnimalLogicService;
import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.models.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GameLoopService {

    private final AnimalLogicService animalLogicService; // مغز متفکر حیوانات
    private  int numOfProductsPerDay=0;

    @Autowired
    public GameLoopService(AnimalLogicService animalLogicService) {
        this.animalLogicService = animalLogicService;
    }

    @Scheduled(fixedRate = 100)
    public void tick() {
        final float delta = 0.1f;

        Game currentGame = AppServer.getCurrentGame();
        if (currentGame == null) {
            return;
        }

        currentGame.getDate().increaseMinute(delta * 5,AppServer.getCurrentGame());
        currentGame.getLightningLogicController().updateLightning(delta);
        //12 for test


        for(AnimalPlaceDTO animalPlaceDTO:AnimalDataService.findAllPlaces()){
            for(AnimalDTO animalDTO:animalPlaceDTO.getAnimals()){
                animalLogicService.updateAnimalState(animalDTO,delta);

            }
        }


    }

}
