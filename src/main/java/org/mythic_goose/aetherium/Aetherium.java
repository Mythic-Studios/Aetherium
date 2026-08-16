package org.mythic_goose.aetherium;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.api.*;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.creative_tab.ModCreativeTabs;
import org.mythic_goose.aetherium.entity.AstralexBoss;
import org.mythic_goose.aetherium.init.*;
import org.mythic_goose.aetherium.network.AetheriumNetworking;
import org.mythic_goose.aetherium.world.gen.AetheriumWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aetherium implements ModInitializer {
	public static final String MOD_ID = "aetherium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Aetherium Inflation go Brrrrr");

		AetheriumBlocks.init();
		AetheriumItems.init();
		AetheriumBlockEntities.register();
		AetheriumEntities.registerAetheriumEntities();

		ModDataComponents.init();
		AetheriumAttachments.register();
		AethCommands.register();
		AethValuesInit.register();
		AethConfigLoader.load();
		AetheriumNetworking.registerClock();
		AetheriumWorldGen.generateModWorldGen();

		AetheriumMenuTypes.registerModMenuTypes();

		ModCreativeTabs.init();

		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamage, damageTaken, blocked) -> {
			for (EquipmentSlot slot : EquipmentSlot.values()) {
				if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
				ItemStack piece = entity.getItemBySlot(slot);
				if (piece.getItem() instanceof ChargedArmorItem armor) {
					armor.drain(piece, armor.chargePerHit());
				}
			}
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
				AstralexBoss.removePlayerFromAll(handler.getPlayer()));

		FabricDefaultAttributeRegistry.register(
				AetheriumEntities.ASTRALEX_BOSS,
				AstralexBoss.createAttributes()
		);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
