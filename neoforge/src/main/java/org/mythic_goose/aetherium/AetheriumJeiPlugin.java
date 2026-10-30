package org.mythic_goose.aetherium;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@JeiPlugin
public class AetheriumJeiPlugin implements IModPlugin {
    @Override
    public @NonNull Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath("aetherium", "jei_plugin");
    }

    @Override
    public void registerRecipes(@NonNull IRecipeRegistration registration) {
        JeiPluginGlobal.registerInfo(registration);
    }
}
