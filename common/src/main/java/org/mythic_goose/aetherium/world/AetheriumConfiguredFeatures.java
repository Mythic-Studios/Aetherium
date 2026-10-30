package org.mythic_goose.aetherium.world;


import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.world.feature.CustomFeatures;

import java.util.List;

public class AetheriumConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> VOID_BERRY_BUSH_KEY = registerKey("void_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENERGY_ORB_BUSH_KEY = registerKey("energy_orb_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASTRAL_ORES_KEY = registerKey("astral_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHARGED_GLOWSTONE_KEY = registerKey("charged_glowstone");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        register(context, VOID_BERRY_BUSH_KEY, Feature.SIMPLE_RANDOM_SELECTOR,
                new CompositeFeatureConfiguration(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(AetheriumBlocks.VOID_BERRY_BUSH.get()
                                        .defaultBlockState().setValue(SweetBerryBushBlock.AGE, 2))),
                                CountPlacement.of(22),
                                RandomOffsetPlacement.ofTriangle(6, 3),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                        ))));

        register(context, ENERGY_ORB_BUSH_KEY, Feature.SIMPLE_RANDOM_SELECTOR,
                new CompositeFeatureConfiguration(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(AetheriumBlocks.ENERGY_ORB_BUSH.get()
                                        .defaultBlockState().setValue(SweetBerryBushBlock.AGE, 2))),
                                CountPlacement.of(12),
                                RandomOffsetPlacement.ofTriangle(6, 5),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                        ))));

        List<OreConfiguration.TargetBlockState> astralOreTargets = List.of(
                OreConfiguration.target(new BlockMatchTest(AetheriumBlocks.CAVERN_ROCK.get()), AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get().defaultBlockState()));

        register(context, ASTRAL_ORES_KEY, Feature.ORE, new OreConfiguration(astralOreTargets, 12));

        register(context, CHARGED_GLOWSTONE_KEY, CustomFeatures.ENERGIZED_GLOWSTONE_BLOB,
                new NoneFeatureConfiguration());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
