package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.mythic_goose.aetherium.entity.AstralexBoss;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumEntities;
import org.mythic_goose.aetherium.init.AetheriumItems;

import java.util.Objects;

public class SummoningStoneBlock extends Block {
    public final String TYPE;
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public SummoningStoneBlock(Properties properties, String type) {
        super(properties);
        TYPE = type;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            // Let the client predict "something happened" without running logic
            if (itemStack.is(AetheriumItems.SUMMONING_GEM) || itemStack.is(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        if (itemStack.is(AetheriumItems.SUMMONING_GEM)) {
            if (Objects.equals(TYPE, "PhaseOne")) {
                AstralexBoss boss = AetheriumEntities.ASTRALEX_BOSS.create(level, EntitySpawnReason.COMMAND);
                if (boss != null) {
                    boss.snapTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            player.getYRot(), 0.0F);
                    level.addFreshEntity(boss);
                    player.sendSystemMessage(Component.translatable("dialogue.summom.astralex.phase_one"));
                    itemStack.shrink(1);
                }
            } else if (Objects.equals(TYPE, "PhaseTwo")) {
                player.sendOverlayMessage(Component.translatable("dialogue.summom.failed.coming_soon"));
                return InteractionResult.SUCCESS;
            }
        }

        if (itemStack.is(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE)) {
            if (Objects.equals(TYPE, "PhaseOne")) {
                level.setBlockAndUpdate(pos, AetheriumBlocks.ULTIMATIUM_STONE.defaultBlockState());
                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            } else if (Objects.equals(TYPE, "PhaseTwo")) {
                level.setBlockAndUpdate(pos, AetheriumBlocks.SUMMONING_STONE.defaultBlockState());
                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
