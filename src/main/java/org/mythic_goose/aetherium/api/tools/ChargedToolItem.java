package org.mythic_goose.aetherium.api.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargeUtil;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargedTool;
import org.mythic_goose.aetherium.api.energy_system.tools.Rechargeable;
import org.mythic_goose.aetherium.component.ModDataComponents;

import java.util.function.Consumer;

public class ChargedToolItem extends Item implements ChargedTool, Rechargeable {

    private final ChargedToolMaterial material;

    public ChargedToolItem(ChargedToolMaterial material, Properties properties) {
        super(properties.component(ModDataComponents.TOOL_CHARGE, 0));
        this.material = material;
    }

    @Override
    public int getCurrentCharge(ItemStack stack) {
        return ChargeUtil.get(stack);
    }

    @Override
    public void setCurrentCharge(ItemStack stack, int amount) {
        stack.set(ModDataComponents.TOOL_CHARGE, Mth.clamp(amount, 0, maxCharge()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        appendChargeTooltip(itemStack, builder);
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    @Override public int maxCharge() { return material.maxCharge(); }
    @Override public int chargePerUse() { return material.chargePerUse(); }
    @Override public int rechargeSegmentSize() { return material.rechargeSegmentSize(); }
    @Override public Aeth rechargeSegmentCost() { return material.rechargeSegmentCost(); }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return chargedIsCorrectToolForDrops(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return chargedDestroySpeed(stack, super.getDestroySpeed(stack, state));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        onMine(stack, level, state, pos);
        return super.mineBlock(stack, level, state, pos, miner);
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        onHurtEnemy(itemStack);
        super.hurtEnemy(itemStack, mob, attacker);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return ChargedTool.super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return ChargedTool.super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ChargedTool.super.getBarColor(stack);
    }

}