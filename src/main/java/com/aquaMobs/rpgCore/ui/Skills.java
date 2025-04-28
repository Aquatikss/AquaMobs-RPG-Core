package com.aquaMobs.rpgCore.ui;

import com.aquaMobs.rpgCore.items.ConfigUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Skills {

    public static void openSkillsMenu(Player p) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        Gui gui = Gui.gui()
                .title(Component.text("Skills Menu"))
                .rows(5)
                .disableAllInteractions()
                .create();

        GuiItem filler = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.filler_menu_item")).asGuiItem();

        GuiItem combat = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.combat_menu_item")).asGuiItem();
        GuiItem farming = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.farming_menu_item")).asGuiItem();
        GuiItem fishing = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.fishing_menu_item")).asGuiItem();
        GuiItem foraging = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.foraging_menu_item")).asGuiItem();
        GuiItem mining = ItemBuilder.from(ConfigUtil.getConfig().getItemStack("items.mining_menu_item")).asGuiItem();

        gui.setItem(2,3, combat);
        gui.setItem(2,5, farming);
        gui.setItem(2,7, fishing);
        gui.setItem(3,4, foraging);
        gui.setItem(3,6, mining);

        gui.open(p);
    }

}
