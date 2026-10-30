package org.mythic_goose.aetherium.api;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.client.AethFormatter;
import org.mythic_goose.amalgamation.api.tooltip_v1.ItemTooltips;

public class AethTooltips {
    /**
     * Set to true by a screen while it builds a tooltip that already carries its own Aeth
     * line (the Arcane Station's browse grid), so the standard label isn't added twice.
     * Client render thread only; always reset in a finally block.
     */
    public static boolean suppressLabel = false;

    public static void register() {
        ItemTooltips.register((stack, context, type, lines) -> {
            if (suppressLabel) return;

            Aeth value = AethValues.lookup(stack);
            if (value == null) return;

            String learnedMark = AethValues.isExcluded(stack) ? "" : isLearned(stack) ? " (§a✔§r)" : " (§c❌§r)";
            lines.add(Component.literal("§5Aeth§r: " + AethFormatter.format(value) + learnedMark));

            if (stack.getCount() > 1) {
                Aeth stackValue = value.multiply(stack.getCount());
                lines.add(Component.literal("§5Stack Aeth§r: " + AethFormatter.format(stackValue)));
            }
        });
    }

    // Checks the whole stack (not just its Item) so enchanted books and gear are matched
    // by their enchantments; the Item overload would look up the plain item instead.
    private static boolean isLearned(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        return player != null && AethKnowledge.knows(player, stack);
    }
}