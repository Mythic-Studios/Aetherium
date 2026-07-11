package org.mythic_goose.aetherium.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;

public class AethHud {
    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "aeth_balance"),
                AethHud::render
        );
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        Aeth balance = AethHelper.get(client.player);

        String text = isShiftDown()
                ? "Aeth: " + balance.toBigDecimal().toPlainString()
                : "Aeth: " + AethFormatter.format(balance);

        int padding = 3;
        int textWidth = client.font.width(text);
        int textHeight = client.font.lineHeight;

        int x = 4;
        int y = 4;

        // semi-transparent black backdrop
        graphics.fill(
                x - padding, y - padding,
                x + textWidth + padding, y + textHeight + padding,
                0x80000000
        );

        graphics.text(client.font, text, x, y, 0xFFFFFFFF, true);
    }

    private static boolean isShiftDown() {
        Window window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
}