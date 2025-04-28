package com.aquaMobs.rpgCore.mining;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import com.aquaMobs.rpgCore.leveling.LevelLoop;
import com.aquaMobs.rpgCore.leveling.XPGain;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import com.aquaMobs.rpgCore.skills.StatsLevelLoop;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class CustomMiningBlock {

    private final float hardness;
    private final Material blockType;
    private final String regionName;
    private final int levelRequired;
    private final int skillLevelRequired;
    private final String skillName;
    private final String itemRewarded;
    private final int skillXpAmountRewarded;
    private final int xpAmountRewarded;
    private final int itemAmountRewarded;
    private final int breakingPowerRequired;

    public CustomMiningBlock (Material blockType, float hardness, String regionName, int levelRequired, int skillLevelRequired, String skillName, String itemRewarded, int skillXpAmountRewarded, int xpAmountRewarded, int itemAmountRewarded, int breakingPowerRequired) {
        this.blockType = blockType;
        this.hardness = hardness;
        this.regionName = regionName;
        this.levelRequired = levelRequired;
        this.skillLevelRequired = skillLevelRequired;
        this.skillName = skillName;
        this.itemRewarded = itemRewarded;
        this.skillXpAmountRewarded = skillXpAmountRewarded;
        this.xpAmountRewarded = xpAmountRewarded;
        this.itemAmountRewarded = itemAmountRewarded;
        this.breakingPowerRequired = breakingPowerRequired;
    }

    public void onBlockDamage(BlockDamageEvent e) {
        Material actualBlockType = e.getBlock().getType();
        Location blockLocation = e.getBlock().getLocation();

        Block b = e.getBlock();

        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();

        int skillLevel = pdc.getOrDefault(new NamespacedKey("aquamobs", this.skillName+"_"+"level"), PersistentDataType.INTEGER, 1);
        int breakingPower = pdc.getOrDefault(new NamespacedKey("aquamobs", "breaking_power"), PersistentDataType.INTEGER, 0);
        int miningSpeed = pdc.getOrDefault(new NamespacedKey("aquamobs", "mining_speed"), PersistentDataType.INTEGER, 0);

        boolean blockIsCorrect = actualBlockType.equals(this.blockType);
        boolean regionIsCorrect = RegionUtil.getRegionsAt(blockLocation).contains(this.regionName);
        boolean playerHasLevel = p.getLevel() >= this.levelRequired;
        boolean playerHasSkillLevel = skillLevel >= this.skillLevelRequired;
        boolean playerHasBreakingPower = breakingPower >= this.breakingPowerRequired;
        ItemStack tool = p.getInventory().getItemInMainHand();

        int constant = 10;

        if (blockIsCorrect && regionIsCorrect && playerHasLevel && playerHasSkillLevel && playerHasBreakingPower) {
            int equality = equality(tool, b);
            p.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(((miningSpeed/(this.hardness*equality))*constant)+0.000001);
        } else if (blockIsCorrect && regionIsCorrect && !playerHasBreakingPower) {
            p.sendMessage("You don't have enough breaking power to break this block!");
            p.sendMessage("Your current breaking power is "+breakingPower+"/"+this.breakingPowerRequired+"!");
        } else if (blockIsCorrect && regionIsCorrect && !playerHasSkillLevel) {
            p.sendMessage("You don't have enough skill level to break this block!");
        } else if (blockIsCorrect && regionIsCorrect && !playerHasLevel) {
            p.sendMessage("You don't have enough level to break this block!");
        }
    }

    private static int equality(ItemStack tool, Block b) {
        boolean isPreferredTool = b.isPreferredTool(tool);
        if (isPreferredTool) {
            switch (tool.getType()) {
                case WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD:
                    return 200;
                case STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SHOVEL, STONE_SWORD:
                    return 400;
                case IRON_AXE, IRON_HOE, IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD:
                    return 600;
                case DIAMOND_AXE, DIAMOND_HOE, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD:
                    return 800;
                case NETHERITE_AXE, NETHERITE_HOE, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD:
                    return 900;
                case GOLDEN_AXE, GOLDEN_HOE, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD:
                    return 1200;
                default:
                    break;
            }
        }
        return 1;
    }

    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        e.setCancelled(true);

        Block b = e.getBlock();
        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();

        PlayerInventory inv = p.getInventory();

        World world = b.getWorld();
        Location blockLocation = b.getLocation();

        p.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0.000001);

        XPGain.levelAndSkillXpGain(p, this.skillName, this.skillXpAmountRewarded, this.xpAmountRewarded);

        ItemStack item = (ItemStack) ConfigUtil.getConfig().get("items." + itemRewarded);

        int fortune = pdc.getOrDefault(new NamespacedKey("aquamobs", "mining_fortune"), PersistentDataType.INTEGER, 0);

        Random random = new Random();

        int rest = fortune % 100;

        int randomNum = random.nextInt(0, 100);

        int finalDropAmount;

        if (randomNum < rest) {
            finalDropAmount = (fortune / 100) + 1;
        } else {
            finalDropAmount = fortune / 100;
        }

        item.setAmount(finalDropAmount * itemAmountRewarded);

        if (inv.firstEmpty() != -1) {
            inv.addItem(item);
        } else {
            world.dropItemNaturally(blockLocation, item);
        }
    }


    public Material getType() {
        return this.blockType;
    }

    public String getRegionName() {
        return this.regionName;
    }
}