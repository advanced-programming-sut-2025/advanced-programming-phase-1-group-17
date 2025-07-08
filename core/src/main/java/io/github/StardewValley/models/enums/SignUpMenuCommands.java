package io.github.StardewValley.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    Username("[a-zA-Z\\d-]+"),
    Email("^(?!.*\\.\\.)[a-zA-Z\\d]([a-zA-Z\\d._-]*[a-zA-Z\\d])?@([a-zA-Z\\d]([a-zA-Z\\d-]*[a-zA-Z\\d])?\\.)+[a-zA-Z]{2,}$"),
    ValidPassword( "^[a-zA-Z\\d!#$%\\^&*()=+{}\\[\\]|\\\\/:;'\\" + "\",<>?]+$");

    private final String pattern;

    SignUpMenuCommands(String pattern){
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()){
            return matcher;
        }
        return null;
    }
}
