package org.mythic_goose.aetherium.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public abstract class ModBiomes {
    public static final ResourceKey<Biome> CRYSTAL_CAVERN = register("crystal_cavern");
    public static final ResourceKey<Biome> ENVOIDED_LAYERS = register("envoided_layers");

    public ModBiomes() {
    }

    private static ResourceKey<Biome> register(final String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("aetherium", name));
    }
}
