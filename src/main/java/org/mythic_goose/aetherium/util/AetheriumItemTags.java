package org.mythic_goose.aetherium.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * See {@link net.minecraft.tags.ItemTags} for vanilla tags.
 */
public final class AetheriumItemTags {
    private AetheriumItemTags() {
    }

    /**
     * These are used to add tags to an already working system.
     * <p>
     * Otherwise make your own or use {@link net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags}
     */
    public static final TagKey<Item> CHISELED_STONE_ITEMS = register("chiseled_stone_items");
    public static final TagKey<Item> SMOOTH_STONE_ITEMS = register("smooth_stone_items");
    public static final TagKey<Item> STONES_SLABS_ITEMS = register("stones_slabs_items");
    public static final TagKey<Item> BRICKS_SLABS_ITEMS = register("bricks_slabs_items");
    public static final TagKey<Item> FROM_SMOOTH_ITEMS = register("from_smooth_items");

    /**
     * Mod Specific Tags. For personal use and not directed for support use
     * <p>
     * You can use but it won't have any support for values
     */
    public static final TagKey<Item> REPAIRS_VOIDMASS = register("repairs_voidmass");
    public static final TagKey<Item> CLOCK_MODE_RECIPE = register("clock_mode_recipe");


    private static TagKey<Item> register(String tagId) {
        return AethTagRegistration.ITEM_TAG.registerAetherium(tagId);
    }
}
