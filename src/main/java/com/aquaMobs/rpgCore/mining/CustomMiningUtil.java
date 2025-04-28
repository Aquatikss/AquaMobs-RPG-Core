package com.aquaMobs.rpgCore.mining;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageAbortEvent;

public class CustomMiningUtil {
    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent e) {
        e.getPlayer().getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0.000001);
    }
}
