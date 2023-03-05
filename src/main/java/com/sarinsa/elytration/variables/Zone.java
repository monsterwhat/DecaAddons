package com.sarinsa.elytration.variables;

import java.util.List;

public record Zone(String name, String worldName, List<PlayerCheckpoint> checkpoints) {}
