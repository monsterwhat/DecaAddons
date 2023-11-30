package com.sarinsa.commands;

import com.sarinsa.core.DecaAddons;
import com.sarinsa.util.References;
import com.sarinsa.util.Utility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GuardiansExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        int guardians = Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).getInt("guardians");

        Component status = Component.text("OFF").color(NamedTextColor.RED);

        if (Utility.isGuardianActive(player)) {
            status = Component.text("ON").color(NamedTextColor.GREEN);
        }
        if (args.length < 1) {
            Component guardiansMessage = Component.text("You currently have ").color(NamedTextColor.AQUA)
                    .append(Component.text(guardians).color(NamedTextColor.GREEN))
                    .append(Component.text(" Guardians. ").color(NamedTextColor.AQUA))
                    .append(Component.text("(Guardians are ").color(NamedTextColor.GRAY))
                    .append(status)
                    .append(Component.text(")").color(NamedTextColor.GRAY));
            player.sendMessage(guardiansMessage);
        }
        else {
            if (args[0].equalsIgnoreCase("buy")) {
                double money = DecaAddons.economy.getBalance(player);
                double cost = player.hasPermission(References.DONOR_PERM)
                        ? DecaAddons.guardianDonorCost
                        : DecaAddons.guardianBaseCost;

                //Buying 1
                if (args.length == 1) {
                    if (money >= cost) {
                        DecaAddons.economy.withdrawPlayer(player, cost);
                        Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).set("guardians", ++guardians);
                        DecaAddons.INSTANCE.saveConfiguration("playerProps.yml", DecaAddons.PLAYER_PROPS);

                        Component guardiansMessage = Component.text("You purchased a Guardian.").color(NamedTextColor.GREEN);
                        player.sendMessage(guardiansMessage);
                    }
                    else {
                        Component guardiansMessage = Component.text("You don't have sufficient funds ").color(NamedTextColor.RED)
                                .append(Component.text("(").color(NamedTextColor.BLUE))
                                .append(Component.text(DecaAddons.economy.format(cost)).color(NamedTextColor.AQUA))
                                .append(Component.text(")").color(NamedTextColor.BLUE));
                        player.sendMessage(guardiansMessage);
                    }
                    return true;
                }

                //Buying multiple
                else if (args.length == 2) {
                    int amount;

                    try {
                        amount = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("GuardiansExecutor: NumberFormatException: " + e.getMessage());
                        Component guardiansMessage = Component.text("\"").color(NamedTextColor.RED)
                                .append(Component.text(args[1]).color(NamedTextColor.AQUA))
                                .append(Component.text("\" is not a valid number.").color(NamedTextColor.RED));
                        player.sendMessage(guardiansMessage);
                        return true;
                    }
                    if (amount <= 0) {
                        Component guardiansMessage = Component.text("Amount must be a positive number!").color(NamedTextColor.RED);
                        player.sendMessage(guardiansMessage);
                        return true;
                    }
                    cost *= amount;

                    if (money >= cost) {
                        DecaAddons.economy.withdrawPlayer(player, cost);
                        Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).set("guardians", guardians + amount);
                        DecaAddons.INSTANCE.saveConfiguration("playerProps.yml", DecaAddons.PLAYER_PROPS);

                        Component guardiansMessage = Component.text("You purchased ").color(NamedTextColor.GREEN)
                                .append(Component.text(amount).color(NamedTextColor.AQUA))
                                .append(Component.text(" guardians.").color(NamedTextColor.GREEN));
                        player.sendMessage(guardiansMessage);
                    }
                    else {
                        Component guardiansMessage = Component.text("You don't have sufficient funds ").color(NamedTextColor.RED)
                                .append(Component.text("(").color(NamedTextColor.BLUE))
                                .append(Component.text(DecaAddons.economy.format(cost)).color(NamedTextColor.AQUA))
                                .append(Component.text(")").color(NamedTextColor.BLUE));
                        player.sendMessage(guardiansMessage);
                    }
                    return true;
                }
                else {
                    player.sendMessage(References.TOO_MANY_ARGS);
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("help")) {
                Component guardiansMessage = Component.text("You currently have ").color(NamedTextColor.AQUA)
                        .append(Component.text(guardians).color(NamedTextColor.GREEN))
                        .append(Component.text(" Guardians. ").color(NamedTextColor.AQUA))
                        .append(Component.text("(Guardians are ").color(NamedTextColor.GRAY))
                        .append(status)
                        .append(Component.text(")").color(NamedTextColor.GRAY));
                player.sendMessage(guardiansMessage);
            }
            else if (args[0].equalsIgnoreCase("toggle")) {

                if (Utility.isGuardianActive(player)) {
                    Utility.setGuardianActive(player, false);
                    Component guardiansMessage = Component.text("Toggled Guardians ").color(NamedTextColor.YELLOW)
                            .append(Component.text("OFF").color(NamedTextColor.RED));
                    player.sendMessage(guardiansMessage);
                }
                else {
                    Utility.setGuardianActive(player, true);
                    Component guardiansMessage = Component.text("Toggled Guardians ").color(NamedTextColor.YELLOW)
                            .append(Component.text("ON").color(NamedTextColor.GREEN));
                    player.sendMessage(guardiansMessage);
                }
                return true;
            }
            else {
                Component guardiansMessage = Component.text("You currently have ").color(NamedTextColor.AQUA)
                        .append(Component.text(guardians).color(NamedTextColor.GREEN))
                        .append(Component.text(" Guardians. ").color(NamedTextColor.AQUA))
                        .append(Component.text("(Guardians are ").color(NamedTextColor.GRAY))
                        .append(status)
                        .append(Component.text(")").color(NamedTextColor.GRAY));
                player.sendMessage(guardiansMessage);
                return true;
            }
        }
        return true;
    }
}
