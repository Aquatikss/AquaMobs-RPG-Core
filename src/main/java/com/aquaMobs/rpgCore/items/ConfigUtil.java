package com.aquaMobs.rpgCore.items;

import com.aquaMobs.rpgCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtil {

    public static File file;
    private static YamlConfiguration config;

    private final static ConfigUtil instance = new ConfigUtil();

    private ConfigUtil() {
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public void load() {
        file = new File(Bukkit.getPluginsFolder() + "/RPGCore", "items.yml");

        if (!file.exists()) {
            Main.getPlugin().saveResource("items.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigUtil getInstance() {
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
