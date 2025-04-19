package org.example.models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    GoToLoginMenu("\\s*menu\\s+enter\\s+login\\s+menu\\s*"),
    GoToMainMenu("\\s*menu\\s+enter\\s+main\\s+menu\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    Exit("\\s*menu\\s+exit\\s*"),
    Register("\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+" +
            "(?<passwordConfirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)\\s+-e(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*"),
    Username("[a-zA-Z\\d-]+"),
    Email("^(?!.*[!#$%^&*()=+{}\\[\\]|\\\\:;'\",<>?/])(?=^[a-zA-Z0-9](?!.*\\.\\.)[a-zA-Z0-9._-]*[a-zA-Z0-9]@)" +
            "[a-zA-Z0-9._-]+@[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?(\\.[a-zA-Z]{2,})+$"),
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
