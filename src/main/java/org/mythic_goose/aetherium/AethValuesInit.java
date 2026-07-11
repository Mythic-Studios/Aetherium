package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.init.ModItems;

public class AethValuesInit {
    public static void register() {
        Aeth naturalBase = Aeth.ofDecimalString("0.001");
        AethValues.setTag(ItemTags.LEAVES, naturalBase);
        AethValues.setTag(ItemTags.DIRT, naturalBase);
        AethValues.setTag(ConventionalItemTags.SEEDS, naturalBase.add(Aeth.ofDecimalString("0.008")));
        AethValues.set(Items.GRASS_BLOCK, naturalBase);
        AethValues.set(Items.PODZOL, naturalBase);
        AethValues.set(Items.MYCELIUM, naturalBase);
        AethValues.setTag(ConventionalItemTags.GRAVELS, naturalBase.add(Aeth.ofDecimalString("0.009")).multiply(2));
        AethValues.set(Items.FLINT, naturalBase.add(Aeth.ofDecimalString("0.009")).divide(2));
        AethValues.set(Items.SAND, naturalBase.add(Aeth.ofDecimalString("0.002")));
        AethValues.set(Items.SANDSTONE, naturalBase.add(Aeth.ofDecimalString("0.002")).multiply(4));

        Aeth rockBase = Aeth.ofDecimalString("0.01");
        Aeth stoneStairsPartical = rockBase.multiply(6);
        AethValues.setTag(ConventionalItemTags.STONES, rockBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.setTag(ConventionalItemTags.COBBLESTONES, rockBase);
        AethValues.setTag(ConventionalItemTags.NETHERRACKS, rockBase);



        AethValues.set(Items.RESIN_CLUMP, Aeth.ofDecimalString("0.111"));


        Aeth glass = Aeth.ofDecimalString("0.005");
        AethValues.setTag(ConventionalItemTags.GLASS_BLOCKS, glass);
        AethValues.setTag(ConventionalItemTags.GLASS_PANES, glass);

        Aeth logBase = Aeth.ofDecimalString("0.025");
        Aeth plankBase = logBase.divide(4);
        Aeth woodFencePartical = plankBase.multiply(5);
        Aeth woodStairsDoorPartical = plankBase.multiply(6);
        Aeth fenceGate = plankBase.multiply(4);
        AethValues.setTag(ItemTags.LOGS, logBase);
        AethValues.setTag(ItemTags.PLANKS, plankBase);
        AethValues.set(Items.STICK, plankBase.divide(2));
        AethValues.setTag(ItemTags.WOODEN_SLABS, plankBase.divide(2));
        AethValues.setTag(ItemTags.WOODEN_FENCES, woodFencePartical.divide(3));
        AethValues.setTag(ItemTags.WOODEN_STAIRS, woodStairsDoorPartical.divide(4));
        AethValues.setTag(ItemTags.FENCE_GATES, fenceGate);
        AethValues.setTag(ItemTags.WOODEN_DOORS, woodStairsDoorPartical.divide(3));
        AethValues.setTag(ItemTags.WOODEN_TRAPDOORS, woodStairsDoorPartical.divide(2));
        AethValues.setTag(ItemTags.WOODEN_PRESSURE_PLATES, plankBase.divide(2));
        AethValues.setTag(ItemTags.WOODEN_BUTTONS, plankBase.divide(4));
        AethValues.setTag(ItemTags.WOODEN_SHELVES, logBase);
        AethValues.set(Items.BAMBOO_BLOCK, logBase);
        AethValues.set(Items.BAMBOO, logBase.divide(4));
        AethValues.set(Items.BAMBOO_MOSAIC, plankBase);
        AethValues.set(Items.BAMBOO_MOSAIC_SLAB, plankBase.divide(2));
        AethValues.set(Items.BAMBOO_MOSAIC_STAIRS, woodStairsDoorPartical.divide(4));

        Aeth goldCopperBase = Aeth.ofDecimalString("12.5");
        Aeth rawCopperGoldBase = goldCopperBase.divide(2);
        AethValues.set(Items.COPPER_INGOT, goldCopperBase);
        AethValues.set(Items.GOLD_INGOT, goldCopperBase);
        AethValues.set(Items.RAW_COPPER, rawCopperGoldBase);
        AethValues.set(Items.RAW_GOLD, rawCopperGoldBase);
        // Armour
        AethValues.set(Items.COPPER_HELMET, goldCopperBase.createHelmet());
        AethValues.set(Items.COPPER_CHESTPLATE, goldCopperBase.createChestplate());
        AethValues.set(Items.COPPER_LEGGINGS, goldCopperBase.createLeggings());
        AethValues.set(Items.COPPER_BOOTS, goldCopperBase.createBoots());
        AethValues.set(Items.GOLDEN_HELMET, goldCopperBase.createHelmet());
        AethValues.set(Items.GOLDEN_CHESTPLATE, goldCopperBase.createChestplate());
        AethValues.set(Items.GOLDEN_LEGGINGS, goldCopperBase.createLeggings());
        AethValues.set(Items.GOLDEN_BOOTS, goldCopperBase.createBoots());
        //Raw
        AethValues.set(Items.GOLD_BLOCK, goldCopperBase.createOreBlocks());
        AethValues.set(Items.RAW_GOLD_BLOCK, rawCopperGoldBase.createOreBlocks());
        AethValues.set(Items.COPPER_BLOCK, goldCopperBase.createOreBlocks());
        AethValues.set(Items.RAW_COPPER_BLOCK, rawCopperGoldBase.createOreBlocks());

        Aeth ironBase = Aeth.ofDecimalString("15.5");
        Aeth rawIronBase = ironBase.divide(2);
        AethValues.set(Items.RAW_IRON, rawIronBase);
        AethValues.set(Items.IRON_INGOT, ironBase);
        AethValues.set(Items.FLINT_AND_STEEL, ironBase.add(naturalBase.add(Aeth.ofDecimalString("0.009")).divide(2)));
        AethValues.set(Items.IRON_HELMET, ironBase.createHelmet());
        AethValues.set(Items.IRON_CHESTPLATE, ironBase.createChestplate());
        AethValues.set(Items.IRON_LEGGINGS, ironBase.createLeggings());
        AethValues.set(Items.IRON_BOOTS, ironBase.createBoots());
        AethValues.set(Items.IRON_BLOCK, ironBase.createOreBlocks());
        AethValues.set(Items.RAW_IRON_BLOCK, rawIronBase.createOreBlocks());

        AethValues.set(Items.CRAFTING_TABLE, plankBase.multiply(6));
        AethValues.set(Items.SMITHING_TABLE, plankBase.multiply(4).add(ironBase.multiply(2)));


        Aeth sugarCaneBase = Aeth.ofDecimalString("0.075");
        AethValues.set(Items.SUGAR_CANE, sugarCaneBase);
        AethValues.set(Items.SUGAR, sugarCaneBase);
        AethValues.set(Items.PAPER, sugarCaneBase);

        AethValues.set(Items.BOOK, Aeth.ofDecimalString("0.275"));

        AethValues.set(Items.ENCHANTING_TABLE, Aeth.ofDecimalString("650.243"));

        Aeth animalBaseline = Aeth.ofDecimalString("0.025");
        AethValues.set(Items.LEATHER, animalBaseline.multiply(2));
        AethValues.set(Items.COD, animalBaseline.divide(2));
        AethValues.set(Items.SALMON, animalBaseline.divide(2));

        AethValues.set(Items.LEATHER_HELMET, animalBaseline.multiply(2).createHelmet());
        AethValues.set(Items.LEATHER_CHESTPLATE, animalBaseline.multiply(2).createChestplate());
        AethValues.set(Items.LEATHER_LEGGINGS, animalBaseline.multiply(2).createLeggings());
        AethValues.set(Items.LEATHER_BOOTS, animalBaseline.multiply(2).createBoots());

        Aeth overworldMonsterBaseline = Aeth.ofDecimalString("0.055");
        AethValues.set(Items.ROTTEN_FLESH, overworldMonsterBaseline.multiply(2));

        Aeth bucketBase = ironBase.multiply(3);
        AethValues.set(Items.BUCKET, bucketBase);
        AethValues.set(Items.WATER_BUCKET, bucketBase);
        AethValues.set(Items.LAVA_BUCKET, bucketBase);
        AethValues.set(Items.MILK_BUCKET, bucketBase);
        AethValues.set(Items.POWDER_SNOW_BUCKET, bucketBase);
        AethValues.set(Items.COD_BUCKET, bucketBase.add(animalBaseline.divide(2)));
        AethValues.set(Items.SALMON_BUCKET, bucketBase.add(animalBaseline.divide(2)));

        Aeth gems = Aeth.ofDecimalString("30");
        AethValues.set(Items.DIAMOND, gems);
        AethValues.set(Items.EMERALD, gems.multiply(2));
        AethValues.set(Items.DIAMOND_BLOCK, gems.createOreBlocks());
        AethValues.set(Items.EMERALD_BLOCK, gems.multiply(2).createOreBlocks());
        Aeth amethystPrismarine = gems.subtract(Aeth.ofDecimalString("4.75"));
        AethValues.set(Items.AMETHYST_SHARD, amethystPrismarine);
        AethValues.set(Items.PRISMARINE_SHARD, amethystPrismarine);

        AethValues.set(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, gems.multiply(7).add(rockBase));

        AethValues.set(Items.DIAMOND_HELMET, gems.createHelmet());
        AethValues.set(Items.DIAMOND_CHESTPLATE, gems.createChestplate());
        AethValues.set(Items.DIAMOND_LEGGINGS, gems.createLeggings());
        AethValues.set(Items.DIAMOND_BOOTS, gems.createBoots());

        AethValues.set(Items.DIAMOND_AXE, gems.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_PICKAXE, gems.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_SHOVEL, gems.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_SPEAR, gems.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_HOE, gems.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_SWORD, gems.multiply(2).add(plankBase.divide(2)));
        AethValues.set(Items.DIAMOND_HORSE_ARMOR, gems.multiply(7).add(animalBaseline.multiply(2)));
        AethValues.set(Items.DIAMOND_NAUTILUS_ARMOR, gems.multiply(4));

        AethValues.set(Items.NETHERITE_SCRAP, Aeth.ofDecimalString("35.2"));
        AethValues.set(Items.NETHERITE_INGOT, Aeth.ofDecimalString("190.8"));
        AethValues.set(Items.NETHER_STAR, Aeth.ofUnits(6800));
        AethValues.set(Items.BEACON, Aeth.ofUnits(7250));

        AethValues.setTag(ConventionalItemTags.OBSIDIANS, Aeth.ofDecimalString("149.992"));

        // Custom Items
        AethValues.set(ModItems.AETHERIUM_CHARGED_AMETHYST, Aeth.ofDecimalString("106.004"));
        AethValues.set(ModItems.TOME_OF_ARCANA, Aeth.ofDecimalString("4500000000000000000"));

        AethValues.set(ModItems.ARCANE_STATION, Aeth.ofDecimalString("755.692"));

        AethValues.exclude(ModItems.AETHERIUM_DUST);
        AethValues.set(ModItems.AETHERIUM_DUST, Aeth.ofDecimalString("0.001"));
        AethValues.set(ModItems.AETHERIUM_CRYSTAL, Aeth.ofUnits(1));
        AethValues.exclude(ModItems.AETHERIUM_CRYSTAL);
        AethValues.set(ModItems.AETHERIUM_INGOT, Aeth.ofUnits(1000));
        AethValues.exclude(ModItems.AETHERIUM_INGOT);
        AethValues.set(ModItems.AETHERIUM_BLOCK, Aeth.ofUnits(1000000)); // 1 million
        AethValues.exclude(ModItems.AETHERIUM_BLOCK);
    }
}