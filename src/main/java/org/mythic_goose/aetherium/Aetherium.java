package org.mythic_goose.aetherium;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.api.AethCommands;
import org.mythic_goose.aetherium.api.AethConfigLoader;
import org.mythic_goose.aetherium.api.AetheriumAttachments;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.creative_tab.ModCreativeTabs;
import org.mythic_goose.aetherium.init.ModBlockEntities;
import org.mythic_goose.aetherium.init.ModBlocks;
import org.mythic_goose.aetherium.init.ModItems;
import org.mythic_goose.aetherium.init.ModMenuTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aetherium implements ModInitializer {
	public static final String MOD_ID = "aetherium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Aetherium Inflation go Brrrrr");

		ModBlocks.init();
		ModItems.init();
		ModBlockEntities.register();

		ModDataComponents.init();
		AetheriumAttachments.register();
		AethCommands.register();
		AethValuesInit.register();
		AethConfigLoader.load();

		ModMenuTypes.registerModMenuTypes();

		ModCreativeTabs.init();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
