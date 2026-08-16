package org.mythic_goose.aetherium.util;

import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.Structure;

public class AetheriumStructureTags {
    private AetheriumStructureTags() {
    }

    /**
     * For Advancement
     */
    public static final TagKey<Structure> NEEDED_FOR_DUNGEON_ADVANCEMENT = register("dungeon_advancement");

    private static TagKey<Structure> register(String tagId) {
        return AethTagRegistration.STRUCTURE_TAG.registerAetherium(tagId);
    }
}
