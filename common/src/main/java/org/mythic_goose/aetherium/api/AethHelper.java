package org.mythic_goose.aetherium.api;

import net.minecraft.world.entity.player.Player;

public class AethHelper {
    public static Aeth get(Player player) {
        return Aeth.ofRaw(AetheriumAttachments.AETH_BALANCE.get(player));
    }

    public static void set(Player player, Aeth value) {
        AetheriumAttachments.AETH_BALANCE.set(player, value.rawValue());
    }

    public static void add(Player player, Aeth amount) {
        set(player, get(player).add(amount));
    }

    public static boolean spend(Player player, Aeth cost) {
        Aeth current = get(player);
        if (!current.canAfford(cost)) return false;
        set(player, current.subtract(cost));
        return true;
    }
}