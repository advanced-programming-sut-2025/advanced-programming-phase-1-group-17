package io.github.StardewValley.server;

import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolType;

import java.util.HashMap;
import java.util.UUID;

public class AppServer {
    private static Game currentGame;
    private static HashMap<UUID, Game> activeGames = new HashMap<>();

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame1) {
        currentGame = currentGame1;
    }

    public static HashMap<UUID, Game> getActiveGames() {
        return activeGames;
    }

    public static void setActiveGames(HashMap<UUID, Game> activeGames) {
        AppServer.activeGames = activeGames;
    }

    public static BackPackableType getEnumInstance(String className, String enumName)
        throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        if (!clazz.isEnum()) throw new IllegalArgumentException("Not an enum: " + className);

        @SuppressWarnings("unchecked")
        Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
        return (BackPackableType) Enum.valueOf(enumClass, enumName);
    }

    public static BackpackableTypeDTO getDTO(BackPackableType backPackableType, BackPack backPack) {
        String texturePath = backPackableType.getInventoryTexturePath();
        if (backPackableType instanceof ToolType toolType) {
            if (toolType.equals(ToolType.FishingPole)) {
                texturePath = toolType.getTexturePath(
                    null,
                    ((Tool) backPack.getBackPackItems().get(toolType).get(0)).getFishingPoleType()
                );
            }
            else texturePath = toolType.getTexturePath(
                ((Tool) backPack.getBackPackItems().get(toolType).get(0)).getMaterial(),
                null
            );
        }
        return new BackpackableTypeDTO(
            backPackableType.getName(),
            backPackableType.getClass().getSimpleName(),
            backPackableType.getPrice(),
            texturePath,
            backPack.getBackPackItems().get(backPackableType).size()
        );
    }
}
