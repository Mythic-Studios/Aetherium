package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.mythic_goose.aetherium.block.*;
import org.mythic_goose.aetherium.registry.BlockRegistry;

public class AetheriumBlocks extends BlockRegistry {
    public static Block ARCANE_STATION;

    public static Block AETHERIUM_BLOCK;
    public static Block VOIDMASS_BLOCK;
    public static Block COMPRESSED_AETHERIUM_BLOCK;
    public static Block VOID_BERRY_BUSH;

    public static Block CRACKED_END_STONE;

    public static Block CRYSTALLIZED_DIRT;
    public static Block CRYSTALLIZED_GRASS_BLOCK;

    public static Block ENERGY_TRANSFORMER;
    public static Block ASTRAL_BRICKS;
    public static Block ASTRAL_DOOR;

    public static Block SUMMONING_STONE;
    public static Block ULTIMATIUM_STONE;

    public static Block ASTRAL_LAMP;

    public static Block CAVERN_ROCK;
    public static Block CAVERN_ROCK_ASTRAL_ORE;
    public static Block ROTTING_DIRT;

    public static void init() {

        CAVERN_ROCK = register("cavern_rock", properties ->
                new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));

        CAVERN_ROCK_ASTRAL_ORE = register("cavern_rock_astral_ore", properties ->
                new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));

        ROTTING_DIRT = register("rotting_dirt", properties ->
                new Block(properties.mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.5F)));


        ARCANE_STATION = register("arcane_station", properties -> new ArcaneStationBlock(properties.mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 1200.0F).lightLevel((_) -> 7)));

        AETHERIUM_BLOCK = register("aetherium_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        COMPRESSED_AETHERIUM_BLOCK = register("compressed_aetherium_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOIDMASS_BLOCK = register("voidmass_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOID_BERRY_BUSH = register("void_berry_bush", properties -> new VoidBerryBushBlock(properties.randomTicks().instabreak()
                .sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY).noCollision()));

        CRACKED_END_STONE = register("cracked_end_stone", properties -> new CrackedEndstoneBlock(properties
                .mapColor(MapColor.COLOR_PINK).sound(SoundType.STONE).strength(1.5f, 100f)));

        CRYSTALLIZED_DIRT = register("crystallized_dirt", properties -> new Block(properties.mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.5F)));
        CRYSTALLIZED_GRASS_BLOCK = register("crystallized_grass_block", properties ->
                new SpreadingBlock(properties.mapColor(MapColor.COLOR_PURPLE).randomTicks()
                        .sound(SoundType.GRASS).strength(0.6F), getRK(CRYSTALLIZED_DIRT)));

        ENERGY_TRANSFORMER = register("energy_transformer", properties -> new EnergyTransformerBlock(properties.requiresCorrectToolForDrops().destroyTime(2.0F)));

        ASTRAL_BRICKS = register("astral_bricks", properties -> new Block(properties.strength(99f, 9999f)));
        ASTRAL_DOOR = register("astral_door", properties -> new AstralDoorBlock(BlockSetType.IRON, properties.strength(99f, 9999f)));


        SUMMONING_STONE = register("summoning_stone", properties -> new SummoningStoneBlock(properties.strength(99f, 9999f).noOcclusion(), "PhaseOne"));
        ULTIMATIUM_STONE = register("ultimatium_stone", properties -> new SummoningStoneBlock(properties.strength(99f, 9999f).noOcclusion(), "PhaseTwo"));

        ASTRAL_LAMP = register("astral_lamp", properties -> new Block(properties.strength(99f, 9999f).lightLevel(blockState -> 15)));
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }
}
