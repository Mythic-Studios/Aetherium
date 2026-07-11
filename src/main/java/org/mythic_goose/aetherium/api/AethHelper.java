package org.mythic_goose.aetherium.api;

import net.minecraft.world.entity.player.Player;

import java.math.BigInteger;

public class AethHelper {
    public static Aeth get(Player player) {
        return Aeth.ofRaw(player.getAttachedOrElse(
                AetheriumAttachments.AETH_BALANCE, BigInteger.ZERO));
    }

    public static void set(Player player, Aeth value) {
        player.setAttached(AetheriumAttachments.AETH_BALANCE, value.rawValue());
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
