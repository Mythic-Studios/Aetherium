package org.mythic_goose.aetherium.world.gen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.mythic_goose.aetherium.world.AetheriumPlacedFeatures;
import org.mythic_goose.aetherium.world.ModBiomes;

import java.util.ArrayList;
import java.util.List;

public class AetheriumBiomeFeatures {

    public record Entry(
            GenerationStep.Decoration step,
            ResourceKey<PlacedFeature> featureKey,
            List<ResourceKey<Biome>> biomes
    ) {}

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static void register(GenerationStep.Decoration step,
                                ResourceKey<PlacedFeature> featureKey,
                                ResourceKey<Biome>... biomes) {
        ENTRIES.add(new Entry(step, featureKey, List.of(biomes)));
    }

    public static List<Entry> getEntries() {
        return ENTRIES;
    }

    // called once from common mod init, no loader classes involved
    public static void generateBushes() {
        register(GenerationStep.Decoration.VEGETAL_DECORATION,
                AetheriumPlacedFeatures.VOID_BERRY_BUSH_PLACED_KEY,
                Biomes.END_BARRENS, Biomes.END_HIGHLANDS, Biomes.THE_END,
                Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS);

    }
}