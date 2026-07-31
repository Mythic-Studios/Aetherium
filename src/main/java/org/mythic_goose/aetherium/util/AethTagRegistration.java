package org.mythic_goose.aetherium.util;

import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.mythic_goose.aetherium.Aetherium;

public record AethTagRegistration<T>(ResourceKey<Registry<T>> registryKey) {
    public static final AethTagRegistration<Item> ITEM_TAG = new AethTagRegistration<>(Registries.ITEM);
    public static final AethTagRegistration<Block> BLOCK_TAG = new AethTagRegistration<>(Registries.BLOCK);

    public TagKey<T> registerAetherium(String tagId) {
        return TagKey.create(registryKey, Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, tagId));
    }
}
