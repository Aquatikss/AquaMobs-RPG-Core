package com.aquaMobs.rpgCore.skills;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

import java.net.http.WebSocket;

public class StatsLevelLoop implements Listener {

    public static void setRequiredXp(Player p, String skillName) {
        int level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_level"), PersistentDataType.INTEGER);
        int xpRequired = (int)Math.pow(level/Math.cbrt(2), 3)+1;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skillName+"_xprequired"), PersistentDataType.INTEGER, xpRequired);
    }

    public static void levelUp(Player p, String skillName) {
        int xpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_xprequired"), PersistentDataType.INTEGER);
        int xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_xp"), PersistentDataType.INTEGER);
        int level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_level"), PersistentDataType.INTEGER);

        while (xp >= xpRequired) {

            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skillName+"_level"), PersistentDataType.INTEGER, level + 1);
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skillName+"_xp"), PersistentDataType.INTEGER, xp-xpRequired);

            setRequiredXp(p, skillName);

            xpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_xprequired"), PersistentDataType.INTEGER);
            xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_xp"), PersistentDataType.INTEGER);
            level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skillName+"_level"), PersistentDataType.INTEGER);

        }

    }

}
