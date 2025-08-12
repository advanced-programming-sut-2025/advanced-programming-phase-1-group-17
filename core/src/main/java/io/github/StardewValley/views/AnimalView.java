package io.github.StardewValley.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.enums.Direction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static io.github.StardewValley.shared.models.animal.AnimalType.*;
import static io.github.StardewValley.shared.models.enums.Direction.*;

public class AnimalView {
    private Map<AnimalType, EnumMap<Direction, Animation<TextureRegion>>> animalsAnimationMap;
    private Texture loveTexture;
    private Texture afterPetTexture;
    private Texture hayTexture;
    private float stateTime = 0f;
    private EnumMap<Direction, Animation<TextureRegion>> chickenMap;
    private EnumMap<Direction, Animation<TextureRegion>> rabbitMap;
    private EnumMap<Direction, Animation<TextureRegion>> cowMap;
    private EnumMap<Direction, Animation<TextureRegion>> pigMap;
    private EnumMap<Direction, Animation<TextureRegion>> goatMap;
    private EnumMap<Direction, Animation<TextureRegion>> dinosaurMap;
    private EnumMap<Direction, Animation<TextureRegion>> sheepMap;
    private EnumMap<Direction, Animation<TextureRegion>> duckMap;
    private float eatingTimer = 0;

    private static class TimedEffect {
        public float timer = 0f;
        public boolean isAfterPet = false; // برای تشخیص نوع قلب
    }

    private final Map<String, TimedEffect> activeEffects = new HashMap<>();

    public void triggerPetEffect(String animalId, boolean wasAlreadyPetted) {
        TimedEffect effect = new TimedEffect();
        effect.isAfterPet = wasAlreadyPetted;
        activeEffects.put(animalId, effect);
    }



    private float heartTimer = 0f;
    private float heartMovement = 0f;

    public AnimalView() {
        chickenMap = new EnumMap<>(Direction.class);
        rabbitMap = new EnumMap<>(Direction.class);
        cowMap = new EnumMap<>(Direction.class);
        pigMap = new EnumMap<>(Direction.class);
        goatMap = new EnumMap<>(Direction.class);
        dinosaurMap = new EnumMap<>(Direction.class);
        sheepMap = new EnumMap<>(Direction.class);
        duckMap = new EnumMap<>(Direction.class);
        // --- تمام منطق لود کردن انیمیشن‌ها و تکسچرها به اینجا منتقل می‌شود ---
        animalsAnimationMap = new HashMap<>();
        loveTexture = new Texture("Heart/Marriage_Icon.png");
        afterPetTexture = new Texture("icons8-pixel-heart-24.png");
        hayTexture = new Texture("Hay.png");



        // (مرغ، گاو، خرگوش و ...) را از کانستراکتور کلاس Animal قدیمی کپی کرده و اینجا قرار دهید.
        // مثال برای مرغ:
        loadChickenAnimations();
        loadCowAnimations();
        loadPigAnimations();
        loadGoatAnimations();
        loadDinosaurAnimations();
        loadSheepAnimations();
        loadDuckAnimations();
        loadRabbitAnimations();
        // ...
    }
    public void renderEffect(AnimalDTO dto,float delta){
        TimedEffect effect = activeEffects.get(dto.getId());
        effect.timer += delta;

        float heartMovement = effect.timer * 25f;
        Texture heartTex = effect.isAfterPet ? afterPetTexture : loveTexture;

        Main.getBatch().draw(heartTex, dto.getX() + 4, dto.getY() + 32 + heartMovement);

        // اگر زمان افکت تمام شد، آن را از لیست حذف کن
        if (effect.timer > 1.5f) {
            activeEffects.remove(dto.getId());
        }
    }

    public void render(SpriteBatch batch, AnimalDTO dto, float delta) {
        stateTime += delta;
        if(!dto.isOutside()){
            return;
        }


        EnumMap<Direction, Animation<TextureRegion>> animalAnims = animalsAnimationMap.get(dto.getAnimalType());
        if (animalAnims == null) return;

        Animation<TextureRegion> anim = animalAnims.get(dto.getDirection());
        if (anim == null) anim = animalAnims.get(Direction.Down); // یک جهت پیش‌فرض

        TextureRegion frame = anim.getKeyFrame(stateTime, true);

        // رسم خود حیوان
        int width = GameAssetManagerClient.getGameAssetManager().getTexture(dto.getAnimalType().getInventoryTexturePath()).getWidth();
        int height = GameAssetManagerClient.getGameAssetManager().getTexture(dto.getAnimalType().getInventoryTexturePath()).getHeight();
        batch.draw(frame, dto.getX(), dto.getY(), width, height);

        // رسم علوفه اگر در حال خوردن است
        if (dto.isEating()) {
            batch.draw(hayTexture, dto.getX(), dto.getY() - 16, 24, 24);

        }

        // رسم افکت قلب (این یک منطق نمایشی خالص است)
        if (dto.isShowPetHeart() || dto.isShowAlreadyPettedHeart()) {
            heartTimer += delta;
            heartMovement += delta * 25f;

            Texture heartTex = dto.isShowPetHeart() ? loveTexture : afterPetTexture;
            batch.draw(heartTex, dto.getX() + 4, dto.getY() + 32 + heartMovement);

            // این منطق باید بهبود یابد. سرور باید بگوید افکت تمام شده است.
            // فعلا به صورت موقت در کلاینت مدیریت می‌شود.
            if (heartTimer > 1.5f) {
                heartTimer = 0f;
                heartMovement = 0f;
            }
        }
    }
    public void updateAnimationTime(float delta) {
        this.stateTime += delta;
    }
    Animation [] animalAnimation;
    TextureRegion[][] animalRegion;
    Texture animalTexture;
    private void loadChickenAnimations() {
        // این یک مثال از کدی است که باید از کانستراکتور قدیمی منتقل شود
        animalTexture = new Texture("sprites/Chicken Brown.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        chickenMap.put(Direction.Down, new Animation<>(0.1f, animalRegion[0]));
        chickenMap.put(Direction.Right, new Animation<>(0.1f, animalRegion[1]));
        chickenMap.put(Direction.Up, new Animation<>(0.1f, animalRegion[2]));
        chickenMap.put(Direction.Left, new Animation<>(0.1f, animalRegion[3]));
        // chickenMap.put(Direction.Eating, new Animation<>(0.1f, animalRegion[4]));
        animalsAnimationMap.put(AnimalType.Chicken, chickenMap);

    }
    private void loadRabbitAnimations(){
        animalTexture = new Texture("sprites/Rabbit.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        rabbitMap.put(Down, animalAnimation[0]);
        rabbitMap.put(Right, animalAnimation[1]);
        rabbitMap.put(Up, animalAnimation[2]);
        rabbitMap.put(Left, animalAnimation[3]);
        rabbitMap.put(Eating, animalAnimation[4]);
        animalsAnimationMap.put(Rabbit, rabbitMap);
    }
    private void loadCowAnimations(){
        animalTexture = new Texture("sprites/Cow Brown.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                TextureRegion[] flippedFrames = new TextureRegion[4];
                for (int j = 0; j < 4; j++) {
                    flippedFrames[j] = new TextureRegion(animalRegion[1][j]);
                    flippedFrames[j].flip(true, false);
                }
                animalAnimation[i] = new Animation<TextureRegion>(0.1f, flippedFrames);
            }
            else animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        cowMap.put(Down, animalAnimation[0]);
        cowMap.put(Right, animalAnimation[1]);
        cowMap.put(Up, animalAnimation[2]);
        cowMap.put(Left, animalAnimation[3]);
        cowMap.put(Eating, animalAnimation[4]);
        animalsAnimationMap.put(AnimalType.Cow, cowMap);
    }
    private void loadPigAnimations(){
        animalTexture = new Texture("sprites/Pig.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                TextureRegion[] flippedFrames = new TextureRegion[4];
                for (int j = 0; j < 4; j++) {
                    flippedFrames[j] = new TextureRegion(animalRegion[1][j]);
                    flippedFrames[j].flip(true, false);
                }
                animalAnimation[i] = new Animation<TextureRegion>(0.1f, flippedFrames);
            }
            else animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        pigMap.put(Down, animalAnimation[0]);
        pigMap.put(Right, animalAnimation[1]);
        pigMap.put(Up, animalAnimation[2]);
        pigMap.put(Left, animalAnimation[3]);
        pigMap.put(Eating, animalAnimation[4]);
        animalsAnimationMap.put(Pig, pigMap);
    }
    private void loadGoatAnimations(){
        animalTexture = new Texture("sprites/Goat.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                TextureRegion[] flippedFrames = new TextureRegion[4];
                for (int j = 0; j < 4; j++) {
                    flippedFrames[j] = new TextureRegion(animalRegion[1][j]);
                    flippedFrames[j].flip(true, false);
                }
                animalAnimation[i] = new Animation<TextureRegion>(0.1f, flippedFrames);
            }
            else animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }

        goatMap.put(Down, animalAnimation[0]);
        goatMap.put(Right, animalAnimation[1]);
        goatMap.put(Up, animalAnimation[2]);
        goatMap.put(Left, animalAnimation[3]);
        goatMap.put(Eating,animalAnimation[4]);
        animalsAnimationMap.put(Goat, goatMap);
    }
    private void loadDuckAnimations(){
        animalTexture = new Texture("sprites/Duck.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            if(i==2) {
                for(int j=0;j<4;j++) {
                    animalRegion[i][j].flip(true,false);
                }
            }
            animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);

        }
        duckMap.put(Down, animalAnimation[0]);
        duckMap.put(Right, animalAnimation[1]);
        duckMap.put(Up, animalAnimation[2]);
        duckMap.put(Left, animalAnimation[3]);
        duckMap.put(Eating,animalAnimation[4]);
        animalsAnimationMap.put(Duck, duckMap);
    }
    private void loadSheepAnimations(){
        animalTexture = new Texture("sprites/Sheep.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                TextureRegion[] flippedFrames = new TextureRegion[4];
                for (int j = 0; j < 4; j++) {
                    flippedFrames[j] = new TextureRegion(animalRegion[1][j]);
                    flippedFrames[j].flip(true, false);
                }
                animalAnimation[i] = new Animation<TextureRegion>(0.1f, flippedFrames);
            }
            else animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        sheepMap.put(Down, animalAnimation[0]);
        sheepMap.put(Right, animalAnimation[1]);
        sheepMap.put(Up, animalAnimation[2]);
        sheepMap.put(Left, animalAnimation[3]);
        sheepMap.put(Eating, animalAnimation[4]);
        animalsAnimationMap.put(Sheep, sheepMap);
    }
    private void loadDinosaurAnimations(){
        animalTexture = new Texture("sprites/Dinosaur.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[5];
        for (int i = 0; i < 5; i++) {
            animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        dinosaurMap.put(Down, animalAnimation[0]);
        dinosaurMap.put(Right, animalAnimation[1]);
        dinosaurMap.put(Up, animalAnimation[2]);
        dinosaurMap.put(Left, animalAnimation[3]);
        dinosaurMap.put(Eating, animalAnimation[4]);
        animalsAnimationMap.put(Dinosaur, dinosaurMap);
    }

    public void dispose() {
        loveTexture.dispose();
        afterPetTexture.dispose();
        hayTexture.dispose();

    }
}
