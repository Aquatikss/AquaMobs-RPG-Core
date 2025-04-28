package com.aquaMobs.rpgCore.leveling;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class StatsNotSet implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER, (int)Math.round(Math.pow(1/Math.cbrt(2), 3)+1));
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, 0);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
        }

    }

}
