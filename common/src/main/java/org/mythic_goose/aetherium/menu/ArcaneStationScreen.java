package org.mythic_goose.aetherium.menu;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.lwjgl.glfw.GLFW;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;
import org.mythic_goose.aetherium.api.AethKnowledge;
import org.mythic_goose.aetherium.api.AethTooltips;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.client.AethFormatter;

import java.util.ArrayList;
import java.util.List;

public class ArcaneStationScreen extends AbstractContainerScreen<ArcaneStationMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID,"textures/gui/arcane_station/arcane_station_gui.png");
    // Assumed 24x24, single-state (no separate hover frame). Adjust the blit() calls
    // below if your actual files differ in size or include hover/pressed states.
    private static final Identifier ARROW_UP_TEXTURE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/sprites/page_up.png");
    private static final Identifier ARROW_DOWN_TEXTURE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/sprites/page_down.png");
    // These live under textures/gui/sprites/, Minecraft's GUI sprite-atlas folder, so
    // they're referenced as sprite ids (no .png, no path prefix) and drawn with
    // blitSprite — same mechanism vanilla uses for its own slot-highlight sprites.
    private static final Identifier PAGE_UP_HIGHLIGHT_SPRITE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/sprites/page_up_hovered.png");
    private static final Identifier PAGE_DOWN_HIGHLIGHT_SPRITE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/sprites/page_down_hovered.png");
    // Assumed 16x16 to match the slot itself.
    private static final Identifier OUTPUT_EMPTY_TEXTURE =
            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/gui/sprites/take_out.png");

    // The color the Aetherium value line uses in the browse-grid tooltip.
    private static final int AETH_VALUE_COLOR = 0xaa00a9;

    // ── Layout constants ── local (leftPos/topPos-relative) coordinates. ──

    // the Aeth balance readout
    private static final int BALANCE_X = 17;
    private static final int BALANCE_Y = 82;

    // 5x4 browse grid
    private static final int GRID_X = 94;
    private static final int GRID_Y = 22;
    private static final int CELL_SIZE = 18;

    // page up/down buttons
    private static final int PAGE_UP_X = 79;
    private static final int PAGE_UP_Y = 18;
    private static final int PAGE_DOWN_X = 79;
    private static final int PAGE_DOWN_Y = 72;
    private static final int ARROW_WIDTH = 13;
    private static final int ARROW_HEIGHT = 23;

    // page number readout
    private static final int PAGE_NUM_X = 100;
    private static final int PAGE_NUM_Y = 8;

    // Item search box — filters the browse grid by name. While text is entered, the
    // grid shows up to ITEMS_PER_PAGE matches (ignoring the page arrows); clearing
    // the box returns to normal paged browsing.
    private static final int SEARCH_X = 141;
    private static final int SEARCH_Y = 8;
    private static final int SEARCH_WIDTH = 59;
    private static final int SEARCH_HEIGHT = 10;

    private EditBox searchBox;
    private String lastQuery = "";
    private int searchPage = 0;

    // Button that opens the Currency Exchange secondary screen. Position is a
    // placeholder — move it to wherever your background art has room for it.
    private static final int EXCHANGE_BUTTON_X = 4;
    private static final int EXCHANGE_BUTTON_Y = 133;
    private static final int EXCHANGE_BUTTON_WIDTH = 16;
    private static final int EXCHANGE_BUTTON_HEIGHT = 16;

    public ArcaneStationScreen(ArcaneStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 206, 177);
    }

    @Override
    protected void init() {
        super.init();

        titleLabelX = 5;
        titleLabelY = 5;

        inventoryLabelX = 9999;

        // Preserve whatever was typed across an init() (e.g. window resize).
        String previousQuery = searchBox != null ? searchBox.getValue() : "";
        searchBox = new EditBox(this.font, this.leftPos + SEARCH_X, this.topPos + SEARCH_Y,
                SEARCH_WIDTH, SEARCH_HEIGHT, Component.literal("Search"));
        searchBox.setMaxLength(50);
        searchBox.setBordered(false);
        searchBox.setValue(previousQuery);
        this.addRenderableWidget(searchBox);

        // Base-menu button: hands the player off to the Currency Exchange menu.
        // The actual screen swap happens server-side (see ArcaneStationMenu's
        // BUTTON_OPEN_EXCHANGE handling) — this button just fires that button id.
        this.addRenderableWidget(Button.builder(Component.literal("E"), btn ->
                        this.minecraft.gameMode.handleInventoryButtonClick(
                                this.menu.containerId, ArcaneStationMenu.BUTTON_OPEN_EXCHANGE))
                .bounds(this.leftPos + EXCHANGE_BUTTON_X, this.topPos + EXCHANGE_BUTTON_Y,
                        EXCHANGE_BUTTON_WIDTH, EXCHANGE_BUTTON_HEIGHT)
                .build());
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (searchBox != null && searchBox.isFocused()) {
            if (searchBox.keyPressed(event)) return true;
            // Don't let "open/close inventory" (default E) close the screen while
            // typing — it should just be a letter in the search box instead.
            if (this.minecraft.options.keyInventory.matches(event)) return false;
        }
        return super.keyPressed(event);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0,
                imageWidth, imageHeight, 256, 256);

        // Brown circles: page up/down arrow icons — swap to the highlighted sprite on hover.
        // Note: the "up" position sends BUTTON_PAGE_DOWN and vice versa (see mouseClicked),
        // but the hover art itself should still match physical position, not the action.
        boolean hoveringUp = this.isHovering(PAGE_UP_X, PAGE_UP_Y, ARROW_WIDTH, ARROW_HEIGHT, mouseX, mouseY);
        if (hoveringUp) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, PAGE_UP_HIGHLIGHT_SPRITE,
                    x + PAGE_UP_X, y + PAGE_UP_Y, 0, 0,
                    ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        } else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_UP_TEXTURE,
                    x + PAGE_UP_X, y + PAGE_UP_Y, 0, 0,
                    ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        }

        boolean hoveringDown = this.isHovering(PAGE_DOWN_X, PAGE_DOWN_Y, ARROW_WIDTH, ARROW_HEIGHT, mouseX, mouseY);
        if (hoveringDown) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, PAGE_DOWN_HIGHLIGHT_SPRITE,
                    x + PAGE_DOWN_X, y + PAGE_DOWN_Y, 0, 0,
                    ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        } else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_DOWN_TEXTURE,
                    x + PAGE_DOWN_X, y + PAGE_DOWN_Y, 0, 0,
                    ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        }
    }

    // extractLabels runs inside the already-translated (leftPos, topPos) matrix,
    // so everything here is drawn in local coordinates — same space the vanilla
    // slots use. mouseX/mouseY passed in are absolute screen coordinates though
    // (that's what isHovering()'s xm/ym subtraction expects).
    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);

        // Yellow highlight: Aeth balance
        Aeth balance = AethHelper.get(this.minecraft.player);
        graphics.text(this.font, Component.literal(AethFormatter.format(balance)),
                BALANCE_X, BALANCE_Y, 0xFFaa00a9, false);

        // Red circle: page number — reflects the search's own page count while active.
        List<ItemStack> known = AethKnowledge.getKnownStacks(this.minecraft.player);
        List<ItemStack> source = resolveSourceList(known);
        int page = getEffectivePage();
        int maxPage = getEffectiveMaxPage(source.size());
        String pageText = String.valueOf(page + 1);
        String maxPageText = String.valueOf(maxPage + 1);
        int textWidth = this.font.width(pageText);
        graphics.text(this.font, Component.literal(pageText + " of " + maxPageText),
                PAGE_NUM_X - textWidth / 2, PAGE_NUM_Y, 0xFF4A4A4A, false);

        // Cyan highlight: paged browse grid
        renderKnownItemGrid(graphics, mouseX, mouseY, known, source);

        // Orange highlight: "take it out" texture, shown only while the output slot is empty.
        Slot outputSlot = this.menu.getOutputSlot();
        if (!outputSlot.hasItem()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, OUTPUT_EMPTY_TEXTURE,
                    outputSlot.x, outputSlot.y, 0, 0, 16, 16, 16, 16);
        }
    }

    private boolean isSearching() {
        return searchBox != null && !searchBox.getValue().trim().isEmpty();
    }

    private List<ItemStack> filterByQuery(List<ItemStack> known, String query) {
        String[] tokens = query.split("\\s+");
        List<ItemStack> matches = new ArrayList<>();

        outer:
        for (ItemStack stack : known) {
            for (String token : tokens) {
                if (token.isEmpty()) continue;
                if (!matchesToken(stack, token)) continue outer;
            }
            matches.add(stack);
        }
        return matches;
    }

    /**
     * A single space-separated search term. "@foo" filters by mod id, "#foo" filters
     * by tag (path or full "namespace:path"), anything else is a plain name search that
     * also matches enchantment names (so "mending" finds the Mending book and "sharpness"
     * finds a Sharpness sword). All terms must match (AND), same as JEI.
     */
    private boolean matchesToken(ItemStack stack, String token) {
        Item item = stack.getItem();

        if (token.startsWith("@")) {
            String modQuery = token.substring(1).toLowerCase();
            if (modQuery.isEmpty()) return true;
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            return id.getNamespace().toLowerCase().contains(modQuery);
        }

        if (token.startsWith("#")) {
            String tagQuery = token.substring(1).toLowerCase();
            if (tagQuery.isEmpty()) return true;
            return BuiltInRegistries.ITEM.wrapAsHolder(item)
                    .tags().anyMatch(tagKey -> {
                        Identifier tagId = tagKey.location();
                        String full = tagId.getNamespace() + ":" + tagId.getPath();
                        return tagId.getPath().toLowerCase().contains(tagQuery)
                                || full.toLowerCase().contains(tagQuery);
                    });
        }

        StringBuilder name = new StringBuilder(stack.getHoverName().getString().toLowerCase());
        for (var e : AethValues.enchantmentsOf(stack).entrySet()) {
            name.append(' ').append(Enchantment.getFullname(e.getKey(), e.getIntValue()).getString().toLowerCase());
        }
        return name.toString().contains(token.toLowerCase());
    }

    /**
     * The list actually being paged through right now — the full known-stacks list
     * normally, or every search match (not capped) while searching. Also resets
     * searchPage back to 0 whenever the query text itself changes.
     */
    private List<ItemStack> resolveSourceList(List<ItemStack> known) {
        String query = searchBox != null ? searchBox.getValue().trim().toLowerCase() : "";
        if (!query.equals(lastQuery)) {
            lastQuery = query;
            searchPage = 0;
        }
        return query.isEmpty() ? known : filterByQuery(known, query);
    }

    private int getEffectivePage() {
        return isSearching() ? searchPage : this.menu.getPage();
    }

    private int getEffectiveMaxPage(int sourceSize) {
        if (!isSearching()) return this.menu.getMaxPage();
        return sourceSize == 0 ? 0 : (sourceSize - 1) / ArcaneStationMenu.ITEMS_PER_PAGE;
    }

    /**
     * Stacks to actually show in the grid this frame: one page (ITEMS_PER_PAGE) of
     * whatever resolveSourceList() returns, using getEffectivePage() as the offset.
     * While searching, paging is entirely client-side (searchPage), since the server
     * has no idea what's been typed — only the item-select action needs the server,
     * and that already works off an absolute known-stacks index regardless of paging.
     */
    private List<ItemStack> computeDisplayedItems(List<ItemStack> known) {
        List<ItemStack> source = resolveSourceList(known);
        int page = getEffectivePage();
        int start = page * ArcaneStationMenu.ITEMS_PER_PAGE;
        if (start >= source.size()) return List.of();
        int end = Math.min(start + ArcaneStationMenu.ITEMS_PER_PAGE, source.size());
        return source.subList(start, end);
    }

    /** ItemStack has no value-based equals, so indexOf() can't be used on stack lists. */
    private static int knownIndexOf(List<ItemStack> known, ItemStack target) {
        for (int i = 0; i < known.size(); i++) {
            if (ItemStack.isSameItemSameComponents(known.get(i), target)) return i;
        }
        return -1;
    }

    private void renderKnownItemGrid(GuiGraphicsExtractor graphics, int mouseX, int mouseY, List<ItemStack> known, List<ItemStack> source) {
        int page = getEffectivePage();
        int start = page * ArcaneStationMenu.ITEMS_PER_PAGE;
        List<ItemStack> displayed = start >= source.size() ? List.of()
                : source.subList(start, Math.min(start + ArcaneStationMenu.ITEMS_PER_PAGE, source.size()));

        for (int i = 0; i < displayed.size(); i++) {
            ItemStack stack = displayed.get(i);

            int col = i % ArcaneStationMenu.GRID_COLUMNS;
            int row = i / ArcaneStationMenu.GRID_COLUMNS;
            int x = GRID_X + col * CELL_SIZE;
            int y = GRID_Y + row * CELL_SIZE;

            graphics.item(stack, x, y);
            graphics.itemDecorations(this.font, stack, x, y, null);

            if (this.isHovering(x, y, 16, 16, mouseX, mouseY)) {
                // Translucent, not opaque — this was fully blotting out the item icon
                // it's drawn on top of.
                graphics.fill(x, y, x + 16, y + 16, 0x55FFFFFF);
                Aeth value = AethValues.lookup(stack);
                if (value != null) {
                    // Vanilla tooltip lines (name, enchantments, ...). The standard
                    // "Aeth: ..." label is suppressed while building them, because the
                    // fixed-colour value line below is this grid's own version of it.
                    List<Component> lines;
                    AethTooltips.suppressLabel = true;
                    try {
                        lines = new ArrayList<>(Screen.getTooltipFromItem(this.minecraft, stack));
                    } finally {
                        AethTooltips.suppressLabel = false;
                    }

                    // Always the very last line, after the enchantments.
                    lines.add(Component.literal(AethFormatter.format(value))
                            .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(AETH_VALUE_COLOR))));

                    graphics.setTooltipForNextFrame(this.font,
                            lines,
                            stack.getTooltipImage(), mouseX, mouseY,
                            stack.get(DataComponents.TOOLTIP_STYLE));
                }
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
        // The search box overlaps the grid area, so give it first refusal on any click
        // within its bounds before our own hit-testing below can steal it.
        if (searchBox != null && this.isHovering(SEARCH_X, SEARCH_Y, SEARCH_WIDTH, SEARCH_HEIGHT, event.x(), event.y())) {
            return super.mouseClicked(event, doubleClick);
        }

        // Shift+double-click bulk actions. These aren't a vanilla gesture (plain
        // double-click just gathers matching stacks to the cursor), so it's safe to
        // intercept here ahead of the vanilla slot handling `super.mouseClicked` would
        // otherwise run.
        if (event.button() == 0 && doubleClick && isShiftDown()) {
            Slot hovered = this.hoveredSlot;
            if (hovered != null && hovered == this.menu.getOutputSlot()) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, ArcaneStationMenu.BUTTON_BULK_BUY);
                return true;
            }
            if (hovered != null && hovered != this.menu.getInputSlot() && hovered != this.menu.getLearnSlot() && hovered.hasItem()) {
                int menuSlotIndex = this.menu.slots.indexOf(hovered);
                if (menuSlotIndex >= 0) {
                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId, ArcaneStationMenu.BUTTON_BULK_SELL_BASE + menuSlotIndex);
                    return true;
                }
            }
        }

        if (event.button() == 0) {
            // Note: the region named "up" sends the PAGE_DOWN button and vice versa —
            // this was flipped from what the visual arrows implied, so it's swapped here
            // rather than renaming the PAGE_UP/PAGE_DOWN constants and logic in the menu.
            // While searching, paging is client-only (searchPage) since the server has
            // no idea what's been typed into the box.
            if (this.isHovering(PAGE_UP_X, PAGE_UP_Y, ARROW_WIDTH, ARROW_HEIGHT, event.x(), event.y())) {
                if (isSearching()) {
                    if (searchPage > 0) searchPage--;
                } else {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, ArcaneStationMenu.BUTTON_PAGE_DOWN);
                }
                return true;
            }
            if (this.isHovering(PAGE_DOWN_X, PAGE_DOWN_Y, ARROW_WIDTH, ARROW_HEIGHT, event.x(), event.y())) {
                if (isSearching()) {
                    List<ItemStack> known = AethKnowledge.getKnownStacks(this.minecraft.player);
                    List<ItemStack> source = resolveSourceList(known);
                    int maxPage = getEffectiveMaxPage(source.size());
                    if (searchPage < maxPage) searchPage++;
                } else {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, ArcaneStationMenu.BUTTON_PAGE_UP);
                }
                return true;
            }

            List<ItemStack> known = AethKnowledge.getKnownStacks(this.minecraft.player);
            List<ItemStack> displayed = computeDisplayedItems(known);
            for (int i = 0; i < displayed.size(); i++) {
                int col = i % ArcaneStationMenu.GRID_COLUMNS;
                int row = i / ArcaneStationMenu.GRID_COLUMNS;
                int x = GRID_X + col * CELL_SIZE;
                int y = GRID_Y + row * CELL_SIZE;

                if (this.isHovering(x, y, 16, 16, event.x(), event.y())) {
                    int knownIndex = knownIndexOf(known, displayed.get(i));
                    if (knownIndex >= 0) {
                        this.minecraft.gameMode.handleInventoryButtonClick(
                                this.menu.containerId, ArcaneStationMenu.BUTTON_SELECT_BASE + knownIndex);
                    }
                    return true;
                }
            }
        }
        return super.mouseClicked(event, doubleClick);
    }
}