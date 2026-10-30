package org.mythic_goose.aetherium.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.command.ConfigCommand;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumEntities;

@EventBusSubscriber(modid = AetheriumConstants.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(AetheriumEntities.CAVERN_COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG,
                ModEvents::isValidSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static boolean isValidSpawn(EntityType<? extends Mob> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        BlockPos below = pos.below();
        return level.getBlockState(below).is(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get());
    }
}