package org.mythic_goose.aetherium.api;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.WeatheringCopperCollection;

import java.util.*;

public class AethValues {
    private static final Map<Item, Aeth> ITEM_VALUES = new LinkedHashMap<>();
    private static final Map<TagKey<Item>, Aeth> TAG_VALUES = new LinkedHashMap<>();

    // Level-I value of each enchantment, added on top of the item's own value. Applies to
    // enchanted books (stored enchantments) and to enchanted gear (regular enchantments).
    // Each extra level doubles the cost (two level-N books combine into level N+1).
    private static final Map<ResourceKey<Enchantment>, Aeth> ENCHANT_VALUES = new LinkedHashMap<>();
    private static Aeth defaultEnchantValue = Aeth.ofUnits(0);

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

    /**
     * Registers every item wool type including the dyed alternatives. That comes
     * as a ColorCollection rather than a single Item. Yeah, I might need to add a multiplier for dyed versions with the
     * additions of dye but I really don't care at this moment
     */
    public static void set(ColorCollection<Item> items, Aeth value) {
        items.forEach(item -> ITEM_VALUES.put(item, value));
    }

    public static void setTag(TagKey<Item> tag, Aeth value) {
        TAG_VALUES.put(tag, value);
    }

    /** Value of one enchantment at level I. Higher levels double per level. */
    public static void setEnchantment(ResourceKey<Enchantment> key, Aeth levelOneValue) {
        ENCHANT_VALUES.put(key, levelOneValue);
    }

    /** Fallback level-I value for any enchantment with no explicit one (modded, datapack). */
    public static void setDefaultEnchantment(Aeth levelOneValue) {
        defaultEnchantValue = levelOneValue;
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

    /**
     * The enchantments that count for this stack: stored enchantments on an enchanted
     * book, regular enchantments on everything else (tools, weapons, armour...).
     */
    public static ItemEnchantments enchantmentsOf(ItemStack stack) {
        return stack.getOrDefault(
                stack.is(Items.ENCHANTED_BOOK) ? DataComponents.STORED_ENCHANTMENTS : DataComponents.ENCHANTMENTS,
                ItemEnchantments.EMPTY);
    }

    /**
     * Returns null if no value is registered for this stack. Enchanted stacks are worth the
     * item's own value plus their enchantment values; a blank enchanted book is worthless.
     */
    public static Aeth lookup(ItemStack stack) {
        Aeth base = lookupBase(stack);
        if (base == null) return null;

        ItemEnchantments ench = enchantmentsOf(stack);
        if (ench.isEmpty()) {
            return stack.is(Items.ENCHANTED_BOOK) ? null : base;
        }
        return base.add(enchantmentValue(ench));
    }

    /** The item's own registered value (direct, then tags), ignoring any enchantments. */
    private static Aeth lookupBase(ItemStack stack) {
        Aeth direct = ITEM_VALUES.get(stack.getItem());
        if (direct != null) return direct;

        for (Map.Entry<TagKey<Item>, Aeth> entry : TAG_VALUES.entrySet()) {
            if (stack.is(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /** Sum of each enchantment's level-I value, doubled per level above I. */
    private static Aeth enchantmentValue(ItemEnchantments ench) {
        Aeth total = Aeth.ofUnits(0);
        for (var e : ench.entrySet()) {
            Aeth levelOne = e.getKey().unwrapKey()
                    .map(k -> ENCHANT_VALUES.getOrDefault(k, defaultEnchantValue))
                    .orElse(defaultEnchantValue);
            int shift = Math.min(e.getIntValue() - 1, 40); // avoid long overflow; Aeth clamps anyway
            total = total.add(levelOne.multiply(1L << shift));
        }
        return total;
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