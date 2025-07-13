package io.github.StardewValley.controllers;

import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.User;
import io.github.StardewValley.views.SecurityQuestionMenu;
import io.github.StardewValley.views.SignUpMenu;

public class SecurityQuestionMenuController {
    private User user;
    private String question = "";
    private SecurityQuestionMenu view;

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void checkAnswer() {
        if (question.equals("")) {
            view.getErrorLabel().setText("You need to pick a question First.");
        } else if (view.getSecurityAnswer().getText().equals(view.getSecurityAnswerConfirm().getText()) &&
            !view.getSecurityAnswer().getText().isEmpty()) {

            user.setSecurityQuestion(question);
            user.setSecurityAnswer(view.getSecurityAnswer().getText());

            Main.getMain().getScreen().dispose();
            SignUpMenu signupMenu = new SignUpMenu(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin());
            signupMenu.getErrorLabel().setText("User %s registered successfully.".formatted(user.getUsername()));
            Main.getMain().setScreen(signupMenu);
        } else {
            view.getErrorLabel().setText("Answer and Answer Confirm are not the same.");
        }
    }

    public void setView(SecurityQuestionMenu view) {
        this.view = view;
    }
}
