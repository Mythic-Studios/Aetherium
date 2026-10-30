package org.mythic_goose.aetherium.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.resources.Identifier;
import net.minecraft.world.BossEvent;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.client.AstralexBossTracker;
import org.mythic_goose.aetherium.mixin.accessor.BossHealthOverlayAccessor;
import org.mythic_goose.aetherium.render.NodeBossBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {

    @Unique
    private static final NodeBossBarRenderer aetherium$astralexBossBarRenderer =
            new NodeBossBarRenderer(
                    Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/bossbar/astralex/background.png"),
                    Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/bossbar/astralex/bar.png"),
                    Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/bossbar/astralex/overlay.png"),
                    182,
                    7
            );

    @Redirect(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;extractBar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/world/BossEvent;)V"
            )
    )
    private void aetherium$redirectExtractBar(BossHealthOverlay instance, GuiGraphicsExtractor graphics, int x, int y, BossEvent event) {
        if (aetherium$isAstralexBar(event)) {
            aetherium$astralexBossBarRenderer.renderBossBar(graphics, x, y, event);
        } else {
            ((BossHealthOverlayAccessor) instance).aetherium$invokeExtractBar(graphics, x, y, event);
        }
    }

    @Unique
    private boolean aetherium$isAstralexBar(BossEvent event) {
        return AstralexBossTracker.isAstralexBar(event.getId());
    }
}