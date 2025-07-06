package io.github.StardewValley;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.Menu;
import io.github.StardewValley.views.AppView;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = LoadUser.loadUsers();
        if (users != null) {
            App.setUsers(users);
        }
        User user1 = LoadUser.loadStayLoggedInUser();
        if (user1 != null) {
            for (User user : users) {
                if (user.equals(user1)) {
                    App.setLoggedInUser(user);
                    App.setCurrentMenu(Menu.MainMenu);
                }
            }
        }
        (new AppView()).run();
    }
}
