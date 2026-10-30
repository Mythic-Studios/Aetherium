package org.mythic_goose.aetherium.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class AetheriumNetwork {

    @FunctionalInterface
    public interface Sender {
        void sendToClient(ServerPlayer player, CustomPacketPayload payload);
    }

    private static Sender sender;

    public static void init(Sender impl) {
        sender = impl;
    }

    public static void sendToClient(ServerPlayer player, CustomPacketPayload payload) {
        if (sender == null) {
            throw new IllegalStateException("AetheriumNetwork.init(...) was never called by the loader module");
        }
        sender.sendToClient(player, payload);
    }
}