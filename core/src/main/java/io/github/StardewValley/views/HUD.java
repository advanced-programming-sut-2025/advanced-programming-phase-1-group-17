package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.StardewValley.models.App;
import io.github.StardewValley.shared.model.Player;
import io.github.StardewValley.models.crafting.CraftingItem;
import io.github.StardewValley.models.crafting.CraftingItemType;

public class HUD {
    private Texture clock;
    private BitmapFont font;
    private BitmapFont dateFont;
    private BitmapFont timeFont;
    private OrthographicCamera hudCamera;
    private TextureRegion arrow;
    private TextureRegion spring;
    private TextureRegion fall;
    private TextureRegion summer;
    private TextureRegion winter;
    private TextureRegion sunny;
    private TextureRegion rainy;
    private TextureRegion storm;
    private TextureRegion snow;


    public HUD() {
        clock = new Texture("Clock.png");
        arrow = new TextureRegion(clock,72,0,8,21);
        spring = new TextureRegion(clock,80,9,13,9);
        summer = new TextureRegion(clock,93,9,13,9);
        fall = new TextureRegion(clock,106,9,13,9);
        winter = new TextureRegion(clock,119,9,13,9);
        sunny = new TextureRegion(clock,119,18,13,9);
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        player.getBackPack().addItemToInventory(new CraftingItem(CraftingItemType.CherryBomb, player));
        player.getBackPack().addItemToInventory(new CraftingItem(CraftingItemType.CherryBomb, player));
        player.getBackPack().addItemToInventory(new CraftingItem(CraftingItemType.CherryBomb, player));
        player.getBackPack().addItemToInventory(new CraftingItem(CraftingItemType.CherryBomb, player));
        player.getBackPack().addItemToInventory(new CraftingItem(CraftingItemType.CherryBomb, player));
        font = new BitmapFont();
        dateFont = new BitmapFont();
        timeFont = new BitmapFont();
        timeFont.getData().setScale(2f,1.6f);
        timeFont.setColor(0,0,0,1);
        dateFont.getData().setScale(2.2f, 1.6f);
        dateFont.setColor(0,0,0,1);
        font.getData().setScale(2f);
        font.setColor(1,0,0,1);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();
    }

    public void render(SpriteBatch batch) {
        // تنظیم دوربین HUD
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        int hudWidth = clock.getWidth();
        int hudHeight = clock.getHeight();

        int x = Gdx.graphics.getWidth() - hudWidth - 80; // فاصله از لبه راست
        int y = Gdx.graphics.getHeight() - hudHeight - 125 ; // فاصله از بالا
        StringBuilder date = new StringBuilder();
        date.append(App.getCurrentGame().getDate().getDayOfTheWeek().toString().substring(0,3)).append(". ")
                .append(App.getCurrentGame().getDate().getDay()).append(" ");
        StringBuilder time = new StringBuilder();
        int hour = App.getCurrentGame().getDate().getHour();
        time.append(hour%12).append(":");
        if(App.getCurrentGame().getDate().getMinute()==0){
            time.append("00");
        }
        else{
            time.append(App.getCurrentGame().getDate().getMinute());
        }
        if(hour<12){
            time.append(" am");
        }
        else{
            time.append(" pm");
        }


        batch.draw(clock,x, y,clock.getWidth()*3,clock.getHeight()*3);
        float arrowSize=3f;
        batch.draw(arrow,1765,980,arrow.getRegionWidth()*arrowSize,arrow.getRegionHeight()*arrowSize);
        float otherSize=3.2f;
        batch.draw(sunny,1794,973,spring.getRegionWidth()*otherSize,spring.getRegionHeight()*otherSize);

        batch.draw(spring,1867,973,spring.getRegionWidth()*otherSize,spring.getRegionHeight()*otherSize);


        // کشیدن مقدار پول
        int money = App.getCurrentGame().getCurrentPlayingPlayer().getCoin();
        int i=0;
        while(money>0){
            font.draw(batch, String.valueOf(money%10), 1885-18*i, Gdx.graphics.getHeight() - 150);
            money /= 10;
            i++;
        }
        dateFont.draw(batch, date.toString(), 1810, Gdx.graphics.getHeight() - 25);
        timeFont.draw(batch, time.toString(), 1800, Gdx.graphics.getHeight() - 92);

        batch.end();
    }

    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
    }

    public void dispose() {
        clock.dispose();
        font.dispose();
    }
}
