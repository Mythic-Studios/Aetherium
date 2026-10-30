package org.mythic_goose.aetherium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.mythic_goose.aetherium.init.AetheriumBlocks;

public class EnergizedGlowstoneFeature extends Feature<NoneFeatureConfiguration> {
    public EnergizedGlowstoneFeature(final Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(final FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        if (!level.isEmptyBlock(origin)) {
            return false;
        } else {
            BlockState aboveState = level.getBlockState(origin.above());
            if (!aboveState.is(AetheriumBlocks.CAVERN_ROCK.get()) && !aboveState.is(AetheriumBlocks.ROTTING_DIRT.get()) && !aboveState.is(AetheriumBlocks.CRYSTALLIZED_DIRT.get())) {
                return false;
            } else {
                level.setBlock(origin, AetheriumBlocks.ENERGIZED_GLOWSTONE.get().defaultBlockState(), 2);

                for(int i = 0; i < 1500; ++i) {
                    BlockPos placePos = origin.offset(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                    if (level.getBlockState(placePos).isAir()) {
                        int neighbours = 0;

                        for(Direction direction : Direction.values()) {
                            if (level.getBlockState(placePos.relative(direction)).is(AetheriumBlocks.ENERGIZED_GLOWSTONE.get())) {
                                ++neighbours;
                            }

                            if (neighbours > 1) {
                                break;
                            }
                        }

                        if (neighbours == 1) {
                            level.setBlock(placePos, AetheriumBlocks.ENERGIZED_GLOWSTONE.get().defaultBlockState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
