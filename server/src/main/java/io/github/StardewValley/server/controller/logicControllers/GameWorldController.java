package io.github.StardewValley.server.controller.logicControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import io.github.StardewValley.shared.dto.HandleWorldClickResponse;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.market.ShippingBin;
import io.github.StardewValley.shared.models.market.StoreType;

import java.util.HashMap;
import java.util.Map;

public class GameWorldController {
    public HandleWorldClickResponse checkBounds(float x, float y, int button, Player player) {
        Vector3 worldCoordinates = new Vector3(x, y, 0);
        HandleWorldClickResponse response;
        if (button == Input.Buttons.RIGHT)
            return checkCraftingItemBounds(worldCoordinates, false);

        response = checkGreenHouseBounds(worldCoordinates, player);
        if (response.isSuccessful())
            return response;

        response = checkCraftingItemBounds(worldCoordinates, true);
        if (response.isSuccessful())
            return response;

        response = handleShippingBin(worldCoordinates, player);
        if (response.isSuccessful()) {
            return response;
        }
        return checkStoreBounds(worldCoordinates, player);
    }

    private HandleWorldClickResponse checkGreenHouseBounds(Vector3 worldCoordinates, Player player) {
        HashMap<GreenHouse, Rectangle> bounds = GreenHouse.getGreenHouseBounds();
        for (GreenHouse greenHouse : bounds.keySet()) {
            if (bounds.get(greenHouse).contains(worldCoordinates.x, worldCoordinates.y)) {
                if (greenHouse.isActive()) {
                    GreenHouse.getGreenHouseBounds().remove(greenHouse);
                    return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.NONE);
                }
                if (!greenHouse.getOwner().equals(player)) {
                    //showNotification("This greenhouse is not yours.");
                    return new HandleWorldClickResponse(true, "This greenhouse is not yours.", HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION);
                }
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new GreenHouseBuildScreen(
//                    new GreenHouseBuildController(),
//                    GameAssetManagerClient.getGameAssetManager().getSkin()
//                ));
                return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.OPEN_GREENHOUSE_BUILD);
            }
        }
        return new HandleWorldClickResponse(false);
    }


    private HandleWorldClickResponse handleShippingBin(Vector3 worldCoordinates, Player player) {
        HashMap<ShippingBin, Rectangle> bounds = ShippingBin.getShippingBinBounds();
        for (ShippingBin shippingBin : bounds.keySet()) {
            if (bounds.get(shippingBin).contains(worldCoordinates.x, worldCoordinates.y)) {
                if (shippingBin.getTodayItemOwner() != null && !shippingBin.getTodayItemOwner().equals(player)) {
//                    showNotification("Player %s has put some items inside this shipping Bin today.\n Try using another shipping Bin."
//                        .formatted(shippingBin.getTodayItemOwner().getUser().getUsername()));
                    return new HandleWorldClickResponse(
                        true,
                        "Player %s has put some items inside this shipping Bin today.\n Try using another shipping Bin."
                            .formatted(shippingBin.getTodayItemOwner().getUser().getUsername()),
                        HandleWorldClickResponse.ActionType.SHOW_NOTIFICATION
                        );
                }
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new ShippingBinScreen(
//                    shippingBin,
//                    new ShippingBinScreenController(),
//                    GameAssetManagerClient.getGameAssetManager().getSkin()
//                ));
                return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.OPEN_SHIPPING_BIN_MENU, shippingBin);
            }
        }
        return new HandleWorldClickResponse(false);
    }


    private HandleWorldClickResponse checkStoreBounds(Vector3 worldCoordinates, Player player) {
        Game currentGame = player.getUser().getActiveGame();
        HashMap<StoreType, Rectangle> storeBounds = currentGame.getMarketsController().getStoreBounds();
        for (StoreType storeType : storeBounds.keySet()) {
            Rectangle rectangle = storeBounds.get(storeType);
            if (rectangle.contains(worldCoordinates.x, worldCoordinates.y)) {
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new StoreMenu(new StoreMenuController(),
//                    GameAssetManagerClient.getGameAssetManager().getSkin(), entry.getKey()));
                return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.OPEN_STORE, storeType);
            }
        }
        return new HandleWorldClickResponse(false);
    }

    private HandleWorldClickResponse checkCraftingItemBounds(Vector3 worldCoordinates , boolean isLeftClick) {
        for (Map.Entry<CraftingItem, Rectangle> entry:  CraftingItem.getCraftingItemBounds().entrySet()) {
            if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                //Main.getScreen().dispose();
                if (isLeftClick)
                    return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.OPEN_ARTISAN_CRAFT_MENU, entry.getKey());
//                    Main.getMain().setScreen(new ArtisanCraftMenu(new ArtisanCraftMenuController(),
//                        GameAssetManagerClient.getGameAssetManager().getSkin(), entry.getKey()));
                else
                    return new HandleWorldClickResponse(true, "", HandleWorldClickResponse.ActionType.OPEN_ARTISAN_INFO_MENU, entry.getKey());
//                    Main.getMain().setScreen(new ArtisanInfoMenu(new ArtisanInfoMenuController(),
//                        GameAssetManagerClient.getGameAssetManager().getSkin(), entry.getKey()));
            }
        }
        return new HandleWorldClickResponse(false);
    }
}
