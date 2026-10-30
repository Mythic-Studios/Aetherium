package org.mythic_goose.aetherium.mixin;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Attributes.class)
public class AttributesMixin {

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/entity/ai/attributes/RangedAttribute"
            ),
            allow = 40 // there are ~30+ RangedAttribute constructions in <clinit>, this lets the redirect apply to all of them
    )
    private static RangedAttribute aetherium$redirectRangedAttribute(String name, double defaultValue, double min, double max) {
        if ("attribute.name.max_health".equals(name)) {
            // bump the cap well above 1024
            return new RangedAttribute(name, defaultValue, min, 10000.0);
        }
        return new RangedAttribute(name, defaultValue, min, max);
    }
}