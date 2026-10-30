package org.mythic_goose.aetherium.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DataComponents.class)
public class DataComponentsMixin {

    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"
            )
    )
    private static Codec<Integer> aetherium$raiseMaxStackSize(int min, int max) {
        if (min == 1 && max == 99) {
            max = 9999; // your new cap
        }
        return ExtraCodecs.intRange(min, max);
    }
}