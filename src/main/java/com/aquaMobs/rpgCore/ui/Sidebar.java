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
            miniMessage.deserialize("<#AAAAAA>[" + formattedTime + "]"),
            miniMessage.deserialize("<#000000> "),
            miniMessage.deserialize("<#8AE6E6><bold>" + p.getName()),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <#1BAB21>$ <#23DE2B>" + balance + " <#1BAB21>ᴍᴏɴᴇʏ"),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <#C4C03B>☆ <#EDE847>" + NumFormatter.format(xp) + "<#AAAAAA>/<#EDE847>" + NumFormatter.format(xpRequired) + " <#C4C03B>ᴇxᴘᴇʀɪᴇɴᴄᴇ"),
            miniMessage.deserialize("<#CCFFFF><bold>|<reset> <#00939E>★ <#00C5D3>" + NumFormatter.format(level) + " <#00939E>ʟᴇᴠᴇʟ")
        );
    }

}