package com.aquaMobs.rpgCore.ui;

import com.aquaMobs.rpgCore.items.recipes.CustomRecipe;
import com.aquaMobs.rpgCore.items.recipes.Recipes;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomCrafts implements Listener {

    private static boolean matches(ItemStack[] matrix, ItemStack[] ingredients) {
        for (int i = 0; i < 9; i++) {
            ItemStack matItem = matrix[i];
            ItemStack ingItem = ingredients[i];

            boolean matAir = matItem == null || matItem.getType() == Material.AIR;
            boolean ingAir = ingItem == null || ingItem.getType() == Material.AIR;

            if (matAir && ingAir) continue; // both empty, valid
            if (matAir != ingAir) return false; // mismatch: one empty, one not
            if (matItem.getType() != ingItem.getType()) return false;
            if (matItem.getAmount() < ingItem.getAmount()) return false;
        }
        return true;
    }

    private static final Map<UUID, int[]> slotAmountsPerPlayer = new HashMap<>();

    @EventHandler
    public void aquaCrafts(PrepareItemCraftEvent e) {

        CraftingInventory inventory = e.getInventory();
        ItemStack[] matrix = inventory.getMatrix();
        UUID playerUUID = e.getView().getPlayer().getUniqueId();

        for (CustomRecipe recipe : Recipes.registeredRecipes) {
            ItemStack[] ingredients = recipe.getIngredients();
            if (matches(matrix, ingredients)) {
                inventory.setResult(recipe.getResult());
                int[] slotAmounts = new int[9];
                for (int i = 0; i < 9; i++) {
                    slotAmounts[i] = ingredients[i] != null ? ingredients[i].getAmount() : 0;
                }
                slotAmountsPerPlayer.put(playerUUID, slotAmounts);
                return;
            }
        }

        inventory.setResult(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void craftEvent(InventoryClickEvent e) {

        UUID uuid = e.getWhoClicked().getUniqueId();
        int[] slotAmount = slotAmountsPerPlayer.getOrDefault(uuid, new int[9]);

        Inventory inv = e.getInventory();

        if (inv.getType() == InventoryType.WORKBENCH && e.getRawSlot() == 0) {
            CraftingInventory inventory = (CraftingInventory) e.getInventory();
            ItemStack[] matrix = inventory.getMatrix();
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i] != null) {
                    matrix[i].setAmount(matrix[i].getAmount() - slotAmount[i]);
                    slotAmountsPerPlayer.remove(uuid);
                }
            }
        }
    }
}