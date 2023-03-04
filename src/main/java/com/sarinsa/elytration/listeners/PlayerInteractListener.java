package com.sarinsa.elytration.listeners;

import com.sarinsa.elytration.variables.Zones;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final Zones zones;

    public PlayerInteractListener(Zones zones) {
        this.zones = zones;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the player is holding a checkpoint item
        if (item != null && item.getType() == Material.SLIME_BALL) {
            // Create a new checkpoint and add it to the zone's list of checkpoints
            Zones.Zone zone = zones.getZone("zone-name");
            zone.checkpoints().put(zone.checkpoints().size() + 1, new Zones.Checkpoint(zone.checkpoints().size() + 1, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
            // Do something here, for example send a message to the player
            player.sendMessage(ChatColor.GREEN + "Checkpoint created!");

            // Remove the checkpoint item from the player's hand
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().removeItem(item);
            }
        }
    }
}
