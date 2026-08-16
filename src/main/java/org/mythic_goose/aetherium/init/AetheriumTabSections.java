package org.mythic_goose.aetherium.init;

import net.minecraft.network.chat.Component;
import org.mythic_goose.aetherium.creative_tab.Section;
import org.mythic_goose.aetherium.creative_tab.SectionColored;

import java.util.List;

public class AetheriumTabSections {

    public static List<Section> ALL = List.of();

    public static List<Section> build() {
        ALL = List.of(
                new SectionColored(
                        "new_items",
                        Component.translatable("itemGroup.aetherium_tab.new_items"),
                        0xFFffc81f,   // ARGB banner background
                        0xFF777777,
                        List.of(
                                AetheriumItems.VOIDMASS_CHISEL,
                                AetheriumItems.CRACKED_END_STONE,
                                AetheriumItems.ASTRAL_KEY,
                                AetheriumItems.ASTRAL_FRAGMENTS,
                                AetheriumItems.ASTRAL_SHARD,
                                AetheriumItems.FULL_CAPSULE_ASTRAL,
                                AetheriumItems.CRYSTALLIZED_DIRT,
                                AetheriumItems.CRYSTALLIZED_GRASS_BLOCK,
                                AetheriumItems.ENERGY_TRANSFORMER,
                                AetheriumItems.ANTIMATTER_DISC,
                                AetheriumItems.ASTRAL_BRICKS,
                                AetheriumItems.ASTRAL_DOOR,
                                AetheriumItems.SUMMONING_STONE,
                                AetheriumItems.ULTIMATIUM_STONE,
                                AetheriumItems.SUMMONING_GEM,
                                AetheriumItems.ASTRAL_UPGRADE_TEMPLATE,
                                AetheriumItems.ASTRAL_LAMP,
                                AetheriumItems.ASTRAL_STAR
                        )
                ),
                new SectionColored(
                        "main",
                        Component.translatable("itemGroup.aetherium_tab.main_stuff"),
                        0xFF346ec9,   // ARGB banner background
                        0xFFFFFFFF,
                        List.of(
                                AetheriumItems.ARCANE_STATION,
                                AetheriumItems.AETHERIUM_CHARGED_AMETHYST,
                                AetheriumItems.TOME_OF_ARCANA,
                                AetheriumItems.EMPTY_CAPSULE,
                                AetheriumItems.CAPSULE_FRAGMENT,
                                AetheriumItems.FULL_CAPSULE_VOIDMASS,
                                AetheriumItems.COMPRESSED_VOIDMASS,
                                AetheriumItems.VOIDMASS_BLOCK,
                                AetheriumItems.VOIDMASS_HELMET,
                                AetheriumItems.VOIDMASS_CHESTPLATE,
                                AetheriumItems.VOIDMASS_LEGGINGS,
                                AetheriumItems.VOIDMASS_BOOTS,
                                AetheriumItems.VOIDMASS_SWORD,
                                AetheriumItems.VOIDMASS_AXE,
                                AetheriumItems.VOIDMASS_PICKAXE,
                                AetheriumItems.VOIDMASS_SPEAR,
                                AetheriumItems.VOIDMASS_SHOVEL,
                                AetheriumItems.VOIDMASS_HOE,
                                AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE,
                                AetheriumItems.CLOCK_OF_MATTER,
                                AetheriumItems.VOID_BERRY
                        )
                ),
                new SectionColored(
                        "crystals",
                        Component.translatable("itemGroup.aetherium_tab.crystals"),
                        0xFF97119f,   // ARGB banner background
                        0xFFFFFFFF,
                        List.of(
                            AetheriumItems.AETHERIUM_DUST,
                            AetheriumItems.AETHERIUM_CRYSTAL,
                            AetheriumItems.AETHERIUM_INGOT,
                            AetheriumItems.AETHERIUM_BLOCK,
                            AetheriumItems.COMPRESS_AETHERIUM_BLOCK
                        )
                )


//              Example of a textured section
//                SectionTextured.of(
//                        MOD_ID, "name",
//                        Component.translatable("itemgroup.mod_id.name"),
//                        0xFFFFAAAA,
//                        List.of(
//
//                        )
//                )
        );
        return ALL;
    }
}