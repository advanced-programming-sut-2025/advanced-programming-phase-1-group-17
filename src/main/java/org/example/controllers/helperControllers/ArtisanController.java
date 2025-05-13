package org.example.controllers.helperControllers;

import org.example.models.App;
import org.example.models.BackPackableType;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.artisan.IngredientGroup;
import org.example.models.crafting.CraftingItemType;
import org.example.models.tools.BackPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArtisanController {

    public Result artisanGet(String artisanName) {
        CraftingItemType type = CraftingItemType.getCraftingItemTypeByName(artisanName);
        if (type == null) {
            return new Result(false, "there is no artisan with name %s".formatted(artisanName));
        }

        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        for (ArtisanProduct artisanProduct : player.getArtisanProductsInProgress()) {
            if (artisanProduct.getType().getArtisan().equals(type)) {
                if (artisanProduct.isReady()) {
                    player.getBackPack().addItemToInventory(artisanProduct);
                    return new Result(true, "artisan product added to backpack");
                } else {
                    return new Result(false, "artisan product is not ready.");
                }
            }
        }
        return new Result(false, "artisan with name %s is not producing anything".formatted(artisanName));
    }


    public Result artisanUse(String artisanName, String itemNames, MarketsController marketsController) {
        // 1. Find artisan tool by name
        CraftingItemType artisan = CraftingItemType.getCraftingItemTypeByName(artisanName);

        if (artisan == null) {
            return new Result(false, "No artisan found with name '%s'".formatted(artisanName));
        }

        // 2. Check ownership of artisan tool
        BackPack playerBackPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
        if (!playerBackPack.getBackPackItems().containsKey(artisan)) {
            return new Result(false, "You do not own the artisan tool '%s'.".formatted(artisan.getName()));
        }

        // 3. Parse input ingredient names into BackPackableTypes
        String[] tokens = itemNames.trim().split("\\s+");
        Map<BackPackableType, Integer> provided = new HashMap<>();
        for (String token : tokens) {
            Optional<BackPackableType> maybeIngredient = marketsController.parseBackPackable(token);
            if (maybeIngredient.isEmpty()) {
                return new Result(false, "Ingredient '%s' not recognized.".formatted(token));
            }
            BackPackableType type = maybeIngredient.get();
            provided.put(type, provided.getOrDefault(type, 0) + 1);
        }

        // 4. Try to match an ArtisanProductType with given artisan and ingredients
        for (ArtisanProductType product : ArtisanProductType.values()) {
            if (!product.getArtisan().equals(artisan)) continue;

            Map<Object, Integer> requiredIngredients = product.getIngredients();

            // Check if all required ingredients are present in the provided map
            boolean match = true;
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                Object key = entry.getKey();
                int requiredAmount = entry.getValue();
                int providedAmount = 0;

                if (key instanceof BackPackableType type) {
                    providedAmount = provided.getOrDefault(type, 0);
                } else if (key instanceof IngredientGroup group) {
                    for (BackPackableType type : group.getMembers()) {
                        providedAmount += provided.getOrDefault(type, 0);
                    }
                }

                if (providedAmount < requiredAmount) {
                    match = false;
                    break;
                }
            }

            if (!match) continue;

            // 5. Check if player owns enough of each required ingredient
            //TODO: check if is already artisan is producing something
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                int required = entry.getValue();

                if (entry.getKey() instanceof BackPackableType type) {
                    int owned = playerBackPack.getBackPackItems()
                            .getOrDefault(type, new ArrayList<>()).size();
                    if (owned < required) {
                        return new Result(false, "Not enough of '%s'.".formatted(type.getName()));
                    }
                } else if (entry.getKey() instanceof IngredientGroup group) {
                    int available = group.countInBackPack(playerBackPack);
                    if (available < required) {
                        return new Result(false, "Not enough items from group '%s'.".formatted(group.name()));
                    }
                }
            }

            // 6. Remove ingredients from backpack
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                int amount = entry.getValue();
                if (entry.getKey() instanceof BackPackableType type) {
                    for (int i = 0; i < amount; i++) {
                        playerBackPack.useItem(type);
                    }
                } else if (entry.getKey() instanceof IngredientGroup group) {
                    group.removeFromBackPack(amount, playerBackPack);
                }
            }

            // 7. Start artisan production
            App.getCurrentGame().getCurrentPlayingPlayer()
                    .getArtisanProductsInProgress().add(new ArtisanProduct(product));

            return new Result(true, "'%s' is now being produced.".formatted(product.getName()));
        }

        return new Result(false, "No matching artisan product found for '%s' with given ingredients.".formatted(artisanName));
    }
}
