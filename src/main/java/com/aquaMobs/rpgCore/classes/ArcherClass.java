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
import org.bukkit.persistence.PersistentDataType;

public class ArcherClass {

    public static void archerClassMenu(Player p) {

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);
        Sound denySound = Sound.sound(Key.key("block.note_block.bass"), Sound.Source.MASTER, 100f, 0f);
        Sound classSelectSound = Sound.sound(Key.key("entity.firework_rocket.twinkle_far"), Sound.Source.MASTER, 0.5f, 1f);

        Gui gui = Gui.gui()
                .title(Component.text("Archer Class"))
                .rows(3)
                .disableAllInteractions()
                .create();

        GuiItem rogue = ItemBuilder
                .from(Material.BOW)
                .name(miniMessage.deserialize("<!italic><dark_green>Archer-Rogue Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Rogue sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_green>\uD83C\uDFF9 Rogue <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed"),
                        miniMessage.deserialize("<!italic><gray>over medium distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Double Shot <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Gain 10% chance to shoot 2 arrows instead of 1."),
                        miniMessage.deserialize("<!italic><gold> \uE014"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Instant Arrow <gray>: <red>Active <dark_gray>(<gray>Cooldown: 5s, Mana Cost: 50)"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <gray>Instantly fire a quick shot that deals double <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 25 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
                    if (level < 25) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be level <dark_aqua>★ <aqua>Level 25 <red>to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "rogue");
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now an <dark_green>Archer-Rogue<gray>!"));
                    p.closeInventory();
                });

        GuiItem sharpshooter = ItemBuilder
                .from(Material.CROSSBOW)
                .name(miniMessage.deserialize("<!italic><dark_green>Archer-Sharpshooter Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Sharpshooter sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold>\uD83C\uDFF9 Sharpshooter <gray>is a <red>\uD83D\uDDE1 <gray>Damage based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense over short distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Speedy Arrow <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Arrows deal 10% more <red>\uD83D\uDDE1 <gray>Damage and travel 10% faster."),
                        miniMessage.deserialize("<!italic><gold> \uE014 "),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Arrow Barrage <gray>: <red>Active <dark_gray>(<gray>Cooldown: 9s, Mana Cost: 50)"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Fire a barrage of arrows that deal double <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <gray>and knock enemies back twice as far."),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 75 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
                    if (level < 75) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be level <dark_aqua>★ <aqua>Level 75 <red>to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "sharpshooter");
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now an <gold>Archer-Sharpshooter<gray>!"));
                    p.closeInventory();
                });

        GuiItem ranger = ItemBuilder
                .from(Material.ARROW)
                .name(miniMessage.deserialize("<!italic><red>Archer-Ranger Sub Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Ranger sub class!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>\uD83C\uDFF9 Ranger <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense at low <yellow>⚡ <gray>Speed over long distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold> \uE016 <yellow><bold>ABILITIES"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Long Shot <gray>: <green>Passive"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Arrows deal 10% more <red>\uD83D\uDDE1 <gray>Damage and travel 10% farther."),
                        miniMessage.deserialize("<!italic><gold> \uE014 "),
                        miniMessage.deserialize("<!italic><gold> \uE014 <yellow>Arrow Rain <gray>: <red>Active <dark_gray>(<gray>Cooldown: 12s, Mana Cost: 50)"),
                        miniMessage.deserialize("<!italic><gold> \uE014 <gray>Fire a rain of arrows that deal triple <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gold> \uE015 <gray>but reduce your <yellow>⚡ <gray>Speed by 50% for 5 seconds."),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 300 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 1);
                    if (level < 300) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be level <dark_aqua>★ <aqua>Level 300 <red>to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(classSelectSound);
                    p.getPersistentDataContainer().set(new NamespacedKey("aquamobs", "class"), PersistentDataType.STRING, "ranger");
                    p.sendTitlePart(TitlePart.TITLE, miniMessage.deserialize("<aqua>You have selected a class!"));
                    p.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<gray>You are now an <red>Archer-Ranger<gray>!"));
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

        gui.setItem(2, 3, rogue);
        gui.setItem(2, 5, sharpshooter);
        gui.setItem(2, 7, ranger);

        gui.open(p);

    }

}
