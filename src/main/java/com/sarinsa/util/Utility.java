package com.sarinsa.util;

import com.sarinsa.core.DecaAddons;

import java.util.Objects;
import java.util.logging.Level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class Utility {


    /**
     * Checks if the given player has enabled Guardians.
     */
    public static boolean isGuardianActive(Player player) {
        try {
            return Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).getBoolean("isGuardianActive");
        }
        catch (Exception e) {
            Component error = Component.text("An error occurred while trying to fetch your active guardian entry.").color(NamedTextColor.RED);
            player.sendMessage(error);
            DecaAddons.INSTANCE.getLogger().log(Level.SEVERE, "Failed to retrieve guardian active for player {0}", player.getName());
            return false;
        }
    }

    /**
     * Utility method for enabling/disabling Guardians for a player.
     */
    public static void setGuardianActive(Player player, boolean active) {
        try {
            Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).set("isGuardianActive", active);
        }
        catch (Exception e) {
            Component error = Component.text("An error occurred while trying to fetch your active guardian entry.").color(NamedTextColor.RED);
            player.sendMessage(error);
            DecaAddons.INSTANCE.getLogger().log(Level.SEVERE, "Failed to toggle guardian active for player {0}", player.getName());
        }
    }

    /**
     * Converts experience levels to experience points.
     * The math used here is based off of the information provided
     * on the wiki.<p></p>
     * <strong><a href="https://minecraft.fandom.com/wiki/Experience">...</a></strong>
     * <p></p>
     */
    public static int getExpFromLevels(Player player) {
        int levels = player.getLevel();

        if (levels < 0) {
            return 0;
        }
        else if (levels > 0 && levels <= 16) {
            return (int) Math.pow(levels, 2) + (6 * levels);
        }
        else if (levels > 16 && levels <= 31) {
            return (int) (2.5 * Math.pow(levels, 2)) - (int) (40.5 * levels) + 360;
        }
        else {
            return (int) (4.5 * Math.pow(levels, 2)) - (int) (162.5 * levels) + 2220;
        }
    }

    /**
     * Converts experience points into experience levels.
     * The math used here is based off of the information
     * provide on the wiki.<p></p>
     * <strong><a href="https://minecraft.fandom.com/wiki/Experience">...</a></strong>
     */
    public static int getLevelsFromExp(int experience) {
        if (experience <= 352) {
            return (int) (Math.sqrt(experience + 9) -3);
        }
        else if (experience >= 394 && experience <= 1507) {
            return (int) ((Math.sqrt(40 * experience - 7839) + 81) * 0.1);
        }
        else if (experience >= 1628) {
            return (int) ((Math.sqrt(72 * experience - 54215) + 325) / 18);
        }
        return 0;
    }


    public static NamespacedKey createKey(String key) {
        return new NamespacedKey(DecaAddons.INSTANCE, key);
    }

    // Utility class, instantiation redundant.
    private Utility() {}
}
