package io.github.StardewValley;

import io.github.StardewValley.models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveUser {
    public static void saveUser(ArrayList<User> users) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveLoggedInUser(User user) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("stayLoggedIn.json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

