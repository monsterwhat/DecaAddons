package com.sarinsa.elytration.listeners;

import com.sarinsa.elytration.variables.Zones;
import com.sarinsa.elytration.variables.Zones.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final Zones zones;

    public PlayerMoveListener(Zones zones) {
        this.zones = zones;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        // Check if player moved from one block to another
        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {

            // Get the zone the player is in
            String zoneName = player.getLocation().getWorld().getName();
            Zone zone = zones.getZone(zoneName);

            // Check if the player is in a whitelisted block
            Block block = to.getBlock();
            Material material = block.getType();
            if (!zones.isWhitelisted(zone.name(), material.toString())) {

                // Get the last checkpoint the player was at
                Checkpoint lastCheckpoint = zone.checkpoints().get(zones.getLastCheckpoint(player.getName()));
                if (lastCheckpoint != null) {

                    // Teleport the player back to the last checkpoint if they die
                    if (player.isDead()) {
                        Location checkpointLocation = new Location(player.getWorld(), lastCheckpoint.x(), lastCheckpoint.y(), lastCheckpoint.z());
                        player.teleport(checkpointLocation);
                    }

                    // Teleport the player back to the last checkpoint if they touch a non-whitelisted block
                    if (event.getTo().getBlock().getType() != Material.AIR) {
                        Location checkpointLocation = new Location(player.getWorld(), lastCheckpoint.x(), lastCheckpoint.y(), lastCheckpoint.z());
                        player.teleport(checkpointLocation);
                    }
                }
            }
        }
    }
}
