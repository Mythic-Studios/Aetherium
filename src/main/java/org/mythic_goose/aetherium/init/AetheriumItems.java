package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedToolMaterial;
import org.mythic_goose.aetherium.api.tools.ChargedAxeItem;
import org.mythic_goose.aetherium.api.tools.ChargedHoeItem;
import org.mythic_goose.aetherium.api.tools.ChargedShovelItem;
import org.mythic_goose.aetherium.api.tools.ChargedToolItem;
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

    public static Item VOIDMASS_HELMET;
    public static Item VOIDMASS_CHESTPLATE;
    public static Item VOIDMASS_LEGGINGS;
    public static Item VOIDMASS_BOOTS;

    public static Item EMPTY_CAPSULE;
    public static Item CAPSULE_FRAGMENT;
    public static Item FULL_CAPSULE_VOIDMASS;

    public static Item VOIDMASS_UPGRADE_TEMPLATE;

    public static Item CLOCK_OF_MATTER;

    public static BlockItem ARCANE_STATION;
    public static BlockItem AETHERIUM_BLOCK;
    public static BlockItem VOIDMASS_BLOCK;

    public static void init() {

        AETHERIUM_CHARGED_AMETHYST = registerItem("aetherium_charged_amethyst", Item::new);
        TOME_OF_ARCANA = registerItem("tome_of_arcana", properties -> new ItemWithTooltip(properties.stacksTo(1).rarity(Rarity.EPIC), "tooltip.arcana_tome")); // Only for Creative (May add a long process for it thou)

        COMPRESSED_VOIDMASS = registerItem("compressed_voidmass", Item::new);
        VOIDMASS_UPGRADE_TEMPLATE = registerItem("voidmass_upgrade_template", Item::new);

        CLOCK_OF_MATTER = registerItem("clock_of_matter", properties ->
                new ClockOfMatterItem(properties.fireResistant().stacksTo(1).rarity(Rarity.RARE)));

        VOID_BERRY = registerItem("void_berry", properties -> new BlockItem(AetheriumBlocks.VOID_BERRY_BUSH, properties
                .food(AetheriumFoodProperties.VOID_BERRIES, AetheriumFoodProperties.VOID_BERRIES_EFFECT)));

        CAPSULE_FRAGMENT = registerItem("capsule_fragment", Item::new);
        EMPTY_CAPSULE = registerItem("empty_capsule", CapsuleItem::new);
        FULL_CAPSULE_VOIDMASS = registerItem("full_capsule_voidmass",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT), CapsuleType.VOIDMASS));

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

        AETHERIUM_DUST = registerItem("aetherium_dust", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_CRYSTAL = registerItem("aetherium_crystal", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_INGOT = registerItem("aetherium_ingot", properties -> new Item(properties.stacksTo(1000)));

        ARCANE_STATION = registerBlockItem("arcane_station", AetheriumBlocks.ARCANE_STATION);
        VOIDMASS_BLOCK = registerBlockItem("voidmass_block", AetheriumBlocks.VOIDMASS_BLOCK);
        AETHERIUM_BLOCK = registerBlockItemWithCustomStackSize("aetherium_block", AetheriumBlocks.AETHERIUM_BLOCK, 1000);
    }

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }
}
