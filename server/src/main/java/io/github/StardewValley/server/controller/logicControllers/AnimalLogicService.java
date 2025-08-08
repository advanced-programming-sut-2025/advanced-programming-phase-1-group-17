package io.github.StardewValley.server.controller.logicControllers;

import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.enums.Direction;
import org.springframework.stereotype.Service;

@Service
public class AnimalLogicService {

    private float timeSinceLastDirectionChange = 0f;
    private float directionChangeInterval = 3f;

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
        timeSinceLastDirectionChange += delta;
        if (timeSinceLastDirectionChange >= directionChangeInterval) {
            animal.setDirection(getRandomDirection());
            this.directionChangeInterval = (float) (Math.random() * 4 + 1);
            timeSinceLastDirectionChange = 0;
        }

        float newX = animal.getX();
        float newY = animal.getY();
        float speed = 100f; // TODO: سرعت را از DTO یا AnimalType بگیرید

        switch (animal.getDirection()) {
            case Up -> newY += speed * delta;
            case Down -> newY -= speed * delta;
            case Left -> newX -= speed * delta;
            case Right -> newX += speed * delta;
        }

        // TODO: منطق بررسی برخورد با موانع
        // if (isPositionValid(newX, newY)) {
        animal.setX(newX);
        animal.setY(newY);
        // }
    }

    private Direction getRandomDirection() {
        return Direction.values()[(int)(Math.random() * 4)];
    }
}
