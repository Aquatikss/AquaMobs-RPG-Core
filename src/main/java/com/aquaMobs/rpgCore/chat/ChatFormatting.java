package com.aquaMobs.rpgCore.chat;

import com.aquaMobs.rpgCore.misc.VaultHook;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatFormatting implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        var miniMessage = MiniMessage.miniMessage();

        Player p = event.getPlayer();

        String prefix = VaultHook.getChat().getPlayerPrefix(p);

        event.renderer((source, displayName, message, viewer) ->
                Component.text()
                        .append(miniMessage.deserialize(prefix.replace("&r", "") + p.getName()))
                        .append(Component.text(" » ", NamedTextColor.DARK_GRAY))
                        .append(message)
                        .build()
        );

    }
}