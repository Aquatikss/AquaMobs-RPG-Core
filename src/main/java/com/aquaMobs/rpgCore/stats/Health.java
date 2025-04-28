package com.aquaMobs.rpgCore.stats;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class Health implements Listener {

    public static void heal(Player p) {
        int canHeal = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER);
        int currentHealth = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER);
        int maxHealth = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER);

        if (currentHealth >= maxHealth) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, maxHealth);
        }

        if (canHeal == 0) {
            int vitality = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "vitality"), PersistentDataType.INTEGER);

            int healAmount = ((maxHealth/80)*(vitality/100));

            p.setHealth(((double) currentHealth /maxHealth)*20);

            if (currentHealth < maxHealth && currentHealth+healAmount <= maxHealth) {
                p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, currentHealth+healAmount);
            } else if (currentHealth < maxHealth && currentHealth+healAmount > maxHealth) {
                p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, maxHealth);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "current_health"))) {
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, 100);
        }
        if (!e.getPlayer().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "can_heal"))) {
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER, 0);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Location location = new Location(e.getPlayer().getWorld(), 0.5, 71, 0.5, 0, 0);
        e.getPlayer().spigot().respawn();
        e.getPlayer().teleport(location);

        e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 10, 2);

        e.getPlayer().setHealth(20);

        e.setCancelled(true);

        e.deathMessage(MiniMessage.miniMessage().deserialize(""));
        e.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<#EE1111><bold>DEATH!</bold> <gray>You died."));

        int health = e.getPlayer().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER);
        e.getPlayer().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, health);

    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        e.setCancelled(true);
    }

}
