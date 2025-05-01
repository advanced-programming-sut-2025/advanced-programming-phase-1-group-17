package org.example.models;

public class Gift {
    private Player playerWhoGiveGift;
    private Player playerWhoGetGift;
    private int giftNumber;
    private Product product;

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

    public void setGiftNumber(int giftNumber) {
        this.giftNumber = giftNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
