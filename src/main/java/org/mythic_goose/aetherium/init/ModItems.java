package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.item.CapsuleItem;
import org.mythic_goose.aetherium.item.FullCapsuleItem;
import org.mythic_goose.aetherium.item.ItemWithTooltip;
import org.mythic_goose.aetherium.item.ModToolMaterials;
import org.mythic_goose.aetherium.registry.ItemRegistry;

public class ModItems extends ItemRegistry {

    public static Item AETHERIUM_CHARGED_AMETHYST;
    public static Item TOME_OF_ARCANA;

    public static Item AETHERIUM_DUST;
    public static Item AETHERIUM_CRYSTAL;
    public static Item AETHERIUM_INGOT;

    public static Item COMPRESSED_VOIDMASS;
    public static Item VOIDMASS_AXE;
    public static Item VOIDMASS_PICKAXE;
    public static Item VOIDMASS_SHOVEL;
    public static Item VOIDMASS_SPEAR;
    public static Item VOIDMASS_SWORD;
    public static Item VOIDMASS_HOE;

    public static Item EMPTY_CAPSULE;
    public static Item CAPSULE_FRAGMENT;
    public static Item FULL_CAPSULE_VOIDMASS;

    public static Item VOIDMASS_UPGRADE_TEMPLATE;

    public static BlockItem ARCANE_STATION;
    public static BlockItem AETHERIUM_BLOCK;
    public static BlockItem VOIDMASS_BLOCK;

    public static void init() {

        AETHERIUM_CHARGED_AMETHYST = registerItem("aetherium_charged_amethyst", Item::new);
        TOME_OF_ARCANA = registerItem("tome_of_arcana", properties -> new ItemWithTooltip(properties.stacksTo(1).rarity(Rarity.EPIC), "tooltip.arcana_tome")); // Only for Creative (May add a long process for it thou)

        COMPRESSED_VOIDMASS = registerItem("compressed_voidmass", Item::new);
        VOIDMASS_UPGRADE_TEMPLATE = registerItem("voidmass_upgrade_template", Item::new);

        CAPSULE_FRAGMENT = registerItem("capsule_fragment", Item::new);
        EMPTY_CAPSULE = registerItem("empty_capsule", CapsuleItem::new);
        FULL_CAPSULE_VOIDMASS = registerItem("full_capsule_voidmass",
                properties -> new FullCapsuleItem(properties.usingConvertsTo(CAPSULE_FRAGMENT), CapsuleType.VOIDMASS));

        VOIDMASS_AXE = registerItem("voidmass_axe", properties -> new AxeItem(ModToolMaterials.VOIDMASS,
                6f, 12f, properties.fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_SPEAR = registerItem("voidmass_spear", properties -> new Item(properties
                .spear(ModToolMaterials.VOIDMASS,1.15F, 2.5F, 0.4F, 2.5F, 9.0F, 5.5F, 5.1F, 8.75F, 4.6F)
                .fireResistant().rarity(Rarity.RARE)));

        VOIDMASS_PICKAXE = registerItem("voidmass_pickaxe", properties -> new Item(properties
                .pickaxe(ModToolMaterials.VOIDMASS, 6f, -2.8f).fireResistant().rarity(Rarity.RARE)));
        VOIDMASS_SWORD = registerItem("voidmass_sword", properties -> new Item(properties
                .sword(ModToolMaterials.VOIDMASS, 6f, 12f).fireResistant().rarity(Rarity.RARE)));
        VOIDMASS_SHOVEL = registerItem("voidmass_shovel", properties -> new ShovelItem(
                ModToolMaterials.VOIDMASS, 6f, -2.8f, properties.fireResistant().rarity(Rarity.RARE)));
        VOIDMASS_HOE = registerItem("voidmass_hoe", properties -> new Item(properties
                .hoe(ModToolMaterials.VOIDMASS, 6f, -2.8f).fireResistant().rarity(Rarity.RARE)));

        AETHERIUM_DUST = registerItem("aetherium_dust", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_CRYSTAL = registerItem("aetherium_crystal", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_INGOT = registerItem("aetherium_ingot", properties -> new Item(properties.stacksTo(1000)));

        ARCANE_STATION = registerBlockItem("arcane_station", ModBlocks.ARCANE_STATION);
        VOIDMASS_BLOCK = registerBlockItem("voidmass_block", ModBlocks.VOIDMASS_BLOCK);
        AETHERIUM_BLOCK = registerBlockItemWithCustomStackSize("aetherium_block", ModBlocks.AETHERIUM_BLOCK, 1000);
    }

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }
}
