package org.mythic_goose.aetherium.api.energy_system.tools;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.component.ModDataComponents;

public final class ChargeUtil {
    private ChargeUtil() {}

    public static int get(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.TOOL_CHARGE, 0);
    }

    public static void set(ItemStack stack, int charge, int maxCharge) {
        stack.set(ModDataComponents.TOOL_CHARGE, Mth.clamp(charge, 0, maxCharge));
    }

    public static boolean isEmpty(ItemStack stack) {
        return get(stack) <= 0;
    }

    /** Returns false (and does nothing) if there isn't enough charge. */
    public static boolean drain(ItemStack stack, int amount) {
        int current = get(stack);
        if (current < amount) return false;
        stack.set(ModDataComponents.TOOL_CHARGE, current - amount);
        return true;
    }

    public static int add(ItemStack stack, int amount, int maxCharge) {
        int next = Math.min(get(stack) + amount, maxCharge);
        stack.set(ModDataComponents.TOOL_CHARGE, next);
        return next;
    }
}