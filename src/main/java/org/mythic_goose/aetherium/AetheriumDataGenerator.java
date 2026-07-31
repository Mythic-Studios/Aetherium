package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.mythic_goose.aetherium.datagen.*;

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

	}
}
