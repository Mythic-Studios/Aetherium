package org.mythic_goose.aetherium.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.creative_tab.ModCreativeTabs;
import org.mythic_goose.aetherium.creative_tab.TabLayout;
import org.mythic_goose.aetherium.mixin.accessor.CreativeModeTabAccessor;
import org.spongepowered.asm.mixin.Mixin;

import java.util.LinkedHashSet;
import java.util.List;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {

    @WrapMethod(method = "buildContents")
    private void msgwoft$buildContents(CreativeModeTab.ItemDisplayParameters parameters, Operation<Void> original) {
        CreativeModeTab self = (CreativeModeTab)(Object) this;
        if (self != ModCreativeTabs.CORE) {
            original.call(parameters);
            return;
        }

        // Build ItemStacks HERE, lazily, when components are guaranteed bound
        List<ItemStack> display = TabLayout.build();

        ((CreativeModeTabAccessor) self).setDisplayItems(display);
        ((CreativeModeTabAccessor) self).setDisplayItemsSearchTab(
                display.stream()
                        .filter(s -> !s.isEmpty())
                        .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new))
        );
    }
}