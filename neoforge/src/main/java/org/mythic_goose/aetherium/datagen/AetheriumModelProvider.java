package org.mythic_goose.aetherium.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.api.external.ComponentIntProperty;
import org.mythic_goose.aetherium.block.EnergyOrbBushBlock;
import org.mythic_goose.aetherium.block.VoidBerryBushBlock;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.amalgamation.api.datagen_helpers_v1.DatagenHelpers;
import org.mythic_goose.amalgamation.api.datagen_helpers_v1.DatagenHelpers.ItemModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AetheriumModelProvider extends ModelProvider {
    public AetheriumModelProvider(PackOutput output) {
        super(output, AetheriumConstants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(AetheriumBlocks.AETHERIUM_BLOCK.get());
        blockModels.createTrivialCube(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get());
        blockModels.createTrivialCube(AetheriumBlocks.VOIDMASS_BLOCK.get());

        blockModels.createCrossBlock(AetheriumBlocks.VOID_BERRY_BUSH.get(), BlockModelGenerators.PlantType.NOT_TINTED,
                VoidBerryBushBlock.AGE, 0,1,2,3);

        blockModels.createTrivialCube(AetheriumBlocks.CRYSTALLIZED_DIRT.get());
        blockModels.createTrivialCube(AetheriumBlocks.CRACKED_END_STONE.get());
        blockModels.createTrivialCube(AetheriumBlocks.ENERGY_TRANSFORMER.get());
        blockModels.createTrivialCube(AetheriumBlocks.ASTRAL_BRICKS.get());
        blockModels.createTrivialCube(AetheriumBlocks.ASTRAL_LAMP.get());

        blockModels.createDoor(AetheriumBlocks.ASTRAL_DOOR.get());


        blockModels.createTrivialCube(AetheriumBlocks.CAVERN_ROCK.get());
        blockModels.createTrivialCube(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get());
        blockModels.createTrivialCube(AetheriumBlocks.ROTTING_DIRT.get());
        blockModels.createTrivialCube(AetheriumBlocks.FERTILE_DIRT.get());

        // None
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get(),
                BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "block/crystallized_grass_block"))));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetheriumBlocks.ARCANE_STATION.get(),
                BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "block/arcane_station"))));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetheriumBlocks.SUMMONING_STONE.get(),
                BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "block/summoning_stone"))));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetheriumBlocks.ULTIMATIUM_STONE.get(),
                BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "block/ultimatium_stone"))));

        // v0.5.0+

        blockModels.createTrivialCube(AetheriumBlocks.ENERGIZED_GLOWSTONE.get());


        // Items
        itemModels.generateFlatItem(AetheriumItems.AETHERIUM_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.AETHERIUM_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.AETHERIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.TOME_OF_ARCANA.get(), ModelTemplates.FLAT_ITEM);

        DatagenHelpers.ItemModels.generate3DItem(itemModels, AetheriumItems.EMPTY_CAPSULE.get());
        DatagenHelpers.ItemModels.generate3DItem(itemModels, AetheriumItems.FULL_CAPSULE_VOIDMASS.get());

        itemModels.generateFlatItem(AetheriumItems.CAPSULE_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_STAR.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.COMPRESSED_VOIDMASS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateSpear(AetheriumItems.VOIDMASS_SPEAR.get());
        itemModels.generateFlatItem(AetheriumItems.VOIDMASS_CHISEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        DatagenHelpers.ItemModels.trimmableHelmet(itemModels, AetheriumItems.VOIDMASS_HELMET.get(), ChargedArmorMaterial.VOIDMASS_KEY, false);
        DatagenHelpers.ItemModels.trimmableChestplate(itemModels, AetheriumItems.VOIDMASS_CHESTPLATE.get(), ChargedArmorMaterial.VOIDMASS_KEY, false);
        DatagenHelpers.ItemModels.trimmableLeggings(itemModels, AetheriumItems.VOIDMASS_LEGGINGS.get(), ChargedArmorMaterial.VOIDMASS_KEY, false);
        DatagenHelpers.ItemModels.trimmableBoots(itemModels, AetheriumItems.VOIDMASS_BOOTS.get(), ChargedArmorMaterial.VOIDMASS_KEY, false);

        itemModels.generateFlatItem(AetheriumItems.ASTRAL_KEY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        DatagenHelpers.ItemModels.generate3DItem(itemModels, AetheriumItems.FULL_CAPSULE_ASTRAL.get());
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_SHARD.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.SUMMONING_GEM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.ANTIMATTER_DISC.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.CLOCK_OF_MATTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.FERTILE_BONE_MEAL.get(), ModelTemplates.FLAT_ITEM);

        generateBow(AetheriumItems.VOIDMASS_BOW.get(), itemModels.itemModelOutput, itemModels);

        // astral tools & armor
        itemModels.generateSpear(AetheriumItems.ASTRAL_SPEAR.get());
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateBow(AetheriumItems.ASTRAL_BOW.get(), itemModels.itemModelOutput, itemModels);

        DatagenHelpers.ItemModels.trimmableHelmet(itemModels, AetheriumItems.ASTRAL_HELMET.get(), ChargedArmorMaterial.ASTRAL_KEY, false);
        DatagenHelpers.ItemModels.trimmableChestplate(itemModels, AetheriumItems.ASTRAL_CHESTPLATE.get(), ChargedArmorMaterial.ASTRAL_KEY, false);
        DatagenHelpers.ItemModels.trimmableLeggings(itemModels, AetheriumItems.ASTRAL_LEGGINGS.get(), ChargedArmorMaterial.ASTRAL_KEY, false);
        DatagenHelpers.ItemModels.trimmableBoots(itemModels, AetheriumItems.ASTRAL_BOOTS.get(), ChargedArmorMaterial.ASTRAL_KEY, false);

        // v0.5.0 Other
        itemModels.generateFlatItem(AetheriumItems.ASTRAL_FRUIT.get(), ModelTemplates.FLAT_ITEM);
        generateIntThresholdItem(itemModels, AetheriumItems.FERTILIZER.get(), "_empty",
                ModDataComponents.FERTILIZER_CHARGE.get(), 12, 0, true);

        itemModels.generateFlatItem(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.CAVERN_HIDE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.CAVERN_COW_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetheriumItems.ENERGY_ORB.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetheriumItems.AETHERIUM_SEEDS.get(), ModelTemplates.FLAT_ITEM);

        blockModels.createCrossBlock(AetheriumBlocks.ENERGY_ORB_BUSH.get(), BlockModelGenerators.PlantType.NOT_TINTED,
                EnergyOrbBushBlock.AGE, 0,1,2,3);
    }

    public void generateBow(Item item, ItemModelOutput itemModelOutput, ItemModelGenerators itemModels) {
        ItemModel.Unbaked bowModel = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_0", ModelTemplates.BOW));
        ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_1", ModelTemplates.BOW));
        ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_pulling_2", ModelTemplates.BOW));
        itemModels.createFlatItemModel(item, ModelTemplates.BOW);
        itemModelOutput.accept(item, ItemModelUtils.conditional(
                ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(new UseDuration(false), 0.05F, pulling0, new RangeSelectItemModel.Entry[]{
                        ItemModelUtils.override(pulling1, 0.65F),
                        ItemModelUtils.override(pulling2, 0.9F)
                }),
                bowModel
        ));
    }

    public void generateIntThresholdItem(ItemModelGenerators itemModelGenerators, Item item, String suffix,
                                         DataComponentType<Integer> component, int defaultStartingNumber, int threshold,
                                         boolean mustEqual) {
        ItemModel.Unbaked unbakedDefault = ItemModelUtils.plainModel(
                itemModelGenerators.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedMatched = ItemModelUtils.plainModel(
                itemModelGenerators.createFlatItemModel(item, suffix, ModelTemplates.FLAT_ITEM));

        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        entries.add(new RangeSelectItemModel.Entry(threshold, unbakedMatched));

        if (mustEqual) {
            // Immediately revert to default the moment the value passes the threshold,
            // so only an exact match at `threshold` shows unbakedMatched.
            entries.add(new RangeSelectItemModel.Entry(threshold + 1, unbakedDefault));
        }
        // if !mustEqual, entries only has the lower bound -> "at or above threshold" behavior

        itemModelGenerators.itemModelOutput.accept(item,
                new ClientItem(new RangeSelectItemModel.Unbaked(
                        Optional.empty(),
                        new ComponentIntProperty(component, defaultStartingNumber),
                        1.0f,
                        entries,
                        Optional.of(unbakedDefault)), // fallback covers "below threshold"
                        new ClientItem.Properties(false, false, 1f)).model());
    }
}
