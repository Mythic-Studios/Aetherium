package org.mythic_goose.aetherium.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.mythic_goose.aetherium.util.AetheriumStructureTags;

import java.util.Optional;

public class DungeonLocatorItem extends Item {

    public DungeonLocatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ServerLevel serverLevel = (ServerLevel) level;
        ItemStack stack = player.getItemInHand(hand);

        // stops it being spammed every tick
        player.getCooldowns().addCooldown(stack.getItem().getDefaultInstance(), 100); // 5 seconds

        Optional<HolderSet.Named<Structure>> structures =
                serverLevel.registryAccess()
                        .lookupOrThrow(Registries.STRUCTURE)
                        .get(AetheriumStructureTags.NEEDED_FOR_DUNGEON_ADVANCEMENT);

        if (structures.isEmpty()) {
            player.sendOverlayMessage(
                    Component.literal("No structures registered for that tag.").withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }

        BlockPos playerPos = player.blockPosition();

        Pair<BlockPos, Holder<Structure>> result =
                serverLevel.getChunkSource().getGenerator().findNearestMapStructure(
                        serverLevel, structures.get(), playerPos, 100, false
                );

        if (result == null) {
            player.sendOverlayMessage(
                    Component.literal("Couldn't find a matching structure nearby.").withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }

        BlockPos structurePos = result.getFirst();
        int dx = structurePos.getX() - playerPos.getX();
        int dz = structurePos.getZ() - playerPos.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        String direction = getDirection(dx, dz);

        player.sendSystemMessage(
                Component.literal(String.format("Nearest Dungeon: %s, %.0f blocks away", direction, distance))
                        .withStyle(ChatFormatting.AQUA)
        );

        return InteractionResult.SUCCESS;
    }

    private static String getDirection(int dx, int dz) {
        double angle = Math.toDegrees(Math.atan2(dx, -dz)); // 0° = north
        if (angle < 0) angle += 360;

        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int index = (int) Math.round(angle / 45.0) % 8;
        return directions[index];
    }
}