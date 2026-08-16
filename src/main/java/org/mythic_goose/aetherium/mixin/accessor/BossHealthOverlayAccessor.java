package org.mythic_goose.aetherium.mixin.accessor;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BossHealthOverlay.class)
public interface BossHealthOverlayAccessor {
    @Invoker("extractBar")
    void aetherium$invokeExtractBar(GuiGraphicsExtractor graphics, int x, int y, BossEvent event);
}