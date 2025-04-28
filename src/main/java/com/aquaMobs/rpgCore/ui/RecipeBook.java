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
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook {

    public static void recipeBookMenu(PlayerSwapHandItemsEvent e) {

        e.setCancelled(true);

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Player p = e.getPlayer();

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("Recipe Book"))
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

        gui.setPageNum((amountOfItems/28)+1);

        for (ItemStack item : ItemUtils.getItemsWithRecipe()) {
            GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {

                String id = item.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "id"), PersistentDataType.STRING);

                Gui recipeSpecificGui = Gui.gui()
                        .title(Component.text(ChatColor.stripColor(item.getItemMeta().getDisplayName())))
                        .rows(6)
                        .disableAllInteractions()
                        .create();

                recipeSpecificGui.getFiller().fill(filler);

                List<GuiItem> recipeItems = new ArrayList<>(9);

                GuiItem empty = ItemBuilder.from(Material.AIR).name(Component.text("")).asGuiItem();

                for (int i = 0; i < 9; i++) {
                    recipeItems.add(empty);
                }

                for (String key : RecipeUtil.getConfig().getConfigurationSection("items." + id + ".ingredients").getKeys(false)) {
                    String ingredient = RecipeUtil.getConfig().getString("items." + id + ".ingredients." + key + ".ingredient");
                    int amount = RecipeUtil.getConfig().getInt("items." + id + ".ingredients." + key + ".amount");
                    recipeItems.set(Integer.parseInt(key) - 1, ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items." + ingredient).asQuantity(amount)).asGuiItem());
                }

                recipeSpecificGui.setItem(2, 2, recipeItems.get(0));
                recipeSpecificGui.setItem(2, 3, recipeItems.get(1));
                recipeSpecificGui.setItem(2, 4, recipeItems.get(2));
                recipeSpecificGui.setItem(3, 2, recipeItems.get(3));
                recipeSpecificGui.setItem(3, 3, recipeItems.get(4));
                recipeSpecificGui.setItem(3, 4, recipeItems.get(5));
                recipeSpecificGui.setItem(4, 2, recipeItems.get(6));
                recipeSpecificGui.setItem(4, 3, recipeItems.get(7));
                recipeSpecificGui.setItem(4, 4, recipeItems.get(8));

                String resultAsString = RecipeUtil.getConfig().getString("items." + id + ".result_item");
                int resultQuantity = RecipeUtil.getConfig().getInt("items." + id + ".result_amount");
                ItemStack resultItem = ConfigUtil.getConfig().getItemStack("items." + resultAsString).clone().asQuantity(resultQuantity);

                GuiItem resultItemAsGuiItem = ItemBuilder.from(resultItem).asGuiItem();

                recipeSpecificGui.setItem(3, 6, resultItemAsGuiItem);

                event.getWhoClicked().playSound(clickSound);

                recipeSpecificGui.setItem(6, 5, close);

                recipeSpecificGui.open(event.getWhoClicked());

            });

            gui.addItem(guiItem);
        }

        gui.open(e.getPlayer());
    }

}