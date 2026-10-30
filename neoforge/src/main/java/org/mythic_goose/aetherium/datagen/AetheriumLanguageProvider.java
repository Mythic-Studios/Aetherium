package org.mythic_goose.aetherium.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;

public class AetheriumLanguageProvider extends LanguageProvider {
    public AetheriumLanguageProvider(PackOutput output) {
        super(output, AetheriumConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        this.add(AetheriumItems.AETHERIUM_DUST.get(), "Aetherium Dust");
        this.add(AetheriumItems.AETHERIUM_CRYSTAL.get(), "Aetherium Crystal");
        this.add(AetheriumItems.AETHERIUM_INGOT.get(), "Aetherium Ingot");

        this.add(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get(), "Aetherium Charged Amethyst");

        this.add(AetheriumItems.TOME_OF_ARCANA.get(), "Tome of Arcana");
        this.add("tooltip.arcana_tome", "~fLearn Everything Possible");

        this.add(AetheriumItems.COMPRESSED_VOIDMASS.get(), "Compressed Voidmass");
        this.add(AetheriumBlocks.VOID_BERRY_BUSH.get(), "Void Berry Bush");
        this.add(AetheriumItems.VOID_BERRY.get(), "Void Berry");
        this.add(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), "Voidmass Upgrade Template");
        this.add(AetheriumItems.VOIDMASS_AXE.get(), "Compressed Voidmass Axe");
        this.add(AetheriumItems.VOIDMASS_PICKAXE.get(), "Compressed Voidmass Pickaxe");
        this.add(AetheriumItems.VOIDMASS_SWORD.get(), "Compressed Voidmass Sword");
        this.add(AetheriumItems.VOIDMASS_SHOVEL.get(), "Compressed Voidmass Shovel");
        this.add(AetheriumItems.VOIDMASS_HOE.get(), "Compressed Voidmass Hoe");
        this.add(AetheriumItems.VOIDMASS_SPEAR.get(), "Compressed Voidmass Spear");
        this.add(AetheriumItems.VOIDMASS_CHISEL.get(), "Compressed Voidmass Chisel");
        this.add(AetheriumItems.VOIDMASS_BOW.get(), "Compressed Voidmass Bow");

        this.add(AetheriumBlocks.CRACKED_END_STONE.get(), "Cracked End Stone");

        this.add(AetheriumBlocks.AETHERIUM_BLOCK.get(), "Aetherium Block");
        this.add(AetheriumBlocks.VOIDMASS_BLOCK.get(), "Compressed Voidmass Block");

        this.add(AetheriumBlocks.ARCANE_STATION.get(), "Arcane Station");
        this.add("container.aetherium.arcane_station", "Arcane Station");
        this.add("container.aetherium.exchange_currency", "Exchange Currency");

        this.add("itemGroup.aetherium.core", "Core");
        this.add("itemGroup.aetherium.voidmass", "Voidmass");
        this.add("itemGroup.aetherium.astral", "Astral");
        this.add("itemGroup.aetherium.caverns", "The Caverns");
        this.add("itemGroup.aetherium.crystal_currency", "Crystalized Currency");
        this.add("itemGroup.aetherium.new", "NEW!");
        this.add("itemGroup.aetherium_tab", "Aetherium");

        this.add("item.aetherium.capsule.charge", "Charge: %s / %s");

        this.add(AetheriumItems.EMPTY_CAPSULE.get(), "Empty Capsule");
        this.add(AetheriumItems.CAPSULE_FRAGMENT.get(), "Capsule Fragment");
        this.add(AetheriumItems.FULL_CAPSULE_VOIDMASS.get(), "Filled Capsule (Voidmass)");

        this.add(AetheriumItems.CLOCK_OF_MATTER.get(), "Clock of Matter");
        this.add("item.aetherium.clock_of_matter.no_charge", "The clock has no charge left");
        this.add("item.aetherium.clock_of_matter.depleted", "The clock ran out of charge and shut off");
        this.add("item.aetherium.clock_of_matter.mode_switched", "§5Mode§r: %s");
        this.add("item.aetherium.clock_of_matter.charge", "§5Charge§r: %s / %s");
        this.add("aetherium.clock_mode.growth", "§aPlant Growth");
        this.add("aetherium.clock_mode.frost", "§3Frosted Mobs");
        this.add("aetherium.clock_mode.regeneration", "§cPlayer Regeneration");
        this.add("aetherium.clock_mode.daybreak", "§eClear Sunny Skies");
        this.add("aetherium.clock_mode.feedme", "§6Feed Me Please");
        this.add("aetherium.clock_mode.speedy_whites", "Speedy Whites");
        this.add("key.aetherium.clock_recharge", "Charge Item");
        this.add("category.aetherium.general", "Aetherium");

        this.add(AetheriumItems.VOIDMASS_HELMET.get(), "Compressed Voidmass Helmet");
        this.add(AetheriumItems.VOIDMASS_CHESTPLATE.get(), "Compressed Voidmass Chestplate");
        this.add(AetheriumItems.VOIDMASS_LEGGINGS.get(), "Compressed Voidmass Leggings");
        this.add(AetheriumItems.VOIDMASS_BOOTS.get(), "Compressed Voidmass Boots");

        this.add("item.aetherium.charged_tool.charge", "§5Charge§r: %s / %s");

        this.add(AetheriumItems.ASTRAL_KEY.get(), "Astral Key");
        this.add(AetheriumItems.ASTRAL_FRAGMENTS.get(), "Astral Fragments");
        this.add(AetheriumItems.FULL_CAPSULE_ASTRAL.get(), "Filled Capsule (Astral)");
        this.add(AetheriumItems.ASTRAL_SHARD.get(), "Astral Shard");

        this.add(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get(), "Crystallized Grass Block");
        this.add(AetheriumBlocks.CRYSTALLIZED_DIRT.get(), "Crystallized Dirt");

        this.add("entity.aetherium.astralex", "Astralex");
        this.add("entity.aetherium.astralex_true", "Astralex");


        this.add(AetheriumItems.ANTIMATTER_DISC.get(), "Antimatter Disc");

        // Dialogue
        this.add("dialogue.summom.astralex.phase_one", "<§dAstralex§r> May the §oodds§r be ever in §omy§r favor");
        this.add("dialogue.summom.astralex.phase_two", "<§dAstralex§r> Lets brawl to your death");
        this.add("dialogue.summom.failed.coming_soon", "<§6Myth§r> Hey, sorry this 2nd boss fight isn't available in this version");

        this.add("dialog.astralex.first_fight.phase.membrane", "<§dAstralex§r> You might have popped my shield but §oyou§r still won't win");
        this.add("dialog.astralex.first_fight.phase.heart", "<§dAstralex§r> You wont win, even the sharpest of swords won't hurt my heart");
        this.add("dialog.astralex.first_fight.phase.dead", "<§dAstralex§r> You can't stop my §oreturn§r");

        // Advancements
        this.add("advancements.aetherium_core.root.title", "Aetherium");
        this.add("advancements.aetherium_core.root.description", "Well let the troubles begin");

        this.add("advancements.aetherium_core.capsule.title", "Containing Fears");
        this.add("advancements.aetherium_core.capsule.description", "Its something but it contains nothing");

        this.add("advancements.aetherium_core.voidmass_capsule.title", "Rock Bottom");
        this.add("advancements.aetherium_core.voidmass_capsule.description", "Get Voidmass from Bedrock");

        this.add("advancements.aetherium_core.voidmass_alternative_tools.title", "Extremely Serious Dedication");
        this.add("advancements.aetherium_core.voidmass_alternative_tools.description", "Create every Voidmass Tool");

        this.add("advancements.aetherium_core.voidmass_armor.title", "Covered in...");
        this.add("advancements.aetherium_core.voidmass_armor.description", "actually I don't know what you're covered in");

        this.add("advancements.aetherium_core.voidmass_chisel.title", "World Destroyer");
        this.add("advancements.aetherium_core.voidmass_chisel.description", "Can even shatter End Stone!");

        this.add("advancements.aetherium_core.shine_bright.title", "Shine Bright like a Star");
        this.add("advancements.aetherium_core.shine_bright.description", "Use a Nether Star on Cracked End Stone");

        this.add("advancements.aetherium_core.astral_template.title", "Building Upon Matter");
        this.add("advancements.aetherium_core.astral_template.description", "The Blueprints of Reality can be affected with this");

        this.add("advancements.aetherium_core.astral.title", "Shards of another light");
        this.add("advancements.aetherium_core.astral.description", "§dOne step closer... to me");

        this.add("advancements.aetherium_core.the_caverns.title", "Down into The Caverns");
        this.add("advancements.aetherium_core.the_caverns.description", "Deep below the surface... Out of Sight");

        this.add("advancements.aetherium_core.lock_key.title", "Behind Lock and Key");
        this.add("advancements.aetherium_core.lock_key.description", "Loot Awaits Me");

        this.add("advancements.aetherium_core.summoning_gem.title", "Key of Life");
        this.add("advancements.aetherium_core.summoning_gem.description", "Manifestation of Matter itself");

        this.add("advancements.aetherium_core.astral_dungeon.title", "To the Dungeons!");
        this.add("advancements.aetherium_core.astral_dungeon.description", "Enter the Astral Dungeon");


        // Table
        this.add("advancements.aetherium_core.charged.title", "Charged with Treason");
        this.add("advancements.aetherium_core.charged.description", "Craft a Aetherium Charged Amethyst Shard");

        this.add("advancements.aetherium_core.physical_table.title", "Endless Knowledge, with nothing in it");
        this.add("advancements.aetherium_core.physical_table.description", "Obtain an Arcane Station");

        // Richy
        this.add("advancements.aetherium_core.crystal_aetherium.title", "Crystalized");
        this.add("advancements.aetherium_core.crystal_aetherium.description", "Well its not worth much but its a start");

        this.add("advancements.aetherium_core.aetherium_ingot.title", "Not that rich");
        this.add("advancements.aetherium_core.aetherium_ingot.description", "Come on, you aren't showing off much");

        this.add("advancements.aetherium_core.aetherium_block.title", "Blocky Mc Block Block");
        this.add("advancements.aetherium_core.aetherium_block.description", "Now we're getting somewhere");

        this.add("advancements.aetherium_core.compressed_aetherium_block.title", "Inefficient Lies");
        this.add("advancements.aetherium_core.compressed_aetherium_block.description", "Your taking so LONG...");

        // Tooltips
        this.add("tooltip.april_fools.removal", """
                §cWarning§r: §dThis is a joke item. This item will do one of the following:\
                \s
                 - Be removed in the next update\
                \s
                 - Stripped of its features in the next hotfix
                \s""");

        this.add("tooltip.work_in_progress.unknown_version", "§cWarning§r: Unconfirmed Item, this may get removed");
        this.add("tooltip.work_in_progress.coming_soon", "§cWarning§r: This item is unfinished and may break your game, use with caution");

        this.add("jei.aetherium.info.astral_template", "Upgrades Voidmass Tools and turns Summoning Stone(s) into Ultimatum Stone(s)");
        this.add("jei.aetherium.info.antimatter_disc", "Use on an Energy Transformer to warp you to The Caverns. \nAlso it points towards the nearest Astral Dungeon");
        this.add("jei.aetherium.info.summon_gem", "Use on Summoning Stones to summon ???");

        this.add("toolip.boss_defences.astralex", "In Order per phase: Bows, Anything, Spears");

        //Continued

        this.add(AetheriumBlocks.ENERGY_TRANSFORMER.get(), "Energy Transformer");
        this.add(AetheriumBlocks.ASTRAL_BRICKS.get(), "Astral Bricks");
        this.add(AetheriumBlocks.ASTRAL_DOOR.get(), "Astral Door");

        this.add(AetheriumBlocks.SUMMONING_STONE.get(), "Summoning Stone");
        this.add(AetheriumBlocks.ULTIMATIUM_STONE.get(), "Ultimatum Stone");

        this.add(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), "Astral Upgrade Template");
        this.add(AetheriumItems.SUMMONING_GEM.get(), "Summoning Gem");
        this.add(AetheriumItems.ASTRAL_STAR.get(), "Astral Star");
        this.add(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get(), "1x Aetherium Block");

        this.add(AetheriumBlocks.ASTRAL_LAMP.get(), "Astral Lamp");

        this.add(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get(), "Astral Ore");
        this.add(AetheriumBlocks.CAVERN_ROCK.get(), "Cavern Rock");
        this.add(AetheriumBlocks.ROTTING_DIRT.get(), "Rotting Dirt");

        // Astral Tools & Armor
        this.add(AetheriumItems.ASTRAL_SPEAR.get(), "Astral Spear");
        this.add(AetheriumItems.ASTRAL_AXE.get(), "Astral Axe");
        this.add(AetheriumItems.ASTRAL_PICKAXE.get(), "Astral Pickaxe");
        this.add(AetheriumItems.ASTRAL_SWORD.get(), "Astral Sword");
        this.add(AetheriumItems.ASTRAL_SHOVEL.get(), "Astral Shovel");
        this.add(AetheriumItems.ASTRAL_HOE.get(), "Astral Hoe");

        this.add(AetheriumItems.ASTRAL_HELMET.get(), "Astral Helmet");
        this.add(AetheriumItems.ASTRAL_CHESTPLATE.get(), "Astral Chestplate");
        this.add(AetheriumItems.ASTRAL_LEGGINGS.get(), "Astral Leggings");
        this.add(AetheriumItems.ASTRAL_BOOTS.get(), "Astral Boots");
        this.add(AetheriumItems.ASTRAL_BOW.get(), "Astral Bow");

        // v0.5.0 Other
        this.add(AetheriumItems.ASTRAL_FRUIT.get(), "Astral Fruit");
        this.add(AetheriumItems.FERTILIZER.get(), "Fertilizer");

        this.add(AetheriumBlocks.FERTILE_DIRT.get(), "Fertile Dirt");

        this.add(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get(), "Music Disc");
        this.add("jukebox_song.aetherium.medicine_technology_kulakovka", "Kulakovka - Medicine Technology (No Copyright)");

        this.add("jei.aetherium.info.fertilizer", "Made from the Leather of a Cavern Cow. Holds up to 12 bits of bone meal or other fertile ingredients.\nCan be used on Rotting dirt or like a bonemeal in a 5x1x5 area aimed at the centre.\nWhen in the inventory just put the items on the Fertilizer like a bundle");
        this.add("jei.aetherium.info.bonemeal", "A more fertile version of bone meal found in the Caverns working in a 3x1x3 area. Cannot be used on Rotting Dirt");

        this.add(AetheriumBlocks.ENERGIZED_GLOWSTONE.get(), "Energized Glowstone");

        this.add("entity.aetherium.cavern_cow", "Cavern Cow");
        this.add(AetheriumItems.CAVERN_HIDE.get(), "Cavern Hide");
        this.add(AetheriumItems.CAVERN_COW_SPAWN_EGG.get(), "Cavern Cow Spawn Egg");

        this.add(AetheriumItems.FERTILE_BONE_MEAL.get(), "Fertile Bone Meal");
        this.add("tooltip.bonemeal_radius", "Fertilizes a 3x3 area");

        this.add(AetheriumItems.AETHERIUM_SEEDS.get(), "Aetherium Seeds");
        this.add("jei.aetherium.info.aeth_seeds", "These seeds can no longer grow in the envoided layers but can provide a base for energy orbs");
        this.add(AetheriumItems.ENERGY_ORB.get(), "Energy Orb");
        this.add("jei.aetherium.info.energy_orb", "Mainly used as a quick money gain, nothing else really");

        this.add(AetheriumItems.ENERGY_SEEDS.get(), "Energy Seeds");
        this.add(AetheriumBlocks.ENERGY_ORB_BUSH.get(), "Energy Orb Bush");

        this.add("advancements.aetherium_core.astral_tools.title", "Astral Dedication");
        this.add("advancements.aetherium_core.astral_tools.description", "Man, you wasted your time for this");
        this.add("advancements.aetherium_core.astral_armor.title", "Brighter than a Star");
        this.add("advancements.aetherium_core.astral_armor.description", "Toughest around town");

        this.add("advancements.aetherium_core.fertile.title", "Dead Hunk of Hide & Meals");
        this.add("advancements.aetherium_core.fertile.description", "Fix the soil you see dying");

        this.add("advancements.aetherium_core.aetherium_seed.title", "Lost to Time");
        this.add("advancements.aetherium_core.aetherium_seed.description", "Obtain Aetherium Seeds");

        this.add("advancements.aetherium_core.energy_orb.title", "Big Ol' Coin");
        this.add("advancements.aetherium_core.energy_orb.description", "Your Rich now!");

        this.add("advancements.aetherium_core.energy_seed.title", "Money Really does grow on plants");
        this.add("advancements.aetherium_core.energy_seed.description", "Seeds to money");
    }
}
