package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class SpreadingBlock extends Block {
    // The block this one spreads ONTO
    private final ResourceKey<Block> targetBlock;

    public SpreadingBlock(Properties properties, ResourceKey<Block> targetBlock) {
        super(properties);
        this.targetBlock = targetBlock;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Only spread if there's enough light above this block
        if (level.getMaxLocalRawBrightness(pos.above()) < 4) {
            return;
        }

        Registry<Block> blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<Block> targetOpt = blocks.getOptional(this.targetBlock);
        if (targetOpt.isEmpty()) return;

        Block target = targetOpt.get();
        BlockState spreadState = this.defaultBlockState();

        // Try up to 4 random neighbors, like vanilla grass
        for (int i = 0; i < 4; i++) {
            BlockPos neighborPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1
            );

            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(target)
                    && level.getMaxLocalRawBrightness(neighborPos.above()) >= 4) {
                level.setBlockAndUpdate(neighborPos, spreadState);
            }
        }
    }
}