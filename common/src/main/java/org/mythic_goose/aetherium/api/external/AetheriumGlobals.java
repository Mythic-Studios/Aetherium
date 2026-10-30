package org.mythic_goose.aetherium.api.external;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.mythic_goose.aetherium.init.AetheriumBlocks;

import java.util.Map;

/**
 * Aetherium Globals built to allow continued accessibility in bits of code if used as an API.
 * <p>
 * {@code AETHERIUM_CHISEL_MAP} - Has been added due to more customizablity within the {@code ChargedChiselItem}
 */
public class AetheriumGlobals {
    public static final Map<Block, Block> AETHERIUM_CHISEL_MAP =
            Map.of(
                    Blocks.END_STONE, AetheriumBlocks.CRACKED_END_STONE.get(),
                    Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST
            );
}
