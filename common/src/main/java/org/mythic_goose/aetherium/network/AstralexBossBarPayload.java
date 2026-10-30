package org.mythic_goose.aetherium.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.mythic_goose.aetherium.AetheriumConstants;

import java.util.UUID;

public record AstralexBossBarPayload(UUID bossId, boolean active) implements CustomPacketPayload {
    public static final Type<AstralexBossBarPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "astralex_boss_bar"));

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

    public static void sendTo(ServerPlayer player, UUID bossId, boolean active) {
        AetheriumNetwork.sendToClient(player, new AstralexBossBarPayload(bossId, active));
    }
}