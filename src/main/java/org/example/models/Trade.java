package org.example.models;

import java.util.regex.Matcher;

public class Trade {
    // ByMoney Or ByItem
    private String tradeType;
    private String type;
    private Player sender;
    private String item;
    private int amount;
    private String targetItem;
    private int targetAmount;
    private double price;
    private int id;
    private static int nextTradeId = 1;
    private Matcher matcher;

    public Trade(Player sender, String type
            , String item
            , int amount
            , double price
            , String targetItem
            , int targetAmount
            , String tradeType
            , Matcher matcher) {
        if (tradeType.equals("byMoney")) {
            this.item = item;
            this.type = type;
            this.tradeType = tradeType;
            this.sender = sender;
            this.amount = amount;
            this.price = price;

        } else {
            this.item = item;
            this.type = type;
            this.tradeType = tradeType;
            this.amount = amount;
            this.sender = sender;
            this.targetItem = targetItem;
            this.targetAmount = targetAmount;
        }
        this.matcher = matcher;
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

    public String getTradeType() {
        return tradeType;
    }

    public Matcher getMatcher() {
        return matcher;
    }
}
