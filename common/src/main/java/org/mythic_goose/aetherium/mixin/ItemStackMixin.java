package org.mythic_goose.aetherium.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"
            )
    )
    private static Codec<Integer> aetherium$raiseItemStackCountCap(int min, int max) {
        if (min == 1 && max == 99) {
            max = 9999;
        }
        return ExtraCodecs.intRange(min, max);
    }
}
