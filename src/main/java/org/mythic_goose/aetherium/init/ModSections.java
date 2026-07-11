package org.mythic_goose.aetherium.init;

import net.minecraft.network.chat.Component;
import org.mythic_goose.aetherium.creative_tab.Section;
import org.mythic_goose.aetherium.creative_tab.SectionColored;

import java.util.List;

public class ModSections {

    public static List<Section> ALL = List.of();

    public static List<Section> build() {
        ALL = List.of(
                new SectionColored(
                        "main",
                        Component.translatable("itemGroup.aetherium_tab.main_stuff"),
                        0xFF346ec9,   // ARGB banner background
                        0xFFFFFFFF,
                        List.of(
                                ModItems.ARCANE_STATION,
                                ModItems.AETHERIUM_CHARGED_AMETHYST,
                                ModItems.TOME_OF_ARCANA
                        )
                ),
                new SectionColored(
                        "crystals",
                        Component.translatable("itemGroup.aetherium_tab.crystals"),
                        0xFF97119f,   // ARGB banner background
                        0xFFFFFFFF,
                        List.of(
                            ModItems.AETHERIUM_DUST,
                            ModItems.AETHERIUM_CRYSTAL,
                            ModItems.AETHERIUM_INGOT,
                            ModItems.AETHERIUM_BLOCK
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