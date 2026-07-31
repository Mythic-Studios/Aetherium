package org.mythic_goose.aetherium.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * See {@link net.minecraft.tags.BlockTags} for vanilla tags.
 * Note that addition to some vanilla tags implies having certain functionality.
 */
public class AetheriumBlockTags {
    private AetheriumBlockTags() {
    }

    public static final TagKey<Block> SPEEDY_BLOCKS = register("speedy_blocks");


    private static TagKey<Block> register(String tagId) {
        return AethTagRegistration.BLOCK_TAG.registerAetherium(tagId);
    }
}
