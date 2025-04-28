package com.aquaMobs.rpgCore.farming;

import com.aquaMobs.rpgCore.misc.BusyUtil;
import com.aquaMobs.rpgCore.Main;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Regrowable {

    public static void regrowOnBreak(Block b) {

        BlockData bdata = b.getBlockData();

        Ageable age = (Ageable) bdata;

        if (age.getAge() == age.getMaximumAge()) {

            age.setAge(0);

            if (BusyUtil.getConfig().getConfigurationSection("blocks") == null) {
                BusyUtil.getConfig().set("blocks.1", b.getWorld().getName() + "_" + b.getLocation().getBlockX() + "_" + b.getLocation().getBlockY() + "_" + b.getLocation().getBlockZ());
            } else {
                BusyUtil.getConfig().set("blocks." + BusyUtil.getConfig().getConfigurationSection("blocks").getKeys(false).size(),
                            b.getWorld().getName() + "_" + b.getLocation().getBlockX() + "_" + b.getLocation().getBlockY() + "_" + b.getLocation().getBlockZ()
                );
            }

            try {
                BusyUtil.getConfig().save(BusyUtil.file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Sound sound = Sound.ITEM_BONE_MEAL_USE;

            b.setBlockData(bdata);

            World world = b.getWorld();

            new BukkitRunnable() {
                @Override
                public void run() {

                    age.setAge(age.getAge() + 1);

                    b.setBlockData(bdata);

                    world.playSound(b.getLocation(), sound, 1f, 2f);

                    world.spawnParticle(Particle.HAPPY_VILLAGER, b.getLocation(), 2, 0.75, 0.5, 0.75);

                    if (age.getAge() == age.getMaximumAge()) {
                        String blockKey = b.getWorld().getName() + "_" + b.getLocation().getBlockX() + "_" + b.getLocation().getBlockY() + "_" + b.getLocation().getBlockZ();

                        for (String key : BusyUtil.getConfig().getConfigurationSection("blocks").getKeys(false)) {
                            if (BusyUtil.getConfig().getString("blocks." + key).equals(blockKey)) {
                                BusyUtil.getConfig().set("blocks." + key, null);
                            }
                        }
                        try {
                            BusyUtil.getConfig().save(BusyUtil.file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 100L, 1);
        }
    }
}