package org.mythic_goose.aetherium.creative_tab;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabLayout {

    public static final Map<String, Integer> SECTION_ROW = new HashMap<>();

    private static List<Section> CACHED_SECTIONS = List.of();

    public static void cacheSections(List<Section> sections) {
        CACHED_SECTIONS = sections;
    }

    public static List<ItemStack> build() {
        SECTION_ROW.clear();
        List<ItemStack> result = new ArrayList<>();
        int row = 0;

        for (Section section : CACHED_SECTIONS) {
            SECTION_ROW.put(section.id(), row);
            for (int i = 0; i < 9; i++) {
                result.add(ItemStack.EMPTY);
            }
            row++;

            List<ItemStack> stacks = section.items().stream()
                    .map(ItemStack::new)
                    .toList();
            result.addAll(stacks);

            int itemCount = stacks.size();
            int usedInLastRow = itemCount % 9;
            if (usedInLastRow != 0) {
                int padding = 9 - usedInLastRow;
                for (int i = 0; i < padding; i++) {
                    result.add(ItemStack.EMPTY);
                }
                row += (itemCount / 9) + 1;
            } else {
                row += itemCount / 9;
            }
        }

        return result;
    }
}