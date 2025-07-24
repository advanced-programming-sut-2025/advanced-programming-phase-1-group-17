package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;

import io.github.StardewValley.views.LoginMenu;
import io.github.StardewValley.views.SignUpMenu;


public class LoginMenuController {
    private LoginMenu view;

    public void setView(LoginMenu view) {
        this.view = view;
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        view.getUserNameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getUserName().setVisible(true);
            }
        });
        view.getPasswordButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getPassword().setVisible(true);
            }
        });
        view.getForgotPasswordButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!view.getAnswer().isVisible()) {
                    view.getAnswer().setVisible(true);
                    return;
                }
                if (view.getUserName().getText().isEmpty()) {
                    view.setError("Please enter a username");
                    return;
                }
                sendForgotPasswordRequest();

//                if (App.getUserWithUsername(view.getUserName().getText()) == null) {
//                    view.setError("Username not found");
//                    return;
//                }
//                User user = App.getUserWithUsername(view.getUserName().getText());
//                if (view.getError().getText().toString().isEmpty()) {
//                    view.setError(user.getSecurityQuestion());
//                    return;
//                }
//                String answer = view.getAnswer().getText();
//                if (answer.equals(user.getSecurityAnswer())) {
//                    SignUpMenuController controller = new SignUpMenuController();
//                    String newPass = controller.generateStrongPassword(12);
//                    user.setPasswordHash(PasswordUtil.hashPassword(newPass));
//                    view.setError("your new pass is : " + newPass + "\n you can change this pass on ProfileMenu");
//                    SaveUser.saveUser(App.getUsers());
//                } else {
//                    view.setError("Wrong answer");
//
//                }


            }
        });
        view.getLoginButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUserName().getText();
                String password = view.getPassword().getText();
                if (username.isEmpty() || password.isEmpty()) {
                    view.setError("Username and Password are required");
                    return;
                }
//                if (App.getUserWithUsername(username) != null) {
//                    User user = App.getUserWithUsername(username);
//                    String hashedInput = PasswordUtil.hashPassword(password);
//                    if (user.getPasswordHash().equals(hashedInput)) {
//                        App.setLoggedInUser(user);
//                        Main.getMain().getScreen().dispose();
//                        if (view.getCheckBox().isChecked()) {
//                            SaveUser.saveLoggedInUser(user);
//                        }
//                        view.getCheckBox().setChecked(false);
//                        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//                    } else {
//                        view.setError("invalid password");
//                    }
//                } else {
//                    view.setError("username not found");
//                }
//

            }
        });
    }

    private void sendForgotPasswordRequest() {

    }


}
