package org.mythic_goose.aetherium.world;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

// Code Copied from Kaupenjoe 26.X Course (MIT LICENCE)
// https://github.com/Tutorials-By-Kaupenjoe/Fabric-Course-26.X/blob/main/src/main/java/net/kaupenjoe/mccourse/world/ModOrePlacements.java
public class AetheriumOrePlacements {
    public static List<PlacementModifier> orePlacement(final PlacementModifier frequencyModifier, final PlacementModifier heightRange) {
        return List.of(frequencyModifier, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(final int count, final PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(final int rarity, final PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), heightRange);
    }
}