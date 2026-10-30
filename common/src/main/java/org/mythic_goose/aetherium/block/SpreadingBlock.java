package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.Optional;

public class SpreadingBlock extends Block {
    // The block this one spreads ONTO (and reverts to when covered)
    private final ResourceKey<Block> targetBlock;

    public SpreadingBlock(Properties properties, ResourceKey<Block> targetBlock) {
        super(properties);
        this.targetBlock = targetBlock;
    }

    /**
     * Same check vanilla grass uses: a single snow layer is fine, a full water
     * block above kills it, otherwise the block above must let light through.
     */
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        }
        int opacity = LightEngine.getLightDampeningInto(state, aboveState, Direction.UP, aboveState.getLightDampening());
        return opacity < 15;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Registry<Block> blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<Block> targetOpt = blocks.getOptional(this.targetBlock);
        if (targetOpt.isEmpty()) return;

        Block target = targetOpt.get();

        // Covered by something solid (or submerged) -> revert to the target block
        if (!canSurvive(state, level, pos)) {
            level.setBlockAndUpdate(pos, target.defaultBlockState());
            return;
        }

        // Only spread if there's enough light above this block
        if (level.getMaxLocalRawBrightness(pos.above()) < 4) {
            return;
        }

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
                    && canSurvive(spreadState, level, neighborPos)
                    && level.getMaxLocalRawBrightness(neighborPos.above()) >= 4) {
                level.setBlockAndUpdate(neighborPos, spreadState);
            }
        }
    }
}