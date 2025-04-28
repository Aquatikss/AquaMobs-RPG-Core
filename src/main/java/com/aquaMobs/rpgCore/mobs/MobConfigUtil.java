package com.aquaMobs.rpgCore.mobs;

import com.aquaMobs.rpgCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MobConfigUtil {

    public static File file;
    private static YamlConfiguration config;

    private final static MobConfigUtil instance = new MobConfigUtil();

    private MobConfigUtil() {
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public void load() {
        file = new File(Bukkit.getPluginsFolder() + "/RPGCore", "mobs.yml");

        if (!file.exists()) {
            Main.getPlugin().saveResource("mobs.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MobConfigUtil getInstance() {
        return instance;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }
}
