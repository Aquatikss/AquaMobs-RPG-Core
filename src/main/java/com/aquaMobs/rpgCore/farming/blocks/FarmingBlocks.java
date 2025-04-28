package com.aquaMobs.rpgCore.farming.blocks;

import com.aquaMobs.rpgCore.farming.CustomFarmingBlock;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class FarmingBlocks implements Listener {

    static List<CustomFarmingBlock> blocks = new ArrayList<>();

    public static void registerBlocks() {

        blocks.add(new CustomFarmingBlock(
                Material.WHEAT,
                "farm",
                1,
                1,
                "farming",
                "wheat",
                1,
                1,
                1
        ));

        blocks.add(new CustomFarmingBlock(
                Material.CARROTS,
                "farm",
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
            if (e.getBlock().getType() == block.getType() && RegionUtil.getRegionsAt(e.getBlock().getLocation()).contains(block.getRegionName())) {
                block.onBlockBreak(e);
                return;
            }
        }
    }

}
