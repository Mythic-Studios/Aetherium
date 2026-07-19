package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.mythic_goose.aetherium.init.ModBlocks;
import org.mythic_goose.aetherium.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add(ModItems.AETHERIUM_DUST, "Aetherium Dust");
        translationBuilder.add(ModItems.AETHERIUM_CRYSTAL, "Aetherium Crystal");
        translationBuilder.add(ModItems.AETHERIUM_INGOT, "Aetherium Ingot");

        translationBuilder.add(ModItems.AETHERIUM_CHARGED_AMETHYST, "Aetherium Charged Amethyst");

        translationBuilder.add(ModItems.TOME_OF_ARCANA, "Tome of Arcana");
        translationBuilder.add("tooltip.arcana_tome", "§6Learn Everything Possible");

        translationBuilder.add(ModItems.COMPRESSED_VOIDMASS, "Compressed Voidmass");
        translationBuilder.add(ModItems.VOIDMASS_UPGRADE_TEMPLATE, "Voidmass Upgrade Template");
        translationBuilder.add(ModItems.VOIDMASS_AXE, "Compressed Voidmass Axe");
        translationBuilder.add(ModItems.VOIDMASS_PICKAXE, "Compressed Voidmass Pickaxe");
        translationBuilder.add(ModItems.VOIDMASS_SWORD, "Compressed Voidmass Sword");
        translationBuilder.add(ModItems.VOIDMASS_SHOVEL, "Compressed Voidmass Shovel");
        translationBuilder.add(ModItems.VOIDMASS_HOE, "Compressed Voidmass Hoe");
        translationBuilder.add(ModItems.VOIDMASS_SPEAR, "Compressed Voidmass Spear");

        translationBuilder.add(ModBlocks.AETHERIUM_BLOCK, "Aetherium Block");
        translationBuilder.add(ModBlocks.VOIDMASS_BLOCK, "Compressed Voidmass Block");

        translationBuilder.add(ModBlocks.ARCANE_STATION, "Arcane Station");
        translationBuilder.add("container.aetherium.arcane_station", "Arcane Station");
        translationBuilder.add("container.aetherium.exchange_currency", "Exchange Currency");

        translationBuilder.add("itemGroup.aetherium_tab.main_stuff", "Core");
        translationBuilder.add("itemGroup.aetherium_tab.crystals", "Crystalized Currency");
        translationBuilder.add("itemGroup.aetherium_tab.new_items", "NEW!");
        translationBuilder.add("itemGroup.aetherium_tab", "Aetherium");

        translationBuilder.add("item.aetherium.capsule.charge", "Charge: %s / %s");

        translationBuilder.add(ModItems.EMPTY_CAPSULE, "Empty Capsule");
        translationBuilder.add(ModItems.CAPSULE_FRAGMENT, "Capsule Fragment");
        translationBuilder.add(ModItems.FULL_CAPSULE_VOIDMASS, "Filled Capsule (Voidmass)");
    }
}
