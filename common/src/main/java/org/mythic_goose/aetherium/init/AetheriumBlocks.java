package org.mythic_goose.aetherium.init;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.block.*;
import org.mythic_goose.amalgamation.api.registry_v1.BlockRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;

public class AetheriumBlocks extends BlockRegistry {
    public static RegistryEntry<Block> ARCANE_STATION;

    public static RegistryEntry<Block> AETHERIUM_BLOCK;
    public static RegistryEntry<Block> VOIDMASS_BLOCK;
    public static RegistryEntry<Block> COMPRESSED_AETHERIUM_BLOCK;
    public static RegistryEntry<Block> VOID_BERRY_BUSH;
    public static RegistryEntry<Block> ENERGY_ORB_BUSH;

    public static RegistryEntry<Block> CRACKED_END_STONE;

    public static RegistryEntry<Block> CRYSTALLIZED_DIRT;
    public static RegistryEntry<Block> CRYSTALLIZED_GRASS_BLOCK;

    public static RegistryEntry<Block> ENERGY_TRANSFORMER;
    public static RegistryEntry<Block> ASTRAL_BRICKS;
    public static RegistryEntry<Block> ASTRAL_DOOR;

    public static RegistryEntry<Block> SUMMONING_STONE;
    public static RegistryEntry<Block> ULTIMATIUM_STONE;

    public static RegistryEntry<Block> ASTRAL_LAMP;

    public static RegistryEntry<Block> CAVERN_ROCK;
    public static RegistryEntry<Block> CAVERN_ROCK_ASTRAL_ORE;
    public static RegistryEntry<Block> ROTTING_DIRT;
    public static RegistryEntry<Block> FERTILE_DIRT;

    public static RegistryEntry<Block> ENERGIZED_GLOWSTONE;

    public static void init() {

        CAVERN_ROCK = registerBlock("cavern_rock", properties ->
                new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));

        CAVERN_ROCK_ASTRAL_ORE = registerBlock("cavern_rock_astral_ore", properties ->
                new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));

        ROTTING_DIRT = registerBlock("rotting_dirt", properties ->
                new Block(properties.mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.5F)));

        FERTILE_DIRT = registerBlock("fertile_dirt", properties ->
                new FertilizedScavanagableBlock(properties.mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.5F),
                        Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "fertilized_scavangables/fertile_dirt"),
                        ROTTING_DIRT.get().defaultBlockState()));


        ARCANE_STATION = registerBlock("arcane_station", properties -> new ArcaneStationBlock(properties.mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 1200.0F).lightLevel((_) -> 7)));

        AETHERIUM_BLOCK = registerBlock("aetherium_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        COMPRESSED_AETHERIUM_BLOCK = registerBlock("compressed_aetherium_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOIDMASS_BLOCK = registerBlock("voidmass_block", properties -> new Block(properties.mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));

        VOID_BERRY_BUSH = registerBlock("void_berry_bush", properties -> new VoidBerryBushBlock(properties.randomTicks().instabreak()
                .sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY).noCollision()));

        ENERGY_ORB_BUSH = registerBlock("energy_orb_bush", properties -> new EnergyOrbBushBlock(properties.randomTicks().instabreak()
                .sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY).noCollision().lightLevel((statex) -> 15)));

        CRACKED_END_STONE = registerBlock("cracked_end_stone", properties -> new CrackedEndstoneBlock(properties
                .mapColor(MapColor.COLOR_PINK).sound(SoundType.STONE).strength(1.5f, 100f)));

        CRYSTALLIZED_DIRT = registerBlock("crystallized_dirt", properties -> new Block(properties.mapColor(MapColor.COLOR_PINK).sound(SoundType.GRASS).strength(0.5F)));
        CRYSTALLIZED_GRASS_BLOCK = registerBlock("crystallized_grass_block", properties ->
                new SpreadingBlock(properties.mapColor(MapColor.COLOR_PURPLE).randomTicks()
                        .sound(SoundType.GRASS).strength(0.6F), getRK(CRYSTALLIZED_DIRT.get())));

        ENERGY_TRANSFORMER = registerBlock("energy_transformer", properties -> new EnergyTransformerBlock(properties.requiresCorrectToolForDrops().destroyTime(2.0F)));

        ASTRAL_BRICKS = registerBlock("astral_bricks", properties -> new Block(properties.strength(99f, 9999f)));
        ASTRAL_DOOR = registerBlock("astral_door", properties -> new AstralDoorBlock(BlockSetType.IRON, properties.strength(99f, 9999f)));


        SUMMONING_STONE = registerBlock("summoning_stone", properties -> new SummoningStoneBlock(properties.strength(99f, 9999f).noOcclusion(), "PhaseOne"));
        ULTIMATIUM_STONE = registerBlock("ultimatium_stone", properties -> new SummoningStoneBlock(properties.strength(99f, 9999f).noOcclusion(), "PhaseTwo"));

        ASTRAL_LAMP = registerBlock("astral_lamp", properties -> new Block(properties.strength(99f, 9999f).lightLevel(blockState -> 15)));

        ENERGIZED_GLOWSTONE = registerBlock("energized_glowstone", properties ->
                new Block(properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.PLING)
                        .strength(0.3F).sound(SoundType.GLASS).lightLevel((statex) -> 15).isRedstoneConductor(Blocks::never)));
    }


    public static Iterable<Block> getRegisteredBlocks() {
        return BLOCKS.all().stream().map(RegistryEntry::get)::iterator;
    }
}
