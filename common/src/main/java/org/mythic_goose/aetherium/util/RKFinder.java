package org.mythic_goose.aetherium.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RKFinder {

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }
}
