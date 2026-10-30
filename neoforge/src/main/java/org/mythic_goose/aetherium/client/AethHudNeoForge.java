package org.mythic_goose.aetherium.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import org.mythic_goose.aetherium.AetheriumConstants;

@EventBusSubscriber(modid = AetheriumConstants.MOD_ID, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class AethHudNeoForge {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        AethHudRenderer.render(event.getGuiGraphics(), event.getPartialTick());
    }
}