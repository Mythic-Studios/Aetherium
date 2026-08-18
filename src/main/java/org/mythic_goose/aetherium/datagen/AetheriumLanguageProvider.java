package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;

import java.util.concurrent.CompletableFuture;

public class AetheriumLanguageProvider extends FabricLanguageProvider {
    public AetheriumLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add(AetheriumItems.AETHERIUM_DUST, "Aetherium Dust");
        translationBuilder.add(AetheriumItems.AETHERIUM_CRYSTAL, "Aetherium Crystal");
        translationBuilder.add(AetheriumItems.AETHERIUM_INGOT, "Aetherium Ingot");

        translationBuilder.add(AetheriumItems.AETHERIUM_CHARGED_AMETHYST, "Aetherium Charged Amethyst");

        translationBuilder.add(AetheriumItems.TOME_OF_ARCANA, "Tome of Arcana");
        translationBuilder.add("tooltip.arcana_tome", "§6Learn Everything Possible");

        translationBuilder.add(AetheriumItems.COMPRESSED_VOIDMASS, "Compressed Voidmass");
        translationBuilder.add(AetheriumBlocks.VOID_BERRY_BUSH, "Void Berry Bush");
        translationBuilder.add(AetheriumItems.VOID_BERRY, "Void Berry");
        translationBuilder.add(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, "Voidmass Upgrade Template");
        translationBuilder.add(AetheriumItems.VOIDMASS_AXE, "Compressed Voidmass Axe");
        translationBuilder.add(AetheriumItems.VOIDMASS_PICKAXE, "Compressed Voidmass Pickaxe");
        translationBuilder.add(AetheriumItems.VOIDMASS_SWORD, "Compressed Voidmass Sword");
        translationBuilder.add(AetheriumItems.VOIDMASS_SHOVEL, "Compressed Voidmass Shovel");
        translationBuilder.add(AetheriumItems.VOIDMASS_HOE, "Compressed Voidmass Hoe");
        translationBuilder.add(AetheriumItems.VOIDMASS_SPEAR, "Compressed Voidmass Spear");
        translationBuilder.add(AetheriumItems.VOIDMASS_CHISEL, "Compressed Voidmass Chisel");

        translationBuilder.add(AetheriumBlocks.CRACKED_END_STONE, "Cracked End Stone");

        translationBuilder.add(AetheriumBlocks.AETHERIUM_BLOCK, "Aetherium Block");
        translationBuilder.add(AetheriumBlocks.VOIDMASS_BLOCK, "Compressed Voidmass Block");

        translationBuilder.add(AetheriumBlocks.ARCANE_STATION, "Arcane Station");
        translationBuilder.add("container.aetherium.arcane_station", "Arcane Station");
        translationBuilder.add("container.aetherium.exchange_currency", "Exchange Currency");

        translationBuilder.add("itemGroup.aetherium_tab.main_stuff", "Core");
        translationBuilder.add("itemGroup.aetherium_tab.crystals", "Crystalized Currency");
        translationBuilder.add("itemGroup.aetherium_tab.new_items", "NEW!");
        translationBuilder.add("itemGroup.aetherium_tab", "Aetherium");

        translationBuilder.add("item.aetherium.capsule.charge", "Charge: %s / %s");

        translationBuilder.add(AetheriumItems.EMPTY_CAPSULE, "Empty Capsule");
        translationBuilder.add(AetheriumItems.CAPSULE_FRAGMENT, "Capsule Fragment");
        translationBuilder.add(AetheriumItems.FULL_CAPSULE_VOIDMASS, "Filled Capsule (Voidmass)");

        translationBuilder.add(AetheriumItems.CLOCK_OF_MATTER, "Clock of Matter");
        translationBuilder.add("item.aetherium.clock_of_matter.no_charge", "The clock has no charge left");
        translationBuilder.add("item.aetherium.clock_of_matter.depleted", "The clock ran out of charge and shut off");
        translationBuilder.add("item.aetherium.clock_of_matter.mode_switched", "§5Mode§r: %s");
        translationBuilder.add("item.aetherium.clock_of_matter.charge", "§5Charge§r: %s / %s");
        translationBuilder.add("aetherium.clock_mode.growth", "§aPlant Growth");
        translationBuilder.add("aetherium.clock_mode.frost", "§3Frosted Mobs");
        translationBuilder.add("aetherium.clock_mode.regeneration", "§cPlayer Regeneration");
        translationBuilder.add("aetherium.clock_mode.daybreak", "§eClear Sunny Skies");
        translationBuilder.add("aetherium.clock_mode.feedme", "§6Feed Me Please");
        translationBuilder.add("aetherium.clock_mode.speedy_whites", "Speedy Whites");
        translationBuilder.add("key.aetherium.clock_recharge", "Charge Item");
        translationBuilder.add("category.aetherium.general", "Aetherium");

        translationBuilder.add(AetheriumItems.VOIDMASS_HELMET, "Compressed Voidmass Helmet");
        translationBuilder.add(AetheriumItems.VOIDMASS_CHESTPLATE, "Compressed Voidmass Chestplate");
        translationBuilder.add(AetheriumItems.VOIDMASS_LEGGINGS, "Compressed Voidmass Leggings");
        translationBuilder.add(AetheriumItems.VOIDMASS_BOOTS, "Compressed Voidmass Boots");

        translationBuilder.add("item.aetherium.charged_tool.charge", "§5Charge§r: %s / %s");

        translationBuilder.add(AetheriumItems.ASTRAL_KEY, "Astral Key");
        translationBuilder.add(AetheriumItems.ASTRAL_FRAGMENTS, "Astral Fragments");
        translationBuilder.add(AetheriumItems.FULL_CAPSULE_ASTRAL, "Filled Capsule (Astral)");
        translationBuilder.add(AetheriumItems.ASTRAL_SHARD, "Astral Shard");

        translationBuilder.add(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK, "Crystallized Grass Block");
        translationBuilder.add(AetheriumBlocks.CRYSTALLIZED_DIRT, "Crystallized Dirt");

        translationBuilder.add("entity.aetherium.astralex", "Astralex");
        translationBuilder.add("entity.aetherium.astralex_true", "Astralex");


        translationBuilder.add(AetheriumItems.ANTIMATTER_DISC, "Antimatter Disc");

        // Dialogue
        translationBuilder.add("dialogue.summom.astralex.phase_one", "<§dAstralex§r> May the §oodds§r be ever in §omy§r favor");
        translationBuilder.add("dialogue.summom.astralex.phase_two", "<§dAstralex§r> Lets brawl to your death");
        translationBuilder.add("dialogue.summom.failed.coming_soon", "<§6Myth§r> Hey, sorry this 2nd boss fight isn't available in this version");

        translationBuilder.add("dialog.astralex.first_fight.phase.membrane", "<§dAstralex§r> You might have popped my shield but §oyou§r still won't win");
        translationBuilder.add("dialog.astralex.first_fight.phase.heart", "<§dAstralex§r> You wont win, even the sharpest of swords won't hurt my heart");
        translationBuilder.add("dialog.astralex.first_fight.phase.dead", "<§dAstralex§r> You can't stop my §oreturn§r");

        translationBuilder.add("dialog.astralex.open_keyed_door.1", "<§dAstralex§r> Flesh Decays over time, yet I found new body in that time");
        translationBuilder.add("dialog.astralex.open_keyed_door.2", "<§dAstralex§r> Summon me again if you dare... from an Ultimatum Stone");

        // Advancements
        translationBuilder.add("advancements.aetherium_core.root.title", "Aetherium");
        translationBuilder.add("advancements.aetherium_core.root.description", "Well let the troubles begin");

        translationBuilder.add("advancements.aetherium_core.capsule.title", "Containing Fears");
        translationBuilder.add("advancements.aetherium_core.capsule.description", "Its something but it contains nothing");

        translationBuilder.add("advancements.aetherium_core.voidmass_capsule.title", "Rock Bottom");
        translationBuilder.add("advancements.aetherium_core.voidmass_capsule.description", "Get Voidmass from Bedrock");

        translationBuilder.add("advancements.aetherium_core.voidmass_alternative_tools.title", "Extremely Serious Dedication");
        translationBuilder.add("advancements.aetherium_core.voidmass_alternative_tools.description", "Create every Voidmass Tool");

        translationBuilder.add("advancements.aetherium_core.voidmass_armor.title", "Covered in...");
        translationBuilder.add("advancements.aetherium_core.voidmass_armor.description", "actually I don't know what you're covered in");

        translationBuilder.add("advancements.aetherium_core.voidmass_chisel.title", "World Destroyer");
        translationBuilder.add("advancements.aetherium_core.voidmass_chisel.description", "Can even shatter End Stone!");

        translationBuilder.add("advancements.aetherium_core.shine_bright.title", "Shine Bright like a Star");
        translationBuilder.add("advancements.aetherium_core.shine_bright.description", "Use a Nether Star on Cracked End Stone");

        translationBuilder.add("advancements.aetherium_core.astral_template.title", "Building Upon Matter");
        translationBuilder.add("advancements.aetherium_core.astral_template.description", "The Blueprints of Reality can be affected with this");

        translationBuilder.add("advancements.aetherium_core.astral.title", "Shards of another light");
        translationBuilder.add("advancements.aetherium_core.astral.description", "§dOne step closer... to me");

        translationBuilder.add("advancements.aetherium_core.the_caverns.title", "Down into The Caverns");
        translationBuilder.add("advancements.aetherium_core.the_caverns.description", "Deep below the surface... Out of Sight");

        translationBuilder.add("advancements.aetherium_core.lock_key.title", "Behind Lock and Key");
        translationBuilder.add("advancements.aetherium_core.lock_key.description", "Loot Awaits Me");

        translationBuilder.add("advancements.aetherium_core.summoning_gem.title", "Key of Life");
        translationBuilder.add("advancements.aetherium_core.summoning_gem.description", "Manifestation of Matter itself");

        translationBuilder.add("advancements.aetherium_core.astral_dungeon.title", "To the Dungeons!");
        translationBuilder.add("advancements.aetherium_core.astral_dungeon.description", "Enter the Astral Dungeon");


        // Table
        translationBuilder.add("advancements.aetherium_core.charged.title", "Charged with Treason");
        translationBuilder.add("advancements.aetherium_core.charged.description", "Craft a Aetherium Charged Amethyst Shard");

        translationBuilder.add("advancements.aetherium_core.physical_table.title", "Endless Knowledge, with nothing in it");
        translationBuilder.add("advancements.aetherium_core.physical_table.description", "Obtain an Arcane Station");

        // Richy
        translationBuilder.add("advancements.aetherium_core.crystal_aetherium.title", "Crystalized");
        translationBuilder.add("advancements.aetherium_core.crystal_aetherium.description", "Well its not worth much but its a start");

        translationBuilder.add("advancements.aetherium_core.aetherium_ingot.title", "Not that rich");
        translationBuilder.add("advancements.aetherium_core.aetherium_ingot.description", "Come on, you aren't showing off much");

        translationBuilder.add("advancements.aetherium_core.aetherium_block.title", "Blocky Mc Block Block");
        translationBuilder.add("advancements.aetherium_core.aetherium_block.description", "Now we're getting somewhere");

        translationBuilder.add("advancements.aetherium_core.compressed_aetherium_block.title", "Inefficient Lies");
        translationBuilder.add("advancements.aetherium_core.compressed_aetherium_block.description", "Your taking so LONG...");

        // Tooltips
        translationBuilder.add("tooltip.april_fools.removal", """
                §cWarning§r: §dThis is a joke item. This item will do one of the following:\
                \s
                 - Be removed in the next update\
                \s
                 - Stripped of its features in the next hotfix
                \s""");

        translationBuilder.add("tooltip.work_in_progress.unknown_version", "§cWarning§r: Unconfirmed Item, this may get removed");
        translationBuilder.add("tooltip.work_in_progress.coming_soon", "§cWarning§r: This item is unfinished and may break your game, use with caution");

        translationBuilder.add("jei.aetherium.info.astral_template", "Upgrades Voidmass Tools and turns Summoning Stone(s) into Ultimatum Stone(s)");
        translationBuilder.add("jei.aetherium.info.antimatter_disc", "Use on an Energy Transformer to warp you to The Caverns. \nAlso it points towards the nearest Astral Dungeon");
        translationBuilder.add("jei.aetherium.info.summon_gem", "Use on Summoning Stones to summon ???");

        translationBuilder.add("toolip.boss_defences.astralex", "In Order per phase: Bows, Anything, Spears");

        //Continued

        translationBuilder.add(AetheriumBlocks.ENERGY_TRANSFORMER, "Energy Transformer");
        translationBuilder.add(AetheriumBlocks.ASTRAL_BRICKS, "Astral Bricks");
        translationBuilder.add(AetheriumBlocks.ASTRAL_DOOR, "Astral Door");

        translationBuilder.add(AetheriumBlocks.SUMMONING_STONE, "Summoning Stone");
        translationBuilder.add(AetheriumBlocks.ULTIMATIUM_STONE, "Ultimatum Stone");

        translationBuilder.add(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE, "Astral Upgrade Template");
        translationBuilder.add(AetheriumItems.SUMMONING_GEM, "Summoning Gem");
        translationBuilder.add(AetheriumItems.ASTRAL_STAR, "Astral Star");
        translationBuilder.add(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK, "Compressed Aetherium Block");

        translationBuilder.add(AetheriumBlocks.ASTRAL_LAMP, "Astral Lamp");

        translationBuilder.add(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE, "Astral Ore");
        translationBuilder.add(AetheriumBlocks.CAVERN_ROCK, "Cavern Rock");
        translationBuilder.add(AetheriumBlocks.ROTTING_DIRT, "Rotting Dirt");
    }
}
