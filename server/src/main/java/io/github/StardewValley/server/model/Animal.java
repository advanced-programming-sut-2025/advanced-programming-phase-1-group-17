package io.github.StardewValley.server.model;

import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.animal.AStarPathfinder;
import io.github.StardewValley.server.model.AnimalPlace;
import io.github.StardewValley.shared.models.animal.AnimalProduct;
import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.enums.Direction;
import io.github.StardewValley.shared.models.map.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animal {
    private String id;
    private static int nextId=1;
    private String name;
    private AnimalType animalType;
    private int friendship;
    private boolean isPettedToday = false;
    private boolean isFedToday = false;
    private boolean isOutside = true;
    private float x = 100, y = 100;
    private Direction direction = Direction.Down;
    private float speed = 100f;
    private ArrayList<AnimalProduct> animalProducts;
    private List<Tile> path = new ArrayList<>();
    private int currentPathIndex = 0;
    private boolean followingPath = false;
    private Player Owner;

    private float timeSinceLastDirectionChange = 0f;
    private float directionChangeInterval = 3f;

    // کانستراکتور
    public Animal(String name, AnimalType animalType,Player Owner) {
        this.Owner = Owner;
        this.id = String.valueOf(nextId++);
        this.name = name;
        this.animalType = animalType;
        this.animalProducts = new ArrayList<>();

    }

    // متد آپدیت که توسط حلقه بازی سرور صدا زده می‌شود
    public void update(float delta) {
        if (!this.isOutside) return;

        timeSinceLastDirectionChange += delta;
        if (timeSinceLastDirectionChange >= directionChangeInterval) {
            this.direction = getRandomDirection();
            this.directionChangeInterval = (float) (Math.random() * 4 + 1);
            timeSinceLastDirectionChange = 0;
        }

        float newX = x, newY = y;
        switch (direction) {
            case Up -> newY += speed * delta;
            case Down -> newY -= speed * delta;
            case Left -> newX -= speed * delta;
            case Right -> newX += speed * delta;
        }

        // TODO: منطق بررسی برخورد با موانع (collision detection) باید اینجا پیاده‌سازی شود
        // شما باید به یک representation از نقشه در سرور دسترسی داشته باشید.
        // if (isPositionValid(newX, newY)) {
        this.x = newX;
        this.y = newY;
        // }
    }
    public Rectangle getHitBox(){
        return new Rectangle(x,y,this.animalType.getTexttureSize(),this.animalType.getTexttureSize());
    }

    public void pet() {
        if (!isPettedToday) {
            this.isPettedToday = true;
            this.friendship += 15;
            // TODO: شاید بخواهید یک افکت موقتی را فعال کنید
        }
    }

    public void feed() {
        if (!isFedToday) {
            this.isFedToday = true;
            // TODO: شاید بخواهید وضعیت را به "Eating" تغییر دهید
        }
    }


    private Direction getRandomDirection() {
        return Direction.values()[(int)(Math.random() * 4)];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Animal.nextId = nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
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

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTimeSinceLastDirectionChange() {
        return timeSinceLastDirectionChange;
    }

    public void setTimeSinceLastDirectionChange(float timeSinceLastDirectionChange) {
        this.timeSinceLastDirectionChange = timeSinceLastDirectionChange;
    }

    public float getDirectionChangeInterval() {
        return directionChangeInterval;
    }

    public void setDirectionChangeInterval(float directionChangeInterval) {
        this.directionChangeInterval = directionChangeInterval;
    }

    public void cheatSetFriendship(int friendship) {
        this.friendship = friendship;
    }
    public static float TILE_SIZE=120f;
    public void startPathTo(int targetPixelX, int targetPixelY) {
        // تبدیل مرکز حیوان به پیکسل
        int centerX = (int)(x + TILE_SIZE/2f);
        int centerY = (int)(y + TILE_SIZE/2f);

        // پیدا کردن تایل شروع و پایان با تابع جدید
        Tile startTile = Tile.getTileFromPixel(centerX, centerY);
        Tile endTile   = Tile.getTileFromPixel(targetPixelX, targetPixelY);

        if(startTile == null){
            System.out.println("startTile is null");
        }
        if(endTile == null){
            System.out.println("endTile is null");
        }
        if(!endTile.isWalkAble()){
            System.out.println("endTile is not walkable");
        }

        // نپذیرفتن مسیر 1 تایی (یعنی وقتی از قبل در مقصد ایستاده)
        AStarPathfinder pathfinder = new AStarPathfinder(Tile.getTiles());
        List<Tile> newPath = pathfinder.findPath(
                startTile.getX(), startTile.getY(),
                endTile.getX(),   endTile.getY()
        );

        if (newPath == null || newPath.isEmpty()) {
            System.out.println("No path found!");
            return;
        }
        if (newPath.size() == 1) {
            System.out.println(animalType.getName() + " already at destination.");
            return;
        }

        // مسیر معتبر
        path = newPath;
        currentPathIndex = 1;      // اندیس 0 = startTile، ما از قدم بعد شروع می‌کنیم
        followingPath = true;
        System.out.print("Path: ");
        for (Tile tile : newPath) {
            System.out.print("-> (" + tile.getX() + "," + tile.getY() + ")");
        }
        System.out.println();
    }

    public ArrayList<AnimalProduct> getAnimalProducts() {
        return animalProducts;
    }

    public void setAnimalProducts(ArrayList<AnimalProduct> animalProducts) {
        this.animalProducts = animalProducts;
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
}
