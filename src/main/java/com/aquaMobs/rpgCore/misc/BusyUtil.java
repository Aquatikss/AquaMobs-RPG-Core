package com.aquaMobs.rpgCore.misc;

import com.aquaMobs.rpgCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BusyUtil {

    public static File file;
    private static YamlConfiguration config;

    private final static BusyUtil instance = new BusyUtil();

    private BusyUtil() {
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public void load() {
        file = new File(Bukkit.getPluginsFolder() + "/RPGCore", "busy.yml");

        if (!file.exists()) {
            Main.getPlugin().saveResource("busy.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BusyUtil getInstance() {
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
