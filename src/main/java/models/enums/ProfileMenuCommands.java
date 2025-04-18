package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {

    changeUsername("\\s*change\\s+username\\s+-u\\s+(?<username>[\\S]+)\\s*"),
    changeNickName("\\s*change\\s+nickname\\s+-u\\s+(?<nickname>[\\S]+)\\s*"),
    changePassword("\\s*change\\s+password\\s+-p\\s+(?<newPassword>[\\S]+)\\s+-o\\s+(?<oldPassword>[\\S]+)\\s*"),
    changeEmail("\\s*change\\s+email\\s+-e\\s+(?<email>[\\S]+)\\s*");

    String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return matcher;
    }
}
