package org.mythic_goose.aetherium.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.block.entity.ArcaneStationBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<ArcaneStationBlockEntity> ARCANE_STATION_BE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "arcane_station"),
                    FabricBlockEntityTypeBuilder.create(
                            ArcaneStationBlockEntity::new,
                            ModBlocks.ARCANE_STATION
                    ).build());

    public static void register() {
        Aetherium.LOGGER.info("Registering Block Entities for " + Aetherium.MOD_ID);
    }
}