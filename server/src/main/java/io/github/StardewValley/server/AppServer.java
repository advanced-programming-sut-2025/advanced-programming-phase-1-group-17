package io.github.StardewValley.server;

import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolType;

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

    public static BackpackableTypeDTO getDTO(BackPackableType backPackableType, BackPack backPack) {
        String texturePath = backPackableType.getInventoryTexturePath();
        if (backPackableType instanceof ToolType toolType) {
            System.out.println("Fishing Pole texture ...");
            if (toolType.equals(ToolType.FishingPole)) {
                System.out.println("Fishing Pole texture ...2");
                texturePath = toolType.getTexturePath(
                    null,
                    ((Tool) backPack.getBackPackItems().get(toolType).get(0)).getFishingPoleMaterial()
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
