package org.mythic_goose.aetherium;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.mythic_goose.aetherium.datagen.*;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = AetheriumConstants.MOD_ID)
public class AetheriumDataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new AetheriumModelProvider(packOutput));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(AetheriumBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(AetheriumEntityLootTableProvider::new, LootContextParamSets.ENTITY)
                ), lookupProvider));

        generator.addProvider(true, new AetheriumBlockTagProvider(packOutput, lookupProvider));
        generator.addProvider(true, new AetheriumRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new AetheriumItemTagProvider(packOutput, lookupProvider));

        generator.addProvider(true, new AetheriumEquipmentAsset(packOutput));

        generator.addProvider(true, new AetheriumDatapackProvider(packOutput, lookupProvider));

        generator.addProvider(true, new AetheriumAdvancements(packOutput, lookupProvider));

        generator.addProvider(true, new AetheriumLanguageProvider(packOutput));
    }
}