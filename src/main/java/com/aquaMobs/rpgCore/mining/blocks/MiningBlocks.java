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
import java.util.Collections;
import java.util.List;

public class MiningBlocks implements Listener {

    static List<CustomMiningBlock> blocks = new ArrayList<>();

    public static void registerBlocks() {

        blocks.add(new CustomMiningBlock(
                List.of(Material.ANDESITE, Material.STONE),
                100f,
                List.of("mine1", "mine3"),
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
                List.of(Material.GRANITE, Material.DIORITE),
                50f,
                List.of("mine1", "mine2"),
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
            boolean typeMatch = block.getType().contains(e.getBlock().getType());
            boolean regionMatch = !Collections.disjoint(block.getRegionName(), RegionUtil.getRegionsAt(e.getBlock().getLocation()));
            if (typeMatch && regionMatch) {
                block.onBlockDamage(e);
                return;
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (CustomMiningBlock block : blocks) {
            boolean typeMatch = block.getType().contains(e.getBlock().getType());
            boolean regionMatch = !Collections.disjoint(block.getRegionName(), RegionUtil.getRegionsAt(e.getBlock().getLocation()));
            if (typeMatch && regionMatch) {
                block.onBlockBreak(e);
                return;
            }
        }
    }
}