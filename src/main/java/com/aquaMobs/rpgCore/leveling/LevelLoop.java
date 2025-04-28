package com.aquaMobs.rpgCore.leveling;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

import java.net.http.WebSocket;

public class LevelLoop implements Listener {

    public static void setRequiredXp(Player p) {
        int level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER);
        int xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER);
        int xpRequired = (int)Math.pow(level/Math.cbrt(2), 3)+1;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER, xpRequired);

        p.setLevel(level);
        p.setExp((float) xp /xpRequired);
    }

    @EventHandler
    public static void playerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
        }
        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, 0);
        }
        if (!p.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER)) {
            setRequiredXp(e.getPlayer());
        }

    }

    public static void levelUp(Player p) {
        int xpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER);
        int xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER);
        int level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER);

        while (xp >= xpRequired) {

            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, level + 1);
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, xp-xpRequired);

            setRequiredXp(p);

            xpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER);
            xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER);
            level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER);

        }

    }

}
