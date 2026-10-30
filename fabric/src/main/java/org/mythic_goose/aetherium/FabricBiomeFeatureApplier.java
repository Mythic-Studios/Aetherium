package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import org.mythic_goose.aetherium.world.gen.AetheriumBiomeFeatures;

public class FabricBiomeFeatureApplier {
    public static void applyAll() {
        for (AetheriumBiomeFeatures.Entry entry : AetheriumBiomeFeatures.getEntries()) {
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(entry.biomes().toArray(new ResourceKey[0])),
                    entry.step(),
                    entry.featureKey()
            );
        }
    }
}
