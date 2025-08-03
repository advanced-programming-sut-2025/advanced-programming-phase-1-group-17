package io.github.StardewValley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TokenStorage {
    private static final String FILE_NAME = "auth.properties";

    public static void saveToken(String token) throws IOException {
        Properties props = new Properties();
        props.setProperty("jwt", token);
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.store(out, "JWT Token");
        }
    }

    public static String loadToken() throws IOException {
        Properties props = new Properties();
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
            return props.getProperty("jwt");
        }
    }

    public static void clearToken() {
        File file = new File(FILE_NAME);
        if (file.exists()) file.delete();
    }
}

