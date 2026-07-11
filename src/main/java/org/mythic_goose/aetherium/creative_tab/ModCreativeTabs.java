package org.mythic_goose.aetherium.creative_tab;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.init.ModItems;
import org.mythic_goose.aetherium.init.ModSections;

public class ModCreativeTabs {
    public static CreativeModeTab CORE;

    public static void init() {
        TabLayout.cacheSections(ModSections.build());

        CORE = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "aetherium_tab"),
                FabricCreativeModeTab.builder()
                        .icon(() -> new ItemStack(ModItems.AETHERIUM_CRYSTAL))
                        .title(Component.translatable("itemGroup.aetherium_tab"))
                        .displayItems((params, output) -> {
                            // Intentionally empty — CreativeModeTabMixin overrides buildContents
                        })
                        .build());
    }
}