package com.sarinsa.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class References {

    // PERMISSIONS
    public static final String DONOR_PERM = "decaaddons.guardians.donor";


    // MESSAGES
    public static String PLAYER_ONLY = "This command can only be issued by a player!";
    public static Component TOO_MANY_ARGS  = Component.text("Too many arguments!").color(NamedTextColor.RED);


    public static Component GUARDIAN_HELP() {
        return Component.text("Guardians Help").color(NamedTextColor.AQUA).append(Component.text(":")).color(NamedTextColor.GRAY)
                .append(Component.text("Use ").append(Component.text("/guardians", NamedTextColor.GREEN)).append(Component.text(" to see how many guardians you have left.")).color(NamedTextColor.GRAY))
                .append(Component.text("Use ").append(Component.text("/guardians buy [amount]|max", NamedTextColor.GREEN)).append(Component.text(" to purchase one or more guardians.")).color(NamedTextColor.GRAY))
                .append(Component.text("Use ").append(Component.text("/guardians toggle", NamedTextColor.GREEN)).append(Component.text(" to toggle guardians on or off.")).color(NamedTextColor.GRAY));
    }
}
