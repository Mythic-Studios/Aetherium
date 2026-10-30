package org.mythic_goose.aetherium.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {

        AetheriumConstants.LOG.info("This line is printed by an example mod common mixin!");
        AetheriumConstants.LOG.info("MC Version: {}", SharedConstants.getCurrentVersion().name());
    }
}