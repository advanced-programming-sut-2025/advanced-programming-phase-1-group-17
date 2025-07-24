package io.github.StardewValley.shared.dto;

public class SecurityQuestionRequest {
    private String username;
    private String question;
    private String answer;

    // Required for Jackson
    public SecurityQuestionRequest() {}

    public SecurityQuestionRequest(String username, String question, String answer) {
        this.username = username;
        this.question = question;
        this.answer = answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
