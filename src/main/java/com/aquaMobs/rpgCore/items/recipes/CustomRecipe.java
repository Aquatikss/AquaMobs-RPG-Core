package com.aquaMobs.rpgCore.items.recipes;

import org.bukkit.inventory.ItemStack;

public class CustomRecipe {
    private final ItemStack result;
    private final ItemStack[] ingredients;

    public CustomRecipe(ItemStack result, ItemStack[] ingredients) {
        this.result = result;
        this.ingredients = ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack[] getIngredients() {
        return ingredients;
    }
}
