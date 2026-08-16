package org.mythic_goose.aetherium.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.init.AetheriumBlocks;

import java.util.function.Consumer;

public class CapsuleItem extends Item {

    public static final int MAX_CHARGE = 5;

    public CapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = context.getItemInHand();
        int charge = stack.getOrDefault(ModDataComponents.CHARGE, 0) + 1;

        level.playSound(null, context.getClickedPos(), SoundEvents.AMETHYST_BLOCK_CHIME,
                SoundSource.PLAYERS, 1.0F, 1.0F + charge * 0.05F);

        Player player = context.getPlayer();

        if (charge >= MAX_CHARGE) {
            ItemStack fullCapsule = null;

            if (level.getBlockState(context.getClickedPos()).is(Blocks.BEDROCK)) {
                 fullCapsule = new ItemStack(CapsuleType.VOIDMASS.getFullCapsuleItem());
                stack.shrink(1);
            }
            if (level.getBlockState(context.getClickedPos()).is(AetheriumBlocks.CRACKED_END_STONE)) {
                fullCapsule = new ItemStack(CapsuleType.ASTRAL.getFullCapsuleItem());
                stack.shrink(1);
            }

            if (player != null) {
                assert fullCapsule != null;
                if (!player.getInventory().add(fullCapsule)) {
                    player.drop(fullCapsule, false);
                }
            }
        } else {
            stack.set(ModDataComponents.CHARGE, charge);
        }

        return InteractionResult.SUCCESS;
    }

    // --- Tooltip ---

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int charge = itemStack.getOrDefault(ModDataComponents.CHARGE, 0);
        builder.accept(Component.translatable("item.aetherium.capsule.charge", charge, MAX_CHARGE)
                .withStyle(ChatFormatting.GRAY));
    }


    // --- Durability-style bar ---

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CHARGE, 0) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int charge = stack.getOrDefault(ModDataComponents.CHARGE, 0);
        // 13 px is the vanilla full-bar width
        return Math.round(13.0F * charge / MAX_CHARGE);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int charge = stack.getOrDefault(ModDataComponents.CHARGE, 0);
        float frac = (float) charge / MAX_CHARGE;
        // green (full) fading down to a dim purple/void tone as it fills — tweak to taste
        int r = (int) (0x8A + (0x2E - 0x8A) * frac);
        int g = (int) (0x2B + (0xE0 - 0x2B) * frac);
        int b = (int) (0xB8 + (0x3B - 0xB8) * frac);
        return (r << 16) | (g << 8) | b;
    }
}