package org.mythic_goose.aetherium;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.init.AetheriumItems;

public class AetheriumJeiPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath("aetherium", "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.SUMMONING_GEM),
                Component.translatable("jei.aetherium.info.summon_gem")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.ANTIMATTER_DISC),
                Component.translatable("jei.aetherium.info.antimatter_disc")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE),
                Component.translatable("jei.aetherium.info.astral_template")
        );
    }
}
