package org.example;

import org.example.models.App;
import org.example.models.Game;
import org.example.models.User;
import org.example.models.enums.Menu;
import org.example.views.AppView;

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
