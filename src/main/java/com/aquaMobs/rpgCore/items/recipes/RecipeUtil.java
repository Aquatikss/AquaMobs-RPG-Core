package com.aquaMobs.rpgCore.items.recipes;

import com.aquaMobs.rpgCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class RecipeUtil {

    public static File file;
    private static YamlConfiguration config;

    private final static RecipeUtil instance = new RecipeUtil();

    private RecipeUtil() {
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public void load() {
        file = new File(Bukkit.getPluginsFolder() + "/RPGCore", "recipe.yml");

        if (!file.exists()) {
            Main.getPlugin().saveResource("recipe.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RecipeUtil getInstance() {
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
