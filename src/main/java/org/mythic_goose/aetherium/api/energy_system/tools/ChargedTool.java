package org.mythic_goose.aetherium.api.energy_system.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public interface ChargedTool {

    int maxCharge();
    int chargePerUse();

    default boolean hasCharge(ItemStack stack) {
        return ChargeUtil.get(stack) > 0;
    }

    default boolean chargedIsCorrectToolForDrops(ItemStack stack) {
        return true; // always drops correctly, charge only affects speed now
    }

    default float chargedDestroySpeed(ItemStack stack, float vanillaSpeed) {
        return hasCharge(stack) ? vanillaSpeed : vanillaSpeed / 10.0F;
    }

    default void onMine(ItemStack stack, Level level, BlockState state, BlockPos pos) {
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) != 0.0F) {
            ChargeUtil.drain(stack, chargePerUse());
        }
    }

    default void onHurtEnemy(ItemStack stack) {
        ChargeUtil.drain(stack, chargePerUse());
    }

    default boolean isBarVisible(ItemStack stack) {
        return true;
    }

    default int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * ChargeUtil.get(stack) / maxCharge());
    }

    default int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb(Math.max(0.0F, (float) ChargeUtil.get(stack) / maxCharge()) / 3.0F, 1.0F, 1.0F);
    }

    default void appendChargeTooltip(ItemStack stack, Consumer<Component> builder) {
        int charge = ChargeUtil.get(stack);
        int max = maxCharge();

        Component chargeText = Component.literal(String.valueOf(charge)).withStyle(chargeColor(charge, max));
        Component maxChargeText = Component.literal(String.valueOf(max)).withStyle(ChatFormatting.DARK_GREEN);

        builder.accept(Component.literal(" "));
        builder.accept(Component.translatable("item.aetherium.charged_tool.charge", chargeText, maxChargeText));
    }

    private ChatFormatting chargeColor(int charge, int maxCharge) {
        if (charge <= 0) {
            return ChatFormatting.DARK_RED;
        }
        if (charge == maxCharge) {
            return ChatFormatting.DARK_GREEN;
        }

        double ratio = (double) charge / maxCharge;
        if (ratio > 2.0 / 3.0) {
            return ChatFormatting.GREEN;
        } else if (ratio > 1.0 / 3.0) {
            return ChatFormatting.YELLOW;
        } else {
            return ChatFormatting.RED;
        }
    }
}
