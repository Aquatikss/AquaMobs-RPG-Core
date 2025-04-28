package com.aquaMobs.rpgCore.stats;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class Mana implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().getPersistentDataContainer().has(new NamespacedKey("aquamobs", "current_mana"))) {
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_mana"), PersistentDataType.INTEGER, 100);
        }
    }
}
