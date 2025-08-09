package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.enums.Direction;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.stereotype.Service;

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
        if (!animal.isOutside()) return;

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
        float speed = 50; // TODO: سرعت را از DTO یا AnimalType بگیرید

        switch (animal.getDirection()) {
            case Up -> newY += speed * delta;
            case Down -> newY -= speed * delta;
            case Left -> newX -= speed * delta;
            case Right -> newX += speed * delta;
        }

        // TODO: منطق بررسی برخورد با موانع
        Tile tile = AppServer.getCurrentGame().getTileFromPixel((int)newX,(int)newY);
        if(tile == null) return;
        if(!tile.isWalkAble()) return;
        animal.setX(newX);
        animal.setY(newY);

    }

    private Direction getRandomDirection() {
        return Direction.values()[(int)(Math.random() * 4)];
    }
}
