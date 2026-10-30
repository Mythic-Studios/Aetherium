package org.mythic_goose.aetherium.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;
import org.mythic_goose.aetherium.util.RKFinder;

import java.util.concurrent.CompletableFuture;

public class AetheriumItemTagProvider extends ItemTagsProvider {


    public AetheriumItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, AetheriumConstants.MOD_ID);
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
                .add(RKFinder.getRK(AetheriumItems.COMPRESSED_VOIDMASS.get()));

        tag(AetheriumItemTags.REPAIRS_ASTRAL)
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_SHARD.get()));

        tag(AetheriumItemTags.REFILLS_FERTILIZER_WEAK)
                .add(ItemIds.BONE_MEAL)
                .add(ItemIds.ROTTEN_FLESH)
        ;

        tag(AetheriumItemTags.REFILLS_FERTILIZER_STRONG)
                .add(RKFinder.getRK(AetheriumItems.VOID_BERRY.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_FRUIT.get()))
                .add(RKFinder.getRK(AetheriumItems.FERTILE_BONE_MEAL.get()))
        ;

        tag(ItemTags.AXES)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_AXE.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_AXE.get()))
        ;
        tag(ItemTags.PICKAXES)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_PICKAXE.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_PICKAXE.get()))
        ;
        tag(ItemTags.SWORDS)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_SWORD.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_SWORD.get()))
        ;
        tag(ItemTags.SPEARS)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_SPEAR.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_SPEAR.get()))
        ;
        tag(ItemTags.SHOVELS)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_SHOVEL.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_SHOVEL.get()))
        ;
        tag(ItemTags.HOES)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_HOE.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_HOE.get()))
        ;

        tag(ItemTags.HEAD_ARMOR)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_HELMET.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_HELMET.get()))
        ;
        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_HELMET.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_HELMET.get()))
        ;

        tag(ItemTags.CHEST_ARMOR)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_CHESTPLATE.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_CHESTPLATE.get()))
        ;
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_CHESTPLATE.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_CHESTPLATE.get()))
        ;

        tag(ItemTags.LEG_ARMOR)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_LEGGINGS.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_LEGGINGS.get()))
        ;
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_LEGGINGS.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_LEGGINGS.get()))
        ;

        tag(ItemTags.FOOT_ARMOR)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_BOOTS.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_BOOTS.get()))
        ;
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_BOOTS.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_BOOTS.get()))
        ;

        tag(ItemTags.BOW_ENCHANTABLE)
                .add(RKFinder.getRK(AetheriumItems.VOIDMASS_BOW.get()))
                .add(RKFinder.getRK(AetheriumItems.ASTRAL_BOW.get()))
        ;
    }
}
