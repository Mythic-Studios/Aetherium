package org.mythic_goose.aetherium.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.mythic_goose.aetherium.init.AetheriumEntities;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.amalgamation.api.registry_v1.EntityRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AetheriumEntityLootTableProvider extends EntityLootSubProvider {
    public AetheriumEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {

        this.add(AetheriumEntities.ASTRALEX_BOSS.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(AetheriumItems.ASTRAL_KEY.get()))
                            .when(LootItemKilledByPlayerCondition.killedByPlayer()))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get()).setWeight(2))
                                .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(10))) // Will be replaced with different item
        );

        this.add(AetheriumEntities.CAVERN_COW.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(AetheriumItems.CAVERN_HIDE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        registries, UniformGenerator.between(1.0F, 2.0F))
                                                .setLimit(4))
                                )
                                .when(LootItemKilledByPlayerCondition.killedByPlayer()))
        );

    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        List<EntityType<?>> list = new ArrayList<>();
        for (RegistryEntry<EntityType<?>> entry : EntityRegistry.all()) {
            list.add(entry.get());
        }
        return list.stream();
    }
}
