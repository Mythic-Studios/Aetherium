package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.tools.*;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.food.AetheriumFoodProperties;
import org.mythic_goose.aetherium.item.*;
import org.mythic_goose.aetherium.registry.ItemRegistry;

public class AetheriumItems extends ItemRegistry {

    public static Item AETHERIUM_CHARGED_AMETHYST;
    public static Item TOME_OF_ARCANA;

    public static Item AETHERIUM_DUST;
    public static Item AETHERIUM_CRYSTAL;
    public static Item AETHERIUM_INGOT;

    public static Item COMPRESSED_VOIDMASS;
    public static Item VOID_BERRY;

    public static Item VOIDMASS_AXE;
    public static Item VOIDMASS_PICKAXE;
    public static Item VOIDMASS_SHOVEL;
    public static Item VOIDMASS_SPEAR;
    public static Item VOIDMASS_SWORD;
    public static Item VOIDMASS_HOE;
    public static Item VOIDMASS_CHISEL;

    public static Item VOIDMASS_HELMET;
    public static Item VOIDMASS_CHESTPLATE;
    public static Item VOIDMASS_LEGGINGS;
    public static Item VOIDMASS_BOOTS;

    public static Item EMPTY_CAPSULE;
    public static Item CAPSULE_FRAGMENT;
    public static Item FULL_CAPSULE_VOIDMASS;
    public static Item FULL_CAPSULE_ASTRAL;

    public static Item VOIDMASS_UPGRADE_TEMPLATE;
    public static Item ASTRAL_UPGRADE_TEMPLATE;

    public static Item CLOCK_OF_MATTER;

    public static Item ASTRAL_KEY;
    public static Item ASTRAL_FRAGMENTS;
    public static Item ASTRAL_SHARD;
    public static Item ASTRAL_STAR;

    public static BlockItem ARCANE_STATION;
    public static BlockItem AETHERIUM_BLOCK;
    public static BlockItem COMPRESS_AETHERIUM_BLOCK;
    public static BlockItem VOIDMASS_BLOCK;

    public static BlockItem CRYSTALLIZED_DIRT;
    public static BlockItem CRYSTALLIZED_GRASS_BLOCK;

    public static BlockItem CRACKED_END_STONE;
    public static BlockItem ENERGY_TRANSFORMER;

    public static BlockItem ASTRAL_BRICKS;
    public static BlockItem ASTRAL_DOOR;
    public static BlockItem ASTRAL_LAMP;

    public static BlockItem SUMMONING_STONE;
    public static BlockItem ULTIMATIUM_STONE;

    public static Item ANTIMATTER_DISC;
    public static Item SUMMONING_GEM;

    public static BlockItem CAVERN_ROCK;
    public static BlockItem CAVERN_ROCK_ASTRAL_ORE;
    public static BlockItem ROTTING_DIRT;

    public static void init() {
        registerVoidMass();
        registerAetherium();
        registerCapsules();
        registerAstral();
        registerAntimatter();

        TOME_OF_ARCANA = registerItem("tome_of_arcana", properties -> new ItemWithTooltip(properties.stacksTo(1).rarity(Rarity.EPIC), "tooltip.arcana_tome")); // Only for Creative (May add a long process for it thou)

        ARCANE_STATION = registerBlockItem("arcane_station", AetheriumBlocks.ARCANE_STATION);

        SUMMONING_GEM = registerItem("summoning_gem", properties ->  new ItemWithTooltip(properties.stacksTo(6), "toolip.boss_defences.astralex"));

        CAVERN_ROCK = registerBlockItem("cavern_rock", AetheriumBlocks.CAVERN_ROCK);
        CAVERN_ROCK_ASTRAL_ORE = registerBlockItem("cavern_rock_astral_ore", AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE);
        ROTTING_DIRT = registerBlockItem("rotting_dirt", AetheriumBlocks.ROTTING_DIRT);

        CRYSTALLIZED_DIRT = registerBlockItem("crystallized_dirt", AetheriumBlocks.CRYSTALLIZED_DIRT);
        CRYSTALLIZED_GRASS_BLOCK = registerBlockItem("crystallized_grass_block", AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK);

        CRACKED_END_STONE = registerBlockItem("cracked_end_stone", AetheriumBlocks.CRACKED_END_STONE);
        ENERGY_TRANSFORMER = registerBlockItem("energy_transformer", AetheriumBlocks.ENERGY_TRANSFORMER);

        ASTRAL_BRICKS = registerBlockItem("astral_bricks", AetheriumBlocks.ASTRAL_BRICKS);
        ASTRAL_DOOR = registerBlockItem("astral_door", AetheriumBlocks.ASTRAL_DOOR);
        ASTRAL_LAMP = registerBlockItem("astral_lamp", AetheriumBlocks.ASTRAL_LAMP);

        SUMMONING_STONE = registerBlockItem("summoning_stone", AetheriumBlocks.SUMMONING_STONE);
        ULTIMATIUM_STONE = registerBlockItem("ultimatium_stone", AetheriumBlocks.ULTIMATIUM_STONE);
    }

    public static void registerAstral() {
        ASTRAL_FRAGMENTS = registerItem("astral_fragments", Item::new);
        ASTRAL_SHARD = registerItem("astral_shard", Item::new);
        ASTRAL_UPGRADE_TEMPLATE = registerItem("astral_upgrade_template", Item::new);

        ASTRAL_KEY = registerItem("astral_key", Item::new);

        ASTRAL_STAR = registerItem("astral_star", properties -> new Item(properties){
            @Override
            public boolean isFoil(ItemStack itemStack) {
                return true;
            }
        });
    }

    public static void registerCapsules() {
        CAPSULE_FRAGMENT = registerItem("capsule_fragment", Item::new);
        EMPTY_CAPSULE = registerItem("empty_capsule", CapsuleItem::new);
        FULL_CAPSULE_VOIDMASS = registerItem("full_capsule_voidmass",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT), CapsuleType.VOIDMASS));
        FULL_CAPSULE_ASTRAL = registerItem("full_capsule_astral",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT), CapsuleType.ASTRAL));
    }

    public static void registerAetherium() {
        AETHERIUM_CHARGED_AMETHYST = registerItem("aetherium_charged_amethyst", Item::new);

        AETHERIUM_DUST = registerItem("aetherium_dust", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_CRYSTAL = registerItem("aetherium_crystal", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_INGOT = registerItem("aetherium_ingot", properties -> new Item(properties.stacksTo(1000)));

        AETHERIUM_BLOCK = registerBlockItemWithCustomStackSize("aetherium_block", AetheriumBlocks.AETHERIUM_BLOCK, 1000);

        COMPRESS_AETHERIUM_BLOCK = registerBlockItemWithCustomStackSize("compressed_aetherium_block", AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK, 1000);
    }

    public static void registerAntimatter() {
        ANTIMATTER_DISC = registerItem("antimatter_disc", DungeonLocatorItem::new);
    }

    public static void registerVoidMass() {
        COMPRESSED_VOIDMASS = registerItem("compressed_voidmass", Item::new);
        VOIDMASS_UPGRADE_TEMPLATE = registerItem("voidmass_upgrade_template", Item::new);

        CLOCK_OF_MATTER = registerItem("clock_of_matter", properties ->
                new ClockOfMatterItem(properties.fireResistant().stacksTo(1).rarity(Rarity.RARE)));

        VOID_BERRY = registerItem("void_berry", properties -> new BlockItem(AetheriumBlocks.VOID_BERRY_BUSH, properties
                .food(AetheriumFoodProperties.VOID_BERRIES, AetheriumFoodProperties.VOID_BERRIES_EFFECT)));

        VOIDMASS_CHISEL = registerItem("voidmass_chisel", properties -> new ChargedChiselItem(
                ChargedToolMaterial.VOIDMASS,
                properties.stacksTo(1).rarity(Rarity.RARE).fireResistant()
        ));

        VOIDMASS_HELMET = registerItem("voidmass_helmet", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.VOIDMASS,
                ChargedArmorMaterial.VOIDMASS.applyArmorProperties(properties, ArmorType.HELMET)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        VOIDMASS_CHESTPLATE = registerItem("voidmass_chestplate", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.VOIDMASS,
                ChargedArmorMaterial.VOIDMASS.applyArmorProperties(properties, ArmorType.CHESTPLATE)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        VOIDMASS_LEGGINGS = registerItem("voidmass_leggings", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.VOIDMASS,
                ChargedArmorMaterial.VOIDMASS.applyArmorProperties(properties, ArmorType.LEGGINGS)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        VOIDMASS_BOOTS = registerItem("voidmass_boots", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.VOIDMASS,
                ChargedArmorMaterial.VOIDMASS.applyArmorProperties(properties, ArmorType.BOOTS)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));


        VOIDMASS_AXE = registerItem("voidmass_axe", properties -> new ChargedAxeItem(
                ChargedToolMaterial.VOIDMASS, 6f, 8f,
                properties.fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_SHOVEL = registerItem("voidmass_shovel", properties -> new ChargedShovelItem(
                ChargedToolMaterial.VOIDMASS, 6f, -2.8f,
                properties.fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_HOE = registerItem("voidmass_hoe", properties -> new ChargedHoeItem(
                ChargedToolMaterial.VOIDMASS, 6f, -2.8f,
                properties.fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_PICKAXE = registerItem("voidmass_pickaxe", properties -> new ChargedToolItem(
                ChargedToolMaterial.VOIDMASS,
                ChargedToolMaterial.VOIDMASS.applyToolProperties(properties, BlockTags.MINEABLE_WITH_PICKAXE, 2.8f, 6f, 0f)
                        .fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_SWORD = registerItem("voidmass_sword", properties -> new ChargedToolItem(
                ChargedToolMaterial.VOIDMASS,
                ChargedToolMaterial.VOIDMASS.applySwordProperties(properties, 6f, 12f)
                        .fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_SPEAR = registerItem("voidmass_spear", properties -> new ChargedToolItem(
                ChargedToolMaterial.VOIDMASS,
                ChargedToolMaterial.VOIDMASS.applySpearProperties(properties,
                                1.15F, 2.5F, 0.4F, 2.5F, 9.0F, 5.5F, 5.1F, 8.75F, 4.6F)
                        .fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_BLOCK = registerBlockItem("voidmass_block", AetheriumBlocks.VOIDMASS_BLOCK);
    }

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }
}
