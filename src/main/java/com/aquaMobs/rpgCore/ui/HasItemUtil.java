package com.aquaMobs.rpgCore.ui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HasItemUtil {

    public static boolean hasItem(Inventory inv, ItemStack item, int amount) {
        int count = 0;
        for (ItemStack i : inv.getContents()) {
            if (i != null && i.isSimilar(item)) {
                count += i.getAmount();
            }
        }
        return count >= amount;
    }
}
