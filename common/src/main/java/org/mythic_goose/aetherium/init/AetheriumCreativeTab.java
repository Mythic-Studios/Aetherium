package org.mythic_goose.aetherium.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.amalgamation.api.creative_tab_v1.CreativeTabRegistrar;
import org.mythic_goose.amalgamation.api.creative_tab_v1.SectionStyle;
import org.mythic_goose.amalgamation.api.creative_tab_v1.SectionTabBuilder;

import java.util.function.Supplier;

public class AetheriumCreativeTab {

    public static Supplier<CreativeModeTab> AETHERIUM_TAB;

    public static void init(CreativeTabRegistrar registrar) {
        AETHERIUM_TAB = registrar.register("aetherium_tab", AetheriumCreativeTab::buildTab);
    }

    private static CreativeModeTab buildTab() {
        SectionTabBuilder builder = SectionTabBuilder.create(
                        Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "aetherium_tab"))
                .icon(AetheriumItems.AETHERIUM_CRYSTAL.get())
                .title(Component.translatable("itemGroup.aetherium_tab"))
                .displaySection("new", SectionStyle.colored(0xFFffc81f, 0xFF777777), output -> {
                    output.add(AetheriumItems.VOIDMASS_BOW.get());

                    output.add(AetheriumItems.ASTRAL_HELMET.get());
                    output.add(AetheriumItems.ASTRAL_CHESTPLATE.get());
                    output.add(AetheriumItems.ASTRAL_LEGGINGS.get());
                    output.add(AetheriumItems.ASTRAL_BOOTS.get());

                    output.add(AetheriumItems.ASTRAL_SWORD.get());
                    output.add(AetheriumItems.ASTRAL_AXE.get());
                    output.add(AetheriumItems.ASTRAL_PICKAXE.get());
                    output.add(AetheriumItems.ASTRAL_SPEAR.get());
                    output.add(AetheriumItems.ASTRAL_SHOVEL.get());
                    output.add(AetheriumItems.ASTRAL_HOE.get());
                    output.add(AetheriumItems.ASTRAL_BOW.get());

                    output.add(AetheriumItems.FERTILE_BONE_MEAL.get());
                    output.add(AetheriumItems.ASTRAL_FRUIT.get());
                    output.add(AetheriumBlocks.FERTILE_DIRT.get());
                    output.add(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get());

                    output.add(AetheriumBlocks.ENERGIZED_GLOWSTONE.get());

                    output.add(AetheriumItems.CAVERN_COW_SPAWN_EGG.get());
                    output.add(AetheriumItems.CAVERN_HIDE.get());
                    output.add(AetheriumItems.FERTILIZER.get());

                    output.add(AetheriumItems.ENERGY_ORB.get());
                    output.add(AetheriumItems.AETHERIUM_SEEDS.get());
                    output.add(AetheriumItems.ENERGY_SEEDS.get());

                })
                .displaySection("core", SectionStyle.colored(0xFF346ec9), output -> {
                    output.add(AetheriumItems.ARCANE_STATION.get());
                    output.add(AetheriumItems.TOME_OF_ARCANA.get());
                    output.add(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get());
                    output.add(AetheriumItems.EMPTY_CAPSULE.get());
                    output.add(AetheriumItems.CAPSULE_FRAGMENT.get());
                    output.add(AetheriumBlocks.ENERGY_TRANSFORMER.get());
                    output.add(AetheriumItems.ANTIMATTER_DISC.get());
                    output.add(AetheriumItems.SUMMONING_GEM.get());
                    output.add(AetheriumItems.VOID_BERRY.get());
                })
                .displaySection("voidmass", SectionStyle.colored(0xFF562bb3), output -> {
                    output.add(AetheriumItems.FULL_CAPSULE_VOIDMASS.get());
                    output.add(AetheriumItems.COMPRESSED_VOIDMASS.get());
                    output.add(AetheriumBlocks.VOIDMASS_BLOCK.get());
                    output.add(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get());
                    output.add(AetheriumItems.VOIDMASS_HELMET.get());
                    output.add(AetheriumItems.VOIDMASS_CHESTPLATE.get());
                    output.add(AetheriumItems.VOIDMASS_LEGGINGS.get());
                    output.add(AetheriumItems.VOIDMASS_BOOTS.get());
                    output.add(AetheriumItems.VOIDMASS_SWORD.get());
                    output.add(AetheriumItems.VOIDMASS_AXE.get());
                    output.add(AetheriumItems.VOIDMASS_PICKAXE.get());
                    output.add(AetheriumItems.VOIDMASS_CHISEL.get());
                    output.add(AetheriumItems.VOIDMASS_SPEAR.get());
                    output.add(AetheriumItems.VOIDMASS_SHOVEL.get());
                    output.add(AetheriumItems.VOIDMASS_HOE.get());
                    output.add(AetheriumItems.CLOCK_OF_MATTER.get());
                })
                .displaySection("astral", SectionStyle.colored(0xFFf04da9), output -> {
                    output.add(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get());
                    output.add(AetheriumBlocks.CRACKED_END_STONE.get());
                    output.add(AetheriumItems.FULL_CAPSULE_ASTRAL.get());
                    output.add(AetheriumItems.ASTRAL_STAR.get());
                    output.add(AetheriumItems.ASTRAL_FRAGMENTS.get());
                    output.add(AetheriumItems.ASTRAL_SHARD.get());
                    output.add(AetheriumItems.ASTRAL_KEY.get());
                })
                .displaySection("caverns", SectionStyle.colored(0xFF595156), output -> {
                    output.add(AetheriumBlocks.ROTTING_DIRT.get());
                    output.add(AetheriumBlocks.CRYSTALLIZED_DIRT.get());
                    output.add(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get());
                    output.add(AetheriumBlocks.CAVERN_ROCK.get());
                    output.add(AetheriumBlocks.CAVERN_ROCK_ASTRAL_ORE.get());
                    output.add(AetheriumBlocks.ASTRAL_BRICKS.get());
                    output.add(AetheriumBlocks.ASTRAL_DOOR.get());
                    output.add(AetheriumBlocks.ASTRAL_LAMP.get());
                    output.add(AetheriumBlocks.SUMMONING_STONE.get());
                    output.add(AetheriumBlocks.ULTIMATIUM_STONE.get());
                })
                .displaySection("crystal_currency", SectionStyle.colored(0xFF97119f), output -> {
                    output.add(AetheriumItems.AETHERIUM_DUST.get());
                    output.add(AetheriumItems.AETHERIUM_CRYSTAL.get());
                    output.add(AetheriumItems.AETHERIUM_INGOT.get());
                    output.add(AetheriumBlocks.AETHERIUM_BLOCK.get());
                    output.add(AetheriumBlocks.COMPRESSED_AETHERIUM_BLOCK.get());
                });

        return builder.build();
    }
}