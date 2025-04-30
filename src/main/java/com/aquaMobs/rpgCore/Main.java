package com.aquaMobs.rpgCore;

import com.aquaMobs.rpgCore.areas.Portals;
import com.aquaMobs.rpgCore.chat.ChatFormatting;
import com.aquaMobs.rpgCore.farming.blocks.FarmingBlocks;
import com.aquaMobs.rpgCore.foraging.CustomForagingUtil;
import com.aquaMobs.rpgCore.foraging.blocks.ForagingBlocks;
import com.aquaMobs.rpgCore.items.ConfigUtil;
import com.aquaMobs.rpgCore.items.ItemCommand;
import com.aquaMobs.rpgCore.items.recipes.RecipeUtil;
import com.aquaMobs.rpgCore.items.recipes.Recipes;
import com.aquaMobs.rpgCore.leveling.LevelLoop;
import com.aquaMobs.rpgCore.mining.CustomMiningUtil;
import com.aquaMobs.rpgCore.mining.blocks.MiningBlocks;
import com.aquaMobs.rpgCore.misc.*;
import com.aquaMobs.rpgCore.mobs.MobConfigUtil;
import com.aquaMobs.rpgCore.mobs.MobUtil;
import com.aquaMobs.rpgCore.mobs.MobRegistration;
import com.aquaMobs.rpgCore.npc.ShopNPCUtil;
import com.aquaMobs.rpgCore.skills.SkillsOnJoin;
import com.aquaMobs.rpgCore.skills.StatsLevelLoop;
import com.aquaMobs.rpgCore.staff.StatsCommand;
import com.aquaMobs.rpgCore.stats.*;
import com.aquaMobs.rpgCore.ui.*;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main plugin;

    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {

        plugin = this;

        Bukkit.getLogger().log(Level.INFO, "AquaMobs RPG-Core Plugin Started!");

        PapiHook.registerHook();

        VaultHook.setupChat();
        VaultHook.setupEconomy();
        VaultHook.setupPermissions();

        MiningBlocks.registerBlocks();
        FarmingBlocks.registerBlocks();
        ForagingBlocks.registerBlocks();

        Bukkit.getPluginManager().registerEvents(new CustomForagingUtil(), this);
        Bukkit.getPluginManager().registerEvents(new CustomMiningUtil(), this);
        Bukkit.getPluginManager().registerEvents(new MobRegistration(), this);
        Bukkit.getPluginManager().registerEvents(new ChatFormatting(), this);
        Bukkit.getPluginManager().registerEvents(new ForagingBlocks(), this);
        Bukkit.getPluginManager().registerEvents(new FarmingBlocks(), this);
        Bukkit.getPluginManager().registerEvents(new SkillsOnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new CustomCrafts(), this);
        Bukkit.getPluginManager().registerEvents(new MiningBlocks(), this);
        Bukkit.getPluginManager().registerEvents(new ShopNPCUtil(), this);
        Bukkit.getPluginManager().registerEvents(new NoTrampling(), this);
        Bukkit.getPluginManager().registerEvents(new Collection(), this);
        Bukkit.getPluginManager().registerEvents(new NoRecipes(), this);
        Bukkit.getPluginManager().registerEvents(new LevelLoop(), this);
        Bukkit.getPluginManager().registerEvents(new NoCombust(), this);
        Bukkit.getPluginManager().registerEvents(new MainMenu(), this);
        Bukkit.getPluginManager().registerEvents(new Sidebar(), this);
        Bukkit.getPluginManager().registerEvents(new MobUtil(), this);
        Bukkit.getPluginManager().registerEvents(new Portals(), this);
        Bukkit.getPluginManager().registerEvents(new Health(), this);
        Bukkit.getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getPluginManager().registerEvents(new Mana(), this);

        getCommand("itemstats").setExecutor(new SetItemStatCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("item").setExecutor(new ItemCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        saveDefaultConfig();
        ConfigUtil.getInstance().load();
        BusyUtil.getInstance().load();
        RecipeUtil.getInstance().load();
        MobConfigUtil.getInstance().load();

        Bukkit.resetRecipes();

        Recipes.registerRecipes();

        if (BusyUtil.getConfig().getConfigurationSection("blocks") !=null) {
            for (String key : BusyUtil.getConfig().getConfigurationSection("blocks").getKeys(false)) {

                World world = Bukkit.getWorld(BusyUtil.getConfig().getString("blocks." + key).split("_")[0]);
                double x = Double.parseDouble(BusyUtil.getConfig().getString("blocks." + key).split("_")[1]);
                double y = Double.parseDouble(BusyUtil.getConfig().getString("blocks." + key).split("_")[2]);
                double z = Double.parseDouble(BusyUtil.getConfig().getString("blocks." + key).split("_")[3]);

                Location loc = new Location(world, x, y, z);

                Block b = loc.getBlock();

                BlockData bData = b.getBlockData();

                if (bData instanceof Ageable) {
                    Ageable age = (Ageable) bData;
                    age.setAge(age.getMaximumAge());
                    b.setBlockData(bData);
                    String blockKey = b.getWorld().getName() + "_" + b.getLocation().getBlockX() + "_" + b.getLocation().getBlockY() + "_" + b.getLocation().getBlockZ();
                    for (String newKey : BusyUtil.getConfig().getConfigurationSection("blocks").getKeys(false)) {
                        if (BusyUtil.getConfig().getString("blocks." + newKey).equals(blockKey)) {
                            BusyUtil.getConfig().set("blocks." + newKey, null);
                        }
                    }

                    try {BusyUtil.getConfig().save(BusyUtil.file);} catch (Exception e) {e.printStackTrace();}

                }

                BusyUtil.getConfig().set("blocks." + key, null);
            }

            try {BusyUtil.getConfig().save(BusyUtil.file);} catch (Exception e) {e.printStackTrace();}

        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            Sidebar.init(p);
            p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    List<String> skills = List.of("combat", "farming", "fishing", "foraging", "mining");

                    for (String skill : skills) {
                        StatsLevelLoop.setRequiredXp(p, skill);
                        StatsLevelLoop.levelUp(p, skill);
                    }

                    LevelLoop.levelUp(p);
                    LevelLoop.setRequiredXp(p);
                    StatLoop.registerStats(p);
                    AutoFeed.feedAll(p);
                    Speed.setSpeed(p);
                    Health.heal(p);
                    ActionBar.sendActionBar(p);
                }
                for (FastBoard board : Sidebar.boards.values()) {
                    Sidebar.updateBoard(board);
                }

            }
        }.runTaskTimer(this, 1L, 5L);

    }

    @Override
    public void onDisable() {

        Bukkit.getLogger().log(Level.INFO, "AquaMobs RPG-Core Plugin Stopped!");

    }

    public static Main getPlugin() {
        return plugin;
    }

}
