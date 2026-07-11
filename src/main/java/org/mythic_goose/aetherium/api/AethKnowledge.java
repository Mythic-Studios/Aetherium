package org.mythic_goose.aetherium.api;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks which items/blocks a player has "learnt" at the Arcane Station.
 * Learning is per-player (per {@link AetheriumAttachments#AETH_KNOWN_ITEMS}), not
 * per-block, so a player keeps their catalogue no matter which station they use.
 */
public class AethKnowledge {

    public static boolean knows(Player player, Item item) {
        if (item == Items.AIR) return false;
        Identifier id = BuiltInRegistries.ITEM.getKey(item);
        return player.getAttachedOrElse(AetheriumAttachments.AETH_KNOWN_ITEMS, List.of()).contains(id.toString());
    }

    /** No-op if the item is already known (idempotent, safe to call every tick). */
    public static void learn(Player player, Item item) {
        if (item == Items.AIR || knows(player, item)) return;

        Identifier id = BuiltInRegistries.ITEM.getKey(item);
        List<String> updated = new ArrayList<>(
                player.getAttachedOrElse(AetheriumAttachments.AETH_KNOWN_ITEMS, List.of()));
        updated.add(id.toString());
        player.setAttached(AetheriumAttachments.AETH_KNOWN_ITEMS, updated);
    }

    /** Ordered (learn-order) list of every item this player currently knows. */
    public static List<Item> getKnownItems(Player player) {
        List<String> ids = player.getAttachedOrElse(AetheriumAttachments.AETH_KNOWN_ITEMS, List.of());
        List<Item> items = new ArrayList<>(ids.size());
        for (String s : ids) {
            Identifier id = Identifier.tryParse(s);
            if (id == null) continue;
            BuiltInRegistries.ITEM.get(id).ifPresent(holder -> items.add(holder.value()));
        }
        return items;
    }
}