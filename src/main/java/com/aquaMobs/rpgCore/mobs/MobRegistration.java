package com.aquaMobs.rpgCore.mobs;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Set;

public class MobRegistration implements Listener {

    @EventHandler
    public void onSewerZombieSpawn(EntitySpawnEvent e) {
        Configuration config = MobConfigUtil.getConfig();

        Set<String> keys = config.getConfigurationSection("mobs").getKeys(false);

        for (String key : keys) {
            ConfigurationSection mobSection = config.getConfigurationSection("mobs." + key);
            String mobID = mobSection.getString("id");
            int health = mobSection.getInt("health");
            int damage = mobSection.getInt("damage");
            String name = mobSection.getString("name");

            MobUtil.onSpawn(e, mobID, health, damage, name);
        }
    }
}