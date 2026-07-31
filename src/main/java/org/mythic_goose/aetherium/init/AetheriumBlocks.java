package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.mythic_goose.aetherium.block.ArcaneStationBlock;
import org.mythic_goose.aetherium.block.VoidBerryBushBlock;
import org.mythic_goose.aetherium.registry.BlockRegistry;

public class AetheriumBlocks extends BlockRegistry {
    public static Block ARCANE_STATION;

    public static Block AETHERIUM_BLOCK;
    public static Block VOIDMASS_BLOCK;
    public static Block VOID_BERRY_BUSH;

    public static void init() {

        ARCANE_STATION = register("arcane_station", properties -> new ArcaneStationBlock(properties.mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 1200.0F).lightLevel((_) -> 7)));

        AETHERIUM_BLOCK = register("aetherium_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOIDMASS_BLOCK = register("voidmass_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOID_BERRY_BUSH = register("void_berry_bush", properties -> new VoidBerryBushBlock(properties.randomTicks().instabreak()
                .sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY).noCollision()));
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }
}
