package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Trade;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeSave {
    private String tradeType;
    private String type;
    private String senderName;
    private String item;
    private int amount;
    private String targetItem;
    private int targetAmount;
    private double price;
    private int id;

    // For matcher recreation
    private String regexPattern;
    private String matchedText;

    public TradeSave() {}

    public TradeSave(Trade trade) {
        this.tradeType = trade.getTradeType();
        this.type = trade.getType();
        this.senderName = trade.getSender().getUser().getUsername();
        this.item = trade.getItem();
        this.amount = trade.getAmount();
        this.targetItem = trade.getTargetItem();
        this.targetAmount = trade.getTargetAmount();
        this.price = trade.getPrice();
        this.id = trade.getId();

        if (trade.getMatcher() != null) {
            this.regexPattern = trade.getMatcher().pattern().pattern();
            this.matchedText = trade.getMatcher().toString(); // or the original string input
        }
    }

    public Trade toTrade(Player sender) {
        Matcher matcher = null;
        if (regexPattern != null && matchedText != null) {
            matcher = Pattern.compile(regexPattern).matcher(matchedText);
        }

        return new Trade(sender, type, item, amount, price, targetItem, targetAmount, tradeType, matcher);
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(String targetItem) {
        this.targetItem = targetItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
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

    public String getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    public String getMatchedText() {
        return matchedText;
    }

    public void setMatchedText(String matchedText) {
        this.matchedText = matchedText;
    }
}
