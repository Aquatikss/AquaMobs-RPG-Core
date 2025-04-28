package com.aquaMobs.rpgCore.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoRecipes implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.clearRecipes();
    }

}
