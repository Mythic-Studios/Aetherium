package org.mythic_goose.aetherium.render;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.BossEvent;

public class NodeBossBarRenderer {
    private final Identifier backgroundTexture;
    private final Identifier barTexture;
    private final Identifier overlayTexture;
    private final int drawWidth;
    private final int drawHeight;

    public NodeBossBarRenderer(Identifier backgroundTexture, Identifier barTexture, Identifier overlayTexture, int drawWidth, int drawHeight) {
        this.backgroundTexture = backgroundTexture;
        this.barTexture = barTexture;
        this.overlayTexture = overlayTexture;
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
    }

    public void renderBossBar(GuiGraphicsExtractor graphics, int x, int y, BossEvent event) {
        // Background + overlay, full texture stretched to the full draw box
        graphics.blit(backgroundTexture, x, y, x + drawWidth, y + drawHeight, 0f, 1f, 0f, 1f);
        graphics.blit(overlayTexture, x, y, x + drawWidth, y + drawHeight, 0f, 1f, 0f, 1f);

        // Progress bar: drawn width AND sampled U-range both driven by the same
        // progress fraction, so they always shrink together regardless of each
        // texture's actual pixel resolution.
        float progress = event.getProgress();
        int width = Math.round(progress * drawWidth);
        if (width > 0) {
            graphics.blit(barTexture, x, y, x + width, y + drawHeight, 0f, progress, 0f, 1f);
            graphics.blit(overlayTexture, x, y, x + width, y + drawHeight, 0f, progress, 0f, 1f);
        }
    }
}