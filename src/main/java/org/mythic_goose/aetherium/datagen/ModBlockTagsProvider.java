package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.mythic_goose.aetherium.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.VOIDMASS_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.ARCANE_STATION))
                .add(ModBlocks.getRK(ModBlocks.AETHERIUM_BLOCK))
        ;

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.VOIDMASS_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.ARCANE_STATION))
                .add(ModBlocks.getRK(ModBlocks.AETHERIUM_BLOCK))
        ;

    }
}