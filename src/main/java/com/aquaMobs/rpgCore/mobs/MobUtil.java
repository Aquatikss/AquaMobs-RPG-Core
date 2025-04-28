package com.aquaMobs.rpgCore.mobs;

import com.aquaMobs.rpgCore.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class MobUtil implements Listener {

    public static MiniMessage miniMessage = MiniMessage.miniMessage();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        e.getDrops().removeAll(e.getDrops());
        e.setDroppedExp(0);
    }

    public static void onSpawn(EntitySpawnEvent e, String mobID, int health, int damage, String name) {

        new BukkitRunnable() {
            @Override
            public void run() {

                if (e.getEntity().getCustomName() != null) {

                    String customName = e.getEntity().getCustomName();

                    if (customName.contains(mobID)) {

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, health);

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER, health);

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "damage"), PersistentDataType.INTEGER, damage);

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "name"), PersistentDataType.STRING, name);

                        e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "id"), PersistentDataType.STRING, mobID);

                        e.getEntity().customName(miniMessage.deserialize(name + " <dark_gray>| <red> " + health + "<dark_gray>/<red>" + health + " ❤ <dark_gray>| <red> " + damage + " \uD83D\uDDE1"));

                    }
                }
            }
        }.runTaskLater(Main.getPlugin(), 1L);
    }
}
