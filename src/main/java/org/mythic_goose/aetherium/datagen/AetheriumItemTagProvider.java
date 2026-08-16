package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

import java.util.concurrent.CompletableFuture;

public class AetheriumItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public AetheriumItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
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
                .add(AetheriumItems.getRK(AetheriumItems.COMPRESSED_VOIDMASS));


        tag(ItemTags.AXES)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_AXE))
        ;
        tag(ItemTags.PICKAXES)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_PICKAXE))
        ;
        tag(ItemTags.SWORDS)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_SWORD))
        ;
        tag(ItemTags.SPEARS)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_SPEAR))
        ;
        tag(ItemTags.SHOVELS)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_SHOVEL))
        ;
        tag(ItemTags.HOES)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_HOE))
        ;

        tag(ItemTags.HEAD_ARMOR)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_HELMET))
        ;
        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_HELMET))
        ;

        tag(ItemTags.CHEST_ARMOR)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_CHESTPLATE))
        ;
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_CHESTPLATE))
        ;

        tag(ItemTags.LEG_ARMOR)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_LEGGINGS))
        ;
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_LEGGINGS))
        ;

        tag(ItemTags.FOOT_ARMOR)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_BOOTS))
        ;
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(AetheriumItems.getRK(AetheriumItems.VOIDMASS_BOOTS))
        ;
    }
}
