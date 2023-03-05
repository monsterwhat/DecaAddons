package com.sarinsa.elytration.commands;

import com.sarinsa.elytration.variables.Players;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCheckpointCommand implements CommandExecutor {

    private final Players players;

    public SetCheckpointCommand(Players players) {
        this.players = players;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has permission to use the command
        if (!player.hasPermission("elytration.setcheckpoint")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        // Set player checkpoint
        players.setCheckpoint(player.getName(), player.getLocation());

        player.sendMessage(ChatColor.GREEN + "Checkpoint set.");

        return true;
    }
}
