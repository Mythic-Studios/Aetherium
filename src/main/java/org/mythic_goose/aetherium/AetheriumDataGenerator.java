package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.mythic_goose.aetherium.datagen.ModLanguageProvider;
import org.mythic_goose.aetherium.datagen.ModModelProvider;

public class AetheriumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLanguageProvider::new);
		pack.addProvider(ModModelProvider::new);

	}
}
