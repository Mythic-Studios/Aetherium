package org.mythic_goose.aetherium.api;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WeatheringCopperCollection;
import org.mythic_goose.aetherium.api.Aeth;

import java.util.*;

public class AethValues {
    private static final Map<Item, Aeth> ITEM_VALUES = new LinkedHashMap<>();
    private static final Map<TagKey<Item>, Aeth> TAG_VALUES = new LinkedHashMap<>();

    // Items/tags that still have an Aeth value (lookup() still returns it — tooltips,
    // formatting, etc. keep working) but that shouldn't be accepted for learning or
    // consuming at an Arcane Station. Meant for items reserved for some other use
    // (e.g. the Currency Exchange, which pulls its 3 buttons straight from this list).
    private static final Set<Item> EXCLUDED_ITEMS = new LinkedHashSet<>();
    private static final Set<TagKey<Item>> EXCLUDED_TAGS = new LinkedHashSet<>();

    public static void set(Item item, Aeth value) {
        ITEM_VALUES.put(item, value);
    }

    /**
     * Registers every item in a copper weathering family (unaffected, exposed,
     * weathered, oxidized — both the plain and waxed variant of each, 8 items total)
     * at the same value. Handy for things like copper blocks/ingots/etc. that come
     * as a WeatheringCopperCollection rather than a single Item.
     */
    public static void set(WeatheringCopperCollection<Item> items, Aeth value) {
        items.forEach(item -> ITEM_VALUES.put(item, value));
    }

    public static void setTag(TagKey<Item> tag, Aeth value) {
        TAG_VALUES.put(tag, value);
    }

    /** Marks this item as valued but not learnable/consumable at a station. */
    public static void exclude(Item item) {
        EXCLUDED_ITEMS.add(item);
    }

    /** Marks every item under this tag as valued but not learnable/consumable at a station. */
    public static void excludeTag(TagKey<Item> tag) {
        EXCLUDED_TAGS.add(tag);
    }

    /** True if this stack has been filtered out of station learning/consuming. */
    public static boolean isExcluded(ItemStack stack) {
        if (EXCLUDED_ITEMS.contains(stack.getItem())) return true;

        for (TagKey<Item> tag : EXCLUDED_TAGS) {
            if (stack.is(tag)) return true;
        }
        return false;
    }

    /**
     * Every individually-excluded item (not tag-excluded ones), in the order they were
     * registered via exclude(). This is the Currency Exchange's button list — dust,
     * crystal, ingot, etc. — so registration order in AethValuesInit is display order.
     */
    public static List<Item> getExcludedItems() {
        return List.copyOf(EXCLUDED_ITEMS);
    }

    /** Returns null if no value is registered for this stack. */
    public static Aeth lookup(ItemStack stack) {
        Aeth direct = ITEM_VALUES.get(stack.getItem());
        if (direct != null) return direct;

        for (Map.Entry<TagKey<Item>, Aeth> entry : TAG_VALUES.entrySet()) {
            if (stack.is(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static List<Item> getAllLearnableItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            ItemStack stack = new ItemStack(item);
            if (isExcluded(stack)) continue;
            if (lookup(stack) != null) {
                result.add(item);
            }
        }
        return result;
    }
}