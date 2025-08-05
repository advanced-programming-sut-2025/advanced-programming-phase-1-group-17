package io.github.StardewValley.server;

import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

public class AppServer {
    private static Game currentGame;

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame1) {
        currentGame = currentGame1;
    }

    public static BackPackableType getEnumInstance(String className, String enumName)
        throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        if (!clazz.isEnum()) throw new IllegalArgumentException("Not an enum: " + className);

        @SuppressWarnings("unchecked")
        Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
        return (BackPackableType) Enum.valueOf(enumClass, enumName);
    }

}
