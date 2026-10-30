package org.mythic_goose.aetherium.api.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.energy_system.energy.ChargeUtil;
import org.mythic_goose.aetherium.api.energy_system.energy.ChargedTool;
import org.mythic_goose.aetherium.api.energy_system.energy.Rechargeable;
import org.mythic_goose.aetherium.component.ModDataComponents;

import java.util.List;
import java.util.function.Consumer;

public class ChargedBowItem extends BowItem implements ChargedTool, Rechargeable {

    private final ChargedToolMaterial material;

    private final float powerMulti;
    private final float uncertainty;
    private final float powerForCrit;

    public ChargedBowItem(ChargedToolMaterial material, float powerMulti,  float uncertainty, float powerForCrit, Properties properties) {
        super(properties.component(ModDataComponents.TOOL_CHARGE.get(), 0).stacksTo(1));
        this.material = material;
        this.powerMulti = powerMulti;
        this.uncertainty = uncertainty;
        this.powerForCrit = powerForCrit;
    }

    @Override
    public int getCurrentCharge(ItemStack stack) {
        return ChargeUtil.get(stack);
    }

    @Override
    public void setCurrentCharge(ItemStack stack, int amount) {
        stack.set(ModDataComponents.TOOL_CHARGE.get(), Mth.clamp(amount, 0, maxCharge()));
    }

    @Override public int maxCharge() { return material.maxCharge(); }
    @Override public int chargePerUse() { return material.chargePerUse(); }
    @Override public int rechargeSegmentSize() { return material.rechargeSegmentSize(); }
    @Override public Aeth rechargeSegmentCost() { return material.rechargeSegmentCost(); }

    @Override public boolean isBarVisible(ItemStack stack) {
        return ChargedTool.super.isBarVisible(stack);
    }
    @Override public int getBarWidth(ItemStack stack) {
        return ChargedTool.super.getBarWidth(stack);
    }
    @Override public int getBarColor(ItemStack stack) {
        return ChargedTool.super.getBarColor(stack);
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        if (entity instanceof Player player) {
            ItemStack projectile = player.getProjectile(itemStack);

            if (projectile.isEmpty()) {
                projectile = new ItemStack(net.minecraft.world.item.Items.ARROW);
            }

            // Require enough charge to fire.
            if (getCurrentCharge(itemStack) < chargePerUse()) {
                if (level.isClientSide()) {
                    level.playSound((Entity) null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.3F, 1.0F);
                }
                return false;
            }

            int timeHeld = this.getUseDuration(itemStack, entity) - remainingTime;
            float pow = getPowerForTime(timeHeld);
            if ((double) pow < 0.1) {
                return false;
            } else {
                List<ItemStack> firedProjectiles = draw(itemStack, projectile, player);
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    if (!firedProjectiles.isEmpty()) {
                        this.shoot(serverLevel, player, player.getUsedItemHand(), itemStack, firedProjectiles, pow * powerMulti, uncertainty, pow == powerForCrit, (LivingEntity) null);
                    }
                }

                level.playSound((Entity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + pow * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
                drainSeparate(itemStack);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // Block drawing entirely if there's not enough charge.
        if (getCurrentCharge(itemStack) < chargePerUse()) {
            if (level.isClientSide()) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.3F, 1.0F);
            }
            return InteractionResult.FAIL;
        }

        boolean hasAmmo = !player.getProjectile(itemStack).isEmpty();

        InteractionResult result = super.use(level, player, hand);

        // If super.use() failed for ammo reasons but we're allowing ammo-less firing,
        // force success so the draw still starts.
        if (result != InteractionResult.SUCCESS && !hasAmmo) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }

        return result;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        appendChargeTooltip(itemStack, builder);
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
