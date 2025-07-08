package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.controllers.SecurityQuestionMenuController;

import javax.swing.*;
import java.util.List;

public class SecurityQuestionMenu implements Screen {
    private final SecurityQuestionMenuController controller;
    private Stage stage;

    private final Label titelLabel;
    private final Label errorLabel;
    private final Label pleaseSelectLabel;

    private final Table securityQuestionsTable;
    private final ScrollPane securityQuestionsPane;

    private final TextField securityAnswer;
    private final TextField securityAnswerConfirm;
    private final TextButton submitAnswer;

    private final List<String> securityQuestions = List.of(
        "What is the name of your first pet?",
        "What is your mother's maiden name?",
        "What was the name of your elementary school?",
        "What is your favorite book?",
        "What city were you born in?",
        "What is your favorite food?",
        "What was the make of your first car?",
        "What is your father's middle name?",
        "In what city did your parents meet?",
        "What was the name of your childhood best friend?"
    );

    public SecurityQuestionMenu(SecurityQuestionMenuController controller, Skin skin) {
        this.controller = controller;
        this.titelLabel = new Label("Security Question Select", skin);
        this.errorLabel = new Label("User registered successfully.", skin);
        this.errorLabel.setAlignment(Align.center);
        this.pleaseSelectLabel = new Label("Please select one of the questions and answer to it.", skin);
        this.pleaseSelectLabel.setAlignment(Align.center);

        this.securityQuestionsTable = new Table();
        for (String securityQuestion : securityQuestions) {
            TextButton button = new TextButton(securityQuestion, skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pleaseSelectLabel.setText("Selected Question: %s".formatted(securityQuestion));
                    controller.setQuestion(securityQuestion);
                }
            });
            securityQuestionsTable.add(button).fillX().pad(4).row();
        }
        this.securityQuestionsPane = new ScrollPane(securityQuestionsTable, skin);
        //this.securityQuestionsPane.setScrollingDisabled(true, false);

        this.securityAnswer = new TextField("", skin);
        this.securityAnswer.setMessageText("Security Answer");
        this.securityAnswerConfirm = new TextField("", skin);
        this.securityAnswerConfirm.setMessageText("Security Answer Confirm");

        this.submitAnswer = new TextButton("Submit Answer", skin);
        this.submitAnswer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.checkAnswer();
            }
        });

        controller.setView(this);
    }

    @Override
    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().pad(30);
        mainTable.defaults().pad(6).width(500);

        // Title and instructions
        mainTable.add(titelLabel).center().padBottom(10).row();
        mainTable.add(pleaseSelectLabel).center().padBottom(10).row();

        // Security questions scrollable pane
        securityQuestionsTable.top().defaults().pad(4).fillX().width(800);
        ScrollPane scrollPane = new ScrollPane(securityQuestionsTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setOverscroll(false, false);
        scrollPane.setScrollbarsOnTop(false);

        mainTable.add(scrollPane).height(250).padBottom(30).row(); // scroll only this part

        // Input fields
        mainTable.add(securityAnswer).padTop(10).row();
        mainTable.add(securityAnswerConfirm).padTop(5).row();

        // Submit button
        mainTable.add(submitAnswer).padTop(10).row();

        // Error/Success message
        mainTable.add(errorLabel).padTop(10).row();

        stage.addActor(mainTable);
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public TextField getSecurityAnswer() {
        return securityAnswer;
    }

    public TextField getSecurityAnswerConfirm() {
        return securityAnswerConfirm;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
