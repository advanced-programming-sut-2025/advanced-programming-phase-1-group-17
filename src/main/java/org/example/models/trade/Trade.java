package org.example.models.trade;

import org.example.models.BackPackable;
import org.example.models.Player;
import org.example.models.Product;

public class Trade {
    private String type;
    private Player sender;
    private String item;
    private int amount;
    private String targetItem;
    private int targetAmount;
    private double price;
    private int id;
    private static int nextTradeId = 1;

    public Trade(Player sender,String type, String item, int amount, double price, String targetItem, int targetAmount) {
        if (type.equals("offer")) {
            this.item = item;
            this.type = type;
            this.sender = sender;
            this.amount = amount;
            this.price = price;
        } else if (type.equals("request")) {
            this.item = item;
            this.type = type;
            this.amount = amount;
            this.sender = sender;
            this.targetItem = targetItem;
            this.targetAmount = targetAmount;
        }
        id = nextTradeId++;
    }

    public Player getSender() {
        return sender;
    }


    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetItem() {
        return targetItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public String getType() {
        return type;
    }
}
