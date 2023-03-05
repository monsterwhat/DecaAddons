package com.sarinsa.elytration.variables;

import java.util.HashMap;
import java.util.Map;

public class Zones {
    public static record Zone(String name, String worldName, double startX, double startY, double startZ, double startYaw, Map<Integer, Checkpoint> checkpoints, Map<Integer, String> whitelist) {}

    public static record Checkpoint(int index, double x, double y, double z) {}

    private final Map<String, Zone> zones;
    private final Map<String, Integer> lastCheckpoints;

    public Zones() {
        this.zones = new HashMap<>();
        this.lastCheckpoints = new HashMap<>();
    }

    public void addZone(String name, String worldName, double startX, double startY, double startZ, double startYaw, Map<Integer, Checkpoint> checkpoints, Map<Integer, String> whitelist) {
        Map<Integer, Checkpoint> indexedCheckpoints = new HashMap<>();
        for (Map.Entry<Integer, Checkpoint> entry : checkpoints.entrySet()) {
            indexedCheckpoints.put(entry.getKey(), new Checkpoint(entry.getKey(), entry.getValue().x(), entry.getValue().y(), entry.getValue().z()));
        }
        Zone zone = new Zone(name, worldName, startX, startY, startZ, startYaw, indexedCheckpoints, whitelist);
        zones.put(name, zone);
    }

    public void removeZone(String name) {
        zones.remove(name);
    }

    public Zone getZone(String name) {
        return zones.get(name);
    }

    public void setLastCheckpoint(String playerName, int checkpointIndex, String zoneName) {
        PlayerCheckpoint checkpoint = new PlayerCheckpoint(checkpointIndex, zoneName);
        playerCheckpoints.put(playerName, checkpoint);
    }


    public boolean zoneExists(String name) {
        return zones.containsKey(name);
    }

    public int getLastCheckpoint(String playerName) {
        return lastCheckpoints.getOrDefault(playerName, 0);
    }

    public boolean isWhitelisted(String zoneName, String blockType) {
        Zone zone = zones.get(zoneName);
        if (zone != null) {
            for (String whitelistBlockType : zone.whitelist().values()) {
                if (whitelistBlockType.equals(blockType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAtCheckpoint(String playerName, Checkpoint checkpoint) {
        int lastCheckpointIndex = getLastCheckpoint(playerName);
        return lastCheckpointIndex + 1 == checkpoint.index();
    }
}
