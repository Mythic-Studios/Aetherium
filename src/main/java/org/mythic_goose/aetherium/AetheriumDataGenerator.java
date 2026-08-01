package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.mythic_goose.aetherium.datagen.*;
import org.mythic_goose.aetherium.world.AetheriumConfiguredFeatures;
import org.mythic_goose.aetherium.world.AetheriumPlacedFeatures;

public class AetheriumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(AetheriumLanguageProvider::new);
		pack.addProvider(AetheriumModelProvider::new);
		pack.addProvider(AetheriumItemTagProvider::new);
		pack.addProvider(AetheriumBlockTagProvider::new);
		pack.addProvider(AetheriumBlockLootTableProvider::new);
		pack.addProvider(AetheriumRecipeProvider::new);
		pack.addProvider(AetheriumEquipmentAssetProvider::new);
		pack.addProvider(AetheriumRegistryDataProvider::new);

	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, AetheriumConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, AetheriumPlacedFeatures::bootstrap);
	}
}
