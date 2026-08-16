package org.mythic_goose.aetherium.api.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargeUtil;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargedTool;
import org.mythic_goose.aetherium.api.energy_system.tools.Rechargeable;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.init.AetheriumBlocks;

import java.util.Map;
import java.util.function.Consumer;

public class ChargedChiselItem extends Item implements ChargedTool, Rechargeable {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.END_STONE, AetheriumBlocks.CRACKED_END_STONE,
                    Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST
            );

    private final ChargedToolMaterial material;

    public ChargedChiselItem(ChargedToolMaterial material, Properties properties) {
        super(properties.component(ModDataComponents.TOOL_CHARGE, 0));
        this.material = material;
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

    @Override
    public int getCurrentCharge(ItemStack stack) {
        return ChargeUtil.get(stack);
    }

    @Override public int maxCharge() { return 3; }
    @Override public int chargePerUse() { return 1; }
    @Override public int rechargeSegmentSize() { return 1; }
    @Override public Aeth rechargeSegmentCost() { return Aeth.ofUnits(1000); }

    @Override public boolean isBarVisible(ItemStack stack) {
        return ChargedTool.super.isBarVisible(stack);
    }
    @Override public int getBarWidth(ItemStack stack) {
        return ChargedTool.super.getBarWidth(stack);
    }
    @Override public int getBarColor(ItemStack stack) {
        return ChargedTool.super.getBarColor(stack);
    }

    /**
     * MIT LICENSE
     * Altered code from Kaupenjoe (Fabric Tutorial 26.X) - <a href="https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-26.X/blob/main/src/main/java/net/kaupenjoe/tutorialmod/item/custom/ChiselItem.java">...</a>
     *
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Right Click Block
        // Change Block from A to B...

        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (hasCharge(context.getItemInHand())) {
            if(CHISEL_MAP.containsKey(clickedBlock) && !level.isClientSide()) {
                // We are on the Server!
                level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState());
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand());
                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 2.0F,
                        0.8F + level.getRandom().nextFloat() * 0.4F);

                // drains on use
                drainSeparate(context.getItemInHand());
            }
        }

        return InteractionResult.SUCCESS;
    }
}
