package com.aquaMobs.rpgCore.mobs;

import com.aquaMobs.rpgCore.Main;
import com.aquaMobs.rpgCore.ui.NumFormatter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DamageIndicator {

    public static void damageIndicator (EntityDamageByEntityEvent e, boolean isCrit) {

        Random random = new Random();

        int randomID = random.nextInt(0, 2147483647);
        int randomYaw = random.nextInt(-180, 180);

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Player attacker = (Player) e.getDamager();
        LivingEntity victim = (LivingEntity) e.getEntity();

        int damage = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "damage"), PersistentDataType.INTEGER);
        int strength = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "strength"), PersistentDataType.INTEGER);
        int critDamage = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "critical_damage"), PersistentDataType.INTEGER);

        TextDisplay indicator = victim.getWorld().spawn(victim.getLocation(), TextDisplay.class);

        indicator.customName(miniMessage.deserialize("aquamobs:indicator_" + randomID));
        indicator.setBillboard(Display.Billboard.CENTER);
        if (isCrit) {
            indicator.text(miniMessage.deserialize("<gold>⚔ <gradient:gold:yellow:gold>" + NumFormatter.formatCommas((damage*strength)*((double) critDamage /100)) + "</gradient> <gold>⚔"));
        } else {
            indicator.text(miniMessage.deserialize("<red>\uD83D\uDDE1 <gradient:red:gold:red>" + NumFormatter.formatCommas((damage*strength)) + "</gradient> <red>\uD83D\uDDE1"));
        }
        indicator.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        indicator.setTeleportDuration(2);

        Location loc = new Location(victim.getWorld(), victim.getLocation().getX(), victim.getLocation().getY()+1, victim.getLocation().getZ(), randomYaw, 0);

        indicator.teleport(loc);

         new BukkitRunnable() {
             int i = 0;
             double gravity = 0.7;
            @Override
            public void run() {
                indicator.teleport(indicator.getLocation().add(indicator.getLocation().getDirection().multiply(0.1)));
                indicator.teleport(indicator.getLocation().add(0, gravity, 0));
                i++;
                gravity = gravity - 0.1;
                if (i >= 20) {
                    indicator.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 1L);

    }

}
