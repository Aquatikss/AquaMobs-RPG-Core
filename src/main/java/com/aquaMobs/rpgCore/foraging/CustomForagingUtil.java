package com.aquaMobs.rpgCore.foraging;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageAbortEvent;

public class CustomForagingUtil implements Listener {

    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent e) {
        e.getPlayer().getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0.000001);
    }
}