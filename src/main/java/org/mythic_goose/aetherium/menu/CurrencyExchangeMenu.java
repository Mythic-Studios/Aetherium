package org.mythic_goose.aetherium.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.block.entity.ArcaneStationBlockEntity;
import org.mythic_goose.aetherium.init.AetheriumMenuTypes;

import java.util.List;

/**
 * The "Currency Exchange" — a secondary screen reached from a button on the
 * Arcane Station GUI (see ArcaneStationMenu.BUTTON_OPEN_EXCHANGE). Unlike the
 * station, the browse list here is NOT the player's learned items — it's the
 * fixed set returned by AethValues.getExcludedItems() (dust/crystal/ingot/etc,
 * items deliberately excluded from station learning so they funnel through
 * here instead).
 *
 * The 2 slots (pay-in / vend-out) are intentionally NOT part of the block
 * entity's persisted inventory. This menu is meant to be opened and closed
 * freely from the station screen, so it keeps its own transient
 * SimpleContainer instead — nothing here needs to survive a server restart.
 * Anything left in the pay-in slot when the menu closes is handed back to the
 * player rather than deleted; the output slot is just a purchase preview and
 * is simply cleared, same as the station's own output slot.
 */
public class CurrencyExchangeMenu extends AbstractContainerMenu {
    public static final int SLOT_INPUT  = 0; // green arrow: consume + pay
    public static final int SLOT_OUTPUT = 1; // orange: extract-only, purchased item
    private static final int CONTAINER_SIZE = 2;

    private final SimpleContainer inventory = new SimpleContainer(CONTAINER_SIZE);
    private final Player player;
    private final BlockPos stationPos;
    private final ContainerData data; // [0] = current page, [1] = max page
    private final Slot outputSlot;

    // The item currently picked from the exchange grid — same "keep re-stocking
    // while affordable" behaviour as the station's browse grid.
    private Item selectedItem;

    // ── Exchange-grid layout: 1 across, 3 down, 88x24 buttons ──────────────
    public static final int GRID_COLUMNS = 1;
    public static final int GRID_ROWS = 3;
    public static final int ITEMS_PER_PAGE = GRID_COLUMNS * GRID_ROWS;

    // ── clickMenuButton() ids, driven by the screen's mouse handling ───────
    public static final int BUTTON_PAGE_UP = 1000;
    public static final int BUTTON_PAGE_DOWN = 1001;
    public static final int BUTTON_SELECT_BASE = 2000; // + absolute index into AethValues.getExcludedItems()
    public static final int BUTTON_BACK = 3000; // vanilla "back to station" button, also fired on screen close

    // Shift-double-click bulk actions, driven from the screen's mouse handling (see
    // CurrencyExchangeScreen#mouseClicked). Kept well above BUTTON_SELECT_BASE's
    // unbounded item-index range so the two can never collide.
    public static final int BUTTON_BULK_BUY = 90000; // shift+double-click the output slot
    public static final int BUTTON_BULK_SELL_BASE = 91000; // + menu slot index of the double-clicked inventory item

    public CurrencyExchangeMenu(int pContainerId, Inventory inv, BlockPos stationPos) {
        this(pContainerId, inv, stationPos, new SimpleContainerData(2));
    }

    public CurrencyExchangeMenu(int pContainerId, Inventory inv, BlockPos stationPos, ContainerData data) {
        super(AetheriumMenuTypes.CURRENCY_EXCHANGE_MENU_TYPE, pContainerId);
        this.player = inv.player;
        this.stationPos = stationPos;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Green arrow — pay-in slot: only accepts items AethValues has excluded
        // from the station (i.e. the currency-exchange item set).
        this.addSlot(new Slot(inventory, SLOT_INPUT, 22, 60) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return AethValues.lookup(stack) != null && AethValues.isExcluded(stack);
            }
        });

        // Orange highlight — output slot: vends whatever was selected from the
        // exchange grid, deducting Aeth from the player as they take items out.
        this.outputSlot = this.addSlot(new Slot(inventory, SLOT_OUTPUT, 60, 60) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return false;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public int getMaxStackSize(ItemStack stack) {
                return 1;
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                ItemStack stack = getItem();
                if (stack.isEmpty()) return false;
                Aeth cost = AethValues.lookup(stack);
                return cost != null && AethHelper.get(pPlayer).canAfford(cost.multiply(stack.getCount()));
            }

            @Override
            public void onTake(Player pPlayer, ItemStack stack) {
                Aeth cost = AethValues.lookup(stack);
                if (cost != null) {
                    AethHelper.spend(pPlayer, cost.multiply(stack.getCount()));
                }
                super.onTake(pPlayer, stack);
            }
        });

        addDataSlots(data);
    }

    public int getPage() { return data.get(0); }
    public int getMaxPage() { return data.get(1); }
    public Slot getOutputSlot() { return outputSlot; }
    public Slot getInputSlot() { return this.slots.get(indexOfContainerSlot(SLOT_INPUT)); }

    /**
     * SLOT_INPUT/SLOT_OUTPUT are indices into this menu's own local 2-slot container,
     * not menu-level slot indices (player inventory + hotbar are added first) — this
     * finds the matching Slot in this.slots by identity of its backing container plus
     * container-slot index.
     */
    private int indexOfContainerSlot(int containerSlotIndex) {
        for (int i = 0; i < this.slots.size(); i++) {
            Slot s = this.slots.get(i);
            if (s.container == this.inventory && s.getContainerSlot() == containerSlotIndex) {
                return i;
            }
        }
        return -1;
    }

    // ── Pagination / selection / back, driven from the screen via handleInventoryButtonClick ──

    @Override
    public boolean clickMenuButton(Player pPlayer, int id) {
        int maxPage = computeMaxPage();

        if (id == BUTTON_PAGE_UP) {
            if (getPage() < maxPage) data.set(0, getPage() + 1);
            return true;
        }
        if (id == BUTTON_PAGE_DOWN) {
            if (getPage() > 0) data.set(0, getPage() - 1);
            return true;
        }
        // Hands the player back to the Arcane Station on the same block. Fired both by
        // the screen's own "back" widget and by onClose() (so ESC/E behaves the same as
        // pressing the button, instead of just closing out to the gameplay screen).
        if (id == BUTTON_BACK) {
            if (pPlayer instanceof ServerPlayer sp) {
                if (sp.level().getBlockEntity(stationPos) instanceof ArcaneStationBlockEntity be) {
                    sp.openMenu(be);
                }
            }
            return true;
        }
        // Shift+double-click on the output slot — keep buying full stacks of the
        // current selection until unaffordable or the player's inventory is full.
        if (id == BUTTON_BULK_BUY) {
            bulkBuyFromOutput(pPlayer);
            return true;
        }
        // Shift+double-click on a player-inventory item — sell every matching stack
        // found anywhere in the player's inventory in one go, not just the clicked one.
        if (id >= BUTTON_BULK_SELL_BASE) {
            int menuSlotIndex = id - BUTTON_BULK_SELL_BASE;
            bulkSellFromInventory(pPlayer, menuSlotIndex);
            return true;
        }
        // Absolute index into AethValues.getExcludedItems() — matches how the station's
        // own browse grid sends absolute indices rather than page-relative ones. This
        // branch must stay last: BUTTON_SELECT_BASE's range is unbounded upward, and the
        // two bulk-action ranges above are carved out ahead of it precisely so they can
        // never be shadowed by a large item index.
        if (id >= BUTTON_SELECT_BASE && id < BUTTON_BULK_BUY) {
            int index = id - BUTTON_SELECT_BASE;
            List<Item> excluded = AethValues.getExcludedItems();
            if (index >= 0 && index < excluded.size()) {
                selectForWithdrawal(excluded.get(index));
            }
            return true;
        }
        return false;
    }

    /**
     * Binary-searches (via repeated Aeth.multiply/canAfford checks — Aeth doesn't
     * expose a divide operation) for the largest count &lt;= upperBound that the
     * player's current Aeth balance can afford at the given per-unit cost.
     */
    private int computeAffordableCount(Player player, Aeth unitCost, int upperBound) {
        if (upperBound <= 0) return 0;
        Aeth balance = AethHelper.get(player);
        if (!balance.canAfford(unitCost)) return 0;

        int hi = 1;
        while (hi < upperBound && balance.canAfford(unitCost.multiply(hi))) {
            hi *= 2;
        }
        hi = Math.min(hi, upperBound);

        int lo = 1, best = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (balance.canAfford(unitCost.multiply(mid))) {
                best = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return best;
    }

    /**
     * Scans the player's hotbar + main inventory (not armor/offhand) for how many
     * more of `item` could physically fit — existing partial stacks' headroom plus
     * every empty slot's full capacity. Read-only; doesn't touch the inventory.
     */
    private int computeInventoryRoom(Player player, Item item, int maxStackSize) {
        Inventory inv = player.getInventory();
        int room = 0;
        for (int i = 0; i < VANILLA_SLOT_COUNT; i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) {
                room += maxStackSize;
            } else if (stack.getItem() == item && stack.getCount() < maxStackSize) {
                room += maxStackSize - stack.getCount();
            }
        }
        return room;
    }

    /**
     * Manually places up to `amount` of `item` into the player's hotbar + main
     * inventory — topping off existing partial stacks first, then filling empty
     * slots — and returns exactly how many were placed. Deterministic by
     * construction, so the caller never has to guess at how much actually landed.
     */
    private int insertIntoInventory(Player player, Item item, int amount, int maxStackSize) {
        Inventory inv = player.getInventory();
        int remaining = amount;

        for (int i = 0; i < VANILLA_SLOT_COUNT && remaining > 0; i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item && stack.getCount() < maxStackSize) {
                int space = maxStackSize - stack.getCount();
                int add = Math.min(space, remaining);
                stack.grow(add);
                remaining -= add;
            }
        }
        for (int i = 0; i < VANILLA_SLOT_COUNT && remaining > 0; i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) {
                int add = Math.min(maxStackSize, remaining);
                inv.setItem(i, new ItemStack(item, add));
                remaining -= add;
            }
        }
        return amount - remaining;
    }

    /**
     * Plain shift-click on the output slot: buy as many of the current item as the
     * player can afford, capped at that item's own real max stack size (not the
     * slot's overridden 1) — a single click gets you "a stack, if you can afford it".
     */
    private ItemStack quickMoveFromOutputSlot(Player playerIn) {
        ItemStack preview = outputSlot.getItem();
        if (preview.isEmpty() || !outputSlot.mayPickup(playerIn)) return ItemStack.EMPTY;

        Item item = preview.getItem();
        Aeth unitCost = AethValues.lookup(preview);
        if (unitCost == null) return ItemStack.EMPTY;

        int realMaxStack = preview.getMaxStackSize();
        int room = computeInventoryRoom(playerIn, item, realMaxStack);
        int wanted = Math.min(realMaxStack, room);
        int affordable = computeAffordableCount(playerIn, unitCost, wanted);
        if (affordable <= 0) return ItemStack.EMPTY;

        int given = insertIntoInventory(playerIn, item, affordable, realMaxStack);
        if (given <= 0) return ItemStack.EMPTY;

        AethHelper.spend(playerIn, unitCost.multiply(given));
        outputSlot.set(ItemStack.EMPTY); // preview consumed; broadcastChanges restocks it next tick
        return new ItemStack(item, given);
    }

    /**
     * Shift+double-click bulk-buy: gives the player as many of the current selection
     * as they can afford, capped at however many will actually fit in their
     * inventory — whichever limit is hit first. Computed up front against the
     * player's current balance and free space rather than looped, so there's no
     * dependence on however the platform's inventory-insert helper reports leftovers.
     */
    private void bulkBuyFromOutput(Player player) {
        ItemStack preview = outputSlot.getItem();
        Item item = selectedItem != null ? selectedItem : (preview.isEmpty() ? null : preview.getItem());
        if (item == null) return;

        Aeth unitCost = AethValues.lookup(new ItemStack(item));
        if (unitCost == null) return;

        int realMaxStack = new ItemStack(item).getMaxStackSize();
        int room = computeInventoryRoom(player, item, realMaxStack);
        if (room <= 0) return; // no space at all — nothing to do

        int affordable = computeAffordableCount(player, unitCost, room);
        if (affordable <= 0) return; // can't afford even one

        int given = insertIntoInventory(player, item, affordable, realMaxStack);
        if (given <= 0) return;

        AethHelper.spend(player, unitCost.multiply(given));
        outputSlot.set(ItemStack.EMPTY); // preview consumed; restocked next tick if still affordable
    }

    /**
     * Shift+double-click bulk-sell: sweeps the player's entire inventory for every
     * stack matching the double-clicked item and cashes them all in at once, rather
     * than requiring one shift-click per stack.
     */
    private void bulkSellFromInventory(Player player, int menuSlotIndex) {
        if (menuSlotIndex < 0 || menuSlotIndex >= slots.size()) return;
        Slot clicked = slots.get(menuSlotIndex);
        if (clicked == null || !clicked.hasItem()) return;

        Item item = clicked.getItem().getItem();
        ItemStack sample = new ItemStack(item);
        if (!AethValues.isExcluded(sample)) return; // only the currency-exchange item set sells here
        Aeth unitCost = AethValues.lookup(sample);
        if (unitCost == null) return;

        Inventory playerInv = player.getInventory();
        int totalCount = 0;
        for (int i = 0; i < playerInv.getContainerSize(); i++) {
            ItemStack stack = playerInv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                totalCount += stack.getCount();
                playerInv.setItem(i, ItemStack.EMPTY);
            }
        }
        if (totalCount == 0) return;

        AethHelper.add(player, unitCost.multiply(totalCount));
    }

    /** Marks this item as the active selection and immediately tries to stock it. */
    private void selectForWithdrawal(Item item) {
        if (AethValues.lookup(new ItemStack(item)) == null) return;
        this.selectedItem = item;

        ItemStack current = inventory.getItem(SLOT_OUTPUT);
        if (!current.isEmpty() && current.getItem() != item) {
            inventory.setItem(SLOT_OUTPUT, ItemStack.EMPTY);
        }
        refillOutputSlotIfAffordable();
    }

    /** QOL: keeps the output slot stocked with the selection for as long as it's affordable. */
    private void refillOutputSlotIfAffordable() {
        if (selectedItem == null) return;
        if (!inventory.getItem(SLOT_OUTPUT).isEmpty()) return;

        Aeth cost = AethValues.lookup(new ItemStack(selectedItem));
        if (cost == null) {
            selectedItem = null; // lost its value somehow (config reload etc.) — drop selection
            return;
        }

        if (AethHelper.get(player).canAfford(cost)) {
            inventory.setItem(SLOT_OUTPUT, new ItemStack(selectedItem, 1));
        }
    }

    private int computeMaxPage() {
        int total = AethValues.getExcludedItems().size();
        return total == 0 ? 0 : (total - 1) / ITEMS_PER_PAGE;
    }

    // ── Pay-in slot processing ──────────────────────────────────────────────
    // Runs server-side every tick the menu is open, same as ArcaneStationMenu.

    @Override
    public void broadcastChanges() {
        if (!player.level().isClientSide()) {
            processInputSlot();
            refillOutputSlotIfAffordable();

            int maxPage = computeMaxPage();
            data.set(1, maxPage);
            if (getPage() > maxPage) data.set(0, maxPage);
        }
        super.broadcastChanges();
    }

    /** Green arrow: consume the stack and pay the player its Aeth value. */
    private void processInputSlot() {
        ItemStack stack = inventory.getItem(SLOT_INPUT);
        if (stack.isEmpty()) return;
        if (!AethValues.isExcluded(stack)) return; // shouldn't happen, mayPlace already filters

        Aeth unitValue = AethValues.lookup(stack);
        if (unitValue == null) return;

        AethHelper.add(player, unitValue.multiply(stack.getCount()));
        inventory.setItem(SLOT_INPUT, ItemStack.EMPTY);
    }

    // Same vanilla shift-click merge pattern as ArcaneStationMenu, just with a 2-slot
    // machine inventory instead of 3. CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = CONTAINER_SIZE;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        // Shift-click on the output slot bypasses the normal vanilla merge entirely:
        // the slot's real max stack size is capped at 1 (just enough for the "next
        // item up" preview), so a plain move would only ever hand over a single item.
        // This buys as many as the player can afford in one go instead, capped at the
        // item's own real max stack size.
        if (sourceSlot == outputSlot) {
            return quickMoveFromOutputSlot(playerIn);
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, copyOfSourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        // No persisted block-entity container to defer to here, so check directly:
        // the station block must still exist, and the player has to still be near it.
        if (!(pPlayer.level().getBlockEntity(stationPos) instanceof ArcaneStationBlockEntity)) {
            return false;
        }
        return pPlayer.distanceToSqr(stationPos.getX() + 0.5, stationPos.getY() + 0.5, stationPos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (!pPlayer.level().isClientSide()) {
            // Give back whatever's still sitting in the pay-in slot rather than deleting
            // it — the player handed over a real item, they should get it back if they
            // navigate away before it's processed.
            ItemStack leftover = inventory.getItem(SLOT_INPUT);
            if (!leftover.isEmpty()) {
                pPlayer.getInventory().placeItemBackInInventory(leftover);
            }
            // The output slot is just a purchase preview, not real stock — clear it,
            // same as the station's own output-slot cleanup.
            inventory.clearContent();
            this.selectedItem = null;
        }
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 22 + l * 18, 97 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 22 + i * 18, 155));
        }
    }
}