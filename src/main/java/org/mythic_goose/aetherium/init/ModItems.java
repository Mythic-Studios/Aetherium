package org.mythic_goose.aetherium.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.mythic_goose.aetherium.item.ItemWithTooltip;
import org.mythic_goose.aetherium.registry.ItemRegistry;

public class ModItems extends ItemRegistry {

    public static Item AETHERIUM_CHARGED_AMETHYST;
    public static Item TOME_OF_ARCANA;

    public static Item AETHERIUM_DUST;
    public static Item AETHERIUM_CRYSTAL;
    public static Item AETHERIUM_INGOT;

    public static BlockItem ARCANE_STATION;
    public static BlockItem AETHERIUM_BLOCK;

    public static void init() {

        AETHERIUM_CHARGED_AMETHYST = registerItem("aetherium_charged_amethyst", Item::new);
        TOME_OF_ARCANA = registerItem("tome_of_arcana", properties -> new ItemWithTooltip(properties.stacksTo(1).rarity(Rarity.EPIC), "tooltip.arcana_tome")); // Only for Creative (May add a long process for it thou)

        AETHERIUM_DUST = registerItem("aetherium_dust", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_CRYSTAL = registerItem("aetherium_crystal", properties -> new Item(properties.stacksTo(1000)));
        AETHERIUM_INGOT = registerItem("aetherium_ingot", properties -> new Item(properties.stacksTo(1000)));

        ARCANE_STATION = registerBlockItem("arcane_station", ModBlocks.ARCANE_STATION);
        AETHERIUM_BLOCK = registerBlockItemWithCustomStackSize("aetherium_block", ModBlocks.AETHERIUM_BLOCK, 1000);
    }
}
