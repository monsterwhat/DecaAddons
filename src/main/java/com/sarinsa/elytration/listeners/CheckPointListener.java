package com.sarinsa.elytration.listeners;

import com.sarinsa.elytration.variables.Zones;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Map;

public class CheckPointListener implements Listener {

    private final Zones zones;

    public CheckPointListener(Zones zones) {
        this.zones = zones;
    }

    public int getCurrentCheckpoint(Player player) {
        Zones.Zone zone = zones.getZone(player.getWorld().getName());
        Map<Integer, Zones.Checkpoint> checkpoints = zone.checkpoints();
        double playerX = player.getLocation().getX();
        double playerY = player.getLocation().getY();
        double playerZ = player.getLocation().getZ();
        int currentCheckpoint = 0;

        for (Zones.Checkpoint checkpoint : checkpoints.values()) {
            if (playerX == checkpoint.x() && playerY == checkpoint.y() && playerZ == checkpoint.z()) {
                currentCheckpoint = checkpoint.index();
            }
        }
        return currentCheckpoint;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Check if player passed through a checkpoint
        int lastCheckpoint = zones.getLastCheckpoint(player.getName());
        int currentCheckpoint = getCurrentCheckpoint(player);
        if (currentCheckpoint > lastCheckpoint) {
            Zones.Checkpoint checkpoint = zones.getZone(player.getWorld().getName()).checkpoints().get(currentCheckpoint);

            // If there is a next checkpoint, apply a velocity boost towards it
            if (checkpoint != null) {
                Vector direction = new Vector(checkpoint.x() - player.getLocation().getX(), checkpoint.y() - player.getLocation().getY(), checkpoint.z() - player.getLocation().getZ()).normalize();
                player.setVelocity(direction.multiply(1.5)); // Change this value to adjust the boost strength
            }

            // Update the player's last checkpoint
            zones.setLastCheckpoint(player.getName(), currentCheckpoint);
        }
    }
}
