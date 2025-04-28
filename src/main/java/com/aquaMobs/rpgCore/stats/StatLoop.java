package com.aquaMobs.rpgCore.stats;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class StatLoop {

    private static int getStatValue (ItemStack item, String stat, String itemType) {
        if (item != null && !item.isEmpty() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", stat), PersistentDataType.INTEGER) && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "item_type")) && item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "item_type"), PersistentDataType.STRING).equals(itemType)) {
            return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", stat), PersistentDataType.INTEGER);
        }
        return 0;
    }

    private static void registerStat (Player p, String stat, int base) {

        int weaponValue = getStatValue(p.getInventory().getItemInMainHand(), stat, "weapon");
        int offhandValue = getStatValue(p.getInventory().getItemInOffHand(), stat, "offhand");
        int helmetValue = getStatValue(p.getInventory().getHelmet(), stat, "armor");
        int chestPlateValue = getStatValue(p.getInventory().getChestplate(), stat, "armor");
        int leggingValue = getStatValue(p.getInventory().getLeggings(), stat, "armor");
        int bootValue = getStatValue(p.getInventory().getBoots(), stat, "armor");

        int gearValue = weaponValue + offhandValue + helmetValue + chestPlateValue + leggingValue + bootValue;

        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", stat), PersistentDataType.INTEGER, base+gearValue);

    }

    public static void registerStats(Player p) {

        registerStat(p, "strength", 1);
        registerStat(p, "health", 100);
        registerStat(p, "damage", 1);
        registerStat(p, "mana", 100);
        registerStat(p, "critical_damage", 100);
        registerStat(p, "critical_chance", 0);
        registerStat(p, "defense", 0);
        registerStat(p, "speed", 100);
        registerStat(p, "jump_strength", 100);
        registerStat(p, "luck", 100);
        registerStat(p, "attack_speed", 100);
        registerStat(p, "looting", 100);
        registerStat(p, "mining_speed", 0);
        registerStat(p, "mining_fortune", 100);
        registerStat(p, "fishing_speed", 0);
        registerStat(p, "fishing_fortune", 100);
        registerStat(p, "foraging_speed", 0);
        registerStat(p, "foraging_fortune", 100);
        registerStat(p, "farming_fortune", 100);
        registerStat(p, "breaking_power", 0);
        registerStat(p, "vitality", 100);

    }

}
