package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.external.AetheriumGlobals;
import org.mythic_goose.aetherium.api.tools.*;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.food.AetheriumFoodProperties;
import org.mythic_goose.aetherium.item.*;
import org.mythic_goose.amalgamation.api.registry_v1.ItemRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;

public class AetheriumItems extends ItemRegistry {
    public static RegistryEntry<Item> AETHERIUM_CHARGED_AMETHYST;
    public static RegistryEntry<Item> TOME_OF_ARCANA;

    public static RegistryEntry<Item> AETHERIUM_DUST;
    public static RegistryEntry<Item> AETHERIUM_CRYSTAL;
    public static RegistryEntry<Item> AETHERIUM_INGOT;

    public static RegistryEntry<Item> COMPRESSED_VOIDMASS;
    public static RegistryEntry<Item> VOID_BERRY;

    public static RegistryEntry<Item> VOIDMASS_AXE;
    public static RegistryEntry<Item> VOIDMASS_PICKAXE;
    public static RegistryEntry<Item> VOIDMASS_SHOVEL;
    public static RegistryEntry<Item> VOIDMASS_SPEAR;
    public static RegistryEntry<Item> VOIDMASS_SWORD;
    public static RegistryEntry<Item> VOIDMASS_HOE;
    public static RegistryEntry<Item> VOIDMASS_CHISEL;
    public static RegistryEntry<Item> VOIDMASS_BOW;

    public static RegistryEntry<Item> VOIDMASS_HELMET;
    public static RegistryEntry<Item> VOIDMASS_CHESTPLATE;
    public static RegistryEntry<Item> VOIDMASS_LEGGINGS;
    public static RegistryEntry<Item> VOIDMASS_BOOTS;

    public static RegistryEntry<Item> EMPTY_CAPSULE;
    public static RegistryEntry<Item> CAPSULE_FRAGMENT;
    public static RegistryEntry<Item> FULL_CAPSULE_VOIDMASS;
    public static RegistryEntry<Item> FULL_CAPSULE_ASTRAL;

    public static RegistryEntry<Item> VOIDMASS_UPGRADE_TEMPLATE;
    public static RegistryEntry<Item> ASTRAL_UPGRADE_TEMPLATE;

    public static RegistryEntry<Item> CLOCK_OF_MATTER;

    public static RegistryEntry<Item> FERTILE_BONE_MEAL;

    public static RegistryEntry<Item> ASTRAL_KEY;
    public static RegistryEntry<Item> ASTRAL_FRAGMENTS;
    public static RegistryEntry<Item> ASTRAL_SHARD;
    public static RegistryEntry<Item> ASTRAL_STAR;

    public static RegistryEntry<Item> ASTRAL_FRUIT;

    public static RegistryEntry<Item> ENERGY_ORB;
    public static RegistryEntry<Item> AETHERIUM_SEEDS;
    public static RegistryEntry<Item> ENERGY_SEEDS;

    public static RegistryEntry<BlockItem> ARCANE_STATION;
    public static RegistryEntry<BlockItem> AETHERIUM_BLOCK;
    public static RegistryEntry<BlockItem> COMPRESS_AETHERIUM_BLOCK;
    public static RegistryEntry<BlockItem> VOIDMASS_BLOCK;

    public static RegistryEntry<BlockItem> CRYSTALLIZED_DIRT;
    public static RegistryEntry<BlockItem> CRYSTALLIZED_GRASS_BLOCK;

    public static RegistryEntry<BlockItem> CRACKED_END_STONE;
    public static RegistryEntry<BlockItem> ENERGY_TRANSFORMER;

    public static RegistryEntry<BlockItem> ASTRAL_BRICKS;
    public static RegistryEntry<BlockItem> ASTRAL_DOOR;
    public static RegistryEntry<BlockItem> ASTRAL_LAMP;

    public static RegistryEntry<BlockItem> SUMMONING_STONE;
    public static RegistryEntry<BlockItem> ULTIMATIUM_STONE;

    public static RegistryEntry<Item> ANTIMATTER_DISC;
    public static RegistryEntry<Item> SUMMONING_GEM;

    public static RegistryEntry<BlockItem> CAVERN_ROCK;
    public static RegistryEntry<BlockItem> CAVERN_ROCK_ASTRAL_ORE;
    public static RegistryEntry<BlockItem> ROTTING_DIRT;
    public static RegistryEntry<BlockItem> FERTILE_DIRT;

    public static RegistryEntry<Item> ASTRAL_AXE;
    public static RegistryEntry<Item> ASTRAL_PICKAXE;
    public static RegistryEntry<Item> ASTRAL_SHOVEL;
    public static RegistryEntry<Item> ASTRAL_SPEAR;
    public static RegistryEntry<Item> ASTRAL_SWORD;
    public static RegistryEntry<Item> ASTRAL_HOE;
    public static RegistryEntry<Item> ASTRAL_BOW;

    public static RegistryEntry<Item> ASTRAL_HELMET;
    public static RegistryEntry<Item> ASTRAL_CHESTPLATE;
    public static RegistryEntry<Item> ASTRAL_LEGGINGS;
    public static RegistryEntry<Item> ASTRAL_BOOTS;

    public static RegistryEntry<Item> FERTILIZER;

    public static RegistryEntry<Item> CAVERN_HIDE;
    public static RegistryEntry<Item> CAVERN_COW_SPAWN_EGG;

    public static RegistryEntry<BlockItem> ENERGIZED_GLOWSTONE;


    public static RegistryEntry<Item> MEDICINE_TECHNOLOGY_DISC;
    public static final ResourceKey<JukeboxSong> MEDICINE_TECHNOLOGY_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "medicine_technology_kulakovka"));

    public static void init() {
        registerVoidMass();
        registerAetherium();
        registerCapsules();
        registerAstral();
        registerAntimatter();
        registerCavernStuff();

        TOME_OF_ARCANA = registerItem("tome_of_arcana", properties -> new ItemWithTooltip(properties.stacksTo(1).rarity(Rarity.EPIC), "tooltip.arcana_tome")); // Only for Creative (May add a long process for it thou)

        ARCANE_STATION = registerBlockItem("arcane_station", AetheriumBlocks.ARCANE_STATION);

        SUMMONING_GEM = registerItem("summoning_gem", properties ->  new ItemWithTooltip(properties.stacksTo(6), "toolip.boss_defences.astralex"));

        CAVERN_ROCK = registerBlockItem("cavern_rock", AetheriumBlocks.CAVERN_ROCK);
        CAVERN_ROCK_ASTRAL_ORE = registerBlockItem("cavern_rock_astral_ore", AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE);
        ROTTING_DIRT = registerBlockItem("rotting_dirt", AetheriumBlocks.ROTTING_DIRT);
        FERTILE_DIRT = registerBlockItem("fertile_dirt", AetheriumBlocks.FERTILE_DIRT);

        CRYSTALLIZED_DIRT = registerBlockItem("crystallized_dirt", AetheriumBlocks.CRYSTALLIZED_DIRT);
        CRYSTALLIZED_GRASS_BLOCK = registerBlockItem("crystallized_grass_block", AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK);

        CRACKED_END_STONE = registerBlockItem("cracked_end_stone", AetheriumBlocks.CRACKED_END_STONE);
        ENERGY_TRANSFORMER = registerBlockItem("energy_transformer", AetheriumBlocks.ENERGY_TRANSFORMER);

        ASTRAL_BRICKS = registerBlockItem("astral_bricks", AetheriumBlocks.ASTRAL_BRICKS);
        ASTRAL_DOOR = registerBlockItem("astral_door", AetheriumBlocks.ASTRAL_DOOR);
        ASTRAL_LAMP = registerBlockItem("astral_lamp", AetheriumBlocks.ASTRAL_LAMP);

        SUMMONING_STONE = registerBlockItem("summoning_stone", AetheriumBlocks.SUMMONING_STONE);
        ULTIMATIUM_STONE = registerBlockItem("ultimatium_stone", AetheriumBlocks.ULTIMATIUM_STONE);

        FERTILE_BONE_MEAL = registerItem("fertile_bone_meal", FertileBoneMealItem::new);

        FERTILIZER = registerItem("fertilizer", FertilizerItem::new);

        MEDICINE_TECHNOLOGY_DISC = registerItem("medicine_technology_kulakovka",
                properties -> new Item(properties.jukeboxPlayable(MEDICINE_TECHNOLOGY_KEY).stacksTo(1).rarity(Rarity.RARE)));

        ENERGIZED_GLOWSTONE = registerBlockItem("energized_glowstone", AetheriumBlocks.ENERGIZED_GLOWSTONE);

        AETHERIUM_SEEDS = registerItem("aetherium_seeds", Item::new);
        ENERGY_ORB = registerItem("energy_orb", Item::new);
        ENERGY_SEEDS =registerItem("energy_seeds", properties -> new BlockItem(AetheriumBlocks.ENERGY_ORB_BUSH.get(), properties));
    }

    public static void registerAstral() {
        ASTRAL_FRAGMENTS = registerItem("astral_fragments", Item::new);
        ASTRAL_SHARD = registerItem("astral_shard", Item::new);
        ASTRAL_UPGRADE_TEMPLATE = registerItem("astral_upgrade_template", Item::new);

        ASTRAL_KEY = registerItem("astral_key", Item::new);

        ASTRAL_STAR = registerItem("astral_star", properties -> new Item(properties){
            @Override
            public boolean isFoil(@NonNull ItemStack itemStack) {
                return true;
            }
        });

        ASTRAL_FRUIT = registerItem("astral_fruit", properties ->
                new Item(properties.food(AetheriumFoodProperties.ASTRAL_FRUIT, AetheriumFoodProperties.ASTRAL_FRUIT_EFFECT)));

        ASTRAL_SPEAR = registerItem("astral_spear", properties -> new ChargedToolItem(
                ChargedToolMaterial.ASTRAL,
                ChargedToolMaterial.ASTRAL.applySpearProperties(properties,
                                1.15F, 3.85F, 0.4F, 2.5F, 9.0F, 5.5F, 5.1F, 8.75F, 4.6F)
                        .fireResistant().rarity(Rarity.RARE)));

        ASTRAL_AXE = registerItem("astral_axe", properties -> new ChargedAxeItem(
                ChargedToolMaterial.ASTRAL, 8f, 10f,
                properties.fireResistant().rarity(Rarity.RARE)
        ));

        ASTRAL_SHOVEL = registerItem("astral_shovel", properties -> new ChargedShovelItem(
                ChargedToolMaterial.ASTRAL, 6f, -2.8f,
                properties.fireResistant().rarity(Rarity.RARE)));

        ASTRAL_HOE = registerItem("astral_hoe", properties -> new ChargedHoeItem(
                ChargedToolMaterial.ASTRAL, 6f, -2.8f,
                properties.fireResistant().rarity(Rarity.RARE)));

        ASTRAL_PICKAXE = registerItem("astral_pickaxe", properties -> new ChargedToolItem(
                ChargedToolMaterial.ASTRAL,
                ChargedToolMaterial.ASTRAL.applyToolProperties(properties, BlockTags.MINEABLE_WITH_PICKAXE, 3.8f, 6.5f, 0f)
                        .fireResistant().rarity(Rarity.RARE)));

        ASTRAL_SWORD = registerItem("astral_sword", properties -> new ChargedToolItem(
                ChargedToolMaterial.ASTRAL,
                ChargedToolMaterial.ASTRAL.applySwordProperties(properties, 7.5f, 14.5f)
                        .fireResistant().rarity(Rarity.RARE)));

        ASTRAL_HELMET = registerItem("astral_helmet", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.ASTRAL,
                ChargedArmorMaterial.ASTRAL.applyArmorProperties(properties, ArmorType.HELMET)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        ASTRAL_CHESTPLATE = registerItem("astral_chestplate", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.ASTRAL,
                ChargedArmorMaterial.ASTRAL.applyArmorProperties(properties, ArmorType.CHESTPLATE)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        ASTRAL_LEGGINGS = registerItem("astral_leggings", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.ASTRAL,
                ChargedArmorMaterial.ASTRAL.applyArmorProperties(properties, ArmorType.LEGGINGS)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)));

        ASTRAL_BOOTS = registerItem("astral_boots", properties -> new ChargedArmorItem(
                ChargedArmorMaterial.ASTRAL,
                ChargedArmorMaterial.ASTRAL.applyArmorProperties(properties, ArmorType.BOOTS)
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)
        ));

        ASTRAL_BOW = registerItem("astral_bow", properties -> new ChargedBowItem(
                ChargedToolMaterial.VOIDMASS,
                6.5F,
                0.45F,
                0.5F,
                properties.stacksTo(1).rarity(Rarity.RARE).fireResistant().enchantable(1)
        ));
    }

    public static void registerCapsules() {
        CAPSULE_FRAGMENT = registerItem("capsule_fragment", Item::new);
        EMPTY_CAPSULE = registerItem("empty_capsule", CapsuleItem::new);
        FULL_CAPSULE_VOIDMASS = registerItem("full_capsule_voidmass",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT.get()), CapsuleType.VOIDMASS));
        FULL_CAPSULE_ASTRAL = registerItem("full_capsule_astral",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT.get()), CapsuleType.ASTRAL));
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

        VOID_BERRY = registerItem("void_berry", properties -> new BlockItem(AetheriumBlocks.VOID_BERRY_BUSH.get(), properties
                .food(AetheriumFoodProperties.VOID_BERRIES, AetheriumFoodProperties.VOID_BERRIES_EFFECT)));

        VOIDMASS_CHISEL = registerItem("voidmass_chisel", properties -> new ChargedChiselItem(
                ChargedToolMaterial.VOIDMASS,
                properties.stacksTo(1).rarity(Rarity.RARE).fireResistant(),
                AetheriumGlobals.AETHERIUM_CHISEL_MAP
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
                        .rarity(Rarity.RARE).fireResistant().stacksTo(1)
        ));


        VOIDMASS_AXE = registerItem("voidmass_axe", properties -> new ChargedAxeItem(
                ChargedToolMaterial.VOIDMASS, 6f, 8f,
                properties.fireResistant().rarity(Rarity.RARE)
        ));

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

        VOIDMASS_BOW = registerItem("voidmass_bow", properties -> new ChargedBowItem(
                ChargedToolMaterial.VOIDMASS,
                5.5F,
                0.65F,
                0.85F,
                properties.stacksTo(1).rarity(Rarity.RARE).fireResistant().enchantable(1)
        ));

        VOIDMASS_BLOCK = registerBlockItem("voidmass_block", AetheriumBlocks.VOIDMASS_BLOCK);
    }

    public static void registerCavernStuff() {
        CAVERN_HIDE = registerItem("cavern_hide", Item::new);

        CAVERN_COW_SPAWN_EGG = registerItem("cavern_cow_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(AetheriumEntities.CAVERN_COW.get())));
    }

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }
}
