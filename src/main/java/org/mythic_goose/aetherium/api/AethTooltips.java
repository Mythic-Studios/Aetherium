package org.mythic_goose.aetherium.api;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.client.AethFormatter;

public class AethTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
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

    // Tooltips only ever render client-side, so reading the local player directly
    // (rather than needing a synced parameter) is safe here.
    private static boolean isLearned(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        return player != null && AethKnowledge.knows(player, stack.getItem());
    }
}