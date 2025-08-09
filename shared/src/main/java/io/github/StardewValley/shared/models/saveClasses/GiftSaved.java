package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.NPCS.Gift;

public class GiftSaved {
    private String playerWhoGiveGiftUsername;
    private String playerWhoGetGiftUsername;
    private int giftNumber;
    private String item;
    private int amount;
    private boolean rateGiven;

    public GiftSaved() {
    }

    public GiftSaved(Gift gift) {
        this.playerWhoGiveGiftUsername = gift.getPlayerWhoGiveGift().getUser().getUsername();
        this.playerWhoGetGiftUsername = gift.getPlayerWhoGetGift().getUser().getUsername();
        this.giftNumber = gift.getGiftNumber();
        this.item = gift.getItem();
        this.amount = gift.getAmount();
        this.rateGiven = gift.getRateGiven();
    }

    public String getPlayerWhoGiveGiftUsername() {
        return playerWhoGiveGiftUsername;
    }

    public void setPlayerWhoGiveGiftUsername(String playerWhoGiveGiftUsername) {
        this.playerWhoGiveGiftUsername = playerWhoGiveGiftUsername;
    }

    public String getPlayerWhoGetGiftUsername() {
        return playerWhoGetGiftUsername;
    }

    public void setPlayerWhoGetGiftUsername(String playerWhoGetGiftUsername) {
        this.playerWhoGetGiftUsername = playerWhoGetGiftUsername;
    }

    public int getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(int giftNumber) {
        this.giftNumber = giftNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isRateGiven() {
        return rateGiven;
    }

    public void setRateGiven(boolean rateGiven) {
        this.rateGiven = rateGiven;
    }
}
