package org.mythic_goose.aetherium.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.mythic_goose.aetherium.world.AetheriumPlacedFeatures;

public class AetheriumBushGen {
    public static void generateBushes() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.END_BARRENS,Biomes.END_HIGHLANDS,Biomes.THE_END,Biomes.END_MIDLANDS,Biomes.SMALL_END_ISLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, AetheriumPlacedFeatures.VOID_BERRY_BUSH_PLACED_KEY);
    }
}
