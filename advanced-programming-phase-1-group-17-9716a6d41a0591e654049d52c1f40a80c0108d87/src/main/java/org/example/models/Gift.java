package org.example.models;

public class Gift {
    private Player playerWhoGiveGift;
    private Player playerWhoGetGift;
    private static int counter = 0;
    private int giftNumber;
    private String item;
    private int amount;
    private boolean rateGiven;

    public Gift (Player playerWhoGiveGift, Player playerWhoGetGift, String item, int amount) {
        this.playerWhoGiveGift = playerWhoGiveGift;
        this.playerWhoGetGift = playerWhoGetGift;
        this.item = item;
        this.amount = amount;
        giftNumber = counter;
        rateGiven = false;
        counter++;
    }
    public Player getPlayerWhoGiveGift() {
        return playerWhoGiveGift;
    }

    public void setPlayerWhoGiveGift(Player playerWhoGiveGift) {
        this.playerWhoGiveGift = playerWhoGiveGift;
    }

    public Player getPlayerWhoGetGift() {
        return playerWhoGetGift;
    }

    public void setPlayerWhoGetGift(Player playerWhoGetGift) {
        this.playerWhoGetGift = playerWhoGetGift;
    }

    public int getGiftNumber() {
        return giftNumber;
    }
    public int getCounter(){
        return counter;
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
    public boolean getRateGiven() {
        return rateGiven;
    }
}
