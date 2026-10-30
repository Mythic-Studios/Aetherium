package org.mythic_goose.aetherium.client;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AstralexBossTracker {
    private static final Set<UUID> ACTIVE_IDS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void setActive(UUID id, boolean active) {
        if (active) {
            ACTIVE_IDS.add(id);
        } else {
            ACTIVE_IDS.remove(id);
        }
    }

    public static boolean isAstralexBar(UUID id) {
        return ACTIVE_IDS.contains(id);
    }
}