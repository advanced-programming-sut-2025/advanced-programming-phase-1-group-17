package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.models.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoadUser {
    public static ArrayList<User> loadUsers() {
        Gson gson = new Gson();
        File file = new File("users.json");
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static User loadStayLoggedInUser() {
        Gson gson = new Gson();
        File file = new File("stayLoggedIn.json");
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader("stayLoggedIn.json")) {
            User user = gson.fromJson(reader, User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(file)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

