package io.github.StardewValley.controllers;

import io.github.StardewValley.views.ProfileMenu;

public class ProfileMenuController {
    private ProfileMenu view;
    public static int avatar = 1;

    public void setView(ProfileMenu profileMenu) {
        this.view = profileMenu;
        setupButtonListener();
    }

    private void setupButtonListener() {
//        view.getBackButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                Sfx_Controller.getInstance().playClick();
//                view.setError("Entering to MainMenu...");
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run() {
//                        Main.getMain().getScreen().dispose();
//                        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//                    }
//                }, 2);
//            }
//        });
//        view.getChangeUserNameButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                Sfx_Controller.getInstance().playClick();
//                view.getNewUserName().setVisible(true);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//                view.setError("please enter new username");
//                String newuserName = view.getNewUserName().getText();
//                if (newuserName.isEmpty()) {
//                    return;
//                }
//                User user = App.getLoggedInUser();
//                String newUsername = view.getNewUserName().getText().trim();
//                if (SignUpMenuCommands.Username.getMatcher(newUsername) == null) {
//                    view.setError("new username format is invalid");
//                    return;
//                }
//                if (newUsername.equals(user.getUsername())) {
//                    view.setError("new username is already taken");
//                    return;
//                }
//                user.setUsername(newUsername);
//                view.setError("set successfully");
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//                SaveUser.saveUser(App.getUsers());
//                SaveUser.saveLoggedInUser(App.getLoggedInUser());
//            }
//        });
//
//
//        view.getChangeEmailButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                Sfx_Controller.getInstance().playClick();
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(true);
//                view.getNewEmail().setVisible(true);
//                view.getNickName().setVisible(false);
//                view.setError("please enter old and new email");
//                String oldEmail = view.getOldEmail().getText().trim();
//                String newEmail = view.getNewEmail().getText().trim();
//                if (newEmail.isEmpty() || oldEmail.isEmpty()) {
//                    return;
//                }
//                User user = App.getLoggedInUser();
//                if (SignUpMenuCommands.Email.getMatcher(newEmail) == null) {
//                    view.setError("new email format is invalid");
//                    return;
//                }
//                if (!oldEmail.equals(user.getEmail())) {
//                    view.setError("old email does not match");
//                    return;
//                }
//                if (newEmail.equals(user.getEmail())) {
//                    view.setError("new username is already taken");
//                    return;
//                }
//                user.setEmail(newEmail);
//                view.setError("set successfully");
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//                SaveUser.saveUser(App.getUsers());
//                SaveUser.saveLoggedInUser(App.getLoggedInUser());
//            }
//        });
//
//
//        view.getChangePasswordButton().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
////                Sfx_Controller.getInstance().playClick();
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(true);
//                view.getOldPassword().setVisible(true);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//                view.setError("please enter old and new password");
//
//                User user = App.getLoggedInUser();
//                String oldPassword = view.getOldPassword().getText().trim();
//                if (oldPassword.isEmpty()) {
//                    return;
//                }
//                if (!user.getRawPassword().equals(oldPassword)) {
//                    view.setError("your old password is incorrect");
//                    return;
//                }
//                String newPassword = view.getNewPassword().getText().trim();
//                if (SignUpMenuCommands.ValidPassword.getMatcher(newPassword) == null) {
//                    view.setError("new password format is invalid");
//                    return;
//                }
//                if (newPassword.equals(user.getRawPassword())) {
//                    view.setError("new passwords is the same as the previous password");
//                    return;
//                }
//                user.setRawPassword(newPassword);
//                view.setError("set successfully");
//                SaveUser.saveUser(App.getUsers());
//                SaveUser.saveLoggedInUser(App.getLoggedInUser());
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//            }
//        });
//        view.getShowUserInfo().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                User user = App.getLoggedInUser();
//                view.setError("username : " + user.getUsername() +
//                    "\n" + "email : " + user.getEmail()
//                    + "\n" + "nickname : " + user.getNickName()
//                    + "\n" + "TheMostMoneyInGame : " + user.getTheMostMoneyInGame()
//                    + "\n" + "NumOfPlay : " + user.getNumOfPlay());
//            }
//        });
//        view.getChangeNickName().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                view.getNickName().setVisible(true);
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                User user = App.getLoggedInUser();
//                String nickName = view.getNickName().getText().trim();
//                if (nickName.isEmpty()) {
//                    return;
//                }
//                if (nickName.equals(user.getNickName())) {
//                    view.setError("nickname is the same as the previous nickname");
//                    return;
//                }
//                view.setError("set successfully");
//                user.setNickName(nickName);
//                SaveUser.saveUser(App.getUsers());
//                SaveUser.saveLoggedInUser(App.getLoggedInUser());
//
//                view.getNewUserName().setVisible(false);
//                view.getNewPassword().setVisible(false);
//                view.getOldPassword().setVisible(false);
//                view.getOldEmail().setVisible(false);
//                view.getNewEmail().setVisible(false);
//                view.getNickName().setVisible(false);
//
//            }
//        });
//        view.getChangeAvatar().addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                if (avatar == 8)
//                    avatar = 1;
//
//                avatar++;
//                if (avatar > 8)
//                    avatar = 1;
//
//                App.getLoggedInUser().setAvatar("avatar/avatar" + avatar + ".jpg");
//                SaveUser.saveUser(App.getUsers());
//
//            }
//        });
//
//
    }
}
