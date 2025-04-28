package com.aquaMobs.rpgCore.areas;

import com.aquaMobs.rpgCore.Main;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Portals implements Listener {

    public static HashMap<Player, Boolean> portalCooldown = new HashMap<>();

    public static void teleportToArea(PlayerToggleSneakEvent e, String regionName, World world, double x, double y, double z, float yaw, float pitch) {

        if (!portalCooldown.containsKey(e.getPlayer())) {
            portalCooldown.put(e.getPlayer(), false);
        }

        if (e.isSneaking() && RegionUtil.getRegionsAt(e.getPlayer().getLocation()).contains(regionName) && !portalCooldown.get(e.getPlayer())) {

            portalCooldown.put(e.getPlayer(), true);

            Location loc = new Location(world, x, y, z, yaw, pitch);

            e.getPlayer().teleport(loc);

            e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_ENDERMAN_TELEPORT, 10000, 0);

            e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_PORTAL_TRAVEL, 0.5f, 2);

            PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 80, 0, false, false, false);
            PotionEffect slow = new PotionEffect(PotionEffectType.SLOWNESS, 60, 255, false, false, false);

            e.getPlayer().getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);

            e.getPlayer().addPotionEffect(blind);
            e.getPlayer().addPotionEffect(slow);

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getPlayer().getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.42f);
                }
            }.runTaskLater(Main.getPlugin(), 60L);

        }
        new BukkitRunnable() {
            @Override
            public void run() {
                portalCooldown.put(e.getPlayer(), false);
            }
        }.runTaskLater(Main.getPlugin(), 1L);
    }

    @EventHandler
    public void onCrouchToggle(PlayerToggleSneakEvent e) {
        teleportToArea(e, "spawntofarm", e.getPlayer().getWorld(), 1000.5, 0, 1000.5, 0, 0);
        teleportToArea(e, "farmtospawn", e.getPlayer().getWorld(), 64.5, 70, -129.5, 90, 0);
    }
}
