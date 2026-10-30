package org.mythic_goose.aetherium.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.api.energy_system.energy.Rechargeable;
import org.mythic_goose.aetherium.item.ClockOfMatterItem;

public class AetheriumNetworkHandlers {

    public static void handleClockRecharge(ServerPlayer player, ClockRechargePayload payload) {
        ItemStack stack = player.getItemInHand(payload.hand());
        if (!(stack.getItem() instanceof ClockOfMatterItem)) return;
        ClockOfMatterItem.tryRecharge(player, stack);
    }

    public static void handleToolRecharge(ServerPlayer player, ToolRechargePayload payload) {
        ItemStack stack = player.getItemInHand(payload.hand());
        if (stack.getItem() instanceof Rechargeable rechargeable) {
            rechargeable.tryRecharge(player, stack);
        }
    }

    public static void handleArmorRecharge(ServerPlayer player, ArmorRechargePayload payload) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack piece = player.getItemBySlot(slot);
            if (piece.getItem() instanceof Rechargeable rechargeable) {
                rechargeable.tryRecharge(player, piece);
            }
        }
    }
}