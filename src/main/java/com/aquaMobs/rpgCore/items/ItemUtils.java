package com.aquaMobs.rpgCore.items;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static List<ItemStack> getItemsWithRecipe() {

        ConfigurationSection section = ConfigUtil.getConfig().getConfigurationSection("items");

        if (section == null) {
            return new ArrayList<>();
        }

        List<ItemStack> items = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            ItemStack item = ConfigUtil.getConfig().getItemStack("items." + key);
            boolean hasRecipe = false;
            boolean hasRecipeTag = item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "has_recipe"), PersistentDataType.BOOLEAN);
            if (hasRecipeTag) {
                hasRecipe = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "has_recipe"), PersistentDataType.BOOLEAN);
            }

            if (hasRecipe) {
                items.add(item);
            }
        }

        return items;
    }

    public static List<ItemStack> getSellableItems() {

        ConfigurationSection section = ConfigUtil.getConfig().getConfigurationSection("items");

        if (section == null) {
            return new ArrayList<>();
        }

        List<ItemStack> items = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            ItemStack item = ConfigUtil.getConfig().getItemStack("items." + key);
            boolean hasRecipe = false;
            boolean hasRecipeTag = item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "can_sell"), PersistentDataType.BOOLEAN);
            if (hasRecipeTag) {
                hasRecipe = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "can_sell"), PersistentDataType.BOOLEAN);
            }

            if (hasRecipe) {
                items.add(item);
            }
        }

        return items;
    }

    public static List<ItemStack> getBuyableItems() {

        ConfigurationSection section = ConfigUtil.getConfig().getConfigurationSection("items");

        if (section == null) {
            return new ArrayList<>();
        }

        List<ItemStack> items = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            ItemStack item = ConfigUtil.getConfig().getItemStack("items." + key);
            boolean hasRecipe = false;
            boolean hasRecipeTag = item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "can_buy"), PersistentDataType.BOOLEAN);
            if (hasRecipeTag) {
                hasRecipe = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "can_buy"), PersistentDataType.BOOLEAN);
            }

            if (hasRecipe) {
                items.add(item);
            }
        }

        return items;
    }

    public static List<ItemStack> getCollectableItems() {

        ConfigurationSection section = ConfigUtil.getConfig().getConfigurationSection("items");

        if (section == null) {
            return new ArrayList<>();
        }

        List<ItemStack> items = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            ItemStack item = ConfigUtil.getConfig().getItemStack("items." + key);
            boolean isCollectable = false;
            boolean hasCollectableTag = item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "is_collectable"), PersistentDataType.BOOLEAN);
            if (hasCollectableTag) {
                isCollectable = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "is_collectable"), PersistentDataType.BOOLEAN);
            }

            if (isCollectable) {
                items.add(item);
            }
        }

        return items;
    }

}
