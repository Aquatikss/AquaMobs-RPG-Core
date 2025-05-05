package com.aquaMobs.rpgCore.classes;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class WarriorClass {

    public static void warriorClassMenu(Player p) {

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);
        Sound denySound = Sound.sound(Key.key("block.note_block.bass"), Sound.Source.MASTER, 100f, 0f);
        Sound classSelectSound = Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.MASTER, 0.5f, 1f);

        Gui gui = Gui.gui()
                .title(Component.text("Warrior Class"))
                .rows(3)
                .disableAllInteractions()
                .create();

        ItemStack berserkItemStack = ConfigUtil.getConfig().getItemStack("items.berserker_menu_item");

        GuiItem berserk = ItemBuilder
                .from(berserkItemStack)
                .name(miniMessage.deserialize("<!italic><dark_red>\uD83D\uDDE1 Warrior-Berserk Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Berserk sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_red>\uD83D\uDDE1 Berserk <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Final Breath <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>When you are below 50% <red>❤ <gray>Health, you gain 20% bonus"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <red>\uD83D\uDDE1 <gray>Damage."),
                        miniMessage.deserialize("<!italic><gold> \uE014"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Bloodlust <gray>: <red>Active <dark_gray>(<gray>Cooldown: 8s, Mana Cost: 35)"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Temporarily gain 50% bonus <red>\uD83D\uDDE1 <gray>Damage. Hitting an enemy"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>will consume the effect and deal 50% more <red>\uD83D\uDDE1 <gray>Damage, but 10%"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <gray>of your <red>❤ <gray>Health will be consumed."),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 1 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "berserk");
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua><bold>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now a <dark_red>\uD83D\uDDE1 Warrior-Berserk<gray>!"));
                    p.closeInventory();
                });

        ItemStack assassinItemStack = ConfigUtil.getConfig().getItemStack("items.assassin_menu_item");

        GuiItem assassin = ItemBuilder
                .from(assassinItemStack)
                .name(miniMessage.deserialize("<!italic><yellow>\uD83D\uDDE1 Warrior-Assassin Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Assassin sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><yellow>\uD83D\uDDE1 Assassin <gray>is a <yellow>⚡ <gray>Speed based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Assassinate <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <blue>⚔ Critical Hits permanently <gray>deal 10% more <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>to the target, but you lose <green>10% <gray>of your <green>\uD83D\uDEE1 <gray>Defense."),
                        miniMessage.deserialize("<!italic><gold> \uE014"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Shadow Step <gray>: <red>Active <dark_gray>(<gray>Cooldown: 4s, Mana Cost: 50)"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Temporarily gain 50% bonus <yellow>⚡ <gray>Speed. Hitting an enemy"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>will consume the effect and deal 50% more <red>\uD83D\uDDE1 <gray>Damage, but 10x"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>of the amount of <yellow>⚡ <gray>Speed consumed will be subtracted from your"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <green>\uD83D\uDEE1 <gray>Defense for 5 seconds."),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 50 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
                    if (level < 50) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be <dark_aqua>★ <aqua>Level 50 <red>to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "assassin");
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class_"), PersistentDataType.INTEGER, 1);
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now a <yellow>\uD83D\uDDE1 Warrior-Assassin<gray>!"));
                    p.closeInventory();
                });

        ItemStack warlordItemStack = ConfigUtil.getConfig().getItemStack("items.warlord_menu_item");

        GuiItem warlord = ItemBuilder
                .from(warlordItemStack)
                .name(miniMessage.deserialize("<!italic><gold>\uD83D\uDDE1 Warrior-Warlord Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Warlord sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Warlord is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense at low <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Fortified Will <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Permanently gain 20% bonus <green>\uD83D\uDEE1 <gray>Defense. Your <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>is reduced by 10% and your <yellow>⚡ <gray>Speed is reduced by 5%"),
                        miniMessage.deserialize("<!italic><gold> \uE014"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Shield Bash <gray>: <red>Active <dark_gray>(<gray>Cooldown: 6s, Mana Cost: 75)"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Temporarily gain 50% bonus <green>\uD83D\uDEE1 <gray>Defense. Hitting an enemy"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>will consume the effect and deal 50% more <red>\uD83D\uDDE1 <gray>Damage, but 10%"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>of your <green>\uD83D\uDEE1 <gray>Defense consumed will be subtracted from your"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <yellow>⚡ <gray>Speed for 5 seconds."),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 250 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
                    if (level < 250) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be <dark_aqua>★ <aqua>Level 250 <red>to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "warlord");
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now a <gold>\uD83D\uDDE1 Warrior-Warlord<gray>!"));
                    p.closeInventory();
                });

        GuiItem close = ItemBuilder
                .from(Material.BARRIER)
                .name(miniMessage.deserialize("<!italic><red>Close"))
                .asGuiItem(event -> {
                    p.closeInventory();
                    p.playSound(clickSound);
                });

        GuiItem filler = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.filler_menu_item")).asGuiItem();

        gui.getFiller().fill(filler);

        gui.setItem(3, 5, close);

        gui.setItem(2, 3, berserk);
        gui.setItem(2, 5, assassin);
        gui.setItem(2, 7, warlord);

        gui.open(p);

    }

}
