package com.aquaMobs.rpgCore.misc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "aquamobs";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Aquatikss";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (params.equalsIgnoreCase("level")) {
            return "" + offlinePlayer.getPlayer().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER);
        }

        return null;
    }

    public static void registerHook() {
        new PapiHook().register();
    }
}
