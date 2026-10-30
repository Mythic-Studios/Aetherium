package org.mythic_goose.aetherium;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.mythic_goose.aetherium.world.feature.CustomFeatures;

@EventBusSubscriber(modid = AetheriumConstants.MOD_ID)
public class NeoForgeFeatureRegistration {
    @SubscribeEvent
    static void register(RegisterEvent event) {
        event.register(Registries.FEATURE, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "energized_glowstone_blob"),
                    CustomFeatures.ENERGIZED_GLOWSTONE_BLOB);
        });
    }
}