package io.github.StardewValley.shared.dto;

public class HandleWorldClickResponse {
    private boolean successful;
    private String message;        // for notifications
    private ActionType actionType; // what should client do?
    private Object payload;        // extra info (e.g. store type, crafting item id, etc.)

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

    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType, Object payload) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
        this.payload = payload;
    }

    public HandleWorldClickResponse(boolean successful, String message, ActionType actionType) {
        this.successful = successful;
        this.message = message;
        this.actionType = actionType;
        this.payload = null;
    }

    public HandleWorldClickResponse(boolean successful) {
        this.successful = successful;
        this.message = "";
        this.actionType = ActionType.NONE;
        this.payload = null;
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

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
