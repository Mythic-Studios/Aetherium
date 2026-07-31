package org.mythic_goose.aetherium.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import org.mythic_goose.aetherium.Aetherium;

/** Sent client -> server whenever the player presses the recharge key ('V'). */
public record ToolRechargePayload(InteractionHand hand) implements CustomPacketPayload {

    public static final Type<ToolRechargePayload> TYPE =
            new Type<>(Aetherium.id("tool_recharge"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToolRechargePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.idMapper(id -> InteractionHand.values()[id], InteractionHand::ordinal),
            ToolRechargePayload::hand,
            ToolRechargePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}