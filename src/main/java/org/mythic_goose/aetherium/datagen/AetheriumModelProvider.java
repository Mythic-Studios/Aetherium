package org.mythic_goose.aetherium.datagen;

import com.geckolib.event.item.CompileItemRenderLayersEvent;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;
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
        blockModelGenerators.createTrivialCube(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.VOIDMASS_BLOCK);

        blockModelGenerators.createCrossBlock(AetheriumBlocks.VOID_BERRY_BUSH, BlockModelGenerators.PlantType.NOT_TINTED,
                VoidBerryBushBlock.AGE, 0,1,2,3);

        blockModelGenerators.createTrivialCube(AetheriumBlocks.CRYSTALLIZED_DIRT);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.CRACKED_END_STONE);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.ENERGY_TRANSFORMER);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.ASTRAL_BRICKS);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.ASTRAL_LAMP);

        blockModelGenerators.createDoor(AetheriumBlocks.ASTRAL_DOOR);


        blockModelGenerators.createTrivialCube(AetheriumBlocks.CAVERN_ROCK);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE);
        blockModelGenerators.createTrivialCube(AetheriumBlocks.ROTTING_DIRT);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_DUST, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_CRYSTAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_INGOT, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.TOME_OF_ARCANA, ModelTemplates.FLAT_ITEM);

        generate3DItem(itemModelGenerators, AetheriumItems.EMPTY_CAPSULE);
        generate3DItem(itemModelGenerators, AetheriumItems.FULL_CAPSULE_VOIDMASS);

        itemModelGenerators.generateFlatItem(AetheriumItems.CAPSULE_FRAGMENT, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.AETHERIUM_CHARGED_AMETHYST, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.ASTRAL_STAR, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.COMPRESSED_VOIDMASS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateSpear(AetheriumItems.VOIDMASS_SPEAR);
        itemModelGenerators.generateFlatItem(AetheriumItems.VOIDMASS_CHISEL, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_HELMET, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_CHESTPLATE, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_LEGGINGS, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerators.generateTrimmableItem(AetheriumItems.VOIDMASS_BOOTS, ChargedArmorMaterial.VOIDMASS_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS, false);

        itemModelGenerators.generateFlatItem(AetheriumItems.ASTRAL_KEY, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.ASTRAL_FRAGMENTS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.FULL_CAPSULE_ASTRAL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.ASTRAL_SHARD, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(AetheriumItems.SUMMONING_GEM, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(AetheriumItems.ANTIMATTER_DISC, ModelTemplates.FLAT_ITEM);
    }

    public final void generate3DItem(@NonNull ItemModelGenerators itemModelGenerators, final Item item) {
        ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));

        Identifier inHandLocation = ModelLocationUtils.getModelLocation(item, "_in_hand");
        ItemModel.Unbaked inHandRef = ItemModelUtils.plainModel(inHandLocation);

        itemModelGenerators.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(flatModel, inHandRef), new ClientItem.Properties(true, false, 1.95F));
    }
}