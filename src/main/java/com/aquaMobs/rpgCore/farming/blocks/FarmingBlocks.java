package com.aquaMobs.rpgCore.farming.blocks;

import com.aquaMobs.rpgCore.farming.CustomFarmingBlock;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FarmingBlocks implements Listener {

    static List<CustomFarmingBlock> blocks = new ArrayList<>();

    public static void registerBlocks() {

        blocks.add(new CustomFarmingBlock(
                List.of(Material.WHEAT),
                List.of("farm", "spawn_farm"),
                1,
                1,
                "farming",
                "wheat",
                1,
                1,
                1
        ));

        blocks.add(new CustomFarmingBlock(
                List.of(Material.CARROTS),
                List.of("farm", "spawn_farm"),
                1,
                1,
                "farming",
                "wheat",
                1,
                1,
                1
        ));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (CustomFarmingBlock block : blocks) {
            boolean typeMatch = block.getType().contains(e.getBlock().getType());
            boolean regionMatch = !Collections.disjoint(block.getRegionName(), RegionUtil.getRegionsAt(e.getBlock().getLocation()));
            if (typeMatch && regionMatch) {
                block.onBlockBreak(e);
                return;
            }
        }
    }

}
