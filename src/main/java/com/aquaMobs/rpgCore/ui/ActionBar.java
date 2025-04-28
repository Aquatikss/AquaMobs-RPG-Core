package com.aquaMobs.rpgCore.ui;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;

public class ActionBar {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void sendActionBar(Player p) {

        MiniMessage miniMessage = MiniMessage.miniMessage();

        int current_health = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "current_health"), PersistentDataType.INTEGER);

        int health = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "health"), PersistentDataType.INTEGER);

        int current_mana = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "current_mana"), PersistentDataType.INTEGER);

        int mana = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mana"), PersistentDataType.INTEGER);

        String skillGainedXpType = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "skill_xp_gained_type"), PersistentDataType.STRING);

        Integer skillDisplayTime = p.getPersistentDataContainer().getOrDefault(new NamespacedKey("aquamobs", "skill_xp_gain_display_time"), PersistentDataType.INTEGER, 0);

        String skillDisplayText = "";

        Integer currentSkillXp;
        Integer currentSkillXpRequired;
        Integer currentSkillLevel;

        switch (skillGainedXpType) {
            case "combat":
                currentSkillXp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "combat_xp"), PersistentDataType.INTEGER);
                currentSkillXpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "combat_xprequired"), PersistentDataType.INTEGER);
                currentSkillLevel = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "combat_level"), PersistentDataType.INTEGER);

                skillDisplayText = "<yellow>[ <red>\uD83D\uDDE1 <white>Combat " + currentSkillLevel + " <dark_gray>(<white>" + df.format((((float) currentSkillXp/currentSkillXpRequired)*100)) + "% <dark_gray>) <yellow>]";
                break;
            case "farming":
                currentSkillXp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "farming_xp"), PersistentDataType.INTEGER);
                currentSkillXpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "farming_xprequired"), PersistentDataType.INTEGER);
                currentSkillLevel = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "farming_level"), PersistentDataType.INTEGER);

                skillDisplayText = "<yellow>[ <yellow>\uD83D\uDD31 <white>Farming " + currentSkillLevel + " <dark_gray>(<white>" + df.format((((float) currentSkillXp/currentSkillXpRequired)*100)) + "% <dark_gray>) <yellow>]";
                break;
            case "fishing":
                currentSkillXp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "fishing_xp"), PersistentDataType.INTEGER);
                currentSkillXpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "fishing_xprequired"), PersistentDataType.INTEGER);
                currentSkillLevel = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "fishing_level"), PersistentDataType.INTEGER);

                skillDisplayText = "<yellow>[ <aqua>\uD83C\uDFA3 <white>Fishing " + currentSkillLevel + " <dark_gray>(<white>" + df.format((((float) currentSkillXp/currentSkillXpRequired)*100)) + "% <dark_gray>) <yellow>]";
                break;
            case "foraging":
                currentSkillXp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "foraging_xp"), PersistentDataType.INTEGER);
                currentSkillXpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "foraging_xprequired"), PersistentDataType.INTEGER);
                currentSkillLevel = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "foraging_level"), PersistentDataType.INTEGER);

                skillDisplayText = "<yellow>[ <dark_green>\uD83E\uDE93 <white>Foraging " + currentSkillLevel + " <dark_gray>(<white>" + df.format((((float) currentSkillXp/currentSkillXpRequired)*100)) + "% <dark_gray>) <yellow>]";
                break;
            case "mining":
                currentSkillXp = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mining_xp"), PersistentDataType.INTEGER);
                currentSkillXpRequired = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mining_xprequired"), PersistentDataType.INTEGER);
                currentSkillLevel = p.getPersistentDataContainer().get(new NamespacedKey("aquamobs", "mining_level"), PersistentDataType.INTEGER);

                skillDisplayText = "<yellow>[ <dark_gray>⛏ <white>Mining " + currentSkillLevel + " <dark_gray>(<white>" + df.format((((float) currentSkillXp/currentSkillXpRequired)*100)) + "% <dark_gray>) <yellow>]";
                break;
            case null:
                break;
            default:

        }

        if (skillDisplayTime == 0) {
            p.sendActionBar(miniMessage.deserialize("<red>" + current_health + "<dark_gray>/<red>" + health + " ❤<dark_gray>              <aqua>" + current_mana + "<dark_gray>/<aqua>" + mana + " ✎"));
        } else {
            p.sendActionBar(miniMessage.deserialize("<red>" + current_health + "<dark_gray>/<red>" + health + " ❤<dark_gray>       " + skillDisplayText + "       <aqua>" + current_mana + "<dark_gray>/<aqua>" + mana + " ✎"));
        }
    }
}
