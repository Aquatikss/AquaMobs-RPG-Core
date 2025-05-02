package com.aquaMobs.rpgCore.ui;

import com.aquaMobs.rpgCore.misc.VaultHook;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sidebar implements Listener {

    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static void init(Player p) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        FastBoard board = new FastBoard(p);
        board.updateTitle(miniMessage.deserialize("<#CCFFFF><bold>AQUAMOBS"));
        if (!boards.containsKey(p.getUniqueId())) {
            boards.put(p.getUniqueId(), board);
        }
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent e) {
        init(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        FastBoard board = boards.remove(p.getUniqueId());

        if (board != null) {
            board.delete();
        }

    }

    public static void updateBoard(FastBoard board) {

        Player p = board.getPlayer();

        LocalDateTime localDateTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String formattedTime = localDateTime.format(dateTimeFormatter);

        MiniMessage miniMessage = MiniMessage.miniMessage();

        long bal = (long) VaultHook.getEconomy().getBalance(p);

        String balance = NumFormatter.format(bal);

        int xp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xp"), PersistentDataType.INTEGER);
        int xpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "xprequired"), PersistentDataType.INTEGER);
        int level = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "level"), PersistentDataType.INTEGER);

        board.updateLines(
            miniMessage.deserialize("<#96B0B3><strikethrough>                            <reset>"),
            miniMessage.deserialize("<gray>[" + formattedTime + "]"),
            miniMessage.deserialize("<#000000> "),
            miniMessage.deserialize("<#8AE6E6><bold>" + p.getName()),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <dark_green>$ <green>" + balance + " <dark_green>ᴍᴏɴᴇʏ"),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <gold>☆ <yellow>" + NumFormatter.format(xp) + "<gray>/<yellow>" + NumFormatter.format(xpRequired) + " <gold>ᴇxᴘᴇʀɪᴇɴᴄᴇ"),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <dark_aqua>★ <aqua>" + NumFormatter.format(level) + " <dark_aqua>ʟᴇᴠᴇʟ")
        );
    }

}