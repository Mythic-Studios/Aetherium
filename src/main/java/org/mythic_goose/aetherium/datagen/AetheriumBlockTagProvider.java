package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.util.AetheriumBlockTags;

import java.util.concurrent.CompletableFuture;

public class AetheriumBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public AetheriumBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        tag(AetheriumBlockTags.SPEEDY_BLOCKS)
                .add(Blocks.SAND.properties().blockIdOrThrow())
                .add(Blocks.CAKE.properties().blockIdOrThrow())
                .add(Blocks.CONCRETE.white().properties().blockIdOrThrow())
                .add(Blocks.CONCRETE_POWDER.white().properties().blockIdOrThrow())
                .add(Blocks.DYED_TERRACOTTA.white().properties().blockIdOrThrow())
                .add(Blocks.GLAZED_TERRACOTTA.white().properties().blockIdOrThrow())
                .add(Blocks.WOOL.white().properties().blockIdOrThrow())
                .add(Blocks.DYED_CANDLE.white().properties().blockIdOrThrow())
                .add(Blocks.DYED_CANDLE_CAKE.white().properties().blockIdOrThrow())
        ;

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.VOIDMASS_BLOCK))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ARCANE_STATION))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.AETHERIUM_BLOCK))
        ;

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(AetheriumBlocks.getRK(AetheriumBlocks.VOIDMASS_BLOCK))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.ARCANE_STATION))
                .add(AetheriumBlocks.getRK(AetheriumBlocks.AETHERIUM_BLOCK))
        ;
    }
}
