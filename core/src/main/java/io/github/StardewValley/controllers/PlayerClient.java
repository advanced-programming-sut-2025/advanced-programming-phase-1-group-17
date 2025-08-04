package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackType;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.enums.Gender;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;

public class PlayerClient {

    private boolean isGuest = false;
    private boolean isPassedOut = false;
    private UserDTO user;
    private double energy;
    private double maxEnergy = 200;
    private boolean isEnergyUnlimited = false;
    private boolean hasPassedOutToday = false;
    private int x;
    private int y;
    private int coin = 550;
    private float speed = 1000f;
    private transient Animation<TextureRegion> walkUpAnimation;
    private transient Animation<TextureRegion> walkDownAnimation;
    private transient Animation<TextureRegion> walkLeftAnimation;
    private transient Animation<TextureRegion> walkRightAnimation;
    private transient TextureRegion currentFrame;
    private Player.Direction lastDirection = Player.Direction.DOWN;
    private Texture backgroundTexture;
    private Texture texture;
    private float animationTimer = 0f;
    private float passOutTimer = 0f;
    private boolean isNewMessage = false;


    PlayerClient(UserDTO user) {
        this.user = user;
        //TODO Handle textures
        walkDownAnimation = new Animation<>(0.1f, new TextureRegion[]{
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex11.png" : "Emily/Emily11.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex12.png" : "Emily/Emily12.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex13.png" : "Emily/Emily13.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex14.png" : "Emily/Emily14.png"))
        });
        walkDownAnimation.setPlayMode(Animation.PlayMode.LOOP);
        walkLeftAnimation = new Animation<>(0.1f, new TextureRegion[]{
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex41.png" : "Emily/Emily41.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex42.png" : "Emily/Emily42.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex43.png" : "Emily/Emily43.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex44.png" : "Emily/Emily44.png"))
        });
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        walkRightAnimation = new Animation<>(0.1f, new TextureRegion[]{
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex21.png" : "Emily/Emily21.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex22.png" : "Emily/Emily22.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex23.png" : "Emily/Emily23.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex24.png" : "Emily/Emily24.png"))
        });
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        walkUpAnimation = new Animation<>(0.1f, new TextureRegion[]{
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex31.png" : "Emily/Emily31.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex32.png" : "Emily/Emily32.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex33.png" : "Emily/Emily33.png")),
            new TextureRegion(new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex34.png" : "Emily/Emily34.png"))
        });
        walkUpAnimation.setPlayMode(Animation.PlayMode.LOOP);
        this.backgroundTexture = GameAssetManagerClient.getGameAssetManager().getTexture(GameAssetManager.getGameAssetManager().getBackgroundTexture1());
        currentFrame = walkDownAnimation.getKeyFrame(0);
        this.texture = new Texture(user.getGender().equals(Gender.Male) ? "Alex/Alex11.png" : "Emily/Emily11.png");
    }



    private Player.Direction currentDirection = Player.Direction.IDLE;


    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isPassedOut() {
        return isPassedOut;
    }

    public void setPassedOut(boolean passedOut) {
        isPassedOut = passedOut;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTileX() {
        int playerWidth = (int)GameAssetManager.getGameAssetManager().getTileWidth();;
        float centerX = x + playerWidth / 2f;
        return (int) (centerX / (int)GameAssetManager.getGameAssetManager().getTileHeight());


    }

    public int getTileY() {
        int playerHeight = (int)GameAssetManager.getGameAssetManager().getTileHeight();;
        float centerY = y + playerHeight / 2f;
        return (int) (centerY / (int)GameAssetManager.getGameAssetManager().getTileHeight());

    }
    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Animation<TextureRegion> getWalkUpAnimation() {
        return walkUpAnimation;
    }

    public void setWalkUpAnimation(Animation<TextureRegion> walkUpAnimation) {
        this.walkUpAnimation = walkUpAnimation;
    }

    public Animation<TextureRegion> getWalkDownAnimation() {
        return walkDownAnimation;
    }

    public void setWalkDownAnimation(Animation<TextureRegion> walkDownAnimation) {
        this.walkDownAnimation = walkDownAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public void setWalkLeftAnimation(Animation<TextureRegion> walkLeftAnimation) {
        this.walkLeftAnimation = walkLeftAnimation;
    }

    public Animation<TextureRegion> getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public void setWalkRightAnimation(Animation<TextureRegion> walkRightAnimation) {
        this.walkRightAnimation = walkRightAnimation;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public boolean isEnergyUnlimited() {
        return isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        isEnergyUnlimited = energyUnlimited;
    }

    public boolean isHasPassedOutToday() {
        return hasPassedOutToday;
    }

    public void setHasPassedOutToday(boolean hasPassedOutToday) {
        this.hasPassedOutToday = hasPassedOutToday;
    }


    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(float animationTimer) {
        this.animationTimer = animationTimer;
    }

    public float getPassOutTimer() {
        return passOutTimer;
    }

    public void setPassOutTimer(float passOutTimer) {
        this.passOutTimer = passOutTimer;
    }

    public Player.Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Player.Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public Player.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Player.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        if (isPassedOut) {
            batch.draw(
                new Texture(user.getGender().equals(Gender.Male) ? "Alex.png" : "Emily.png"),
                getX() == 0 ? 1 : getX(),
                getY() == 0 ? 1 : getY(),
                (int) GameAssetManager.getGameAssetManager().getTileWidth() / 1.5f,
                (int) GameAssetManager.getGameAssetManager().getTileHeight() / 1.5f
            );
            return;
        }
        switch (this.currentDirection) {
            case UP:
                this.currentFrame = walkUpAnimation.getKeyFrame(animationTimer);
                break;
            case DOWN:
                this.currentFrame = walkDownAnimation.getKeyFrame(animationTimer);
                break;
            case LEFT:
                this.currentFrame = walkLeftAnimation.getKeyFrame(animationTimer);
                break;
            case RIGHT:
                this.currentFrame = walkRightAnimation.getKeyFrame(animationTimer);
                break;
            case IDLE:
                switch (this.lastDirection) {
                    case UP:
                        this.currentFrame = walkUpAnimation.getKeyFrame(0);
                        break;
                    case DOWN:
                        this.currentFrame = walkDownAnimation.getKeyFrame(0);
                        break;
                    case LEFT:
                        this.currentFrame = walkLeftAnimation.getKeyFrame(0);
                        break;
                    case RIGHT:
                        this.currentFrame = walkRightAnimation.getKeyFrame(0);
                        break;
                    default:
                        this.currentFrame = walkDownAnimation.getKeyFrame(0);
                        break;
                }
                break;
        }
        Main.getBatch().draw(this.currentFrame, getX() == 0 ? 1 : getX(), getY() == 0 ? 1 : getY(), (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
//            else
//                Main.getBatch().draw(texture, getX() == 0 ? 1 : getX(), getY() == 0 ? 1 : getY(), (float) backgroundTexture.getWidth() / 1.5f, (float) backgroundTexture.getHeight() / 1.5f);
    }

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }
}

