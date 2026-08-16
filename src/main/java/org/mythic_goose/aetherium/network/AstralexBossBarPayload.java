package org.mythic_goose.aetherium.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.mythic_goose.aetherium.Aetherium;

import java.util.UUID;

public record AstralexBossBarPayload(UUID bossId, boolean active) implements CustomPacketPayload {
    public static final Type<AstralexBossBarPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "astralex_boss_bar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AstralexBossBarPayload> CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeUUID(payload.bossId());
                buf.writeBoolean(payload.active());
            },
            (buf) -> new AstralexBossBarPayload(buf.readUUID(), buf.readBoolean())
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(TYPE, CODEC);
    }

    public static void sendTo(ServerPlayer player, UUID bossId, boolean active) {
        ServerPlayNetworking.send(player, new AstralexBossBarPayload(bossId, active));
    }
}