package org.mythic_goose.aetherium.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screens.TitleScreen;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {

        AetheriumConstants.LOG.info("This line is printed by an example mod mixin from Fabric!");
        AetheriumConstants.LOG.info("MC Version: {}", SharedConstants.getCurrentVersion().name());
    }
}