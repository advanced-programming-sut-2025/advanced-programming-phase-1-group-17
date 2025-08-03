package io.github.StardewValley.controllers;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.enums.SignUpMenuCommands;
import io.github.StardewValley.views.MainMenu;
import io.github.StardewValley.views.ProfileMenu;


public class ProfileMenuController {
    private ProfileMenu view;
    public static int avatar = 1;

    public void setView(ProfileMenu profileMenu) {
        this.view = profileMenu;
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getBackButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.setError("Entering to MainMenu...");
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                    }
                }, 2);
            }
        });
        view.getChangeUserNameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getNewUserName().setVisible(true);
                view.getNewPassword().setVisible(false);
                view.getOldPassword().setVisible(false);
                view.getOldEmail().setVisible(false);
                view.getNewEmail().setVisible(false);
                view.getNickName().setVisible(false);
                view.setError("please enter new username");
                String newuserName = view.getNewUserName().getText();
                if (newuserName.isEmpty()) {
                    return;
                }
                try {
                    UserDTO user = GameClient.getGameStateApiClient().getUserWithUserDTO();
                    String newUsername = view.getNewUserName().getText().trim();
                    if (SignUpMenuCommands.Username.getMatcher(newUsername) == null) {
                        view.setError("new username format is invalid");
                        return;
                    }
                    if (newUsername.equals(user.getUsername())) {
                        view.setError("new username is already taken");
                        return;
                    }
                    user.setUsername(newUsername);
                    view.setError("set successfully");
                    view.getNewUserName().setVisible(false);
                    view.getNewPassword().setVisible(false);
                    view.getOldPassword().setVisible(false);
                    view.getOldEmail().setVisible(false);
                    view.getNewEmail().setVisible(false);
                    view.getNickName().setVisible(false);
                    GameClient.getGameStateApiClient().updateUser(user);
                    if (GameClient.getPlayer() != null)
                        GameClient.getPlayer().setUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }
            }
        });


        view.getChangeEmailButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getNewUserName().setVisible(false);
                view.getNewPassword().setVisible(false);
                view.getOldPassword().setVisible(false);
                view.getOldEmail().setVisible(true);
                view.getNewEmail().setVisible(true);
                view.getNickName().setVisible(false);
                view.setError("please enter old and new email");
                String oldEmail = view.getOldEmail().getText().trim();
                String newEmail = view.getNewEmail().getText().trim();
                if (newEmail.isEmpty() || oldEmail.isEmpty()) {
                    return;
                }
                try {
                    UserDTO user = GameClient.gameStateApiClient.getUserWithUserDTO();
                    if (SignUpMenuCommands.Email.getMatcher(newEmail) == null) {
                        view.setError("new email format is invalid");
                        return;
                    }
                    if (!oldEmail.equals(user.getEmail())) {
                        view.setError("old email does not match");
                        return;
                    }
                    if (newEmail.equals(user.getEmail())) {
                        view.setError("new username is already taken");
                        return;
                    }
                    user.setEmail(newEmail);
                    view.setError("set successfully");
                    view.getNewUserName().setVisible(false);
                    view.getNewPassword().setVisible(false);
                    view.getOldPassword().setVisible(false);
                    view.getOldEmail().setVisible(false);
                    view.getNewEmail().setVisible(false);
                    view.getNickName().setVisible(false);
                    GameClient.getGameStateApiClient().updateUser(user);
                    if (GameClient.getPlayer() != null)
                        GameClient.getPlayer().setUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }
            }
        });


        view.getChangePasswordButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getNewUserName().setVisible(false);
                view.getNewPassword().setVisible(true);
                view.getOldPassword().setVisible(true);
                view.getOldEmail().setVisible(false);
                view.getNewEmail().setVisible(false);
                view.getNickName().setVisible(false);
                view.setError("please enter old and new password");
                try {
                    UserDTO user = GameClient.getGameStateApiClient().getUserWithUserDTO();
                    String oldPassword = view.getOldPassword().getText().trim();
                    if (oldPassword.isEmpty()) {
                        return;
                    }
                    if (!GameClient.getGameStateApiClient().passWordCheck(oldPassword)) {
                        view.setError("your old password is incorrect");
                        return;
                    }
                    String newPassword = view.getNewPassword().getText().trim();
                    if (SignUpMenuCommands.ValidPassword.getMatcher(newPassword) == null || !new SignUpMenuController().isPasswordStrong(newPassword).isSuccessful()) {
                        view.setError("new password format is invalid");
                        return;
                    }
                    if (GameClient.getGameStateApiClient().passWordCheck(newPassword)) {
                        view.setError("new passwords is the same as the previous password");
                        return;
                    }
                    GameClient.getGameStateApiClient().changePassword(oldPassword, newPassword);
                    view.setError("set successfully");
                    GameClient.getGameStateApiClient().updateUser(user);
                    if (GameClient.getPlayer() != null)
                        GameClient.getPlayer().setUser(user);
                    view.getNewUserName().setVisible(false);
                    view.getNewPassword().setVisible(false);
                    view.getOldPassword().setVisible(false);
                    view.getOldEmail().setVisible(false);
                    view.getNewEmail().setVisible(false);
                    view.getNickName().setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }
            }
        });
        view.getShowUserInfo().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    UserDTO user = GameClient.getGameStateApiClient().getUserWithUserDTO();
                    view.setError("username : " + user.getUsername() +
                        "\n" + "email : " + user.getEmail()
                        + "\n" + "nickname : " + user.getNickname()
                        + "\n" + "TheMostMoneyInGame : " + user.getTheMostMoneyInGame()
                        + "\n" + "NumOfPlay : " + user.getNumOfPlay());
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }
            }
        });
        view.getChangeNickName().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.getNickName().setVisible(true);
                view.getNewUserName().setVisible(false);
                view.getNewPassword().setVisible(false);
                view.getOldPassword().setVisible(false);
                view.getOldEmail().setVisible(false);
                view.getNewEmail().setVisible(false);
                try {
                    UserDTO user = GameClient.getGameStateApiClient().getUserWithUserDTO();
                    String nickName = view.getNickName().getText().trim();
                    if (nickName.isEmpty()) {
                        return;
                    }
                    if (nickName.equals(user.getNickname())) {
                        view.setError("nickname is the same as the previous nickname");
                        return;
                    }
                    view.setError("set successfully");
                    user.setNickname(nickName);
                    GameClient.getGameStateApiClient().updateUser(user);
                    if (GameClient.getPlayer() != null)
                        GameClient.getPlayer().setUser(user);
                    view.getNewUserName().setVisible(false);
                    view.getNewPassword().setVisible(false);
                    view.getOldPassword().setVisible(false);
                    view.getOldEmail().setVisible(false);
                    view.getNewEmail().setVisible(false);
                    view.getNickName().setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }

            }
        });
        view.getChangeAvatar().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (avatar == 8)
                    avatar = 1;

                avatar++;
                if (avatar > 8)
                    avatar = 1;

                try {
                    UserDTO user = GameClient.gameStateApiClient.getUserWithUserDTO();
                    user.setAvatar("avatar/avatar" + avatar + ".jpg");
                    GameClient.getGameStateApiClient().updateUser(user);
                    if (GameClient.getPlayer() != null)
                        GameClient.getPlayer().setUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setError(e.getMessage());
                }

            }
        });


    }
}
