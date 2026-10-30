package org.mythic_goose.aetherium.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.block.entity.ArcaneStationBlockEntity;
import org.mythic_goose.amalgamation.api.registry_v1.BlockEntityRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.CommonBlockEntityTypeBuilder;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;

public class AetheriumBlockEntities extends BlockEntityRegistry {

    public static final RegistryEntry<BlockEntityType<ArcaneStationBlockEntity>> ARCANE_STATION_BE =
            registerBlockEntity("arcane_station", () ->
                    CommonBlockEntityTypeBuilder.create(
                            ArcaneStationBlockEntity::new,
                            AetheriumBlocks.ARCANE_STATION.get()
                    ).build());

    public static void register() {
        AetheriumConstants.LOG.info("Registering Block Entities for " + AetheriumConstants.MOD_ID);
    }
}