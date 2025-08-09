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
}
