package com.aquaMobs.rpgCore.leveling;

import com.aquaMobs.rpgCore.Main;
import com.aquaMobs.rpgCore.skills.StatsLevelLoop;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class XPGain {


    public static void skillXpGain(Player p, String skill, int amountAdded) {

        Random random = new Random();
        float pitch = random.nextFloat(1f, 2f);

        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, pitch);

        int xp = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", skill+"_xp"), PersistentDataType.INTEGER, 0);
        xp += amountAdded;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skill+"_xp"), PersistentDataType.INTEGER, xp);

        StatsLevelLoop.levelUp(p, skill);

        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gained_amount"), PersistentDataType.INTEGER, amountAdded);
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gained_type"), PersistentDataType.STRING, skill);
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) + 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) - 1);
                if (p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) <= 0) {
                    p.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", "skill_xp_gained_amount"));
                    p.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", "skill_xp_gained_type"));
                }
            }
        }.runTaskLater(Main.getPlugin(), 100L);

    }

    public static void levelXpGain(Player p, int amountAdded) {

        Random random = new Random();
        float pitch = random.nextFloat(1f, 2f);

        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, pitch);

        int xp = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, 0);
        xp += amountAdded;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, xp);

        LevelLoop.levelUp(p);

    }

    public static void levelAndSkillXpGain(Player p, String skill, int amountAddedSkill, int amountAdded) {

        Random random = new Random();
        float pitch = random.nextFloat(1f, 2f);

        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, pitch);

        int defaultXp = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, 0);
        defaultXp += amountAdded;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER, defaultXp);

        LevelLoop.levelUp(p);

        int skillXp = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", skill+"_xp"), PersistentDataType.INTEGER, 0);
        skillXp += amountAddedSkill;
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", skill+"_xp"), PersistentDataType.INTEGER, skillXp);

        StatsLevelLoop.levelUp(p, skill);

        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gained_amount"), PersistentDataType.INTEGER, amountAdded);
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gained_type"), PersistentDataType.STRING, skill);
        p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) + 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) - 1);
                if (p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0) <= 0) {
                    p.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", "skill_xp_gained_amount"));
                    p.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", "skill_xp_gained_type"));
                }
            }
        }.runTaskLater(Main.getPlugin(), 100L);

    }

}
