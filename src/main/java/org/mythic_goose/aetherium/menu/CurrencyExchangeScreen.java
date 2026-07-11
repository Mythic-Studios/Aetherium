package org.mythic_goose.aetherium.menu;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.client.AethFormatter;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class CurrencyExchangeScreen extends AbstractContainerScreen<CurrencyExchangeMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/currency_exchange/currency_exchange_gui.png");

    // Page-arrow sprites — reusing the same sprite names/sizing as the station's
    // own page_up/page_down arrows.
    private static final Identifier ARROW_UP_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/sprites/page_up.png");
    private static final Identifier ARROW_UP_HOVERED_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/sprites/page_up_hovered.png");
    private static final Identifier ARROW_DOWN_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/sprites/page_down.png");
    private static final Identifier ARROW_DOWN_HOVERED_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/sprites/page_down_hovered.png");

    // Output-slot "empty" prompt.
    private static final Identifier TAKE_OUT_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/sprites/take_out.png");

    // Exchange-button backgrounds. can_convert has a "_selected" variant;
    // cannot_convert does not (you can't have a selected-but-unaffordable state
    // that looks any different from plain unaffordable).
    private static final Identifier CAN_CONVERT_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/currency_exchange/can_convert.png");
    private static final Identifier CAN_CONVERT_SELECTED_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/currency_exchange/can_convert_selected.png");
    private static final Identifier CANNOT_CONVERT_TEXTURE =
            Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/gui/currency_exchange/cannot_convert.png");

    private static final int AETH_VALUE_COLOR = 0xaa00a9;

    // ── Layout constants ── local (leftPos/topPos-relative) coordinates.

    // the Aeth balance readout
    private static final int BALANCE_X = 36;
    private static final int BALANCE_Y = 82;

    // 1-across, 3-down grid of 88x24 exchange buttons
    private static final int GRID_X = 104;
    private static final int GRID_Y = 20;
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 24;
    private static final int BUTTON_SPACING = 0;

    // page up/down arrows, stacked to the left of the grid
    private static final int PAGE_UP_X = 89;
    private static final int PAGE_UP_Y = 16;
    private static final int PAGE_DOWN_X = 89;
    private static final int PAGE_DOWN_Y = 72;
    private static final int ARROW_WIDTH = 13;
    private static final int ARROW_HEIGHT = 23;

    // "Page X of Y" readout
    private static final int PAGE_NUM_X = 140;
    private static final int PAGE_NUM_Y = 6;

    // Vanilla "back to station" button — pressing it, or closing this screen
    // outright (ESC/E), both return the player to the Arcane Station menu.
    private static final int BACK_BUTTON_X = 4;
    private static final int BACK_BUTTON_Y = 155;
    private static final int BACK_BUTTON_WIDTH = 16;
    private static final int BACK_BUTTON_HEIGHT = 16;

    // Right-edge padding kept clear of the item name text so it never touches
    // (or overruns) the button's border art.
    private static final int NAME_RIGHT_PADDING = 4;

    public CurrencyExchangeScreen(CurrencyExchangeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.translatable("container.aetherium.exchange_currency"), 206, 177);
    }

    @Override
    protected void init() {
        super.init();

        titleLabelX = 5;
        titleLabelY = 5;
        inventoryLabelX = 9999;

        this.addRenderableWidget(Button.builder(Component.literal("A"), btn -> goBack())
                .bounds(this.leftPos + BACK_BUTTON_X, this.topPos + BACK_BUTTON_Y,
                        BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT)
                .build());
    }

    private void goBack() {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, CurrencyExchangeMenu.BUTTON_BACK);
    }

    @Override
    public void onClose() {
        // Closing this screen (ESC/E) should behave the same as pressing Back — hand
        // the player back to the Arcane Station rather than dropping to the gameplay
        // screen. Deliberately skipping super.onClose() here: that would send the
        // normal container-close packet and clear the client's screen, racing against
        // the ClientboundOpenScreenPacket that BUTTON_BACK's sp.openMenu(...) sends.
        goBack();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0,
                imageWidth, imageHeight, 256, 256);

        boolean hoveringUp = this.isHovering(PAGE_UP_X, PAGE_UP_Y, ARROW_WIDTH, ARROW_HEIGHT, mouseX, mouseY);
        Identifier upTex = hoveringUp ? ARROW_UP_HOVERED_TEXTURE : ARROW_UP_TEXTURE;
        graphics.blit(RenderPipelines.GUI_TEXTURED, upTex, x + PAGE_UP_X, y + PAGE_UP_Y, 0, 0,
                ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);

        boolean hoveringDown = this.isHovering(PAGE_DOWN_X, PAGE_DOWN_Y, ARROW_WIDTH, ARROW_HEIGHT, mouseX, mouseY);
        Identifier downTex = hoveringDown ? ARROW_DOWN_HOVERED_TEXTURE : ARROW_DOWN_TEXTURE;
        graphics.blit(RenderPipelines.GUI_TEXTURED, downTex, x + PAGE_DOWN_X, y + PAGE_DOWN_Y, 0, 0,
                ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);

        // Yellow highlight: Aeth balance
        Aeth balance = AethHelper.get(this.minecraft.player);
        graphics.text(this.font, Component.literal(AethFormatter.format(balance)),
                BALANCE_X, BALANCE_Y, 0xFFaa00a9, false);

        // Red marking: page number
        int page = this.menu.getPage();
        int maxPage = this.menu.getMaxPage();
        String pageText = String.valueOf(page + 1);
        String maxPageText = String.valueOf(maxPage + 1);
        int textWidth = this.font.width(pageText);
        graphics.text(this.font, Component.literal("Page " + pageText + " of " + maxPageText),
                PAGE_NUM_X - textWidth / 2, PAGE_NUM_Y, 0xFF4A4A4A, false);

        // Cyan highlight: paged exchange-button grid
        renderExchangeGrid(graphics, mouseX, mouseY);

        // Orange highlight: "take it out" prompt, shown only while the output slot is empty.
        Slot outputSlot = this.menu.getOutputSlot();
        if (!outputSlot.hasItem()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, TAKE_OUT_TEXTURE,
                    outputSlot.x, outputSlot.y, 0, 0, 16, 16, 16, 16);
        }
    }

    /** Converts leading prefixes like "Double " to "2x " or "Triple " to "3x ". */
    private String convertPrefix(String name) {
        if (name.startsWith("Double ")) {
            return "2x " + name.substring("Double ".length());
        } else if (name.startsWith("Triple ")) {
            return "3x " + name.substring("Triple ".length());
        } else if (name.startsWith("Quadruple ")) {
            return "4x " + name.substring("Quadruple ".length());
        } else if (name.startsWith("Quintuple ")) {
            return "5x " + name.substring("Quintuple ".length());
        } else if (name.startsWith("Sextuple ")) {
            return "6x " + name.substring("Sextuple ".length());
        } else if (name.startsWith("Septuple ")) {
            return "7x " + name.substring("Septuple ".length());
        } else if (name.startsWith("Octuple ")) {
            return "8x " + name.substring("Octuple ".length());
        } else if (name.startsWith("Aetherium ")) {
            return " " + name.substring("Aetherium ".length());
        }

        return name; // Return unchanged if no prefix matches
    }

    /** Truncates text with a trailing "..." if it would exceed maxWidth pixels. */
    private String trimToWidth(String text, int maxWidth) {
        if (maxWidth <= 0) return "";
        if (this.font.width(text) <= maxWidth) return text;
        String ellipsis = "...";
        int ellipsisWidth = this.font.width(ellipsis);
        String trimmed = this.font.plainSubstrByWidth(text, Math.max(0, maxWidth - ellipsisWidth));
        return trimmed + ellipsis;
    }

    private List<Item> getDisplayedItems() {
        List<Item> excluded = AethValues.getExcludedItems();
        int start = this.menu.getPage() * CurrencyExchangeMenu.ITEMS_PER_PAGE;
        if (start >= excluded.size()) return List.of();
        int end = Math.min(start + CurrencyExchangeMenu.ITEMS_PER_PAGE, excluded.size());
        return excluded.subList(start, end);
    }

    private void renderExchangeGrid(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        List<Item> displayed = getDisplayedItems();
        ItemStack currentOutput = this.menu.getOutputSlot().getItem();

        for (int i = 0; i < displayed.size(); i++) {
            Item item = displayed.get(i);
            int col = i % CurrencyExchangeMenu.GRID_COLUMNS;
            int row = i / CurrencyExchangeMenu.GRID_COLUMNS;
            int x = GRID_X + col * (BUTTON_WIDTH + BUTTON_SPACING);
            int y = GRID_Y + row * (BUTTON_HEIGHT + BUTTON_SPACING);

            ItemStack stack = new ItemStack(item);
            Aeth cost = AethValues.lookup(stack);
            boolean canAfford = cost != null && AethHelper.get(this.minecraft.player).canAfford(cost);
            boolean selected = !currentOutput.isEmpty() && currentOutput.getItem() == item;

            Identifier bg = !canAfford ? CANNOT_CONVERT_TEXTURE
                    : (selected ? CAN_CONVERT_SELECTED_TEXTURE : CAN_CONVERT_TEXTURE);
            graphics.blit(RenderPipelines.GUI_TEXTURED, bg, x, y, 0, 0,
                    BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

            // Item icon on the left of the button, name text to its right — the
            // "Aetherium " prefix is dropped so names like "Aetherium Crystal" fit
            // without truncation; a rare still-too-long name falls back to "...".
            graphics.item(stack, x + 4, y + 4);
            int nameX = x + 24;
            int maxNameWidth = (x + BUTTON_WIDTH - NAME_RIGHT_PADDING) - nameX;
            String shortName = convertPrefix(stack.getHoverName().getString());
            // Safety net: even the shortened name gets ellipsis-truncated on the rare
            // item whose name is still too long for the button (e.g. no "Aetherium "
            // prefix to drop, or just a genuinely long name).
            String displayName = trimToWidth(shortName, maxNameWidth);
            graphics.text(this.font, Component.literal(displayName), nameX, y + 9, 0xFFFFFFFF, false);

            if (this.isHovering(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY) && cost != null) {
                Component nameLine = stack.getStyledHoverName();
                Component valueLine = Component.literal(AethFormatter.format(cost))
                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(AETH_VALUE_COLOR)));

                graphics.setTooltipForNextFrame(this.font,
                        List.of(nameLine, valueLine),
                        stack.getTooltipImage(), mouseX, mouseY,
                        stack.get(DataComponents.TOOLTIP_STYLE));
            }
        }
    }

    /**
     * Screen.hasShiftDown() isn't available on this version's Screen (the input
     * refactor to KeyEvent/MouseButtonEvent dropped that static helper), so this
     * checks the same underlying GLFW key state it used to wrap.
     */
    private boolean isShiftDown() {
        Window window = this.minecraft.getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        // Shift+double-click bulk actions. These aren't a vanilla gesture (plain
        // double-click just gathers matching stacks to the cursor), so they're safe to
        // intercept here ahead of everything else, including the vanilla slot handling
        // that `super.mouseClicked` would otherwise run.
        if (event.button() == 0 && doubleClick && isShiftDown()) {
            Slot hovered = this.hoveredSlot;
            if (hovered != null && hovered == this.menu.getOutputSlot()) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, CurrencyExchangeMenu.BUTTON_BULK_BUY);
                return true;
            }
            if (hovered != null && hovered != this.menu.getInputSlot() && hovered.hasItem()) {
                int menuSlotIndex = this.menu.slots.indexOf(hovered);
                if (menuSlotIndex >= 0) {
                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId, CurrencyExchangeMenu.BUTTON_BULK_SELL_BASE + menuSlotIndex);
                    return true;
                }
            }
        }

        if (event.button() == 0) {
            // Note: the region named "up" sends PAGE_DOWN and vice versa — matches the
            // same swapped convention ArcaneStationScreen uses.
            if (this.isHovering(PAGE_UP_X, PAGE_UP_Y, ARROW_WIDTH, ARROW_HEIGHT, event.x(), event.y())) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, CurrencyExchangeMenu.BUTTON_PAGE_DOWN);
                return true;
            }
            if (this.isHovering(PAGE_DOWN_X, PAGE_DOWN_Y, ARROW_WIDTH, ARROW_HEIGHT, event.x(), event.y())) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, CurrencyExchangeMenu.BUTTON_PAGE_UP);
                return true;
            }

            List<Item> excluded = AethValues.getExcludedItems();
            List<Item> displayed = getDisplayedItems();
            for (int i = 0; i < displayed.size(); i++) {
                int col = i % CurrencyExchangeMenu.GRID_COLUMNS;
                int row = i / CurrencyExchangeMenu.GRID_COLUMNS;
                int x = GRID_X + col * (BUTTON_WIDTH + BUTTON_SPACING);
                int y = GRID_Y + row * (BUTTON_HEIGHT + BUTTON_SPACING);

                if (this.isHovering(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, event.x(), event.y())) {
                    int absoluteIndex = excluded.indexOf(displayed.get(i));
                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId, CurrencyExchangeMenu.BUTTON_SELECT_BASE + absoluteIndex);
                    return true;
                }
            }
        }
        return super.mouseClicked(event, doubleClick);
    }
}