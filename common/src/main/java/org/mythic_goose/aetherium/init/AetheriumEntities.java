package org.mythic_goose.aetherium.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.entity.astralex.AstralexBoss;
import org.mythic_goose.aetherium.entity.cavern_cow.CavernCowAnimal;
import org.mythic_goose.amalgamation.api.registry_v1.EntityRegistry;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;

public class AetheriumEntities extends EntityRegistry {

    public static final RegistryEntry<EntityType<AstralexBoss>> ASTRALEX_BOSS =
            registerEntity("astralex", key ->
                    EntityType.Builder.of(AstralexBoss::new, MobCategory.MONSTER)
                            .sized(4.5f, 4.5f)
                            .eyeHeight(2.5f
                            ).build(key));

    public static final RegistryEntry<EntityType<CavernCowAnimal>> CAVERN_COW =
            registerEntity("cavern_cow", key ->
                    EntityType.Builder.of(CavernCowAnimal::new, MobCategory.CREATURE)
                            .sized(0.9f, 1.4f)
                            .eyeHeight(1.3f
                            ).build(key));


    public static void registerAetheriumEntities() {
        AetheriumConstants.LOG.info("Registering Aetherium Entities for " + AetheriumConstants.MOD_ID);
    }
}
