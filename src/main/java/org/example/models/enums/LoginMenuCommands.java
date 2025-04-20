package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    Login("^\\s*login\\s+-u\\s+(?<username>[\\S ]+?)\\s+-p\\s+(?<password>[\\S ]+?)\\s*(--stay-logged-in)?\\s*$"),
    ForgetPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>[\\S ]+)\\s*"),
    Exit("\\s*menu\\s+exit\\s*");

    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if ( matcher.matches() ) {
            return matcher;
        }
        return null;
    }
}
