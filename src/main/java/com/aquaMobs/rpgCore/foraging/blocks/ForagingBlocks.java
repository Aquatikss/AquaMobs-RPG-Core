package com.aquaMobs.rpgCore.foraging.blocks;

import com.aquaMobs.rpgCore.foraging.CustomForagingBlock;
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

public class ForagingBlocks implements Listener {

    static List<CustomForagingBlock> blocks = new ArrayList<>();

    public static void registerBlocks() {

        blocks.add(new CustomForagingBlock(
                List.of(Material.DARK_OAK_LOG,
                        Material.SOUL_SAND,
                        Material.BROWN_TERRACOTTA,
                        Material.BROWN_CONCRETE,
                        Material.BROWN_STAINED_GLASS_PANE,
                        Material.JUNGLE_WOOD,
                        Material.JUNGLE_LOG,
                        Material.SPRUCE_WOOD,
                        Material.SPRUCE_PLANKS,
                        Material.TERRACOTTA),
                10f,
                List.of("large_dark_oak_tree_1",
                        "large_dark_oak_tree_2",
                        "large_dark_oak_tree_3"),
                1,
                1,
                "foraging",
                "dark_oak",
                1,
                1,
                1,
                1
        ));

        blocks.add(new CustomForagingBlock(
                List.of(Material.BIRCH_LOG,
                        Material.DIORITE,
                        Material.QUARTZ_SLAB,
                        Material.QUARTZ_BLOCK,
                        Material.QUARTZ_STAIRS),
                5f,
                List.of("birch_tree_1",
                        "birch_tree_2",
                        "birch_tree_3"),
                1,
                1,
                "foraging",
                "birch",
                1,
                1,
                1,
                1
        ));
    }

    @EventHandler
    public void onMine(BlockDamageEvent e) {
        for (CustomForagingBlock block : blocks) {
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
        for (CustomForagingBlock block : blocks) {
            boolean typeMatch = block.getType().contains(e.getBlock().getType());
            boolean regionMatch = !Collections.disjoint(block.getRegionName(), RegionUtil.getRegionsAt(e.getBlock().getLocation()));
            if (typeMatch && regionMatch) {
                block.onBlockBreak(e);
                return;
            }
        }
    }

}
