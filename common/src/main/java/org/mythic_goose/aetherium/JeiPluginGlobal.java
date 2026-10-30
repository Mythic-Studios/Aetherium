package org.mythic_goose.aetherium;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.init.AetheriumItems;

public class JeiPluginGlobal {
    public static void registerInfo(IRecipeRegistration registration) {
        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.SUMMONING_GEM.get()),
                Component.translatable("jei.aetherium.info.summon_gem")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.ANTIMATTER_DISC.get()),
                Component.translatable("jei.aetherium.info.antimatter_disc")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get()),
                Component.translatable("jei.aetherium.info.astral_template")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.FERTILIZER.get()),
                Component.translatable("jei.aetherium.info.fertilizer")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.FERTILE_BONE_MEAL.get()),
                Component.translatable("jei.aetherium.info.bonemeal")
        );

        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.AETHERIUM_SEEDS.get()),
                Component.translatable("jei.aetherium.info.aeth_seeds")
        );
        registration.addItemStackInfo(
                new ItemStack(AetheriumItems.ENERGY_ORB.get()),
                Component.translatable("jei.aetherium.info.energy_orb")
        );
    }
}
