package org.mythic_goose.aetherium.datagen;

import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.block.VoidBerryBushBlock;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;

import java.util.Set;

public class AetheriumBlockLootTableProvider extends BlockLootSubProvider {
    public AetheriumBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return AetheriumBlocks.getRegisteredBlocks();
    }

    @Override
    public void generate() {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        dropSelf(AetheriumBlocks.VOIDMASS_BLOCK.get());
        dropSelf(AetheriumBlocks.AETHERIUM_BLOCK.get());
        dropSelf(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get());
        dropSelf(AetheriumBlocks.ARCANE_STATION.get());
        dropSelf(AetheriumBlocks.ENERGY_TRANSFORMER.get());
        dropSelf(AetheriumBlocks.CRYSTALLIZED_DIRT.get());
        dropOther(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get(), AetheriumItems.CRYSTALLIZED_DIRT.get());

        dropSelf(AetheriumBlocks.ROTTING_DIRT.get());
        dropOther(AetheriumBlocks.FERTILE_DIRT.get(), AetheriumItems.ROTTING_DIRT.get());
        dropSelf(AetheriumBlocks.CAVERN_ROCK.get());

        this.add(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get(), block -> createMultipleOreDrops(block, AetheriumItems.ASTRAL_FRAGMENTS.get(), 3, 8));

        dropOther(AetheriumBlocks.CRACKED_END_STONE.get(), Items.END_STONE);

        this.add(AetheriumBlocks.VOID_BERRY_BUSH.get(), (block) -> (LootTable.Builder) this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.VOID_BERRY_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(AetheriumItems.VOID_BERRY.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))).withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.VOID_BERRY_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(AetheriumItems.VOID_BERRY.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));

        this.add(AetheriumBlocks.ENERGY_ORB_BUSH.get(), (block) -> (LootTable.Builder) this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.ENERGY_ORB_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(AetheriumItems.ENERGY_ORB.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))).withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.ENERGY_ORB_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(AetheriumItems.ENERGY_ORB.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));

        // It Cannot drop in Survival
        dropSelf(AetheriumBlocks.ASTRAL_BRICKS.get());
        dropSelf(AetheriumBlocks.ASTRAL_DOOR.get());
        dropSelf(AetheriumBlocks.ASTRAL_LAMP.get());
        dropSelf(AetheriumBlocks.SUMMONING_STONE.get());
        dropSelf(AetheriumBlocks.ULTIMATIUM_STONE.get());

        // v0.5.0+
        dropSelf(AetheriumBlocks.ENERGIZED_GLOWSTONE.get());
    }

    public LootTable.Builder createMultipleOreDrops(final Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(
                block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }
}