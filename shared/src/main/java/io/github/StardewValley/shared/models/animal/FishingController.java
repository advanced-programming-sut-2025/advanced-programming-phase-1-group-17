package io.github.StardewValley.shared.models.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.TimeAndDate;
import io.github.StardewValley.shared.models.enums.FishType;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.market.ItemQuality;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    public Tool savedTool;

    public FishingController(Player player) {
        this.whichMovement = MathUtils.random(1,5);
        savedTool = player.getCurrentTool();
        player.setCurrentTool(new Tool(ToolType.FishingPole, ToolMaterial.Basic,FishingPoleType.BambooFishingPole));
        fishing((FishingPoleType) player.getCurrentTool().getType(), player);
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
            success+=delta*5;
        }
        else{
            success-= delta*4;
            isPerfect=false;
        }
        if(success >= 100){

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
    public void fishing(FishingPoleType fishingPoleType, Player player) {
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


        double R = Math.random();
        double M = 1;
        //TODO
        //TimeAndDate date = App.getCurrentGame().getDate();
        TimeAndDate date = null;
        switch (date.getTodayWeatherType()) {
            case Sunny -> M = 1.5;
            case Rainy -> M = 1.2;
            case Storm -> M = 0.5;
            default -> M = 1;
        }
        int level = player.getAbilities().getFishingLevel();
        int count = (int) Math.ceil(R * M * (level + 2));
        count = Math.min(6, count);
        double pole = fishingPoleType.getPole();
        double qualityInt = ((R * (level + 2) * pole) / (7 - M));
        ItemQuality quality;
        if (qualityInt < 0.5) {
            quality = ItemQuality.Regular;
        } else if (qualityInt < 0.7) {
            quality = ItemQuality.Silver;
        } else if (qualityInt < 0.9) {
            quality = ItemQuality.Gold;
        } else {
            quality = ItemQuality.Iridium;
        }
        Fish fish = new Fish(null, null);
        ArrayList<FishType> fishes = new ArrayList<>();
        if (fishingPoleType.equals(FishingPoleType.TrainingFishingPole)) {
            fishes.addAll(new ArrayList<>(Arrays.asList
                (FishType.Sardine, FishType.Perch, FishType.Herring, FishType.SunFish)));
        } else {
            for (FishType fishType : FishType.values()) {
                if (fishType.getSeason().equals(date.getSeason())) {
                    fishes.add(fishType);
                }
            }
        }
        if (player.getAbilities().getFishingLevel() != 4) {
            ArrayList<FishType> fishesToRemove = new ArrayList<>();
            for (FishType fishType : fishes) {
                if (fishType.isLegendary()) {
                    fishesToRemove.add(fishType);
                }
            }
            fishes.removeAll(fishesToRemove);
        }
        Random rand = new Random();
        FishType randomElement = fishes.get(rand.nextInt(fishes.size()));
        fish.setFishType(randomElement);
        fish.setQuality(quality);
//        for (int i = 0; i < count; i++) {
//            player.getBackPack().addItemToInventory(fish);
//        }
        this.fish = fish;
        this.fishCount = count;
        player.getAbilities().increaseFishingAbility();
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
}
