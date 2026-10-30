package org.mythic_goose.aetherium.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumEntities;
import org.mythic_goose.aetherium.world.ModBiomes;
import org.mythic_goose.aetherium.world.gen.AetheriumBiomeFeatures;

import java.util.List;

public class NeoBiomeModifierProvider {
    public static final ResourceKey<BiomeModifier> SPAWN_COW = registerKey("spawn_cow");

    public static void bootstrap(BootstrapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> featureLookup = ctx.lookup(Registries.PLACED_FEATURE);
        var biome = ctx.lookup(Registries.BIOME);

        for (AetheriumBiomeFeatures.Entry entry : AetheriumBiomeFeatures.getEntries()) {
            HolderSet<Biome> biomes = HolderSet.direct(
                    entry.biomes().stream().map(biomeLookup::getOrThrow).toList()
            );
            HolderSet<PlacedFeature> features = HolderSet.direct(
                    featureLookup.getOrThrow(entry.featureKey())
            );

            ResourceKey<BiomeModifier> key = ResourceKey.create(
                    NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    Identifier.fromNamespaceAndPath(
                            AetheriumConstants.MOD_ID,
                            entry.featureKey().identifier().getPath() // one modifier id per feature entry
                    )
            );

            ctx.register(key, new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes,
                    features,
                    entry.step()
            ));


        }

        ctx.register(SPAWN_COW, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                HolderSet.direct(biome.getOrThrow(ModBiomes.CRYSTAL_CAVERN)),
                new Weighted<>(new MobSpawnSettings.SpawnerData(AetheriumEntities.CAVERN_COW.get(), 6, 25), 6)));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, name));
    }
}