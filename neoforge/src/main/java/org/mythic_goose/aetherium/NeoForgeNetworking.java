package org.mythic_goose.aetherium;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.mythic_goose.aetherium.network.AetheriumNetwork;
import org.mythic_goose.aetherium.network.AetheriumNetworkHandlers;
import org.mythic_goose.aetherium.network.ArmorRechargePayload;
import org.mythic_goose.aetherium.network.ClockRechargePayload;
import org.mythic_goose.aetherium.network.ToolRechargePayload;

@EventBusSubscriber(modid = AetheriumConstants.MOD_ID)
public class NeoForgeNetworking {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                ClockRechargePayload.TYPE,
                ClockRechargePayload.CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        AetheriumNetworkHandlers.handleClockRecharge((ServerPlayer) context.player(), payload))
        );

        registrar.playToServer(
                ToolRechargePayload.TYPE,
                ToolRechargePayload.CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        AetheriumNetworkHandlers.handleToolRecharge((ServerPlayer) context.player(), payload))
        );

        registrar.playToServer(
                ArmorRechargePayload.TYPE,
                ArmorRechargePayload.CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        AetheriumNetworkHandlers.handleArmorRecharge((ServerPlayer) context.player(), payload))
        );

        // Lets common code send packets to clients
        AetheriumNetwork.init((player, payload) -> PacketDistributor.sendToPlayer(player, payload));
    }
}