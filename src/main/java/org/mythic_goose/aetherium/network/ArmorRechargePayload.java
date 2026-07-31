package org.mythic_goose.aetherium.network;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.mythic_goose.aetherium.Aetherium;

public record ArmorRechargePayload() implements CustomPacketPayload {
    public static final Type<ArmorRechargePayload> TYPE =
            new Type<>(Aetherium.id("armor_recharge"));

    public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, ArmorRechargePayload> CODEC =
            StreamCodec.unit(new ArmorRechargePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}