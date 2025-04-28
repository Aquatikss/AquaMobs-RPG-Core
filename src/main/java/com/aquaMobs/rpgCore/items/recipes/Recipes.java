package com.aquaMobs.rpgCore.items.recipes;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

    public static final List<CustomRecipe> registeredRecipes = new ArrayList<>();

    public static void registerRecipes() {

        for (String key : RecipeUtil.getConfig().getConfigurationSection("items").getKeys(false)) {
            String resultAsString = RecipeUtil.getConfig().getString("items." + key + ".result_item");
            int resultQuantity = RecipeUtil.getConfig().getInt("items." + key + ".result_amount");

            ItemStack resultItem = ConfigUtil.getConfig().getItemStack("items." + resultAsString).clone().asQuantity(resultQuantity);

            String[] ingredientsAsString = new String[9];
            ItemStack[] ingredients = new ItemStack[9];

            for (String ingredientsKey : RecipeUtil.getConfig().getConfigurationSection("items." + key + ".ingredients").getKeys(false)) {

                ingredientsAsString[Integer.parseInt(ingredientsKey)-1] = RecipeUtil.getConfig().getString("items." + key + ".ingredients." + ingredientsKey + ".ingredient");
                int ingredientAmount = RecipeUtil.getConfig().getInt("items." + key + ".ingredients." + ingredientsKey + ".amount");

                ingredients[Integer.parseInt(ingredientsKey)-1] = ConfigUtil.getConfig().getItemStack("items." + ingredientsAsString[Integer.parseInt(ingredientsKey)-1]).clone().asQuantity(ingredientAmount);

            }

            for (int i = 0; i < 9; i++) {
                if (ingredients[i] == null) {
                    ingredients[i] = new ItemStack(Material.AIR, 1);
                }
            }

            registeredRecipes.add(new CustomRecipe(resultItem, ingredients));

        }
    }
}