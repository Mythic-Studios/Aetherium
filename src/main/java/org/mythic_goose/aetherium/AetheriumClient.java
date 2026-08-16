package org.mythic_goose.aetherium;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.mythic_goose.aetherium.api.AethTooltips;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargedTool;
import org.mythic_goose.aetherium.client.AethHud;
import org.mythic_goose.aetherium.client.AstralexBossTracker;
import org.mythic_goose.aetherium.entity.AstralexRenderer;
import org.mythic_goose.aetherium.init.AetheriumEntities;
import org.mythic_goose.aetherium.init.AetheriumMenuTypes;
import org.mythic_goose.aetherium.item.ClockOfMatterItem;
import org.mythic_goose.aetherium.menu.ArcaneStationScreen;
import org.mythic_goose.aetherium.menu.CurrencyExchangeScreen;
import org.mythic_goose.aetherium.network.ArmorRechargePayload;
import org.mythic_goose.aetherium.network.AstralexBossBarPayload;
import org.mythic_goose.aetherium.network.ClockRechargePayload;
import org.mythic_goose.aetherium.network.ToolRechargePayload;

public class AetheriumClient implements ClientModInitializer {

    private static KeyMapping RECHARGE_KEY;
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Aetherium.id("general"));

    @Override
    public void onInitializeClient() {
        AethHud.register();
        AethTooltips.register();

        MenuScreens.register(AetheriumMenuTypes.ARCANE_STATION_MENU_TYPE, ArcaneStationScreen::new);
        MenuScreens.register(AetheriumMenuTypes.CURRENCY_EXCHANGE_MENU_TYPE, CurrencyExchangeScreen::new);

        AstralexBossBarPayload.register();
        ClientPlayNetworking.registerGlobalReceiver(AstralexBossBarPayload.TYPE, (payload, context) ->
                context.client().execute(() -> AstralexBossTracker.setActive(payload.bossId(), payload.active())));

        EntityRenderers.register(AetheriumEntities.ASTRALEX_BOSS, AstralexRenderer::new);

        RECHARGE_KEY = new KeyMapping(
                "key.aetherium.clock_recharge",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                CATEGORY
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (RECHARGE_KEY.consumeClick()) {
                LocalPlayer player = client.player;

                InteractionHand clockHand = findClockHand(player);
                if (clockHand != null) {
                    ClientPlayNetworking.send(new ClockRechargePayload(clockHand));
                    continue;
                }

                InteractionHand toolHand = findToolHand(player);
                if (toolHand != null) {
                    ClientPlayNetworking.send(new ToolRechargePayload(toolHand));
                }


                ClientPlayNetworking.send(new ArmorRechargePayload());
            }
        });
    }

    private static InteractionHand findClockHand(LocalPlayer player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof ClockOfMatterItem) return InteractionHand.MAIN_HAND;

        ItemStack off = player.getOffhandItem();
        if (off.getItem() instanceof ClockOfMatterItem) return InteractionHand.OFF_HAND;

        return null;
    }

    private static InteractionHand findToolHand(LocalPlayer player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof ChargedTool) return InteractionHand.MAIN_HAND;

        ItemStack off = player.getOffhandItem();
        if (off.getItem() instanceof ChargedTool) return InteractionHand.OFF_HAND;

        return null;
    }
}
