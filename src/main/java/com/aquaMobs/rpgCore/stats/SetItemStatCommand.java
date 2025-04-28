package com.aquaMobs.rpgCore.stats;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SetItemStatCommand implements CommandExecutor, TabExecutor {

    public static ItemMeta publicMeta;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player) {
            if (args.length >= 2) {
                MiniMessage miniMessage = MiniMessage.miniMessage();
                Player p = (Player) sender;
                ItemStack item = p.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                publicMeta = item.getItemMeta();

                switch (args[0]) {
                    case "set":
                        if (args.length >= 3) {
                            switch (args[2]) {
                                case "boolean":
                                    meta.getPersistentDataContainer().set(new NamespacedKey("aquamobs", args[1]), PersistentDataType.BOOLEAN, Boolean.parseBoolean(args[3]));
                                    break;
                                case "int":
                                    meta.getPersistentDataContainer().set(new NamespacedKey("aquamobs", args[1]), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    break;
                                case "string":
                                    meta.getPersistentDataContainer().set(new NamespacedKey("aquamobs", args[1]), PersistentDataType.STRING, args[3]);
                                    break;
                                case "float":
                                    meta.getPersistentDataContainer().set(new NamespacedKey("aquamobs", args[1]), PersistentDataType.FLOAT, Float.parseFloat(args[3]));
                                    break;
                            }
                            item.setItemMeta(meta);
                            p.getInventory().setItemInMainHand(item);
                            p.sendMessage(miniMessage.deserialize("\uE018 Successfully set " + args[1] + " on this tool to " + args[3] + "!"));
                        } else {
                            p.sendMessage(miniMessage.deserialize("\uE017 Please input a value for " + args[1] + "!"));
                        }
                        break;
                    case "get":
                        if (meta.getPersistentDataContainer().has(new NamespacedKey("aquamobs", args[1]))) {
                            switch (args[2]) {
                                case "boolean":
                                    boolean val1 = meta.getPersistentDataContainer().get(new NamespacedKey("aquamobs", args[1]), PersistentDataType.BOOLEAN);
                                    p.sendMessage(miniMessage.deserialize("\uE017 " + args[1] + " on this tool is currently set to " + val1 + "!"));
                                    break;
                                case "int":
                                    int val2 = meta.getPersistentDataContainer().get(new NamespacedKey("aquamobs", args[1]), PersistentDataType.INTEGER);
                                    p.sendMessage(miniMessage.deserialize("\uE017 " + args[1] + " on this tool is currently set to " + val2 + "!"));
                                    break;
                                case "string":
                                    String val3 = meta.getPersistentDataContainer().get(new NamespacedKey("aquamobs", args[1]), PersistentDataType.STRING);
                                    p.sendMessage(miniMessage.deserialize("\uE017 " + args[1] + " on this tool is currently set to " + val3 + "!"));
                                    break;
                                case "float":
                                    float val4 = meta.getPersistentDataContainer().get(new NamespacedKey("aquamobs", args[1]), PersistentDataType.FLOAT);
                                    p.sendMessage(miniMessage.deserialize("\uE017 " + args[1] + " on this tool is currently set to " + val4 + "!"));
                                    break;
                            }

                        } else {
                            p.sendMessage(miniMessage.deserialize("\uE017 No value found for " + args[1] + "!"));
                        }
                        break;
                    case "delete":
                        meta.getPersistentDataContainer().remove(new NamespacedKey("aquamobs", args[1]));
                        p.sendMessage(miniMessage.deserialize("\uE018 Successfully deleted " + args[1] + "!"));
                        item.setItemMeta(meta);
                        p.getInventory().setItemInMainHand(item);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String [] args) {
        switch (args.length) {
            case 1:
                return List.of("set", "get", "delete");
            case 2:
                return List.of(
                        "strength",
                        "health",
                        "damage",
                        "mana",
                        "critical_damage",
                        "critical_chance",
                        "defense",
                        "speed",
                        "jump_strength",
                        "luck",
                        "attack_speed",
                        "looting",
                        "mining_speed",
                        "mining_fortune",
                        "fishing_speed",
                        "fishing_fortune",
                        "foraging_speed",
                        "foraging_fortune",
                        "farming_fortune",
                        "breaking_power",
                        "vitality",
                        "sell_price",
                        "buy_price",
                        "has_recipe",
                        "can_sell",
                        "can_buy",
                        "shop_id",
                        "is_collectable",
                        "item_type"
                    );
            case 3:
                return List.of("boolean", "int", "string", "float");
            case 4:
                if (args[2].equals("string") && args[1].equals("item_type")) {
                    return List.of("weapon", "armor", "offhand");
                }

                if (args[2].equals("boolean")) {
                    return List.of("true", "false");
                } else if (args[2].equals("int")) {
                    return List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
                } else if (args[2].equals("string")) {
                    return List.of("exampleString");
                } else if (args[2].equals("float")) {
                    return List.of("0.0", "1.0", "2.0", "3.0", "4.0", "5.0");
                }
            default:
                return null;
        }
    }
}
