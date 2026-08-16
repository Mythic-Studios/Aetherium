package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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
import org.mythic_goose.aetherium.block.VoidBerryBushBlock;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.init.AetheriumItems;

import java.util.concurrent.CompletableFuture;

public class AetheriumBlockLootTableProvider extends FabricBlockLootSubProvider {
    public AetheriumBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        dropSelf(AetheriumBlocks.VOIDMASS_BLOCK);
        dropSelf(AetheriumBlocks.AETHERIUM_BLOCK);
        dropSelf(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK);
        dropSelf(AetheriumBlocks.ARCANE_STATION);
        dropSelf(AetheriumBlocks.ENERGY_TRANSFORMER);
        dropSelf(AetheriumBlocks.CRYSTALLIZED_DIRT);
        dropOther(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK, AetheriumItems.CRYSTALLIZED_DIRT);

        dropSelf(AetheriumBlocks.ROTTING_DIRT);
        dropSelf(AetheriumBlocks.CAVERN_ROCK);

        this.add(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE, createMultipleOreDrops(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE, AetheriumItems.ASTRAL_FRAGMENTS, 1, 6));

        dropOther(AetheriumBlocks.CRACKED_END_STONE, Items.END_STONE);

        this.add(AetheriumBlocks.VOID_BERRY_BUSH, (block) -> (LootTable.Builder) this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.VOID_BERRY_BUSH)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(AetheriumItems.VOID_BERRY)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))).withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetheriumBlocks.VOID_BERRY_BUSH)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidBerryBushBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(AetheriumItems.VOID_BERRY))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
    }

    public LootTable.Builder createMultipleOreDrops(final Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(
                block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }
}
