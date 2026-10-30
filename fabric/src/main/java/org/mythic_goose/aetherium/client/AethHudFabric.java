package org.mythic_goose.aetherium.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.AetheriumConstants;

public class AethHudFabric {
    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "aeth_balance"),
                AethHudRenderer::render
        );
    }
}