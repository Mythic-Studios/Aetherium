package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.ModItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        tag(AetheriumItemTags.CHISELED_STONE_ITEMS)
                .add(BlockItemIds.CHISELED_CINNABAR.item())
                .add(BlockItemIds.CHISELED_RED_SANDSTONE.item())
                .add(BlockItemIds.CHISELED_SANDSTONE.item())
                .add(BlockItemIds.CHISELED_SULFUR.item())
                .add(BlockItemIds.CHISELED_DEEPSLATE.item())
                .add(BlockItemIds.CHISELED_TUFF.item())
        ;

        tag(AetheriumItemTags.STONES_SLABS_ITEMS)
                .add(BlockItemIds.STONE_SLAB.item())
                .add(BlockItemIds.SULFUR_SLAB.item())
                .add(BlockItemIds.CINNABAR_SLAB.item())
                .add(BlockItemIds.COBBLED_DEEPSLATE_SLAB.item())
                .add(BlockItemIds.SANDSTONE_SLAB.item())
                .add(BlockItemIds.RED_SANDSTONE_SLAB.item())
        ;

        tag(AetheriumItemTags.FROM_SMOOTH_ITEMS)
                .add(BlockItemIds.TUFF_BRICKS.item())
                .add(BlockItemIds.DEEPSLATE_BRICKS.item())
        ;

        tag(AetheriumItemTags.SMOOTH_STONE_ITEMS)
                .add(BlockItemIds.POLISHED_TUFF.item())
                .add(BlockItemIds.POLISHED_ANDESITE.item())
                .add(BlockItemIds.POLISHED_ANDESITE.item())
                .add(BlockItemIds.POLISHED_BLACKSTONE.item())
        ;

        tag(AetheriumItemTags.BRICKS_SLABS_ITEMS)
                .add(BlockItemIds.STONE_BRICK_SLAB.item())
                .add(BlockItemIds.DEEPSLATE_BRICK_SLAB.item())
                .add(BlockItemIds.TUFF_BRICK_SLAB.item())
        ;

        tag(AetheriumItemTags.REPAIRS_VOIDMASS)
                .add(ModItems.getRK(ModItems.COMPRESSED_VOIDMASS));

        tag(ItemTags.AXES)
                .add(ModItems.getRK(ModItems.VOIDMASS_AXE))
        ;
        tag(ItemTags.PICKAXES)
                .add(ModItems.getRK(ModItems.VOIDMASS_PICKAXE))
        ;
        tag(ItemTags.SWORDS)
                .add(ModItems.getRK(ModItems.VOIDMASS_SWORD))
        ;
        tag(ItemTags.SPEARS)
                .add(ModItems.getRK(ModItems.VOIDMASS_SPEAR))
        ;
        tag(ItemTags.SHOVELS)
                .add(ModItems.getRK(ModItems.VOIDMASS_SHOVEL))
        ;
        tag(ItemTags.HOES)
                .add(ModItems.getRK(ModItems.VOIDMASS_HOE))
        ;
    }
}
