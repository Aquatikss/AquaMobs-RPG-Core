package com.aquaMobs.rpgCore.farming;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import com.aquaMobs.rpgCore.leveling.XPGain;
import com.aquaMobs.rpgCore.misc.RegionUtil;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CustomFarmingBlock {

    private final List<Material> blockType;
    private final List<String> regionName;
    private final String skillName;
    private final String itemRewarded;
    private final int skillXpAmountRewarded;
    private final int xpAmountRewarded;
    private final int itemAmountRewarded;
    private final int skillLevelRequired;
    private final int levelRequired;

    public CustomFarmingBlock (List<Material> blockType, List<String> regionName, int levelRequired, int skillLevelRequired, String skillName, String itemRewarded, int skillXpAmountRewarded, int xpAmountRewarded, int itemAmountRewarded) {
        this.blockType = blockType;
        this.regionName = regionName;
        this.levelRequired = levelRequired;
        this.skillLevelRequired = skillLevelRequired;
        this.skillName = skillName;
        this.itemRewarded = itemRewarded;
        this.skillXpAmountRewarded = skillXpAmountRewarded;
        this.xpAmountRewarded = xpAmountRewarded;
        this.itemAmountRewarded = itemAmountRewarded;
    }

    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        e.setCancelled(true);

        Material actualBlockType = e.getBlock().getType();
        Location blockLocation = e.getBlock().getLocation();

        Block b = e.getBlock();

        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();

        int skillLevel = pdc.getOrDefault(new NamespacedKey("aquamobs", this.skillName+"_"+"level"), PersistentDataType.INTEGER, 1);
        int breakingPower = pdc.getOrDefault(new NamespacedKey("aquamobs", "breaking_power"), PersistentDataType.INTEGER, 0);
        int miningSpeed = pdc.getOrDefault(new NamespacedKey("aquamobs", "mining_speed"), PersistentDataType.INTEGER, 0);

        boolean blockIsCorrect = this.blockType.contains(actualBlockType);
        boolean regionIsCorrect = !Collections.disjoint(this.regionName, RegionUtil.getRegionsAt(blockLocation));
        boolean playerHasLevel = p.getLevel() >= this.levelRequired;
        boolean playerHasSkillLevel = skillLevel >= this.skillLevelRequired;

        if (!blockIsCorrect) return;
        if (!regionIsCorrect) return;

        if (!playerHasLevel) {
            e.getPlayer().sendMessage("You need to be level " + this.levelRequired + " to break this block.");
            return;
        }

        if (e.getBlock().getBlockData() instanceof Ageable) {
            if (((Ageable) e.getBlock().getBlockData()).getAge() != ((Ageable) e.getBlock().getBlockData()).getMaximumAge()) {
                return;
            }
        }

        if (!playerHasSkillLevel) {
            p.sendMessage("You need to be " + this.skillLevelRequired + " in " + this.skillName + " to break this block.");
            return;
        }

        PlayerInventory inv = p.getInventory();

        World world = b.getWorld();

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

        if (e.getBlock().getBlockData() instanceof Ageable) {
            Regrowable.regrowOnBreak(b);
        }
    }


    public List<Material> getType() {
        return this.blockType;
    }

    public List<String> getRegionName() {
        return this.regionName;
    }
}