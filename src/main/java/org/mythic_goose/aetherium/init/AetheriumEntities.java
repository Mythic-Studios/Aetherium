package org.mythic_goose.aetherium.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.entity.AstralexBoss;

public class AetheriumEntities {
    public static final ResourceKey<EntityType<?>> ASTRALEX_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "astralex"));

    public static final EntityType<AstralexBoss> ASTRALEX_BOSS = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "astralex"),
            EntityType.Builder.of(AstralexBoss::new, MobCategory.MONSTER).sized(4.5f, 4.5f)
                    .eyeHeight(2.5f).build(ASTRALEX_KEY));

    public static void registerAetheriumEntities() {
        Aetherium.LOGGER.info("Registering Aetherium Entities for " + Aetherium.MOD_ID);
    }
}
