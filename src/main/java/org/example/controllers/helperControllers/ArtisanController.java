package org.example.controllers.helperControllers;

import org.example.models.App;
import org.example.models.BackPackableType;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.artisan.ArtisanProductType;
import org.example.models.artisan.IngredientGroup;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;
import org.example.models.map.Tile;
import org.example.models.tools.BackPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArtisanController {

    public Result artisanGet(String artisanName) {
        CraftingItemType artisan = CraftingItemType.getCraftingItemTypeByName(artisanName);
        if (artisan == null) {
            return new Result(false, "there is no artisan with name %s".formatted(artisanName));
        }

        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        Tile artisanTile = null;
        for (int i = 0; i < 8; i++) {
            Tile tile = Tile.getTile(player.getX() + dx[i], player.getY() + dy[i]);
            if (tile == null)
                continue;
            if (tile.getPlaceable() == null)
                continue;
            if (tile.getPlaceable().getClass().equals(CraftingItem.class)) {
                CraftingItem craftingItem = (CraftingItem) tile.getPlaceable();
                if (craftingItem.getType().equals(artisan)) {
                    artisanTile = tile;
                    if (craftingItem.getArtisanProductInProgress() == null)
                        continue;
                    break;
                }
            }
        }

        if (artisanTile == null)
            return new Result(false, "You must be near the artisan machine of type %s.".formatted(artisan.getName()));

        CraftingItem craftingItem = (CraftingItem) artisanTile.getPlaceable();
        if (craftingItem.getArtisanProductInProgress() == null)
            return new Result(false, "artisan with name %s is not producing anything".formatted(artisanName));

        ArtisanProduct artisanProduct = craftingItem.getArtisanProductInProgress();

        if (artisanProduct.isReady()) {
            craftingItem.setArtisanProductInProgress(null);
            //TODO
            player.getBackPack().addItemToInventory(artisanProduct);
            CraftingItem.getAllArtisanProductsInProgress().remove(artisanProduct);
            return new Result(true, "artisan product added to backpack");
        } else {
            return new Result(false, "artisan product is not ready.");
        }
    }


    public Result artisanUse(String artisanName, String itemNames, MarketsController marketsController) {
        // 1. Find artisan tool by name
        CraftingItemType artisan = CraftingItemType.getCraftingItemTypeByName(artisanName);

        if (artisan == null) {
            return new Result(false, "No artisan found with name '%s'".formatted(artisanName));
        }

        // 2. (alternative)
        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();

        Tile artisanTile = null;
        for (int i = 0; i < 8; i++) {
            Tile tile = Tile.getTile(player.getX() + dx[i], player.getY() + dy[i]);
            if (tile == null)
                continue;
            if (tile.getPlaceable() == null)
                continue;
            if (tile.getPlaceable().getClass().equals(CraftingItem.class)) {
                CraftingItem craftingItem = (CraftingItem) tile.getPlaceable();
                if (craftingItem.getType().equals(artisan)) {
                    artisanTile = tile;
                    break;
                }
            }
        }
        if (artisanTile == null)
            return new Result(false, "You must be near the artisan machine of type %s.".formatted(artisan.getName()));


//        // 2. Check ownership of artisan tool
        BackPack playerBackPack = App.getCurrentGame().getCurrentPlayingPlayer().getBackPack();
//        if (!playerBackPack.getBackPackItems().containsKey(artisan)) {
//            return new Result(false, "You do not own the artisan tool '%s'.".formatted(artisan.getName()));
//        }

        // 3. Parse input ingredient names into BackPackableTypes
        String[] tokens;
        if (itemNames == null)
            tokens = new String[0];
        else
            tokens = itemNames.trim().split("\\s+");
        ArrayList<BackPackableType> provided = new ArrayList<>();
        for (String token : tokens) {
            Optional<BackPackableType> maybeIngredient = marketsController.parseBackPackable(token);
            if (maybeIngredient.isEmpty()) {
                return new Result(false, "Ingredient '%s' not recognized.".formatted(token));
            }
            BackPackableType type = maybeIngredient.get();
            provided.add(type);
        }

        // 4. Try to match an ArtisanProductType with given artisan and ingredients
        for (ArtisanProductType product : ArtisanProductType.values()) {
            if (!product.getArtisan().equals(artisan)) continue;

            Map<Object, Integer> requiredIngredients = product.getIngredients();

            // Check if all required ingredients are present in the provided map
            boolean match = true;
            for (Map.Entry<Object, Integer> entry : requiredIngredients.entrySet()) {
                Object key = entry.getKey();

                if (key instanceof BackPackableType type) {
                    if (!provided.contains(type))
                        match = false;
                } else if (key instanceof IngredientGroup group) {
                    boolean isFound = false;
                    for (BackPackableType type : group.getMembers()) {
                        if (provided.contains(type)){
                            isFound = true;
                            break;
                        }
                    }
                    match = isFound;
                }
            }
            if (tokens.length != product.getIngredients().size())
                match = false;

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
            ArtisanProduct artisanProduct = new ArtisanProduct(product, ArtisanProduct.getIngredient(product, provided));
            CraftingItem.getAllArtisanProductsInProgress().add(artisanProduct);
            ((CraftingItem)artisanTile.getPlaceable()).setArtisanProductInProgress(artisanProduct);

            return new Result(true, "'%s' is now being produced.".formatted(product.getName()));
        }

        return new Result(false, "No matching artisan product found for '%s' with given ingredients.".formatted(artisanName));
    }
}
