package org.mythic_goose.aetherium.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.init.ModItems;

public class FullCapsuleItem extends Item {

    private final CapsuleType type;

    public FullCapsuleItem(Properties properties, CapsuleType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack result = type.rollResult(level.getRandom());

        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        stack.shrink(1);

        return InteractionResult.SUCCESS;
    }
}