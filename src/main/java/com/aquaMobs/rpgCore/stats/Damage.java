package com.aquaMobs.rpgCore.stats;

import com.aquaMobs.rpgCore.Main;
import com.aquaMobs.rpgCore.mobs.DamageIndicator;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Damage implements Listener {

    MiniMessage miniMessage = MiniMessage.miniMessage();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            Random random = new Random();

            int randomCrit = random.nextInt(0, 100);

            int attackerDamage;
            int victimHealth;
            int victimMaxHealth;
            int critChance = 0;
            int critDamage = 0;
            int strength = 0;

            LivingEntity attacker = (LivingEntity) e.getDamager();
            LivingEntity victim = (LivingEntity) e.getEntity();

            if (attacker instanceof Player) {
                critDamage = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "critical_damage"), PersistentDataType.INTEGER);
                critChance = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "critical_chance"), PersistentDataType.INTEGER);
                strength = attacker.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "strength"), PersistentDataType.INTEGER);
            }

            boolean isCritical = (randomCrit <= critChance);

            attackerDamage = attacker.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "damage"), PersistentDataType.INTEGER, 0);

            victimHealth = victim.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, 0);


            victimMaxHealth = victim.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER, 0);

            if (victim.getNoDamageTicks() <= 10) {
                if (attacker instanceof Player) {
                    DamageIndicator.damageIndicator(e, isCritical);
                    if (isCritical) {
                        victimHealth = (int) (victimHealth - ((attackerDamage) * (strength)) * ((double) critDamage / 100));
                    } else {
                        victimHealth = victimHealth - ((attackerDamage) * (strength));
                    }
                    victim.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
                    victim.setHealth(((double) victimHealth /victimMaxHealth)*20);
                } else {
                    victimHealth = victimHealth - attackerDamage;
                }
            }

            victim.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, victimHealth);

            String name = e.getEntity().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "name"), PersistentDataType.STRING);

            int current_health = e.getEntity().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER);
            int health = e.getEntity().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER);

            int damage = e.getEntity().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "damage"), PersistentDataType.INTEGER);

            if (victim instanceof Player) {
                if ((victimHealth + attackerDamage) - attackerDamage <= 0) {
                    ((Player) e.getEntity()).setHealth(20);
                } else {
                    ((Player) e.getEntity()).setHealth((((double) current_health / (double) health) * 20));
                }

                int canHeal = victim.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER);

                victim.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER, canHeal + 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int canHeal = victim.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER);
                        victim.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "can_heal"), PersistentDataType.INTEGER, canHeal - 1);
                    }
                }.runTaskLater(Main.getPlugin(), 100L);
            }

            if (!(victim instanceof Player)) {
                if (isCritical) {
                    if ((victimHealth + attackerDamage) - ((attackerDamage) * (strength)) * ((double) critDamage / 100) <= 0) {
                        victim.setHealth(0);
                        e.setCancelled(true);
                        e.getEntity().customName(miniMessage.deserialize(name + " <dark_gray>| <red> 0<dark_gray>/<red>" + health + " ❤ <dark_gray>| <red> " + damage + " \uD83D\uDDE1"));
                        return;
                    }
                } else {
                    if ((victimHealth + attackerDamage) - ((attackerDamage) * (strength)) <= 0) {
                        victim.setHealth(0);
                        e.setCancelled(true);
                        e.getEntity().customName(miniMessage.deserialize(name + " <dark_gray>| <red> 0<dark_gray>/<red>" + health + " ❤ <dark_gray>| <red> " + damage + " \uD83D\uDDE1"));
                        return;
                    }
                }
            } else {
                if ((victimHealth + attackerDamage) - attackerDamage <= 0) {
                    victim.setHealth(0);
                    e.setCancelled(true);
                    e.getEntity().getPersistentDataContainer().set(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER, health);
                    return;
                }
            }

            e.getEntity().customName(miniMessage.deserialize(name + " <dark_gray>| <red> " + victimHealth + "<dark_gray>/<red>" + health + " ❤ <dark_gray>| <red> " + damage + " \uD83D\uDDE1"));

        }
    }

    @EventHandler
    public void onDamage (EntityDamageEvent e){

        if (e.getDamageSource().getDamageType() != DamageType.GENERIC_KILL) {
            e.setDamage(0);
        }
    }

}
