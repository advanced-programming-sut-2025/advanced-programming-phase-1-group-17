package io.github.StardewValley.server.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.controller.logicControllers.AnimalLogicService;
import io.github.StardewValley.server.repository.AnimalDataService;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameLoopService {

    private final AnimalDataService animalDataService; // انبار داده حیوانات
    private final AnimalLogicService animalLogicService; // مغز متفکر حیوانات

    @Autowired
    public GameLoopService(AnimalDataService animalDataService, AnimalLogicService animalLogicService) {
        this.animalDataService = animalDataService;
        this.animalLogicService = animalLogicService;
    }

    /**
     * این متد هر 100 میلی‌ثانیه (10 بار در ثانیه) به طور خودکار اجرا می‌شود.
     * این قلب تپنده کل دنیای بازی شما در سرور است.
     */
    @Scheduled(fixedRate = 100)
    public void tick() {
        // دلتای زمانی ثابت برای هر تیک سرور (0.1 ثانیه)
        final float delta = 0.1f;

        Game currentGame = AppServer.getCurrentGame();
        if (currentGame == null) {
            return; // اگر بازی فعال نیست، کاری نکن
        }

        // ۱. آپدیت زمان بازی (منطق قبلی شما)
        currentGame.getDate().increaseMinute(delta * 5,AppServer.getCurrentGame());
        currentGame.getLightningLogicController().updateLightning(delta);

        // ۲. آپدیت تمام حیوانات (منطق جدید، دقیقا کنار قبلی)
        List<AnimalDTO> allAnimals = animalDataService.findAll();
        for (AnimalDTO animal : allAnimals) {
            //TODO nullPointer
            //animalLogicService.updateAnimalState(animal, delta);
        }

    }

}
