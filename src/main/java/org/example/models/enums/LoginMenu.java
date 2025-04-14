package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenu {
    login("^\\s*login\\s+-u\\s+(?<username>[\\S ]+?)\\s+-p\\s+(?<password>[\\S ]+?)\\s*(--stay-logged-in)?\\s*$"),
    forgetPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>[\\S ]+)\\s*");
    String regex;

    LoginMenu(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return matcher;
    }
}
