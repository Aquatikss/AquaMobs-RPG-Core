package com.aquaMobs.rpgCore.stats;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class Speed {

    public static void setSpeed(Player p) {
        int speed = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "speed"), PersistentDataType.INTEGER);

        if (speed > 500) {
            speed = 500;
        }

        float finalSpeed = (float) speed/500;

        if (p.getGameMode() == GameMode.ADVENTURE || p.getGameMode() == GameMode.SURVIVAL) {
            p.setWalkSpeed(finalSpeed);
        }
    }
}
