package com.aquaMobs.rpgCore.misc;

import org.bukkit.entity.Player;

public class AutoFeed {

    public static void feedAll(Player p) {
        p.setFoodLevel(20);
        p.setSaturation(20);
    }

}
