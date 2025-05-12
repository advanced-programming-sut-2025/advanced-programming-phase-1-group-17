package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    tradeByMoney("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>offer|request)\\s+-i\\s+(?<item>\\S+)\\s+" +
            "-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>\\d[.|\\d]*)\\s*"),
    tradeByItem("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>request|offer)\\s+-i\\s+(?<item>\\S+)\\s+" +
            "-a\\s+(?<amount>\\d+)\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+)\\s*"),
    tradeResponse("\\s*trade\\s+response\\s+(?<accept>-accept|-reject)\\s+-i\\s+(?<id>\\d+)\\s*");

    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }


}
