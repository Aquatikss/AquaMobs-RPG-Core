package com.aquaMobs.rpgCore.classes;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ClassSelectionMenu implements Listener {

    public void openClassSelectionMenu(Player p) {

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Gui gui = Gui.gui()
                .title(Component.text("Class Selection"))
                .rows(4)
                .disableAllInteractions()
                .create();

        Sound clickSound = Sound.sound(Key.key("ui.button.click"), Sound.Source.MASTER, 1f, 1f);
        Sound denySound = Sound.sound(Key.key("block.note_block.bass"), Sound.Source.MASTER, 100f, 0f);
        ItemStack warriorItemStack = ConfigUtil.getConfig().getItemStack("items.warrior_menu_item");

        GuiItem warriorClass = ItemBuilder
                .from(warriorItemStack)
                .name(miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 Warrior Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Warrior sub classes!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Warrior is a melee class that excels in close combat!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>It has three sub classes:"),
                        miniMessage.deserialize("<!italic><gray>1. <dark_red>\uD83D\uDDE1 Berserk"),
                        miniMessage.deserialize("<!italic><gray>2. <yellow>\uD83D\uDDE1 Assassin"),
                        miniMessage.deserialize("<!italic><gray>3. <gold>\uD83D\uDDE1 Warlord"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_red>\uD83D\uDDE1 Berserk <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><yellow>\uD83D\uDDE1 Assassin <gray>is a <yellow>⚡ <gray>Speed based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold>\uD83D\uDDE1 Warlord <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense at low <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 1 <red>to unlock!"))
                .asGuiItem(event -> {
                    p.playSound(clickSound);
                    WarriorClass.warriorClassMenu(p);
                });

        GuiItem archerClass = ItemBuilder
                .from(Material.BOW)
                .name(miniMessage.deserialize("<!italic><green>\uD83C\uDFF9 Archer Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Archer sub classes!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Archer is a ranged class that excels in long range combat and single targets!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>It has three sub classes:"),
                        miniMessage.deserialize("<!italic><gray>1. <dark_green>\uD83C\uDFF9 Rogue"),
                        miniMessage.deserialize("<!italic><gray>2. <gold>\uD83C\uDFF9 Sharpshooter"),
                        miniMessage.deserialize("<!italic><gray>3. <red>\uD83C\uDFF9 Ranger"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_green>\uD83C\uDFF9 Rogue <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed"),
                        miniMessage.deserialize("<!italic><gray>over medium distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold>\uD83C\uDFF9 Sharpshooter <gray>is a <red>\uD83D\uDDE1 <gray>Damage based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense over short distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>\uD83C\uDFF9 Ranger <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense at low <yellow>⚡ <gray>Speed over long distances!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 25 <red>to unlock!"))
                .asGuiItem(event -> {
                    int level = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER, 0);
                    if (level < 25) {
                        p.sendMessage(miniMessage.deserialize("<!italic><white>\uE017 <red>You need to be level <dark_aqua>★ <aqua>Level 25 <red>to to unlock this class!"));
                        p.playSound(denySound);
                        return;
                    }
                    p.playSound(clickSound);
                    ArcherClass.archerClassMenu(p);
                });

        GuiItem mageClass = ItemBuilder
                .from(Material.BOOK)
                .name(miniMessage.deserialize("<!italic><aqua>✎ Mage Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Mage sub classes!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Mage is a magic class that excels in ranged combat and crowd control!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>It has three sub classes:"),
                        miniMessage.deserialize("<!italic><gray>1. <dark_aqua>✎ Arcanist"),
                        miniMessage.deserialize("<!italic><gray>2. <dark_purple>✎ Voidcaller"),
                        miniMessage.deserialize("<!italic><gray>3. <blue>✎ Battlemage"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_aqua>✎ Arcanist <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_purple>✎ Voidcaller <gray>is a <yellow>⚡ <gray>Speed based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with low"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense"),
                        miniMessage.deserialize("<!italic><gray>with large AOE!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><blue>✎ Battlemage <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense at low <yellow>⚡ <gray>Speed with medium AOE!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 75 <red>to unlock!"))
                .asGuiItem(event -> {
                    p.playSound(clickSound);
                });

        ItemStack tankItemStack = ConfigUtil.getConfig().getItemStack("items.tank_menu_item");

        GuiItem tankClass = ItemBuilder
                .from(tankItemStack)
                .name(miniMessage.deserialize("<!italic><dark_gray>\uD83D\uDEE1 Tank Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Tank sub classes!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Tank is a melee class that excels in close combat and crowd control!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>It has three sub classes:"),
                        miniMessage.deserialize("<!italic><gray>1. <gold>\uD83D\uDEE1 Shielder"),
                        miniMessage.deserialize("<!italic><gray>2. <dark_aqua>\uD83D\uDEE1 Guardian"),
                        miniMessage.deserialize("<!italic><gray>3. <dark_green>\uD83D\uDEE1 Bulwark"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gold>\uD83D\uDEE1 Shielder <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_aqua>\uD83D\uDEE1 Guardian <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense and low <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_green>\uD83D\uDEE1 Bulwark <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing low <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense and low <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 150 <red>to unlock!"))
                .asGuiItem(event -> {
                    p.playSound(clickSound);
                });

        ItemStack healerItemStack = ConfigUtil.getConfig().getItemStack("items.healer_menu_item");

        GuiItem healerClass = ItemBuilder
                .from(healerItemStack)
                .name(miniMessage.deserialize("<!italic><yellow>⚗ Healer Class"))
                .lore(
                        miniMessage.deserialize("<!italic><gray>Click to view the Healer sub classes!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>Healer is a magic class that excels in healing and crowd control!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><gray>It has three sub classes:"),
                        miniMessage.deserialize("<!italic><gray>1. <dark_green>⚗ Druid"),
                        miniMessage.deserialize("<!italic><gray>2. <dark_purple>⚗ Soulweaver"),
                        miniMessage.deserialize("<!italic><gray>3. <blue>⚗ Templar"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_green>⚗ Druid <gray>is an all around <red>\uD83D\uDDE1 <gray>Damage"),
                        miniMessage.deserialize("<!italic><gray>dealer class that focuses on having medium"),
                        miniMessage.deserialize("<!italic><red>\uD83D\uDDE1 <gray>Damage, <green>\uD83D\uDEE1 <gray>Defense and <yellow>⚡ <gray>Speed!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><dark_purple>⚗ Soulweaver <gray>is a <yellow>⚡ <gray>Speed based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing high <red>\uD83D\uDDE1 <gray>Damage with low"),
                        miniMessage.deserialize("<!italic><yellow>⚡ <gray>Speed and low <green>\uD83D\uDEE1 <gray>Defense"),
                        miniMessage.deserialize("<!italic><gray>with large AOE!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><blue>⚗ Templar <gray>is a <green>\uD83D\uDEE1 <gray>Defense based class that"),
                        miniMessage.deserialize("<!italic><gray>focuses on dealing medium <red>\uD83D\uDDE1 <gray>Damage with high"),
                        miniMessage.deserialize("<!italic><green>\uD83D\uDEE1 <gray>Defense and low <yellow>⚡ <gray>Speed with medium AOE!"),
                        miniMessage.deserialize(" "),
                        miniMessage.deserialize("<!italic><red>Requires <dark_aqua>★ <aqua>Level 300 <red>to unlock!")
                )
                .asGuiItem(event -> {
                    p.playSound(clickSound);
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

        gui.setItem(2, 3, warriorClass);
        gui.setItem(3, 4, archerClass);
        gui.setItem(2, 5, mageClass);
        gui.setItem(3, 6, tankClass);
        gui.setItem(2, 7, healerClass);

        gui.setItem(4, 5, close);

        gui.open(p);

    }

    @EventHandler
    public void onLecternInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.LECTERN) {
                e.setCancelled(true);
                openClassSelectionMenu(e.getPlayer());
            }
        }
    }

}
