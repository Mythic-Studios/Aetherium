package org.mythic_goose.aetherium.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.component.CapsuleType;
import org.mythic_goose.aetherium.init.ModBlocks;
import org.mythic_goose.aetherium.init.ModItems;

import java.util.function.BiConsumer;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

        blockModelGenerators.createTrivialCube(ModBlocks.AETHERIUM_BLOCK);
        blockModelGenerators.createTrivialCube(ModBlocks.VOIDMASS_BLOCK);

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

        itemModelGenerators.generateFlatItem(ModItems.AETHERIUM_DUST, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AETHERIUM_CRYSTAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AETHERIUM_INGOT, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.TOME_OF_ARCANA, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.EMPTY_CAPSULE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CAPSULE_FRAGMENT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FULL_CAPSULE_VOIDMASS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.AETHERIUM_CHARGED_AMETHYST, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.COMPRESSED_VOIDMASS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_UPGRADE_TEMPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.VOIDMASS_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateSpear(ModItems.VOIDMASS_SPEAR);
    }
}