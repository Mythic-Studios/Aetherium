package org.mythic_goose.aetherium;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

public class AethValuesInit {
    public static void register() {
        Aeth naturalBase = Aeth.ofDecimalString("0.001");
        AethValues.setTag(ItemTags.LEAVES, naturalBase);
        AethValues.setTag(ItemTags.SAPLINGS, naturalBase.multiply(4));
        AethValues.setTag(ItemTags.DIRT, naturalBase);
        AethValues.set(Items.GRASS_BLOCK, naturalBase);
        AethValues.set(Items.TALL_GRASS, naturalBase);
        AethValues.set(Items.SHORT_GRASS, naturalBase);
        AethValues.set(Items.DRY_SHORT_GRASS, naturalBase);
        AethValues.set(Items.DRY_TALL_GRASS, naturalBase);
        AethValues.set(Items.SEAGRASS, naturalBase);
        AethValues.set(Items.PODZOL, naturalBase);
        AethValues.set(Items.MYCELIUM, naturalBase);
        AethValues.set(Items.FLINT, naturalBase.add(Aeth.ofDecimalString("0.009")).divide(2));
        AethValues.set(Items.SAND, naturalBase.add(Aeth.ofDecimalString("0.002")));
        AethValues.setTag(ItemTags.SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE, naturalBase.add(Aeth.ofDecimalString("0.004")));
        AethValues.set(Items.SANDSTONE, naturalBase.add(Aeth.ofDecimalString("0.002")).multiply(4));
        AethValues.set(Items.RED_SANDSTONE, naturalBase.add(Aeth.ofDecimalString("0.002")).multiply(4));
        AethValues.set(Items.CLAY, Aeth.ofDecimalString("0.018"));
        AethValues.set(Items.CLAY_BALL, Aeth.ofDecimalString("0.018").divide(4));
        AethValues.set(Items.HONEYCOMB, naturalBase.multiply(18));
        AethValues.set(Items.CARVED_PUMPKIN, naturalBase.multiply(36));
        AethValues.set(Items.PUMPKIN, naturalBase.multiply(36));

        AethValues.set(Items.LEAF_LITTER, naturalBase.add(Aeth.ofDecimalString("0.009")));

        AethValues.set(Items.APPLE, naturalBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.set(Items.SWEET_BERRIES, naturalBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.set(Items.GLOW_BERRIES, naturalBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.set(Items.CHORUS_FRUIT, naturalBase.add(Aeth.ofDecimalString("0.005")).multiply(16));
        AethValues.set(Items.CHORUS_FLOWER, naturalBase.add(Aeth.ofDecimalString("0.005")).multiply(16));

        long growthMultiplier = 6;
        AethValues.set(Items.WHEAT, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier));
        AethValues.set(Items.BREAD, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier).multiply(3));
        AethValues.set(Items.HAY_BLOCK, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier).multiply(9));
        AethValues.set(Items.BEETROOT, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier));
        AethValues.set(Items.MELON, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier));
        AethValues.set(Items.MELON_SLICE, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier).divide(4));
        AethValues.set(Items.CARROT, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier));
        AethValues.set(Items.POTATO, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier));

        AethValues.set(Items.CANDLE, Aeth.ofDecimalString("0.055").add(naturalBase.multiply(18)));
        AethValues.set(Items.DYED_CANDLE, Aeth.ofDecimalString("0.055").add(naturalBase.multiply(18)).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));

        AethValues.set(Items.CONCRETE_POWDER, Aeth.ofDecimalString("0.104"));
        AethValues.set(Items.CONCRETE, Aeth.ofDecimalString("0.104").add(naturalBase));

        Aeth rockBase = Aeth.ofDecimalString("0.01");
        Aeth stoneStairs = rockBase.multiply(6);
        Aeth smeltingStoneAddition = Aeth.ofDecimalString("0.040");
        AethValues.set(Items.END_STONE, rockBase);
        AethValues.set(Items.SULFUR, rockBase);
        AethValues.set(Items.CINNABAR, rockBase);
        AethValues.set(Items.BLACKSTONE, rockBase);
        AethValues.set(Items.POLISHED_BLACKSTONE, rockBase);
        AethValues.set(Items.POLISHED_BLACKSTONE_SLAB, rockBase.divide(2));
        AethValues.set(Items.CHISELED_POLISHED_BLACKSTONE, rockBase);
        AethValues.set(Items.FURNACE, rockBase.multiply(8));

        AethValues.setTag(AetheriumItemTags.STONES_SLABS_ITEMS, rockBase.divide(2));
        AethValues.setTag(AetheriumItemTags.CHISELED_STONE_ITEMS, rockBase.divide(2).multiply(2));
        AethValues.setTag(AetheriumItemTags.BRICKS_SLABS_ITEMS, rockBase.add(Aeth.ofDecimalString("0.005")).divide(2));
        AethValues.setTag(AetheriumItemTags.SMOOTH_STONE_ITEMS, rockBase.add(Aeth.ofDecimalString("0.005")).multiply(4));
        AethValues.setTag(AetheriumItemTags.FROM_SMOOTH_ITEMS, rockBase.add(Aeth.ofDecimalString("0.005")).multiply(4));

        Aeth animalBaseline = Aeth.ofDecimalString("0.025");
        Aeth animalRawMeatAditional = Aeth.ofDecimalString("0.006");
        AethValues.set(Items.LEATHER, animalBaseline.multiply(2));
        AethValues.set(Items.WOOL, animalBaseline.multiply(2));
        AethValues.set(Items.RABBIT_FOOT, animalBaseline.multiply(2));
        AethValues.set(Items.CARPET, animalBaseline.multiply(2).multiply(2).divide(3));
        AethValues.set(Items.BEEF, animalBaseline.add(animalRawMeatAditional));
        AethValues.set(Items.MUTTON, animalBaseline.add(animalRawMeatAditional));
        AethValues.set(Items.PORKCHOP, animalBaseline.add(animalRawMeatAditional));
        AethValues.set(Items.RABBIT, animalBaseline.add(animalRawMeatAditional));
        AethValues.set(Items.CHICKEN, animalBaseline.add(animalRawMeatAditional));
        AethValues.set(Items.COD, animalBaseline.divide(2));
        AethValues.set(Items.TROPICAL_FISH, animalBaseline.divide(2));
        AethValues.set(Items.PUFFERFISH, animalBaseline.divide(2));
        AethValues.set(Items.SALMON, animalBaseline.divide(2));

        AethValues.set(AetheriumItems.CAVERN_HIDE.get(), animalBaseline.multiply(2).multiply(2));

        AethValues.set(Items.LEATHER_HELMET, animalBaseline.multiply(2).createHelmet());
        AethValues.set(Items.LEATHER_CHESTPLATE, animalBaseline.multiply(2).createChestplate());
        AethValues.set(Items.LEATHER_LEGGINGS, animalBaseline.multiply(2).createLeggings());
        AethValues.set(Items.LEATHER_BOOTS, animalBaseline.multiply(2).createBoots());
        AethValues.set(Items.LEATHER_HORSE_ARMOR, animalBaseline.multiply(2).multiply(7));

        Aeth overworldMonsterBaseline = Aeth.ofDecimalString("0.055");
        AethValues.set(Items.ROTTEN_FLESH, overworldMonsterBaseline.multiply(2));
        AethValues.set(Items.STRING, overworldMonsterBaseline);
        AethValues.set(Items.RESIN_CLUMP, Aeth.ofDecimalString("0.111"));
        AethValues.set(Items.BONE, overworldMonsterBaseline.add(Aeth.ofDecimalString("0.012")));
        AethValues.set(Items.BONE_MEAL, overworldMonsterBaseline.add(Aeth.ofDecimalString("0.012")).divide(3));
        AethValues.set(Items.BONE_BLOCK, overworldMonsterBaseline.add(Aeth.ofDecimalString("0.012")).divide(3).multiply(9));

        AethValues.set(Items.BUNDLE, animalBaseline.multiply(2).add(overworldMonsterBaseline));
        AethValues.set(Items.DYED_BUNDLE, animalBaseline.multiply(2).add(overworldMonsterBaseline).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));

        AethValues.set(Items.LEAD, overworldMonsterBaseline.multiply(5).divide(2));

        Aeth netherMonsterBaseline = Aeth.ofDecimalString("0.070");
        AethValues.set(Items.BLAZE_ROD, netherMonsterBaseline.add(Aeth.ofDecimalString("0.027")));

        Aeth endMonsterBaseline = Aeth.ofDecimalString("0.125");
        AethValues.set(Items.ENDER_PEARL, endMonsterBaseline.add(Aeth.ofDecimalString("0.024")));

        AethValues.set(Items.BLAZE_POWDER, netherMonsterBaseline.add(Aeth.ofDecimalString("0.027")).divide(2));
        AethValues.set(Items.ENDER_EYE, endMonsterBaseline.add(Aeth.ofDecimalString("0.024")).add(netherMonsterBaseline.add(Aeth.ofDecimalString("0.027")).divide(2)));

        Aeth glass = Aeth.ofDecimalString("0.005");
        AethValues.set(Items.GLASS, glass);
        AethValues.set(Items.GLASS_PANE, glass.multiply(6).divide(16));
        AethValues.set(Items.STAINED_GLASS, glass.add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));
        AethValues.set(Items.STAINED_GLASS_PANE, glass.multiply(6).divide(16).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));

        Aeth logBase = Aeth.ofDecimalString("0.025");
        Aeth plankBase = logBase.divide(4);
        Aeth woodFencePartical = plankBase.multiply(5);
        Aeth woodStairsDoorPartical = plankBase.multiply(6);
        Aeth fenceGate = plankBase.multiply(4);
        AethValues.setTag(ItemTags.LOGS, logBase);
        AethValues.setTag(ItemTags.PLANKS, plankBase);
        AethValues.set(Items.BOWL, plankBase.multiply(3).divide(4));
        AethValues.set(Items.CHEST, plankBase.multiply(8));
        AethValues.set(Items.STICK, plankBase.divide(2));
        AethValues.set(Items.LEVER, plankBase.divide(2).add(rockBase));
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

        AethValues.set(Items.OAK_BOAT, plankBase.multiply(5));
        AethValues.set(Items.SPRUCE_BOAT, plankBase.multiply(5));
        AethValues.set(Items.BIRCH_BOAT, plankBase.multiply(5));
        AethValues.set(Items.ACACIA_BOAT, plankBase.multiply(5));
        AethValues.set(Items.DARK_OAK_BOAT, plankBase.multiply(5));
        AethValues.set(Items.MANGROVE_BOAT, plankBase.multiply(5));
        AethValues.set(Items.CHERRY_BOAT, plankBase.multiply(5));
        AethValues.set(Items.PALE_OAK_BOAT, plankBase.multiply(5));
        AethValues.set(Items.JUNGLE_BOAT, plankBase.multiply(5));
        AethValues.set(Items.BAMBOO_RAFT, plankBase.multiply(5));

        AethValues.set(Items.OAK_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.SPRUCE_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.BIRCH_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.ACACIA_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.DARK_OAK_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.MANGROVE_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.CHERRY_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.PALE_OAK_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.JUNGLE_CHEST_BOAT, plankBase.multiply(5).add(plankBase.multiply(8)));
        AethValues.set(Items.BAMBOO_CHEST_RAFT, plankBase.multiply(5).add(plankBase.multiply(8)));

        AethValues.set(Items.BED, plankBase.multiply(3).add(animalBaseline.multiply(2).multiply(3)));
        AethValues.set(Items.BANNER, plankBase.divide(2).add(animalBaseline.multiply(2).multiply(6)));

        AethValues.set(Items.FISHING_ROD, plankBase.divide(2).multiply(3).add(overworldMonsterBaseline.multiply(2)));

        // Wood Tools
        AethValues.set(Items.WOODEN_PICKAXE, plankBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.WOODEN_AXE, plankBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.WOODEN_SHOVEL, plankBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.WOODEN_SPEAR, plankBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.WOODEN_HOE, plankBase.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.WOODEN_SWORD, plankBase.multiply(2).add(plankBase.divide(2)));

        // Stone Tools
        AethValues.set(Items.STONE_PICKAXE, rockBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.STONE_AXE, rockBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.STONE_SHOVEL, rockBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.STONE_SPEAR, rockBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.STONE_HOE, rockBase.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.STONE_SWORD, rockBase.multiply(2).add(plankBase.divide(2)));

        // Coal & Smelting

        Aeth coalBase = Aeth.ofDecimalString("2.500");
        long cookingFoodMultiplier = 3;
        long cookingMeatMultiplier = 8;
        long cookingMeatMultiplierBeef = 16;
        AethValues.set(Items.POPPED_CHORUS_FRUIT, naturalBase.add(Aeth.ofDecimalString("0.005")).multiply(16).multiply(cookingFoodMultiplier));
        AethValues.set(Items.BAKED_POTATO, naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier).multiply(cookingFoodMultiplier));
        AethValues.set(Items.CHARCOAL, coalBase.divide(3));
        AethValues.set(Items.COAL, coalBase);
        AethValues.set(Items.TORCH, coalBase.add(plankBase.divide(2)).divide(4));
        AethValues.set(Items.SOUL_TORCH, coalBase.add(plankBase.divide(2)).add(naturalBase.add(Aeth.ofDecimalString("0.004"))).divide(4));
        AethValues.set(Items.SMOOTH_STONE, rockBase.add(Aeth.ofDecimalString("0.005")).add(smeltingStoneAddition));
        AethValues.set(Items.SMOOTH_STONE_SLAB, rockBase.add(Aeth.ofDecimalString("0.005")).add(smeltingStoneAddition).divide(2));
        AethValues.set(Items.RESIN_BRICK, Aeth.ofDecimalString("0.111").add(smeltingStoneAddition));
        AethValues.set(Items.RESIN_BRICKS, Aeth.ofDecimalString("0.111").add(smeltingStoneAddition).multiply(4));
        AethValues.set(Items.RESIN_BRICK_SLAB, Aeth.ofDecimalString("0.111").add(smeltingStoneAddition).multiply(2));
        AethValues.set(Items.CHISELED_RESIN_BRICKS, Aeth.ofDecimalString("0.111").add(smeltingStoneAddition).multiply(4));

        AethValues.set(Items.TERRACOTTA, Aeth.ofDecimalString("0.018").add(smeltingStoneAddition));
        AethValues.set(Items.DYED_TERRACOTTA, Aeth.ofDecimalString("0.018").add(smeltingStoneAddition).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));
        AethValues.set(Items.GLAZED_TERRACOTTA, Aeth.ofDecimalString("0.018").add(smeltingStoneAddition).add(smeltingStoneAddition).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2))));

        AethValues.set(Items.NETHER_BRICK, rockBase.add(smeltingStoneAddition));
        AethValues.set(Items.NETHER_BRICKS, rockBase.add(smeltingStoneAddition).multiply(4));
        AethValues.set(Items.NETHER_BRICK_SLAB, rockBase.add(smeltingStoneAddition).multiply(2));
        AethValues.set(Items.CHISELED_NETHER_BRICKS, rockBase.add(smeltingStoneAddition).multiply(4));
        // Cooking Meats
        AethValues.set(Items.COOKED_BEEF, animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplierBeef));
        AethValues.set(Items.COOKED_CHICKEN, animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplier));
        AethValues.set(Items.COOKED_MUTTON, animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplier));
        AethValues.set(Items.COOKED_RABBIT, animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplier));
        AethValues.set(Items.COOKED_PORKCHOP, animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplier));
        AethValues.set(Items.COOKED_COD, animalBaseline.divide(2).multiply(cookingMeatMultiplier));
        AethValues.set(Items.COOKED_SALMON, animalBaseline.divide(2).multiply(cookingMeatMultiplier));

        Aeth redstoneLapisBase = Aeth.ofDecimalString("0.701");
        AethValues.set(Items.REDSTONE, redstoneLapisBase);
        AethValues.set(Items.REDSTONE_BLOCK, redstoneLapisBase.multiply(9));
        AethValues.set(Items.REDSTONE_TORCH, redstoneLapisBase.add(plankBase.divide(2)));

        AethValues.set(Items.LAPIS_LAZULI, redstoneLapisBase);
        AethValues.set(Items.LAPIS_BLOCK, redstoneLapisBase.multiply(9));

        // Gold & Copper
        Aeth goldCopperBase = Aeth.ofDecimalString("12.5");
        Aeth rawCopperGoldBase = goldCopperBase.divide(2);
        AethValues.set(Items.COPPER_INGOT, goldCopperBase);
        AethValues.set(Items.GOLD_INGOT, goldCopperBase);
        AethValues.set(Items.RAW_COPPER, rawCopperGoldBase);
        AethValues.set(Items.RAW_GOLD, rawCopperGoldBase);
        AethValues.set(Items.COPPER_NUGGET, goldCopperBase.divide(9));
        AethValues.set(Items.COPPER_TORCH, coalBase.add(plankBase.divide(2)).add(goldCopperBase.divide(9)).divide(4));
        AethValues.set(Items.GOLD_NUGGET, goldCopperBase.divide(9));

        AethValues.set(Items.CLOCK, goldCopperBase.multiply(4).add(redstoneLapisBase));

        AethValues.set(Items.GOLDEN_DANDELION, goldCopperBase.divide(9).multiply(8).add(naturalBase.add(Aeth.ofDecimalString("0.003").multiply(2))));
        AethValues.set(Items.GOLDEN_CARROT, goldCopperBase.divide(9).multiply(8).add(naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier)));
        AethValues.set(Items.GLISTERING_MELON_SLICE, goldCopperBase.divide(9).multiply(8).add(naturalBase.add(Aeth.ofDecimalString("0.008")).multiply(growthMultiplier)));
        AethValues.set(Items.GOLDEN_APPLE, naturalBase.add(Aeth.ofDecimalString("0.005")).add(goldCopperBase.multiply(8)));
        AethValues.set(Items.ENCHANTED_GOLDEN_APPLE, naturalBase.add(Aeth.ofDecimalString("0.005")).add(goldCopperBase.multiply(9).multiply(8)));

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
        AethValues.set(Items.RAW_COPPER_BLOCK, rawCopperGoldBase.createOreBlocks());
        AethValues.set(Items.COPPER_BLOCK, goldCopperBase.createOreBlocks());
        AethValues.set(Items.CUT_COPPER, goldCopperBase.createOreBlocks());
        AethValues.set(Items.CUT_COPPER_SLAB, goldCopperBase.createOreBlocks().divide(2));
        AethValues.set(Items.CHISELED_COPPER, goldCopperBase.createOreBlocks());
        AethValues.set(Items.COPPER_GRATE, goldCopperBase.createOreBlocks());
        AethValues.set(Items.COPPER_BARS, goldCopperBase.multiply(6).divide(16));
        AethValues.set(Items.COPPER_DOOR, goldCopperBase.multiply(6).divide(3));
        AethValues.set(Items.COPPER_TRAPDOOR, goldCopperBase.multiply(4));
        AethValues.set(Items.COPPER_CHAIN, goldCopperBase.add(goldCopperBase.divide(9)).add(goldCopperBase.divide(9)));
        AethValues.set(Items.COPPER_LANTERN, goldCopperBase.divide(9).multiply(8).add(coalBase.add(plankBase.divide(2)).add(goldCopperBase.divide(9)).divide(4)));
        AethValues.set(Items.COPPER_CHEST, goldCopperBase.multiply(9).add(plankBase.multiply(8)));
        AethValues.set(Items.COPPER_GOLEM_STATUE, goldCopperBase.multiply(9).add(plankBase.multiply(8)).add(naturalBase.multiply(36)));
        AethValues.set(Items.COPPER_BULB, goldCopperBase.createOreBlocks().multiply(3).add(netherMonsterBaseline.add(Aeth.ofDecimalString("0.027"))).add(redstoneLapisBase));
        // Copper Other
        AethValues.set(Items.COPPER_PICKAXE, goldCopperBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_AXE, goldCopperBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_SHOVEL, goldCopperBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_SPEAR, goldCopperBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_HOE, goldCopperBase.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_SWORD, goldCopperBase.multiply(2).add(plankBase.divide(2)));
        AethValues.set(Items.COPPER_HORSE_ARMOR, goldCopperBase.multiply(7).add(animalBaseline.multiply(2)));
        AethValues.set(Items.COPPER_NAUTILUS_ARMOR, goldCopperBase.multiply(4));
        // Gold Other
        AethValues.set(Items.GOLDEN_PICKAXE, goldCopperBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_AXE, goldCopperBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_SHOVEL, goldCopperBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_SPEAR, goldCopperBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_HOE, goldCopperBase.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_SWORD, goldCopperBase.multiply(2).add(plankBase.divide(2)));
        AethValues.set(Items.GOLDEN_HORSE_ARMOR, goldCopperBase.multiply(7).add(animalBaseline.multiply(2)));
        AethValues.set(Items.GOLDEN_NAUTILUS_ARMOR, goldCopperBase.multiply(4));

        AethValues.set(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, goldCopperBase.multiply(2));

        // Iron
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
        AethValues.set(Items.IRON_NUGGET, ironBase.divide(9));


        AethValues.set(Items.IRON_PICKAXE, ironBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_AXE, ironBase.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_SHOVEL, ironBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_SPEAR, ironBase.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_HOE, ironBase.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_SWORD, ironBase.multiply(2).add(plankBase.divide(2)));
        AethValues.set(Items.IRON_HORSE_ARMOR, ironBase.multiply(7).add(animalBaseline.multiply(2)));
        AethValues.set(Items.IRON_NAUTILUS_ARMOR, ironBase.multiply(4));

        AethValues.set(Items.CRAFTING_TABLE, plankBase.multiply(6));
        AethValues.set(Items.SMITHING_TABLE, plankBase.multiply(4).add(ironBase.multiply(2)));
        AethValues.set(Items.BLAST_FURNACE, rockBase.multiply(8).add(ironBase.multiply(5).add(Aeth.ofDecimalString("0.165"))));

        Aeth sugarCaneBase = Aeth.ofDecimalString("0.075");
        AethValues.set(Items.SUGAR_CANE, sugarCaneBase);
        AethValues.set(Items.SUGAR, sugarCaneBase);
        AethValues.set(Items.PAPER, sugarCaneBase);

        AethValues.set(Items.BOOK, Aeth.ofDecimalString("0.275"));
        AethValues.set(Items.ENCHANTED_BOOK, Aeth.ofDecimalString("0.275")); // same as a plain book

        AethValues.setDefaultEnchantment(Aeth.ofDecimalString("40"));
        AethValues.setEnchantment(Enchantments.MENDING, Aeth.ofDecimalString("400"));
        AethValues.setEnchantment(Enchantments.UNBREAKING, Aeth.ofDecimalString("30"));
        AethValues.setEnchantment(Enchantments.SHARPNESS, Aeth.ofDecimalString("30"));
        AethValues.setEnchantment(Enchantments.PROTECTION, Aeth.ofDecimalString("30"));
        AethValues.setEnchantment(Enchantments.SILK_TOUCH, Aeth.ofDecimalString("150"));
        AethValues.setEnchantment(Enchantments.FORTUNE, Aeth.ofDecimalString("80"));

        AethValues.set(Items.ENCHANTING_TABLE, Aeth.ofDecimalString("650.243"));

        Aeth bucketBase = ironBase.multiply(3);
        AethValues.set(Items.BUCKET, bucketBase);
        AethValues.set(Items.WATER_BUCKET, bucketBase);
        AethValues.set(Items.LAVA_BUCKET, bucketBase);
        AethValues.set(Items.MILK_BUCKET, bucketBase);
        AethValues.set(Items.POWDER_SNOW_BUCKET, bucketBase);
        AethValues.set(Items.COD_BUCKET, bucketBase.add(animalBaseline.divide(2)));
        AethValues.set(Items.SALMON_BUCKET, bucketBase.add(animalBaseline.divide(2)));
        AethValues.set(Items.TROPICAL_FISH_BUCKET, bucketBase.add(animalBaseline.divide(2)));
        AethValues.set(Items.PUFFERFISH_BUCKET, bucketBase.add(animalBaseline.divide(2)));

        Aeth gems = Aeth.ofDecimalString("30");
        AethValues.set(Items.DIAMOND, gems);
        AethValues.set(Items.EMERALD, gems.multiply(2));
        AethValues.set(Items.DIAMOND_BLOCK, gems.createOreBlocks());
        AethValues.set(Items.EMERALD_BLOCK, gems.multiply(2).createOreBlocks());
        Aeth amethystPrismarine = gems.subtract(Aeth.ofDecimalString("4.75"));
        AethValues.set(Items.AMETHYST_SHARD, amethystPrismarine);
        AethValues.set(Items.AMETHYST_BLOCK, amethystPrismarine);
        AethValues.set(Items.PRISMARINE_SHARD, amethystPrismarine);
        AethValues.set(Items.PRISMARINE_CRYSTALS, amethystPrismarine);
        AethValues.set(Items.PRISMARINE, amethystPrismarine.multiply(4));
        AethValues.set(Items.PRISMARINE_SLAB, amethystPrismarine.multiply(4).divide(2));
        AethValues.set(Items.PRISMARINE_STAIRS, amethystPrismarine.multiply(4).multiply(6).divide(4));
        AethValues.set(Items.PRISMARINE_WALL, amethystPrismarine.multiply(4));
        Aeth darkPrismarine = amethystPrismarine.multiply(8).add(naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2)));
        AethValues.set(Items.DARK_PRISMARINE, darkPrismarine);
        AethValues.set(Items.DARK_PRISMARINE_SLAB, darkPrismarine.divide(2));
        AethValues.set(Items.DARK_PRISMARINE_STAIRS, darkPrismarine.multiply(6).divide(4));
        AethValues.set(Items.PRISMARINE_BRICKS, amethystPrismarine.multiply(9));
        AethValues.set(Items.PRISMARINE_BRICK_SLAB, amethystPrismarine.multiply(9).divide(2));
        AethValues.set(Items.PRISMARINE_BRICK_STAIRS, amethystPrismarine.multiply(9).multiply(6).divide(4));
        AethValues.set(Items.SEA_LANTERN, amethystPrismarine.multiply(9));
        AethValues.set(Items.QUARTZ, amethystPrismarine);
        AethValues.set(Items.QUARTZ_BLOCK, amethystPrismarine.multiply(4));
        AethValues.set(Items.QUARTZ_PILLAR, amethystPrismarine.multiply(4));
        AethValues.set(Items.QUARTZ_BRICKS, amethystPrismarine.multiply(4));
        AethValues.set(Items.QUARTZ_SLAB, amethystPrismarine.multiply(4).divide(2));
        AethValues.set(Items.QUARTZ_STAIRS, amethystPrismarine.multiply(4).multiply(6).divide(4));
        AethValues.set(Items.SMOOTH_QUARTZ, amethystPrismarine.multiply(4).add(smeltingStoneAddition));
        AethValues.set(Items.SMOOTH_QUARTZ_SLAB, amethystPrismarine.multiply(4).add(smeltingStoneAddition).divide(2));
        AethValues.set(Items.SMOOTH_QUARTZ_STAIRS, amethystPrismarine.multiply(4).add(smeltingStoneAddition).multiply(6).divide(4));

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
        AethValues.set(Items.NETHERITE_BLOCK, Aeth.ofDecimalString("190.8").createOreBlocks());
        AethValues.set(Items.NETHER_STAR, Aeth.ofUnits(6800));
        AethValues.set(Items.BEACON, Aeth.ofUnits(7250));

        AethValues.set(Items.NETHERITE_HELMET, gems.createHelmet().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));
        AethValues.set(Items.NETHERITE_CHESTPLATE, gems.createChestplate().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));
        AethValues.set(Items.NETHERITE_LEGGINGS, gems.createLeggings().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));
        AethValues.set(Items.NETHERITE_BOOTS, gems.createBoots().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));

        AethValues.set(Items.NETHERITE_AXE, gems.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8"))));
        AethValues.set(Items.NETHERITE_PICKAXE, gems.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8"))));
        AethValues.set(Items.NETHERITE_SHOVEL, gems.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8"))));
        AethValues.set(Items.NETHERITE_SPEAR, gems.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8"))));
        AethValues.set(Items.NETHERITE_HOE, gems.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8"))));
        AethValues.set(Items.NETHERITE_SWORD, gems.multiply(2).add(plankBase.divide(2)).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));
        AethValues.set(Items.NETHERITE_HORSE_ARMOR, gems.multiply(7).add(animalBaseline.multiply(2)).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));
        AethValues.set(Items.NETHERITE_NAUTILUS_ARMOR, gems.multiply(4).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")));

        AethValues.set(Items.OBSIDIAN, Aeth.ofDecimalString("149.992"));
        AethValues.set(Items.CRYING_OBSIDIAN, Aeth.ofDecimalString("149.992"));

        AethValues.set(Items.DRAGON_EGG, Aeth.ofDecimalString("15750.578"));

        // v0.5.0


        Aeth bowBase = overworldMonsterBaseline.add(plankBase.divide(2).multiply(3));
        AethValues.set(Items.BOW, bowBase);

        AethValues.set(AetheriumItems.FERTILIZER.get(), Aeth.ofDecimalString("108.501").add(animalBaseline.multiply(2).multiply(2).multiply(5)));
        AethValues.set(AetheriumItems.FERTILE_BONE_MEAL.get(), Aeth.ofDecimalString("108.501"));

        // Custom Items
        AethValues.set(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get(), Aeth.ofDecimalString("106.004"));
        AethValues.set(AetheriumItems.TOME_OF_ARCANA.get(), Aeth.ofDecimalString("4500000000000000000")); // 4.5 qt

        AethValues.set(AetheriumItems.ARCANE_STATION.get(), Aeth.ofDecimalString("755.692"));

        AethValues.set(AetheriumItems.EMPTY_CAPSULE.get(), Aeth.ofDecimalString("106.004").add(glass.multiply(4)).add(Aeth.ofDecimalString("190.8").multiply(4)));
        AethValues.set(AetheriumItems.CAPSULE_FRAGMENT.get(), Aeth.ofDecimalString("106.004").add(glass.multiply(4)).add(Aeth.ofDecimalString("190.8")));

        AethValues.set(AetheriumItems.ENERGY_TRANSFORMER.get(), Aeth.ofDecimalString("106.004").add(ironBase.multiply(9).multiply(4)).add(goldCopperBase.multiply(4)));

        AethValues.set(AetheriumItems.CRYSTALLIZED_GRASS_BLOCK.get(), naturalBase);
        AethValues.set(AetheriumItems.CRYSTALLIZED_DIRT.get(), naturalBase);

        Aeth voidmassBase = Aeth.ofDecimalString("1250.568");
        AethValues.set(AetheriumItems.COMPRESSED_VOIDMASS.get(), voidmassBase);
        AethValues.set(AetheriumItems.VOIDMASS_BLOCK.get(), voidmassBase.multiply(9));
        AethValues.set(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), voidmassBase.multiply(7).add(gems.multiply(2)));

        AethValues.set(AetheriumItems.VOID_BERRY.get(), voidmassBase.divide(2).subtract(Aeth.ofDecimalString("326")));

        AethValues.set(AetheriumItems.VOIDMASS_BOW.get(), voidmassBase.add(bowBase).add(voidmassBase.multiply(7).add(gems.multiply(2))));

        AethValues.set(AetheriumItems.CLOCK_OF_MATTER.get(), voidmassBase.multiply(4).add(goldCopperBase.multiply(4).add(redstoneLapisBase)).add(animalBaseline.add(animalRawMeatAditional).multiply(cookingMeatMultiplierBeef)));

        Aeth voidmassHelmet = gems.createHelmet().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase);
        Aeth voidmassChestplate = gems.createChestplate().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase);
        Aeth voidmassLeggings = gems.createLeggings().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase);
        Aeth voidmassBoots = gems.createBoots().add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase);

        AethValues.set(AetheriumItems.VOIDMASS_HELMET.get(), voidmassHelmet);
        AethValues.set(AetheriumItems.VOIDMASS_CHESTPLATE.get(), voidmassChestplate);
        AethValues.set(AetheriumItems.VOIDMASS_LEGGINGS.get(), voidmassLeggings);
        AethValues.set(AetheriumItems.VOIDMASS_BOOTS.get(), voidmassBoots);

        Aeth voidmassAxes = gems.multiply(3).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase));
        Aeth voidmassShovelSpear = gems.multiply(1).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase));
        Aeth voidmassHoe = gems.multiply(2).add(plankBase.divide(2)).add(plankBase.divide(2).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase));
        Aeth voidmassSword = gems.multiply(2).add(plankBase.divide(2)).add(Aeth.ofDecimalString("210.01")).add(Aeth.ofDecimalString("190.8")).add(voidmassBase.multiply(7).add(gems.multiply(2))).add(voidmassBase);

        AethValues.set(AetheriumItems.VOIDMASS_AXE.get(), voidmassAxes);
        AethValues.set(AetheriumItems.VOIDMASS_PICKAXE.get(), voidmassAxes);
        AethValues.set(AetheriumItems.VOIDMASS_SHOVEL.get(), voidmassShovelSpear);
        AethValues.set(AetheriumItems.VOIDMASS_SPEAR.get(), voidmassShovelSpear);
        AethValues.set(AetheriumItems.VOIDMASS_HOE.get(), voidmassHoe);
        AethValues.set(AetheriumItems.VOIDMASS_SWORD.get(), voidmassSword);

        Aeth astralBase = Aeth.ofDecimalString("12505.681");
        AethValues.set(AetheriumItems.ASTRAL_FRAGMENTS.get(), astralBase.divide(4));
        AethValues.set(AetheriumItems.ASTRAL_SHARD.get(), astralBase);
        AethValues.set(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), astralBase.multiply(7).add(voidmassBase.multiply(2)));
        AethValues.set(AetheriumItems.ASTRAL_STAR.get(), astralBase.divide(2).add(Aeth.ofUnits(6800)));
        AethValues.set(AetheriumItems.SUMMONING_GEM.get(), astralBase.multiply(8).add(astralBase.divide(2).add(Aeth.ofUnits(6800))));

        Aeth astralUpgrade = voidmassBase.multiply(7).add(gems.multiply(2)).add(astralBase).add(astralBase.multiply(7).add(voidmassBase.multiply(2)));
        AethValues.set(AetheriumItems.ASTRAL_BOW.get(), voidmassBase.add(bowBase).add(astralUpgrade));

        AethValues.set(AetheriumItems.ASTRAL_AXE.get(), voidmassAxes.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_PICKAXE.get(), voidmassAxes.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_SHOVEL.get(), voidmassShovelSpear.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_SPEAR.get(), voidmassShovelSpear.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_HOE.get(), voidmassHoe.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_SWORD.get(), voidmassSword.add(astralUpgrade));

        AethValues.set(AetheriumItems.ASTRAL_HELMET.get(), voidmassHelmet.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_CHESTPLATE.get(), voidmassChestplate.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_LEGGINGS.get(), voidmassLeggings.add(astralUpgrade));
        AethValues.set(AetheriumItems.ASTRAL_BOOTS.get(), voidmassBoots.add(astralUpgrade));

        AethValues.set(AetheriumItems.ANTIMATTER_DISC.get(), astralBase.multiply(2).add(voidmassBase.multiply(9).multiply(2)));

        AethValues.set(AetheriumItems.ENERGY_ORB.get(), Aeth.ofDecimalString("9999.999"));
        AethValues.set(AetheriumItems.AETHERIUM_SEEDS.get(), Aeth.ofDecimalString("1000.999"));
        AethValues.set(AetheriumItems.ENERGY_SEEDS.get(), Aeth.ofDecimalString("9999.999").add(Aeth.ofDecimalString("1000.999")));

        AethValues.exclude(AetheriumItems.AETHERIUM_DUST.get());
        AethValues.set(AetheriumItems.AETHERIUM_DUST.get(), Aeth.ofDecimalString("0.001"));
        AethValues.set(AetheriumItems.AETHERIUM_CRYSTAL.get(), Aeth.ofUnits(1));
        AethValues.exclude(AetheriumItems.AETHERIUM_CRYSTAL.get());
        AethValues.set(AetheriumItems.AETHERIUM_INGOT.get(), Aeth.ofUnits(1000));
        AethValues.exclude(AetheriumItems.AETHERIUM_INGOT.get());
        AethValues.set(AetheriumItems.AETHERIUM_BLOCK.get(), Aeth.ofUnits(1000000)); // 1 million
        AethValues.exclude(AetheriumItems.AETHERIUM_BLOCK.get());
        AethValues.set(AetheriumItems.COMPRESS_AETHERIUM_BLOCK.get(), Aeth.ofUnits(1000000000)); // 1 billion
        AethValues.exclude(AetheriumItems.COMPRESS_AETHERIUM_BLOCK.get());
    }
}