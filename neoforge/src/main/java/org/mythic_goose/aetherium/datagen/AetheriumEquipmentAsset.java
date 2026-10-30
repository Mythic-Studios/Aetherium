package org.mythic_goose.aetherium.datagen;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;

public class AetheriumEquipmentAsset implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public AetheriumEquipmentAsset(PackOutput packOutput) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        consumer.accept(ChargedArmorMaterial.VOIDMASS_KEY,
                EquipmentClientInfo.builder()
                        .addHumanoidLayers(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "voidmass"))
                        .build());

        consumer.accept(ChargedArmorMaterial.ASTRAL_KEY,
                EquipmentClientInfo.builder()
                        .addHumanoidLayers(Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "astral"))
                        .build());
    }

    @Override
    public @NonNull CompletableFuture<?> run(final @NonNull CachedOutput cache) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
        bootstrap((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to registerClock equipment asset twice for id: " + id);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }

    @Override
    public @NonNull String getName() {
        return "Aetherium Equipment Asset Provider";
    }
}
