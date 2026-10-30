package org.mythic_goose.aetherium.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.world.AetheriumConfiguredFeatures;
import org.mythic_goose.aetherium.world.AetheriumPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AetheriumDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, AetheriumConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, AetheriumPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NeoBiomeModifierProvider::bootstrap)
            ;

    public AetheriumDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(AetheriumConstants.MOD_ID));
    }
}