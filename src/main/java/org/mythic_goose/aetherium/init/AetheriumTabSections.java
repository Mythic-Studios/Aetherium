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
                                AetheriumItems.VOID_BERRY,
                                AetheriumItems.CLOCK_OF_MATTER,

                                AetheriumItems.VOIDMASS_HELMET,
                                AetheriumItems.VOIDMASS_CHESTPLATE,
                                AetheriumItems.VOIDMASS_LEGGINGS,
                                AetheriumItems.VOIDMASS_BOOTS
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
                                AetheriumItems.COMPRESSED_VOIDMASS,
                                AetheriumItems.VOIDMASS_BLOCK,
                                AetheriumItems.VOIDMASS_SWORD,
                                AetheriumItems.VOIDMASS_AXE,
                                AetheriumItems.VOIDMASS_PICKAXE,
                                AetheriumItems.VOIDMASS_SPEAR,
                                AetheriumItems.VOIDMASS_SHOVEL,
                                AetheriumItems.VOIDMASS_HOE,
                                AetheriumItems.EMPTY_CAPSULE,
                                AetheriumItems.CAPSULE_FRAGMENT,
                                AetheriumItems.FULL_CAPSULE_VOIDMASS,
                                AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE
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
                            AetheriumItems.AETHERIUM_BLOCK
                        )
                )


//              Example of a textured section
//                SectionTextured.of(
//                        MSGWOFT.MOD_ID, "name",
//                        Component.translatable("itemgroup.msgwoft.name"),
//                        0xFFFFAAAA,
//                        List.of(
//
//                        )
//                )
        );
        return ALL;
    }
}