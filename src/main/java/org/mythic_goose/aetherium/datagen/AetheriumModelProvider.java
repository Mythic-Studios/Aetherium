package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.block.VoidBerryBushBlock;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;

public class AetheriumModelProvider extends FabricModelProvider {
    public AetheriumModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

        blockModelGenerators.createTrivialCube(AetheriumBlocks.AETHERIUM_BLOCK);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.VOIDMASS_BLOCK);

        blockModelGenerators.createCrossBlock(AetheriumBlocks.VOID_BERRY_BUSH, BlockModelGenerators.PlantType.NOT_TINTED,
                VoidBerryBushBlock.AGE, 0,1,2,3);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_DUST, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_CRYSTAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_INGOT, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.TOME_OF_ARCANA, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.EMPTY_CAPSULE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.CAPSULE_FRAGMENT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.FULL_CAPSULE_VOIDMASS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_CHARGED_AMETHYST, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.COMPRESSED_VOIDMASS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateSpear(AetheriumItems.VOIDMASS_SPEAR);

        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_HELMET, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_CHESTPLATE, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_LEGGINGS, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_BOOTS, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
    }
}