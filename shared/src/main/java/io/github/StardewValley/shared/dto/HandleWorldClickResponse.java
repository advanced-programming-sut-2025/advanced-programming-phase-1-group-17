package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.market.StoreType;

public class HandleWorldClickResponse {
    private boolean successful;
    private String message;        // for notifications
    private ActionType actionType; // what should client do?
    private StoreType storeType;
    private CraftingItemDTO craftingItemDTO;
    private TileDTO tileDTO;
    private boolean startToolAnimation = false;

    public enum ActionType {
        NONE,
        SHOW_NOTIFICATION,
        OPEN_STORE,
        OPEN_ARTISAN_CRAFT_MENU,
        OPEN_ARTISAN_INFO_MENU,
        OPEN_GREENHOUSE_BUILD,
        OPEN_SHIPPING_BIN_MENU
    }

    public HandleWorldClickResponse() {
    }

    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType, StoreType storeType) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
        this.storeType = storeType;
    }

    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType, CraftingItemDTO craftingItemDTO) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
        this.craftingItemDTO = craftingItemDTO;
    }


    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType, TileDTO tileDTO) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
        this.tileDTO = tileDTO;
    }

    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
    }

    public HandleWorldClickResponse(boolean startToolAnimation, boolean successful, String message, ActionType actionType) {
        this.startToolAnimation = startToolAnimation;
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
    }

    public HandleWorldClickResponse(boolean successful) {
        this.successful = successful;
        this.message = "";
        this.actionType = ActionType.NONE;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public CraftingItemDTO getCraftingItemDTO() {
        return craftingItemDTO;
    }

    public TileDTO getTileDTO() {
        return tileDTO;
    }

    public boolean isStartToolAnimation() {
        return startToolAnimation;
    }
}
