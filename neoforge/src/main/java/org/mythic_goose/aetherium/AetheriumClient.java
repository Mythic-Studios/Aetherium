package org.mythic_goose.aetherium;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.lwjgl.glfw.GLFW;
import org.mythic_goose.aetherium.api.AethTooltips;
import org.mythic_goose.aetherium.api.energy_system.energy.ChargedTool;
import org.mythic_goose.aetherium.api.external.ComponentIntProperty;
import org.mythic_goose.aetherium.entity.astralex.AstralexRenderer;
import org.mythic_goose.aetherium.entity.cavern_cow.CavernCowRenderer;
import org.mythic_goose.aetherium.init.AetheriumEntities;
import org.mythic_goose.aetherium.init.AetheriumMenuTypes;
import org.mythic_goose.aetherium.item.ClockOfMatterItem;
import org.mythic_goose.aetherium.menu.ArcaneStationScreen;
import org.mythic_goose.aetherium.menu.CurrencyExchangeScreen;
import org.mythic_goose.aetherium.network.ArmorRechargePayload;
import org.mythic_goose.aetherium.network.ClockRechargePayload;
import org.mythic_goose.aetherium.network.ToolRechargePayload;

@Mod(value = AetheriumConstants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = AetheriumConstants.MOD_ID, value = Dist.CLIENT)
public class AetheriumClient {

    private static KeyMapping RECHARGE_KEY;
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(AetheriumCommon.id("general"));

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        AethTooltips.register();
        AetheriumMenuTypes.ARCANE_STATION_MENU_TYPE.get().registerScreen(ArcaneStationScreen::new);
        AetheriumMenuTypes.CURRENCY_EXCHANGE_MENU_TYPE.get().registerScreen(CurrencyExchangeScreen::new);

        RECHARGE_KEY = new KeyMapping(
                "key.aetherium.clock_recharge",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                CATEGORY
        );
    }

    @SubscribeEvent
    public static void onRegisterRangeSelectProperty(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(
                Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "component_int_property"),
                ComponentIntProperty.MAP_CODEC
        );
    }

    @SubscribeEvent
    static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AetheriumEntities.ASTRALEX_BOSS.get(), AstralexRenderer::new);
        event.registerEntityRenderer(AetheriumEntities.CAVERN_COW.get(), CavernCowRenderer::new);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        while (RECHARGE_KEY.consumeClick()) {
            LocalPlayer player = client.player;

            InteractionHand clockHand = findClockHand(player);
            if (clockHand != null) {
                ClientPacketDistributor.sendToServer(new ClockRechargePayload(clockHand));
                continue;
            }

            InteractionHand toolHand = findToolHand(player);
            if (toolHand != null) {
                ClientPacketDistributor.sendToServer(new ToolRechargePayload(toolHand));
            }

            ClientPacketDistributor.sendToServer(new ArmorRechargePayload());
        }
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