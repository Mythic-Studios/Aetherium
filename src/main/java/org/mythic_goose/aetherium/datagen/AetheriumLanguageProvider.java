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
    }
}
