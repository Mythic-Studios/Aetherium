package org.mythic_goose.aetherium.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import org.mythic_goose.aetherium.entity.cavern_cow.CavernCowAnimal;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumEntities;

public class AetheriumEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.CRYSTAL_CAVERN),
                MobCategory.CREATURE, AetheriumEntities.CAVERN_COW.get(), 5, 6, 18);

        SpawnPlacements.register(AetheriumEntities.CAVERN_COW.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG,
                AetheriumEntitySpawns::isValidSpawn);
    }

    public static boolean isValidSpawn(EntityType<? extends Mob> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        BlockPos below = pos.below();
        return level.getBlockState(below).is(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get());
    }
}
