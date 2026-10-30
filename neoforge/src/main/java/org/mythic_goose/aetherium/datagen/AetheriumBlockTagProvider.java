package org.mythic_goose.aetherium.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.util.AetheriumBlockTags;
import org.mythic_goose.aetherium.util.RKFinder;

import java.util.concurrent.CompletableFuture;

public class AetheriumBlockTagProvider extends BlockTagsProvider {
    public AetheriumBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, AetheriumConstants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        tag(AetheriumBlockTags.SPEEDY_BLOCKS)
                .add(BlockItemIds.SAND.block())
                .add(BlockItemIds.CAKE.block())
                .add(BlockItemIds.CONCRETE.white().block())
                .add(BlockItemIds.CONCRETE_POWDER.white().block())
                .add(BlockItemIds.DYED_TERRACOTTA.white().block())
                .add(BlockItemIds.GLAZED_TERRACOTTA.white().block())
                .add(BlockItemIds.WOOL.white().block())
                .add(BlockItemIds.DYED_CANDLE.white().block())
        ;

        tag(BlockTags.VALID_SPAWN)
                .add(RKFinder.getRK(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get()))
                .add(RKFinder.getRK(AetheriumBlocks.CRYSTALLIZED_DIRT.get()))
        ;

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.VOIDMASS_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ARCANE_STATION.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.AETHERIUM_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ENERGY_TRANSFORMER.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CAVERN_ROCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get()))
        ;

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CRYSTALLIZED_DIRT.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ROTTING_DIRT.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.FERTILE_DIRT.get()))
        ;

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.VOIDMASS_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ARCANE_STATION.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.AETHERIUM_BLOCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get()))
        ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CAVERN_ROCK.get()))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get()))
        ;
    }
}
