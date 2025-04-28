package com.aquaMobs.rpgCore.mining.blocks;

import com.aquaMobs.rpgCore.mining.CustomMiningBlock;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class MiningBlocks implements Listener {

    static List<CustomMiningBlock> blocks = new ArrayList<>();

    public static void registerBlocks() {

        blocks.add(new CustomMiningBlock(
                Material.ANDESITE,
                100f,
                "mine1",
                1,
                1,
                "mining",
                "andesite",
                1,
                1,
                1,
                1
        ));

        blocks.add(new CustomMiningBlock(
                Material.GRANITE,
                50f,
                "mine1",
                1,
                1,
                "mining",
                "granite",
                1,
                1,
                1,
                1
        ));
    }

    @EventHandler
    public void onMine(BlockDamageEvent e) {
        for (CustomMiningBlock block : blocks) {
            boolean typeMatch = e.getBlock().getType() == block.getType();
            boolean regionMatch = RegionUtil.getRegionsAt(e.getBlock().getLocation()).contains(block.getRegionName());
            e.getPlayer().getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0.000001);
            if (typeMatch && regionMatch) {
                block.onBlockDamage(e);
                return;
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (CustomMiningBlock block : blocks) {
            e.getPlayer().getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0.000001);
            if (e.getBlock().getType() == block.getType() && RegionUtil.getRegionsAt(e.getBlock().getLocation()).contains(block.getRegionName())) {
                block.onBlockBreak(e);
                return;
            }
        }
    }
}