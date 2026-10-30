package org.mythic_goose.aetherium.api;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Tracks which items/blocks a player has "learnt" at the Arcane Station.
 * Learning is per-player (per {@link AetheriumAttachments#AETH_KNOWN_ITEMS}), not
 * per-block, so a player keeps their catalogue no matter which station they use.
 * <p>
 * Entries are string keys: the item id, plus "#enchant=level,..." for stacks that carry
 * enchantments — stored enchantments on enchanted books, regular enchantments on gear — so
 * each item + enchantment combination is its own entry. Plain items keep the exact same key
 * they always had, so existing saves stay valid.
 * <p>
 * Backed by {@code CommonAttachment<List<String>>}, so storage/sync/persistence is
 * handled per-loader by whatever registers {@code AETH_KNOWN_ITEMS} via
 * {@code Services.ATTACHMENTS} — nothing loader-specific lives here.
 */
public class AethKnowledge {
    private static final String SEP = "#";

    /**
     * "minecraft:enchanted_book#minecraft:mending=1,minecraft:unbreaking=3" or
     * "minecraft:diamond_sword#minecraft:sharpness=5" (plain items: just the id).
     */
    public static String keyOf(ItemStack stack) {
        String base = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        ItemEnchantments ench = AethValues.enchantmentsOf(stack);
        if (ench.isEmpty()) return base;

        String suffix = ench.entrySet().stream()
                .map(e -> e.getKey().unwrapKey()
                        .map(k -> k.identifier() + "=" + e.getIntValue())
                        .orElse(null))
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.joining(","));
        return suffix.isEmpty() ? base : base + SEP + suffix;
    }

    public static boolean knows(Player player, ItemStack stack) {
        return !stack.isEmpty() && knownKeys(player).contains(keyOf(stack));
    }

    public static boolean knows(Player player, Item item) {
        return knows(player, new ItemStack(item));
    }

    /**
     * No-op if already known (idempotent, safe to call every tick). Learning an enchanted
     * piece of gear also teaches the plain item, since the player obviously knows it.
     */
    public static void learn(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;

        List<String> current = knownKeys(player);
        LinkedHashSet<String> next = new LinkedHashSet<>(current); // keeps existing learn order

        if (!AethValues.enchantmentsOf(stack).isEmpty()) {
            ItemStack plain = new ItemStack(stack.getItem());
            if (AethValues.lookup(plain) != null) next.add(keyOf(plain)); // skipped for enchanted books
        }
        next.add(keyOf(stack));

        if (next.size() != current.size()) {
            AetheriumAttachments.AETH_KNOWN_ITEMS.set(player, new ArrayList<>(next));
        }
    }

    public static void learn(Player player, Item item) {
        learn(player, new ItemStack(item));
    }

    /**
     * Teaches every learnable item plus every enchanted book (each enchantment at levels
     * I..max) in a single attachment write. Used by the Tome of Arcana — learning each
     * entry individually would rewrite and sync the whole list over a thousand times.
     * Enchanted gear isn't included: its combinations are effectively unlimited.
     */
    public static void learnEverything(Player player) {
        List<String> current = knownKeys(player);
        LinkedHashSet<String> all = new LinkedHashSet<>(current); // keeps existing learn order

        for (Item item : AethValues.getAllLearnableItems()) {
            all.add(BuiltInRegistries.ITEM.getKey(item).toString());
        }

        player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).listElements().forEach(holder -> {
            int max = holder.value().getMaxLevel();
            for (int level = 1; level <= max; level++) {
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                ItemEnchantments.Mutable m = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                m.set(holder, level);
                book.set(DataComponents.STORED_ENCHANTMENTS, m.toImmutable());
                if (AethValues.lookup(book) != null) all.add(keyOf(book));
            }
        });

        if (all.size() != current.size()) {
            AetheriumAttachments.AETH_KNOWN_ITEMS.set(player, new ArrayList<>(all));
        }
    }

    /** Ordered (learn-order) list of one-count template stacks this player currently knows. */
    public static List<ItemStack> getKnownStacks(Player player) {
        HolderLookup.Provider registries = player.registryAccess();
        List<String> keys = knownKeys(player);
        List<ItemStack> out = new ArrayList<>(keys.size());
        for (String key : keys) {
            ItemStack stack = fromKey(registries, key);
            if (stack != null) out.add(stack);
        }
        return out;
    }

    private static ItemStack fromKey(HolderLookup.Provider registries, String key) {
        int hash = key.indexOf(SEP);
        String itemPart = hash < 0 ? key : key.substring(0, hash);

        Identifier itemId = Identifier.tryParse(itemPart);
        if (itemId == null) return null;
        Optional<? extends Holder<Item>> item = BuiltInRegistries.ITEM.get(itemId);
        if (item.isEmpty()) return null;

        ItemStack stack = new ItemStack(item.get().value());
        if (hash < 0) return stack;

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        var enchLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);

        for (String part : key.substring(hash + 1).split(",")) {
            int eq = part.lastIndexOf('=');
            if (eq < 0) continue;
            Identifier enchId = Identifier.tryParse(part.substring(0, eq));
            if (enchId == null) continue;
            int level;
            try {
                level = Integer.parseInt(part.substring(eq + 1));
            } catch (NumberFormatException ex) {
                continue;
            }

            enchLookup.get(ResourceKey.create(Registries.ENCHANTMENT, enchId))
                    .ifPresent(holder -> mutable.set(holder, level));
        }

        ItemEnchantments built = mutable.toImmutable();
        if (!built.isEmpty()) {
            // Same rule as AethValues.enchantmentsOf: stored on books, regular on everything else.
            stack.set(stack.is(Items.ENCHANTED_BOOK)
                    ? DataComponents.STORED_ENCHANTMENTS : DataComponents.ENCHANTMENTS, built);
        }
        return stack;
    }

    /** The known-key list for {@code player}, or an empty list if nothing's set yet. */
    private static List<String> knownKeys(Player player) {
        return AetheriumAttachments.AETH_KNOWN_ITEMS.has(player)
                ? AetheriumAttachments.AETH_KNOWN_ITEMS.get(player)
                : List.of();
    }
}