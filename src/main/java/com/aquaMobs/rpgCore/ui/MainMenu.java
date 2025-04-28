package com.aquaMobs.rpgCore.ui;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenu implements Listener {

    public static void mainMenu(PlayerSwapHandItemsEvent e) {

        // Cancel Event

        e.setCancelled(true);

        // MiniMessage

        MiniMessage miniMessage = MiniMessage.miniMessage();

        // Player

        Player p = e.getPlayer();

        // Declare Gui

        Gui gui = Gui.gui()
                .title(Component.text("Main Menu"))
                .rows(6)
                .disableAllInteractions()
                .create();

        // Player Head

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        headMeta.setOwningPlayer(p);
        head.setItemMeta(headMeta);

        Component[] playerHeadLore = {
                miniMessage.deserialize("<!italic><gray>View all your stats and how"),
                miniMessage.deserialize("<!italic><gray>they affect your character."),
                miniMessage.deserialize(" "),
                miniMessage.deserialize("<!italic><#8AE6E6><bold>STATS"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <red>\uE012 <gray>Strength <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "strength"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <red>\uD83D\uDDE1 <gray>Damage <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "damage"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <red>❤ <gray>Health <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <dark_red>❤ <gray>Vitality <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "vitality"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <blue>% <gray>Crit Chance <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "critical_chance"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <blue>⚔ <gray>Crit Damage <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "critical_damage"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <green>\uD83D\uDEE1 <gray>Defense <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "defense"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <yellow>⚡ <gray>Speed <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "speed"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <yellow>⚡ <gray>Attack Speed <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "attack_speed"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <aqua>✎ <gray>Mana <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mana"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83D\uDDE1 <gray>Looting <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "looting"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>⛏ <gray>Mining Speed <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mining_speed"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>⛏ <gray>Mining Fortune <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mining_fortune"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83C\uDFA3 <gray>Fishing Speed <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "fishing_speed"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83C\uDFA3 <gray>Fishing Fortune <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "fishing_fortune"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83E\uDE93 <gray>Foraging Speed <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "foraging_speed"), PersistentDataType.INTEGER)),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83E\uDE93 <gray>Foraging Fortune <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "foraging_fortune"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <gold>\uD83D\uDD31 <gray>Farming Fortune <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "farming_fortune"), PersistentDataType.INTEGER) + "%"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE015 <dark_purple>⛏ <gray>Breaking Power <white>" + p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "breaking_power"), PersistentDataType.INTEGER))
        };

        GuiItem playerHead = ItemBuilder.from(head).name(miniMessage.deserialize("<!italic><green>Your Profile")).lore(playerHeadLore).asGuiItem();

        //Sounds

        Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);

        Sound bookSound = Sound.sound(Key.key("item.book.page_turn"), Sound.Source.MASTER, 1f, 1f);

        // Recipe Book

        GuiItem recipeBook = ItemBuilder.from(Material.KNOWLEDGE_BOOK).name(miniMessage.deserialize("<!italic><green>Recipe Book")).lore(miniMessage.deserialize("<!italic><gray>View all of the recipes.")).asGuiItem(event -> {
            RecipeBook.recipeBookMenu(e);
            p.playSound(bookSound);
            p.playSound(clickSound);
        });

        // Collection Menu

        GuiItem collectionMenu = ItemBuilder.from(Material.PAPER).name(miniMessage.deserialize("<!italic><green>Collections")).lore(miniMessage.deserialize("<!italic><gray>View all of the collectables.")).asGuiItem(event -> {
            Collection.collectionGui(e.getPlayer());
            p.playSound(bookSound);
            p.playSound(clickSound);
        });

        // Skills Item

        List<String> skillsAsList = List.of("combat", "farming", "fishing", "foraging", "mining");

        Map<String, Integer> skillLevels = new HashMap<>();
        Map<String, Integer> skillXP = new HashMap<>();
        Map<String, Integer> skillRequiredXP = new HashMap<>();

        for (String skill : skillsAsList) {

            skillLevels.put(skill, p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skill + "_level"), PersistentDataType.INTEGER));
            skillXP.put(skill, p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skill + "_xp"), PersistentDataType.INTEGER));
            skillRequiredXP.put(skill, p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", skill + "_xprequired"), PersistentDataType.INTEGER));

        }

        Component[] skillsLore = {
                miniMessage.deserialize("<!italic><gray>View all your skills and how"),
                miniMessage.deserialize("<!italic><gray>they benefit your character."),
                miniMessage.deserialize(" "),
                miniMessage.deserialize("<!italic><#8AE6E6><bold>STATS"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <red>\uD83D\uDDE1 <gray>Combat <white>" + NumFormatter.formatCommas(skillLevels.get("combat")) + " <dark_gray>(<gray>" + NumFormatter.formatCommas(skillXP.get("combat")) + "<dark_gray>/<gray>" + NumFormatter.formatCommas(skillRequiredXP.get("combat")) + "<dark_gray>)"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <yellow>\uD83D\uDD31 <gray>Farming <white>" + NumFormatter.formatCommas(skillLevels.get("farming")) + " <dark_gray>(<gray>" + NumFormatter.formatCommas(skillXP.get("farming")) + "<dark_gray>/<gray>" + NumFormatter.formatCommas(skillRequiredXP.get("farming")) + "<dark_gray>)"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <aqua>\uD83C\uDFA3 <gray>Fishing <white>" + NumFormatter.formatCommas(skillLevels.get("fishing")) + " <dark_gray>(<gray>" + NumFormatter.formatCommas(skillXP.get("fishing")) + "<dark_gray>/<gray>" + NumFormatter.formatCommas(skillRequiredXP.get("fishing")) + "<dark_gray>)"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE014 <dark_green>\uD83E\uDE93 <gray>Foraging <white>" + NumFormatter.formatCommas(skillLevels.get("foraging")) + " <dark_gray>(<gray>" + NumFormatter.formatCommas(skillXP.get("foraging")) + "<dark_gray>/<gray>" + NumFormatter.formatCommas(skillRequiredXP.get("foraging")) + "<dark_gray>)"),
                miniMessage.deserialize("<!italic><#8AE6E6>\uE015 <dark_gray>⛏ <gray>Mining <white>" + NumFormatter.formatCommas(skillLevels.get("mining")) + " <dark_gray>(<gray>" + NumFormatter.formatCommas(skillXP.get("mining")) + "<dark_gray>/<gray>" + NumFormatter.formatCommas(skillRequiredXP.get("mining")) + "<dark_gray>)"),
        };

        GuiItem skills = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.skills_menu_item")).name(miniMessage.deserialize("<!italic><green>Your Skills")).lore(skillsLore).asGuiItem(event -> {
            event.getWhoClicked().playSound(clickSound);
            Skills.openSkillsMenu((Player) event.getWhoClicked());
        });

        //Filler

        GuiItem filler = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.filler_menu_item")).asGuiItem();

        for (int i = 0; i < gui.getRows()*9; i++) {
            gui.addItem(filler);
        }

        //Close

        GuiItem close = ItemBuilder.from(Material.BARRIER).name(miniMessage.deserialize("<!italic><red>Close")).asGuiItem(event -> {
            p.closeInventory();
            p.playSound(clickSound);
        });

        //Declaration of Items

        gui.setItem(2, 5, playerHead);
        gui.setItem(3, 5, recipeBook);
        gui.setItem(3, 4, skills);
        gui.setItem(3, 6, collectionMenu);
        gui.setItem(6, 5, close);

        gui.open(p);

    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        mainMenu(e);
    }
}
