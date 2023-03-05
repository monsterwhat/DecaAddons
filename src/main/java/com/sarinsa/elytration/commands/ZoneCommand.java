package com.sarinsa.elytration.commands;

import com.sarinsa.elytration.variables.Zones;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZoneCommand implements CommandExecutor {

    private final Zones zones;

    public ZoneCommand(Zones zones) {
        this.zones = zones;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has permission to use the command
        if (!player.hasPermission("elytration.zone")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        // Check if the command has the correct number of arguments
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /zone <zone-name>");
            return true;
        }

        String zoneName = args[0];

        // Check if the zone already exists
        if (zones.zoneExists(zoneName)) {
            player.sendMessage(ChatColor.RED + "The specified zone already exists.");
            return true;
        }

        // Create a new zone with the specified name
        zones.addZone(zoneName, player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), null, null);
        player.sendMessage(ChatColor.GREEN + "Zone " + zoneName + " created.");

        return true;
    }
}
