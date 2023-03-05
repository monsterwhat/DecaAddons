package com.sarinsa.elytration.commands;

import com.sarinsa.elytration.variables.PlayerZone;
import com.sarinsa.elytration.variables.Zone;
import com.sarinsa.elytration.variables.Zones;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckpointCommand implements CommandExecutor {

    private final Zones zones;

    public CheckpointCommand(Zones zones) {
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
        if (!player.hasPermission("elytration.checkpoint")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        // Check if the command has the correct number of arguments
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /checkpoint <checkpoint-index>");
            return true;
        }

        // Check if the player is in a valid zone
        Zones.Zone zone = zones.getZone(player.getWorld().getName());
        if (zone == null) {
            player.sendMessage(ChatColor.RED + "You are not in a valid zone.");
            return true;
        }

        // Check if the checkpoint index is valid
        int checkpointIndex;
        try {
            checkpointIndex = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid checkpoint index. Must be an integer.");
            return true;
        }
        if (checkpointIndex < 1 || checkpointIndex > zone.checkpoints().size()) {
            player.sendMessage(ChatColor.RED + "Invalid checkpoint index. Must be between 1 and " + zone.checkpoints().size() + ".");
            return true;
        }

        // Check if the player has already passed the checkpoint
        PlayerZone playerZone = zones.getLastCheckpoint(player.getName());
        if (playerZone != null && playerZone.zoneName().equals(zone.name()) && playerZone.getLastCheckpointIndex() >= checkpointIndex) {
            player.sendMessage(ChatColor.RED + "You have already passed this checkpoint.");
            return true;
        }

        // Update the player's last checkpoint and set the checkpoint position
        zones.setLastCheckpoint(player.getName(),checkpointIndex, zone.name());
        player.sendMessage(ChatColor.GREEN + "Checkpoint " + checkpointIndex + " reached!");

        return true;
    }
}
