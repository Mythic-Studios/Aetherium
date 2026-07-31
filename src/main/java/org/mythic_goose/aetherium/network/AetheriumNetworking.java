package org.mythic_goose.aetherium.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.item.ClockOfMatterItem;
import org.mythic_goose.aetherium.api.energy_system.tools.Rechargeable;

public class AetheriumNetworking {

    public static void registerClock() {
        PayloadTypeRegistry.serverboundPlay().register(ClockRechargePayload.TYPE, ClockRechargePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ToolRechargePayload.TYPE, ToolRechargePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ArmorRechargePayload.TYPE, ArmorRechargePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ClockRechargePayload.TYPE, (payload, context) -> {
            var player = context.player();
            // Networking callbacks run off the main server thread - hop back on
            // before touching world/inventory state.
            player.server.execute(() -> {
                ItemStack stack = player.getItemInHand(payload.hand());
                if (!(stack.getItem() instanceof ClockOfMatterItem)) return;
                ClockOfMatterItem.tryRecharge(player, stack);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ToolRechargePayload.TYPE, (payload, context) -> {
            var player = context.player();
            player.server.execute(() -> {
                ItemStack stack = player.getItemInHand(payload.hand());
                if (stack.getItem() instanceof Rechargeable rechargeable) {
                    rechargeable.tryRecharge(player, stack);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ArmorRechargePayload.TYPE, (payload, context) -> {
            var player = context.player();
            player.server.execute(() -> {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
                    ItemStack piece = player.getItemBySlot(slot);
                    if (piece.getItem() instanceof Rechargeable rechargeable) {
                        rechargeable.tryRecharge(player, piece);
                    }
                }
            });
        });
    }
}