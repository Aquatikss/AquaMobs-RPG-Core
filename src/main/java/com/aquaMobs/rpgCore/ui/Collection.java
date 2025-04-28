package com.aquaMobs.rpgCore.ui;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import com.aquaMobs.rpgCore.items.ItemUtils;
import com.aquaMobs.rpgCore.items.recipes.RecipeUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Collection implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for (ItemStack item : ItemUtils.getCollectableItems()) {
            String id = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "id"), PersistentDataType.STRING);

            if (!e.getPlayer().getPersistentDataContainer().has(new NamespacedKey("aquamobs", id+"_collection"), PersistentDataType.INTEGER)) {
                e.getPlayer().getPersistentDataContainer().set(new NamespacedKey("aquamobs", id+"_collection"), PersistentDataType.INTEGER, 0);
            }
        }
    }

    public static void collectionGui(Player p) {

        MiniMessage miniMessage = MiniMessage.miniMessage();

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("Collection Menu"))
                .rows(6)
                .disableAllInteractions()
                .create();

        GuiItem filler = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.filler_menu_item")).asGuiItem();

        Sound sound = Sound.sound(Key.key("item.book.page_turn"), Sound.Source.MASTER, 1f, 1f);

        Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);

        Sound denySound = Sound.sound(Key.key("block.note_block.bass"), Sound.Source.MASTER, 100f, 0f);

        GuiItem close = ItemBuilder.from(Material.BARRIER).name(miniMessage.deserialize("<!italic><red>Close")).asGuiItem(event -> {
            p.closeInventory();
            p.playSound(clickSound);
        });

        GuiItem previous = ItemBuilder.from(Material.PAPER).name(miniMessage.deserialize("<!italic><red>Previous")).asGuiItem(event -> {
            if (gui.getCurrentPageNum() != 1) {
                gui.previous();
                p.playSound(sound);
            } else {
                p.playSound(denySound);
            }
        });

        GuiItem next = ItemBuilder.from(Material.PAPER).name(miniMessage.deserialize("<!italic><red>Next")).asGuiItem(event -> {
            if (gui.getCurrentPageNum() < gui.getPagesNum()) {
                gui.next();
                p.playSound(sound);
            } else {
                p.playSound(denySound);
            }
        });

        gui.setItem(1, 1, filler);
        gui.setItem(1, 2, filler);
        gui.setItem(1, 3, filler);
        gui.setItem(1, 4, filler);
        gui.setItem(1, 5, filler);
        gui.setItem(1, 6, filler);
        gui.setItem(1, 7, filler);
        gui.setItem(1, 8, filler);
        gui.setItem(1, 9, filler);
        gui.setItem(2, 1, filler);
        gui.setItem(2, 9, filler);
        gui.setItem(3, 1, filler);
        gui.setItem(3, 9, filler);
        gui.setItem(4, 1, filler);
        gui.setItem(4, 9, filler);
        gui.setItem(5, 1, filler);
        gui.setItem(5, 9, filler);
        gui.setItem(6, 1, filler);
        gui.setItem(6, 2, filler);
        gui.setItem(6, 3, previous);
        gui.setItem(6, 4, filler);
        gui.setItem(6, 5, close);
        gui.setItem(6, 6, filler);
        gui.setItem(6, 7, next);
        gui.setItem(6, 8, filler);
        gui.setItem(6, 9, filler);

        int amountOfItems = ItemUtils.getItemsWithRecipe().size();

        gui.setPageNum((amountOfItems / 28) + 1);

        for (ItemStack item : ItemUtils.getCollectableItems()) {

            ItemStack tempItem = item.clone();

            ItemMeta itemMeta = tempItem.getItemMeta();
            List<Component> lore = new ArrayList<>(itemMeta.lore() != null ? itemMeta.lore() : new ArrayList<>());

            String id = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("aquamobs", "id"), PersistentDataType.STRING);

            int amount = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", id+"_collection"), PersistentDataType.INTEGER);

            lore.add(miniMessage.deserialize(" "));
            lore.add(miniMessage.deserialize("<!italic><gray>You have collected: <white>" + NumFormatter.format(amount)));
            itemMeta.lore(lore);
            tempItem.setItemMeta(itemMeta);

            GuiItem guiItem = ItemBuilder.from(tempItem).asGuiItem();
            gui.addItem(guiItem);
        }

        gui.open(p);

    }
}
