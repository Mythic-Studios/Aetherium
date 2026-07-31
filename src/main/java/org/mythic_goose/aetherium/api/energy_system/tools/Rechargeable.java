package org.mythic_goose.aetherium.api.energy_system.tools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;

public interface Rechargeable {

    int maxCharge();
    int rechargeSegmentSize();
    Aeth rechargeSegmentCost();

    int getCurrentCharge(ItemStack stack);
    void setCurrentCharge(ItemStack stack, int amount);

    /**
     * Tops up one segment (or whatever's left to hit maxCharge, if less than
     * a full segment remains), charging Aeth proportional to how much charge
     * was actually added. Returns false if already full or unaffordable.
     */
    default boolean tryRecharge(Player player, ItemStack stack) {
        int missing = maxCharge() - getCurrentCharge(stack);
        if (missing <= 0) return false;

        int amount = Math.min(rechargeSegmentSize(), missing);
        Aeth cost = rechargeSegmentCost().multiply(amount).divide(rechargeSegmentSize());

        if (!AethHelper.spend(player, cost)) return false;

        setCurrentCharge(stack, getCurrentCharge(stack) + amount);
        return true;
    }
}