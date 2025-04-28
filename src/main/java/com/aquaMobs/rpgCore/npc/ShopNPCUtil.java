package com.aquaMobs.rpgCore.npc;

import com.aquaMobs.rpgCore.misc.VaultHook;
import com.aquaMobs.rpgCore.items.ConfigUtil;
import com.aquaMobs.rpgCore.items.ItemUtils;
import com.aquaMobs.rpgCore.ui.NumFormatter;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static com.aquaMobs.rpgCore.ui.HasItemUtil.hasItem;

public class ShopNPCUtil implements Listener {

    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent e) {
        shopNPC(e, "Gourdric");
    }

    public void shopNPC(PlayerInteractEntityEvent e, String npcName) {
        if (npcName.equalsIgnoreCase(e.getRightClicked().getName()) && e.getRightClicked().hasMetadata("NPC") && e.getHand() == EquipmentSlot.HAND) {

            e.setCancelled(true);

            MiniMessage miniMessage = MiniMessage.miniMessage();

            Player p = e.getPlayer();

            Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);

            Sound openSound = Sound.sound(Key.key("entity.villager.trade"), Sound.Source.MASTER, 1f, 1f);

            Sound denySound = Sound.sound(Key.key("block.note_block.bass"), Sound.Source.MASTER, 100f, 0f);

            Sound confirmSound = Sound.sound(Key.key("block.note_block.bell"), Sound.Source.MASTER, 100f, 1f);

            p.playSound(openSound);

            Gui gui = Gui.gui()
                    .title(miniMessage.deserialize(e.getRightClicked().getName()))
                    .rows(6)
                    .disableAllInteractions()
                    .create();

            GuiItem filler = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.filler_menu_item")).asGuiItem();

            GuiItem close = ItemBuilder.from(Material.BARRIER).name(miniMessage.deserialize("<!italic><red>Close")).asGuiItem(event -> {
                p.closeInventory();
                p.playSound(clickSound);
            });

            for (int i = 0; i < 9; i++) {
                gui.addItem(filler);
            }

            gui.setItem(2, 1, filler);
            gui.setItem(2, 9, filler);
            gui.setItem(3, 1, filler);
            gui.setItem(3, 9, filler);
            gui.setItem(4, 1, filler);
            gui.setItem(4, 9, filler);
            gui.setItem(5, 1, filler);
            gui.setItem(5, 9, filler);

            for (int i = 1; i < 10; i++) {
                gui.setItem(6, i, filler);
            }

            for (ItemStack item : ItemUtils.getSellableItems()) {

                boolean isInShop;

                boolean hasShopID = item.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "shop_id"), PersistentDataType.STRING);

                if (hasShopID) {
                    isInShop = item.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "shop_id"), PersistentDataType.STRING).equalsIgnoreCase("Gourdric");
                } else {
                    isInShop = false;
                }

                Component name = Component.text(item.getItemMeta().getDisplayName());

                int sellPrice = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "sell_price"), PersistentDataType.INTEGER);

                if (isInShop) {

                    ItemStack tempItem = item.clone();

                    ItemMeta itemMeta = tempItem.getItemMeta();
                    List<Component> lore = new ArrayList<>(itemMeta.lore() != null ? itemMeta.lore() : new ArrayList<>());
                    lore.add(miniMessage.deserialize(" "));
                    lore.add(miniMessage.deserialize("<!italic><dark_gray><underlined>Left Click to sell 1"));
                    lore.add(miniMessage.deserialize("<!italic><dark_gray><underlined>Right Click to sell 64"));
                    itemMeta.lore(lore);
                    tempItem.setItemMeta(itemMeta);

                    GuiItem guiItem = ItemBuilder.from(tempItem).asGuiItem(event -> {

                        HumanEntity whoClicked = event.getWhoClicked();
                        OfflinePlayer whoClickedOffline = (OfflinePlayer) event.getWhoClicked();
                        Inventory inv = whoClicked.getInventory();

                        if (event.isLeftClick() && hasItem(inv, item, 1)) {
                            inv.removeItemAnySlot(item);
                            whoClicked.sendMessage(miniMessage.deserialize("\uE018 <white>You sold 1x ").append(name).append(miniMessage.deserialize(" for <gold>$" + NumFormatter.formatCommas(sellPrice) + "<white>!")));
                            VaultHook.getEconomy().depositPlayer(whoClickedOffline, sellPrice);
                            whoClicked.playSound(confirmSound);
                        } else if (event.isRightClick() && hasItem(inv, item, 64)) {
                            inv.removeItemAnySlot(item.asQuantity(64));
                            whoClicked.sendMessage(miniMessage.deserialize("\uE018 <white>You sold 64x ").append(name).append(miniMessage.deserialize(" for <gold>$" + NumFormatter.formatCommas(sellPrice * 64) + "<white>!")));
                            VaultHook.getEconomy().depositPlayer(whoClickedOffline, sellPrice * 64);
                            whoClicked.playSound(confirmSound);
                        } else {
                            whoClicked.sendMessage(miniMessage.deserialize("\uE017 <white>You don't have enough ").append(name).append(miniMessage.deserialize(" <white>to sell!")));
                            whoClicked.playSound(denySound);
                            whoClicked.closeInventory();
                        }
                    });

                    gui.addItem(guiItem);

                }
            }

            for (ItemStack item : ItemUtils.getBuyableItems()) {

                boolean isInShop;

                boolean hasShopID = item.getPersistentDataContainer().has(new NamespacedKey("aquamobs", "shop_id"), PersistentDataType.STRING);

                if (hasShopID) {
                    isInShop = item.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "shop_id"), PersistentDataType.STRING).equalsIgnoreCase("Gourdric");
                } else {
                    isInShop = false;
                }

                Component name = Component.text(item.getItemMeta().getDisplayName());

                int buyPrice = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "buy_price"), PersistentDataType.INTEGER);

                if (isInShop) {

                    ItemStack tempItem = item.clone();

                    ItemMeta itemMeta = tempItem.getItemMeta();
                    List<Component> lore = new ArrayList<>(itemMeta.lore() != null ? itemMeta.lore() : new ArrayList<>());
                    lore.add(miniMessage.deserialize(" "));
                    lore.add(miniMessage.deserialize("<!italic><dark_gray><underlined>Left Click to buy 1"));
                    lore.add(miniMessage.deserialize("<!italic><dark_gray><underlined>Right Click to buy 64"));
                    itemMeta.lore(lore);
                    tempItem.setItemMeta(itemMeta);

                    GuiItem guiItem = ItemBuilder.from(tempItem).asGuiItem(event -> {

                        HumanEntity whoClicked = event.getWhoClicked();
                        OfflinePlayer whoClickedOffline = (OfflinePlayer) event.getWhoClicked();
                        Inventory inv = whoClicked.getInventory();
                        if (inv.firstEmpty() != -1) {
                            if (event.isLeftClick() && VaultHook.getEconomy().getBalance(e.getPlayer()) >= buyPrice) {
                                VaultHook.getEconomy().withdrawPlayer(whoClickedOffline, buyPrice);
                                whoClicked.sendMessage(miniMessage.deserialize("\uE018 <white>You bought 1x ").append(name).append(miniMessage.deserialize(" for <gold>$" + NumFormatter.formatCommas(buyPrice) + "<white>!")));
                                inv.addItem(item.asQuantity(1));
                                whoClicked.playSound(confirmSound);
                            } else if (event.isRightClick() && VaultHook.getEconomy().getBalance(e.getPlayer()) >= buyPrice * 64) {
                                VaultHook.getEconomy().withdrawPlayer(whoClickedOffline, buyPrice * 64);
                                whoClicked.sendMessage(miniMessage.deserialize("\uE018 <white>You bought 64x ").append(name).append(miniMessage.deserialize(" for <gold>$" + NumFormatter.formatCommas(buyPrice * 64) + "<white>!")));
                                inv.addItem(item.asQuantity(64));
                                whoClicked.playSound(confirmSound);
                            } else {
                                whoClicked.closeInventory();
                                whoClicked.playSound(denySound);
                                whoClicked.sendMessage(miniMessage.deserialize("\uE017 <white>You don't have enough <gold>$ Money <white>to buy ").append(name).append(miniMessage.deserialize("<white>!")));
                            }
                        } else {
                            whoClicked.closeInventory();
                            whoClicked.playSound(denySound);
                            whoClicked.sendMessage(miniMessage.deserialize("\uE017 <white>Your inventory is full!"));
                        }
                    });

                    gui.addItem(guiItem);

                }
            }

            gui.setItem(6, 5, close);

            gui.open(p);

        }
    }
}