package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.mythic_goose.aetherium.init.AetheriumItems;

public class CrackedEndstoneBlock extends Block {
    public CrackedEndstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (itemStack.is(Items.NETHER_STAR)) {
            itemStack.shrink(1);
            player.addItem(new ItemStack(AetheriumItems.ASTRAL_STAR));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
