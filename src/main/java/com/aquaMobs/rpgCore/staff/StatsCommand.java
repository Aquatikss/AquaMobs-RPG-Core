package com.aquaMobs.rpgCore.staff;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        Player victim = (Bukkit.getPlayer(args[0]));

        if (args.length >= 3) {
            switch (args[1].toLowerCase()) {
                case "set":
                    victim.getPersistentDataContainer().set(new NamespacedKey("aquamobs", args[2]), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                    return true;
                case "get":
                    var val = victim.getPersistentDataContainer().get(new NamespacedKey("aquamobs", args[2]), PersistentDataType.INTEGER);
                    s.sendMessage(MiniMessage.miniMessage().deserialize("<#22EE22>" + victim.getName() + " <#EE22EE>has <#22EE22>" + val + " <#EE22EE>of <#22EE22>" + args[2] + "<#EE22EE>!"));
                    return true;
                case "delete":
                    victim.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", args[2]));
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        switch (args.length) {
            case 2:
                return List.of("set", "get", "delete");
            case 3:
                return List.of("level", "xp", "combat_level", "farming_level", "fishing_level", "foraging_level", "mining_level", "combat_xp", "farming_xp", "fishing_xp", "foraging_xp", "mining_xp");
            case 4:
                return List.of("1", "5", "10", "100", "500", "1000", "0");
            default:
                return null;
        }
    }
}
