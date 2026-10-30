package org.mythic_goose.aetherium;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import org.mythic_goose.aetherium.api.AethConfigLoader;
import org.mythic_goose.aetherium.api.AethEnchantConfigLoader;
import org.mythic_goose.aetherium.api.AetheriumAttachments;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.init.*;
import org.mythic_goose.aetherium.network.AstralexBossBarPayload;
import org.mythic_goose.aetherium.platform.Services;
import org.mythic_goose.aetherium.world.feature.CustomFeatures;
import org.mythic_goose.aetherium.world.gen.AetheriumWorldGen;
import org.mythic_goose.amalgamation.api.networking_v1.PayloadTypeRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.DeferredRegister;


public class AetheriumCommon {

    public static void init() {
        DeferredRegister.setModId(AetheriumConstants.MOD_ID);

        AetheriumBlocks.init();
        AetheriumItems.init();
        AetheriumSounds.init();
        AetheriumBlockEntities.register();
        AetheriumEntities.registerAetheriumEntities();

        PayloadTypeRegistry.clientboundPlay().register(
                AstralexBossBarPayload.TYPE,
                AstralexBossBarPayload.CODEC);

        AetheriumAttachments.register();
        AethConfigLoader.load();
        AethEnchantConfigLoader.load();
        AetheriumWorldGen.generateModWorldGen();

        if (Services.PLATFORM.isModLoaded(AetheriumConstants.MOD_ID)) {
            AetheriumConstants.LOG.info("Hello from Aetherium");
        }
    }

    public static void commonSetup() {
        AethValuesInit.register();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, path);
    }
}