package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.market.ItemQuality;
import io.github.StardewValley.shared.models.tools.FishingPoleType;

public class FishingController {
    private float barY = 0; // موقعیت مستطیل
    private float velocity = 0;
    private float gravity = -200;
    private float barHeight = 60;
    private float targetY=0f; // موقعیت خط ماهی
    private float timeElapsed = 0;
    private boolean finished = false;
    private boolean isPerfect=true;
    private float targetVelocity=50f;
    private float success=90f;
    private int whichMovement;
    private Fish fish;
    private int fishCount=0;
    private ItemQuality fishQuality;

    public FishingController() throws Exception {
        this.whichMovement = MathUtils.random(1,5);
        this.fish = GameClient.getGameStateApiClient().calculateFishCatch().getFish();
        this.fishCount = GameClient.getGameStateApiClient().calculateFishCatch().getFishCount();
        this.fishQuality = GameClient.getGameStateApiClient().calculateFishCatch().getQuality();
    }



    public void update(float delta) {
        timeElapsed += delta;
        targetY += targetVelocity * delta;
        switch (whichMovement){
            case 1: mixedMovement(delta); break;
            case 2: smoothMovement(delta); break;
            case 3: sinkerMovement(delta); break;
            case 4: floaterMovement(delta); break;
            case 5: dartMovement(delta); break;
            default: break;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity += 400 * delta; // بالا رفتن وقتی کلیک می‌کنی
        } else {
            velocity += gravity * delta;
        }
        if(barY <= 0){
            velocity = Math.max(velocity, 0);
        }
        if(barY>=300){
            velocity = Math.min(velocity, 0);
        }

        barY += velocity * delta;
        barY = MathUtils.clamp(barY, 0, 300);

        // بررسی موفقیت
        if (isBarOverTarget()) {
            success+=delta*10;
        }
        else{
            success-= delta*4;
            isPerfect=false;
        }

    }

    public boolean isFinished() {
        return finished;
    }

    public float getBarY() {
        return barY;
    }

    public float getTargetY() {
        return targetY;
    }

    public boolean isBarOverTarget() {
        return barY < targetY + 20 && barY + barHeight > targetY;
    }

    public float getSuccess() {
        return success;
    }

    public void setSuccess(float success) {
        this.success = success;
    }

    private float moveTimer = 0f;
    private int currentMove = 1;

    public void mixedMovement(float delta) {
        moveTimer += delta;
        if (moveTimer >= 0.5f) {
            currentMove = MathUtils.random(1, 3);
            moveTimer = 0f;
        }
        float speed = 100f;
        switch (currentMove) {
            case 1:
                targetY += speed * delta;
                break;
            case 2:
                targetY -= speed * delta;
                break;
            case 3:
                break;
        }

        targetY = MathUtils.clamp(targetY, 0, 300);
    }
    public void smoothMovement(float delta){
        moveTimer += delta;
        if(moveTimer >= 0.5f) {
            int rand = MathUtils.random(0,99);
            moveTimer =0f;
            if(rand>60 && rand<80){
                currentMove+=1;
                currentMove = currentMove %3;
            }
            else if(rand>=80){
                currentMove+=2;
                currentMove = currentMove %3;
            }
        }
        float speed = 100f;
        switch (currentMove) {
            case 1: targetY+=delta*speed;
            break;
            case 2:
                targetY -= speed * delta;
                break;
            case 3:
                break;
        }
        targetY = MathUtils.clamp(targetY, 0, 300);
    }
    private float leverage=1f;
    public void sinkerMovement(float delta){
        moveTimer += delta;
        if(moveTimer >= 0.5f) {
            currentMove = MathUtils.random(1,3);
            moveTimer = 0f;


        }
        float speed = 100f;
        switch (currentMove) {
            case 1:
                targetY += speed * delta;
                leverage=1f;
                break;
            case 2:
                targetY -= speed * delta*leverage;
                leverage=1f;
                break;
            case 3:
                leverage=3f;
                break;
        }

        targetY = MathUtils.clamp(targetY, 0, 300);
    }
    public void floaterMovement(float delta){
        moveTimer += delta;
        if(moveTimer >= 0.5f) {
            currentMove = MathUtils.random(1,3);
            moveTimer = 0f;


        }
        float speed = 100f;
        switch (currentMove) {
            case 1:
                targetY += speed * delta * leverage;
                leverage=1f;
                break;
            case 2:
                targetY -= speed * delta;
                leverage=1f;
                break;
            case 3:
                leverage=3f;
                break;
        }

        targetY = MathUtils.clamp(targetY, 0, 300);
    }

    public void dartMovement(float delta) {
        moveTimer += delta;
        if (moveTimer >= 0.5f) {
            currentMove = MathUtils.random(1, 3);
            moveTimer = 0f;
        }
        float speed = 180;
        switch (currentMove) {
            case 1:
                targetY += speed * delta;
                break;
            case 2:
                targetY -= speed * delta;
                break;
            case 3:
                break;
        }

        targetY = MathUtils.clamp(targetY, 0, 300);
    }
    public void fishing(FishingPoleType fishingPoleType) {
//        if (!Animal.areWeNearWater(player.getTileX(), player.getTileY())) {
//            //TODO: maybe graphical error
//            return;
//            //return new Result(false, "first go near water");
//        }
//        if (player.getBackPack().isBackPackFull()) {
//            //TODO: maybe graphical error
//            return;
//            //return new Result(false, "your backpack is full");
//        }
//
//        if (!player.getBackPack().getBackPackItems().containsKey(fishingPoleType)) {
//            //TODO: maybe graphical error
//            return;
//            //return new Result(false, "you dont have this fishing pole in your backpack");
//        }



        //TODO: maybe graphical error

    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
    }

    public float getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(float barHeight) {
        this.barHeight = barHeight;
    }

    public boolean isPerfect() {
        return isPerfect;
    }

    public void setPerfect(boolean perfect) {
        isPerfect = perfect;
    }

    public ItemQuality getFishQuality() {
        return fishQuality;
    }

    public void setFishQuality(ItemQuality fishQuality) {
        this.fishQuality = fishQuality;
    }
}
