package org.mythic_goose.aetherium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.component.FabricDataComponentRegistrar;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.entity.astralex.AstralexBoss;
import org.mythic_goose.aetherium.entity.cavern_cow.CavernCowAnimal;
import org.mythic_goose.aetherium.init.*;
import org.mythic_goose.aetherium.network.AetheriumNetwork;
import org.mythic_goose.aetherium.network.AetheriumNetworkHandlers;
import org.mythic_goose.aetherium.network.ArmorRechargePayload;
import org.mythic_goose.aetherium.network.AstralexBossBarPayload;
import org.mythic_goose.aetherium.network.ClockRechargePayload;
import org.mythic_goose.aetherium.network.ToolRechargePayload;
import org.mythic_goose.aetherium.world.AetheriumEntitySpawns;
import org.mythic_goose.aetherium.world.ModBiomes;
import org.mythic_goose.aetherium.world.feature.CustomFeatures;
import org.mythic_goose.amalgamation.api.registry_v1.FabricCreativeTabRegistrar;

public class Aetherium implements ModInitializer {

    @Override
    public void onInitialize() {

        ModDataComponents.init(new FabricDataComponentRegistrar(AetheriumConstants.MOD_ID));

        AetheriumCommon.init();

        AetheriumCreativeTab.init(new FabricCreativeTabRegistrar(AetheriumConstants.MOD_ID));
        AetheriumMenuTypes.init(new FabricMenuRegistrar());

        AetheriumCommon.commonSetup();

        registerNetworking();

        FabricBiomeFeatureApplier.applyAll();

        FabricAethValues.init();

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> {
            output.accept(AetheriumItems.CAVERN_COW_SPAWN_EGG.get());
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            output.accept(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get());
        });

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
                AetheriumEntities.ASTRALEX_BOSS.get(),
                AstralexBoss.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                AetheriumEntities.CAVERN_COW.get(),
                CavernCowAnimal.createAttributes()
        );

        Registry.register(BuiltInRegistries.FEATURE,
                Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "energized_glowstone_blob"),
                CustomFeatures.ENERGIZED_GLOWSTONE_BLOB);

        AetheriumEntitySpawns.addSpawns();
    }

    private static void registerNetworking() {
        // Client -> server payloads (codec registration)
        PayloadTypeRegistry.serverboundPlay().register(ClockRechargePayload.TYPE, ClockRechargePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ToolRechargePayload.TYPE, ToolRechargePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ArmorRechargePayload.TYPE, ArmorRechargePayload.CODEC);


        // Server-side receivers
        ServerPlayNetworking.registerGlobalReceiver(ClockRechargePayload.TYPE, (payload, context) ->
                context.server().execute(() ->
                        AetheriumNetworkHandlers.handleClockRecharge(context.player(), payload)));

        ServerPlayNetworking.registerGlobalReceiver(ToolRechargePayload.TYPE, (payload, context) ->
                context.server().execute(() ->
                        AetheriumNetworkHandlers.handleToolRecharge(context.player(), payload)));

        ServerPlayNetworking.registerGlobalReceiver(ArmorRechargePayload.TYPE, (payload, context) ->
                context.server().execute(() ->
                        AetheriumNetworkHandlers.handleArmorRecharge(context.player(), payload)));

        // Lets common code send packets to clients
        AetheriumNetwork.init((player, payload) -> ServerPlayNetworking.send(player, payload));
    }
}