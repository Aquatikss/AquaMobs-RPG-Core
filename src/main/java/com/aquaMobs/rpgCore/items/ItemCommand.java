package com.aquaMobs.rpgCore.items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandExecutor, TabExecutor {

    MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ItemStack item;
            if (args.length >= 2) {
                switch (args[0]) {
                    case "get":
                        item = (ItemStack) ConfigUtil.getConfig().get("items." + args[1]);
                        p.getInventory().addItem(item);
                        p.sendMessage(miniMessage.deserialize("<#11EE11>Successfully been given " + args[1] + "!"));
                        break;
                    case "set":
                        item = p.getItemInHand().asOne();
                        ItemMeta meta = item.getItemMeta();
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        data.set(new NamespacedKey("aquamobs", "id"), PersistentDataType.STRING, args[1]);
                        item.setItemMeta(meta);
                        p.getItemInHand().setItemMeta(meta);
                        ConfigUtil.getConfig().set("items." + args[1], item);
                        try {
                            ConfigUtil.getConfig().save(ConfigUtil.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        p.sendMessage(miniMessage.deserialize("<#11EE11>Successfully set " + args[1] + " to " + item + "!"));
                        break;
                    case "delete":
                        ConfigUtil.getConfig().set("items." + args[1], null);
                        try {
                            ConfigUtil.getConfig().save(ConfigUtil.file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        p.sendMessage(miniMessage.deserialize("<#11EE11>Successfully deleted " + args[1] + "!"));
                        break;
                    default:
                        p.sendMessage(miniMessage.deserialize("<#EE1111>Please use get/set/delete for the first argument!"));
                        return false;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete( CommandSender sender, Command command, String label, String [] args) {
        if (sender instanceof Player) {
            List<String> returning = new ArrayList<String>();
            switch (args.length) {
                case 1:
                    returning.add("get");
                    returning.add("set");
                    returning.add("delete");
                    return returning;
                case 2:
                    returning.addAll(ConfigUtil.getConfig().getConfigurationSection("items").getKeys(false));
                    return returning;
                default:
                    return null;
            }
        }
        return null;
    }
}
