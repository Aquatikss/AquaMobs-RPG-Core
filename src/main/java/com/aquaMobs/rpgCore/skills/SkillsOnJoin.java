package com.aquaMobs.rpgCore.skills;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SkillsOnJoin implements Listener {

    private static void skillLevelNotSet(Player player, String skillName, String skillType) {
        if (!player.getPersistentDataContainer().has(new NamespacedKey("aquamobs", skillName+"_"+skillType))) {
            if (skillType.equalsIgnoreCase("level")) {
                player.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skillName+"_"+skillType), PersistentDataType.INTEGER, 1);
            } else if (skillType.equalsIgnoreCase("xp")) {
                player.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skillName+"_"+skillType), PersistentDataType.INTEGER, 0);
            }
        }
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player p = e.getPlayer();

        List<String> skills = List.of("combat", "farming", "fishing", "foraging", "mining");

        for (String skill : skills) {
            skillLevelNotSet(p, skill, "level");
            skillLevelNotSet(p, skill, "xp");
        }
    }
}
