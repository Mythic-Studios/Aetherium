package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.world.CavernPlatformFeature;

import java.util.Objects;
import java.util.Set;

public class EnergyTransformerBlock extends Block implements Portal {
    public EnergyTransformerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (itemStack.is(AetheriumItems.ANTIMATTER_DISC)) {
            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                player.teleport(Objects.requireNonNull(getPortalDestination(serverLevel, player, pos)));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos) {
        ResourceKey<Level> currentDimension = currentLevel.dimension();
        boolean isLeavingCustomDim = currentDimension == ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "the_caverns"));

        LevelData.RespawnData respawnData = currentLevel.getRespawnData();
        ResourceKey<Level> targetDimensionKey = isLeavingCustomDim ? respawnData.dimension() : ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "the_caverns"));
        BlockPos baseSpawnPos = isLeavingCustomDim ? respawnData.pos() : ServerLevel.END_SPAWN_POINT.above(14);

        ServerLevel targetLevel = currentLevel.getServer().getLevel(targetDimensionKey);
        if (targetLevel == null) {
            return null;
        }

        Vec3 exactSpawnPos = Vec3.atBottomCenterOf(baseSpawnPos);
        float yRot, xRot;
        Set<Relative> relatives;

        if(isLeavingCustomDim) {
            // Returning from Custom Dimension
            if(entity instanceof ServerPlayer serverPlayer) {
                return serverPlayer.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
            }

            exactSpawnPos = Vec3.atBottomCenterOf(entity.adjustSpawnLocation(targetLevel, baseSpawnPos));
            yRot = respawnData.yaw();
            xRot = respawnData.pitch();
            relatives = Relative.union(Relative.DELTA, Relative.ROTATION);
        } else {
            // Entering our custom Dimension
            BlockPos platformCenter = BlockPos.containing(exactSpawnPos).below();
            BlockPos portalPos = platformCenter.west(2);

            if(!targetLevel.getBlockState(portalPos.above()).is(AetheriumBlocks.ENERGY_TRANSFORMER)) {
                CavernPlatformFeature.createPlatform(targetLevel, platformCenter, false);

                targetLevel.setBlockAndUpdate(portalPos, AetheriumBlocks.ASTRAL_BRICKS.defaultBlockState());
                targetLevel.setBlockAndUpdate(portalPos.above(), AetheriumBlocks.ENERGY_TRANSFORMER.defaultBlockState());
            }

            yRot = Direction.WEST.toYRot();
            xRot = 0f;
            relatives = Relative.union(Relative.DELTA, Set.of(Relative.X_ROT));

            if(entity instanceof ServerPlayer) {
                exactSpawnPos = exactSpawnPos.subtract(0, 1, 0);
            }
        }

        return new TeleportTransition(
                targetLevel,
                exactSpawnPos,
                Vec3.ZERO,
                yRot,
                xRot,
                relatives,
                TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
    }
}
