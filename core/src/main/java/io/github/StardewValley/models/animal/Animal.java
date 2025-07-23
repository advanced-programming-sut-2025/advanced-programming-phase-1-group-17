package io.github.StardewValley.models.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.StardewValley.controllers.WorldController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Placeable;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.models.enums.Direction;
import io.github.StardewValley.models.map.Tile;

import java.util.*;

import static io.github.StardewValley.models.animal.AnimalType.*;
import static io.github.StardewValley.models.enums.Direction.*;
import static io.github.StardewValley.models.market.ItemQuality.*;

public class Animal implements Placeable {
    private String name;
    private AnimalPlace animalPlace;
    private AnimalType animalType;
    private int price;
    private int friendship;
    private ArrayList<AnimalProduct> animalProducts = new ArrayList<>();
    private boolean isPettedToday = false;
    private boolean isFedToday = false;
    private boolean isFedOutside = false;
    private boolean isOutside=false;
    private Tile tile=null;
    private float x=100,y=100;
    private Direction direction = Up;
    private float stateTime = 0f;
    private EnumMap<Direction, Animation<TextureRegion>> chickenMap;
    private EnumMap<Direction, Animation<TextureRegion>> rabbitMap;
    private EnumMap<Direction, Animation<TextureRegion>> cowMap;
    private EnumMap<Direction, Animation<TextureRegion>> pigMap;
    private EnumMap<Direction, Animation<TextureRegion>> goatMap;
    private EnumMap<Direction, Animation<TextureRegion>> dinosaurMap;
    private EnumMap<Direction, Animation<TextureRegion>> sheepMap;
    private EnumMap<Direction, Animation<TextureRegion>> duckMap;
    private Vector2 vector2;
    private Rectangle hitBox;
    private Texture loveTexture;


    private EnumMap<AnimalType,EnumMap<Direction,Animation<TextureRegion>>> animalsAnimationMap;
    private float speed = 100f;
    private int dayTillProduce=0;
    private int counter=0;
    public Animal(String name, AnimalType animalType){
        this.loveTexture = new Texture("Heart/Marriage_Icon.png");
        this.vector2 = new Vector2();
        this.chickenMap = new EnumMap<>(Direction.class);
        this.rabbitMap = new EnumMap<>(Direction.class);
        this.cowMap = new EnumMap<>(Direction.class);
        this.pigMap = new EnumMap<>(Direction.class);
        this.goatMap = new EnumMap<>(Direction.class);
        this.dinosaurMap = new EnumMap<>(Direction.class);
        this.sheepMap = new EnumMap<>(Direction.class);
        this.duckMap = new EnumMap<>(Direction.class);
        animalsAnimationMap = new EnumMap<>(AnimalType.class);
        this.name=name;
        this.animalType=animalType;
        switch (animalType) {
            case Duck -> dayTillProduce =2;
            case Rabbit -> dayTillProduce =4;
            case Dinosaur -> dayTillProduce =7;
            case Goat -> dayTillProduce =2;
            case Sheep -> dayTillProduce =3;
            default -> dayTillProduce =0;
        }
        Texture animalTexture = new Texture("sprites/Chicken Brown.png");
        TextureRegion[][] animalRegion = TextureRegion.split(animalTexture, 16, 16);
        Animation<TextureRegion>[] animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
                animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        chickenMap.put(Down, animalAnimation[0]);
        chickenMap.put(Right, animalAnimation[1]);
        chickenMap.put(Up, animalAnimation[2]);
        chickenMap.put(Left, animalAnimation[3]);
        animalsAnimationMap.put(Chicken, chickenMap);

         animalTexture = new Texture("sprites/Cow Brown.png");
         animalRegion = TextureRegion.split(animalTexture, 32, 32);
         animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
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
        animalsAnimationMap.put(Cow, cowMap);

        animalTexture = new Texture("sprites/Rabbit.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
            animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        rabbitMap.put(Down, animalAnimation[0]);
        rabbitMap.put(Right, animalAnimation[1]);
        rabbitMap.put(Up, animalAnimation[2]);
        rabbitMap.put(Left, animalAnimation[3]);
        animalsAnimationMap.put(Rabbit, rabbitMap);

        animalTexture = new Texture("sprites/Dinosaur.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
            animalAnimation[i] = new Animation<TextureRegion>(0.1f, animalRegion[i]);
        }
        dinosaurMap.put(Down, animalAnimation[0]);
        dinosaurMap.put(Right, animalAnimation[1]);
        dinosaurMap.put(Up, animalAnimation[2]);
        dinosaurMap.put(Left, animalAnimation[3]);
        animalsAnimationMap.put(Dinosaur, dinosaurMap);

        animalTexture = new Texture("sprites/Goat.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
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
        animalsAnimationMap.put(Goat, goatMap);

        animalTexture = new Texture("sprites/Sheep.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
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
        animalsAnimationMap.put(Sheep, sheepMap);

        animalTexture = new Texture("sprites/Pig.png");
        animalRegion = TextureRegion.split(animalTexture, 32, 32);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
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
        animalsAnimationMap.put(Pig, pigMap);

        animalTexture = new Texture("sprites/Duck.png");
        animalRegion = TextureRegion.split(animalTexture, 16, 16);
        animalAnimation = new Animation[4];
        for (int i = 0; i < 4; i++) {
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
        animalsAnimationMap.put(Duck, duckMap);

    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalPlace getAnimalPlace() {
        return animalPlace;
    }

    public void setAnimalPlace(AnimalPlace animalPlace) {
        this.animalPlace = animalPlace;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        if (friendship > 1000) {
            if (friendship < this.friendship) {
                this.friendship = friendship;
            }
        } else if (friendship < 0) {
            if (friendship > this.friendship) {
                this.friendship = friendship;
            }
        } else {
            this.friendship = friendship;
        }
    }

    public void cheatSetFriendship(int friendship) {
        this.friendship = friendship;
    }

    public ArrayList<AnimalProduct> getAnimalProducts() {
        return animalProducts;
    }

    public void setAnimalProducts(ArrayList<AnimalProduct> animalProducts) {
        this.animalProducts = animalProducts;
    }

    public static Animal findAnimalByName(String name) {
        for (Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimals()) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    public boolean isPettedToday() {
        return isPettedToday;
    }

    public void setPettedToday(boolean pettedToday) {
        isPettedToday = pettedToday;
    }

    public boolean isFedToday() {
        return isFedToday;
    }

    public void setFedToday(boolean fedToday) {
        isFedToday = fedToday;
    }

    public boolean isFedOutside() {
        return isFedOutside;
    }

    public void setFedOutside(boolean fedOutside) {
        isFedOutside = fedOutside;
        isFedToday = fedOutside;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public void produce() {
//        if (!isFedToday) {
//            return;
//        }
        if(this.getAnimalType().equals(AnimalType.Pig) && !this.isOutside){
            return ;
        }
        double randomNumber = 0.5 + Math.random();
        double chance = (double) (friendship + 150 * randomNumber) / 1500;
        double r = Math.random();
        double quality = ((double) friendship / 1000) * (0.5 + r / 2);
        AnimalProductType animalProductType;
        AnimalProduct animalProduct = new AnimalProduct();
        if (Math.random() <= chance && friendship > 100) {
            if (this.animalType.getProductTypes().size() == 2) {
                animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(1));
            }
        } else {
            animalProduct.setAnimalProductType(this.animalType.getProductTypes().get(0));
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
        animalProduct.setAnimal(this);
        addProduct(animalProduct);
        animalProduct.setX(x);
        animalProduct.setY(y);



    }

    public void addProduct(AnimalProduct animalProduct) {
        this.getAnimalProducts().add(animalProduct);
    }

    public void sell() {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        player.getPlayerMap().getAnimals().remove(this);
        for (AnimalPlace animalPlace : player.getPlayerMap().getAnimalPlaces()) {
            animalPlace.getAnimals().remove(this);
        }
        double price = this.animalType.getPrice() * (((double) friendship / 1000) + 0.3);
        player.getBackPack().addcoin(price);
    }
    public static void goToNextDay(){

        for(Animal animal : App.getCurrentGame().getCurrentPlayingPlayer().getPlayerMap().getAnimals()) {
            animal.counter++;
            if(animal.isFedToday && animal.counter >= animal.dayTillProduce){
                animal.counter=0;
                animal.produce();
                animal.setFedToday(false);

            }

            else{
                animal.setFriendship(animal.getFriendship()-20);
            }
            if (!animal.isPettedToday) {
                animal.setFriendship(animal.getFriendship() - 10);
            }
            else{
                animal.setFriendship(animal.getFriendship() + 15);
            }
            if(animal.isOutside){
                animal.setFriendship(animal.getFriendship() - 20);
                animal.setFedOutside(true);
            }
            else{
                animal.setFedOutside(false);
            }
        }
    }

    public static Map<AnimalProduct, Integer> getMapListOfAnimalProducts(ArrayList<AnimalProduct> animalProducts) {
        Map<AnimalProduct, Integer> productIntegerMap = new HashMap<>();
        for (AnimalProduct animalProduct : animalProducts) {
            if (productIntegerMap.containsKey(animalProduct)) {
                productIntegerMap.put(animalProduct, productIntegerMap.get(animalProduct) + 1);
            } else {
                productIntegerMap.put(animalProduct, 1);
            }
        }
        return productIntegerMap;
    }

    public static boolean areWeNearWater(int x , int y){
        for(int i=-3;i<4;i++){
            for(int j=-3;j<4;j++){
                Tile tile = Tile.getTile(x + i, y +j);
                if(tile != null){
                    if(tile.isWater()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Texture getTexture() {
        //TODO
        return null;
    }
    private float timeSinceLastDirectionChange = 0f;
    private float directionChangeInterval = 3f;
    public void update(float delta) {

        // هر ۳ ثانیه جهت رو عوض کن
        if (timeSinceLastDirectionChange >= directionChangeInterval) {
            direction = getRandomDirection();
            timeSinceLastDirectionChange = 0;
        }
         vector2 = new Vector2(App.getCurrentGame().getCurrentPlayingPlayer().getX()-x,App.getCurrentGame().getCurrentPlayingPlayer().getY()- y);

        if(true) {
            timeSinceLastDirectionChange += delta;
            stateTime += delta;
            float newX = x;
            float newY = y;
            switch (direction) {
                case Up -> newY += speed * delta;
                case Down -> newY -= speed * delta;
                case Left -> newX -= speed * delta;
                case Right -> newX += speed * delta;
            }
            float centerX = newX + this.animalType.getTexttureSize()/2f;
            float centerY = newY + this.animalType.getTexttureSize()/2f;
            Texture backgroundTexture = App.getCurrentGame().getCurrentPlayingPlayer().getBackgroundTexture();
            int tileX = (int) (centerX / backgroundTexture.getWidth());
            int tileY = (int) (centerY / backgroundTexture.getHeight());
            if (tileX == 0) tileX = 1;
            if (tileY == 0) tileY = 1;

            Tile destination = Tile.getTile(tileX, tileY);
            if(destination != null && destination.isWalkAble()){
                int mapWidth = backgroundTexture.getWidth() * 300;
                int mapHeight = backgroundTexture.getHeight() * 300;

//                    x = tileX;
//                    y = tileY;

                x = (int) Math.max(1, Math.min(newX, mapWidth - this.animalType.getTexttureSize()));
                y = (int) Math.max(1, Math.min(newY, mapHeight - this.animalType.getTexttureSize()));
            }
        }

    }
    private boolean showHeart = false;
    private float heartTimer = 0f;
    private float heartMovement = 0f;
    public void render(SpriteBatch batch,float v) {

        TextureRegion frame = animalsAnimationMap.get(this.animalType).get(direction).getKeyFrame(stateTime, true);
        int originalWidth = frame.getRegionWidth();
        int originalHeight = frame.getRegionHeight();
        float scale = 1f;
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            App.getCamera().unproject(touchPos);
            if(this.getHitBox().contains(touchPos.x,touchPos.y) ){
                System.out.println(this.isPettedToday);
                if(!this.isPettedToday){
                    this.setPettedToday(true);
                    showHeart = true;
                    heartTimer = 0f;        // ریست زمان
                    heartMovement = 0f;
                }
            }
        }
        if (showHeart) {
            heartTimer += v;
            heartMovement += v * 25f;

            batch.draw(
                loveTexture,
                x + animalType.getTexttureSize() / 2f - loveTexture.getWidth() / 2f,
                y + animalType.getTexttureSize() + heartMovement
            );

            if (heartTimer > 1.5f) {
                showHeart = false;
            }
        }
        batch.draw(frame, x, y, this.animalType.getTexttureSize()*scale, this.animalType.getTexttureSize()*scale);
        List<AnimalProduct> toRemove = new ArrayList<>();

        for (AnimalProduct animalProduct : this.getAnimalProducts()) {
            animalProduct.render(batch, v);
            if (!animalProduct.isRender()) {
                toRemove.add(animalProduct);
            }
        }

        this.getAnimalProducts().removeAll(toRemove);

    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[(int) (Math.random() * directions.length)];
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public Rectangle getHitBox(){
        return new Rectangle(x,y,this.animalType.getTexttureSize(),this.animalType.getTexttureSize());
    }
}
