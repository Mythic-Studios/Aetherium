package org.mythic_goose.aetherium.menu;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;
import org.mythic_goose.aetherium.api.AethKnowledge;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.block.entity.ArcaneStationBlockEntity;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.init.AetheriumMenuTypes;

import java.util.List;

public class ArcaneStationMenu extends AbstractContainerMenu {
    private final Container inventory; // the block entity's own 3-slot container
    private final Player player;
    private final ContainerData data;  // [0] = current page, [1] = max page
    public final ArcaneStationBlockEntity blockEntity;
    private final Slot outputSlot;

    // The item currently picked from the browse grid. Kept as menu state (not just
    // "is the output slot non-empty") so it can be auto-refilled every tick while
    // affordable, and cleared entirely when the menu closes.
    private Item selectedItem;

    // ── Browse-grid layout: 6 across, 4 down ────────────────────────────────
    public static final int GRID_COLUMNS = 6;
    public static final int GRID_ROWS = 4;
    public static final int ITEMS_PER_PAGE = GRID_COLUMNS * GRID_ROWS;

    // ── clickMenuButton() ids, driven by the screen's mouse handling ───────
    public static final int BUTTON_PAGE_UP = 1000;
    public static final int BUTTON_PAGE_DOWN = 1001;
    public static final int BUTTON_SELECT_BASE = 2000; // + absolute index into AethKnowledge.getKnownItems()
    public static final int BUTTON_OPEN_EXCHANGE = 4000; // opens the Currency Exchange secondary screen

    // Shift-double-click bulk actions, driven from the screen's mouse handling (see
    // ArcaneStationScreen#mouseClicked). Kept well above BUTTON_SELECT_BASE's unbounded
    // item-index range so the two can never collide.
    public static final int BUTTON_BULK_BUY = 90000; // shift+double-click the output slot
    public static final int BUTTON_BULK_SELL_BASE = 91000; // + menu slot index of the double-clicked inventory item

    public ArcaneStationMenu(int pContainerId, Inventory inv, BlockPos blockPos) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(blockPos), new SimpleContainerData(2));
    }

    public ArcaneStationMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(AetheriumMenuTypes.ARCANE_STATION_MENU_TYPE, pContainerId);
        blockEntity = ((ArcaneStationBlockEntity) entity);
        this.inventory = blockEntity;
        this.player = inv.player;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Green arrow — spend slot: consuming items get paid out and learnt.
        this.addSlot(new Slot(inventory, ArcaneStationBlockEntity.SLOT_INPUT, 26, 22));

        // Purple highlight — learn slot: teaches the station without consuming.
        this.addSlot(new Slot(inventory, ArcaneStationBlockEntity.SLOT_LEARN, 8, 40));

        // Orange highlight — output slot: vends whatever was selected in the browse grid,
        // deducting Aeth from the player as they take items out.
        this.outputSlot = this.addSlot(new Slot(inventory, ArcaneStationBlockEntity.SLOT_OUTPUT, 26, 58) {
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
    public Slot getInputSlot() { return this.slots.get(indexOfContainerSlot(ArcaneStationBlockEntity.SLOT_INPUT)); }
    public Slot getLearnSlot() { return this.slots.get(indexOfContainerSlot(ArcaneStationBlockEntity.SLOT_LEARN)); }

    private int indexOfContainerSlot(int containerSlotIndex) {
        for (int i = 0; i < this.slots.size(); i++) {
            Slot s = this.slots.get(i);
            if (s.container == this.inventory && s.getContainerSlot() == containerSlotIndex) {
                return i;
            }
        }
        return -1;
    }

    // ── Pagination / selection, driven from the screen via handleInventoryButtonClick ──

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
        // Opens the Currency Exchange as a fresh menu on the same station block.
        // Server-side only: openMenu() sends a new ClientboundOpenScreenPacket, and the
        // client's registered screen factory for CURRENCY_EXCHANGE_MENU_TYPE takes care
        // of swapping the open screen — this ArcaneStationScreen doesn't need to do
        // anything itself beyond firing this button.
        if (id == BUTTON_OPEN_EXCHANGE) {
            if (pPlayer instanceof ServerPlayer sp) {
                sp.openMenu(new ExtendedMenuProvider<BlockPos>() {
                    @Override
                    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player p) {
                        return new CurrencyExchangeMenu(syncId, inv, blockEntity.getBlockPos());
                    }

                    @Override
                    public Component getDisplayName() {
                        return blockEntity.getDisplayName();
                    }

                    @Override
                    public BlockPos getScreenOpeningData(ServerPlayer player) {
                        return blockEntity.getBlockPos();
                    }
                });
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

        // Selection is sent as an absolute index into AethKnowledge.getKnownItems(),
        // not a page-relative one — this lets the search box (client-side) select any
        // matching item regardless of which page it'd normally fall on.
        if (id >= BUTTON_SELECT_BASE && id < BUTTON_BULK_BUY) {
            int knownIndex = id - BUTTON_SELECT_BASE;
            List<Item> known = AethKnowledge.getKnownItems(pPlayer);
            if (knownIndex >= 0 && knownIndex < known.size()) {
                selectForWithdrawal(known.get(knownIndex));
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
        blockEntity.setItem(ArcaneStationBlockEntity.SLOT_OUTPUT, ItemStack.EMPTY); // restocked next tick
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
        ItemStack preview = blockEntity.getItem(ArcaneStationBlockEntity.SLOT_OUTPUT);
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
        blockEntity.setItem(ArcaneStationBlockEntity.SLOT_OUTPUT, ItemStack.EMPTY);
    }

    /**
     * Shift+double-click bulk-sell: sweeps the player's entire inventory for every
     * stack matching the double-clicked item and cashes them all in at once (learning
     * it too, same as a normal sell), rather than requiring one shift-click per stack.
     */
    private void bulkSellFromInventory(Player player, int menuSlotIndex) {
        if (menuSlotIndex < 0 || menuSlotIndex >= slots.size()) return;
        Slot clicked = slots.get(menuSlotIndex);
        if (clicked == null || !clicked.hasItem()) return;

        Item item = clicked.getItem().getItem();
        ItemStack sample = new ItemStack(item);
        if (AethValues.isExcluded(sample)) return; // excluded items funnel through the Currency Exchange instead
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

        if (item == AetheriumItems.TOME_OF_ARCANA) {
            for (Item learnable : AethValues.getAllLearnableItems()) {
                AethKnowledge.learn(player, learnable); // no-op if already known
            }
        } else {
            AethKnowledge.learn(player, item);
        }
        AethHelper.add(player, unitCost.multiply(totalCount));
    }

    /** Marks this item as the active selection and immediately tries to stock it. */
    private void selectForWithdrawal(Item item) {
        if (AethValues.lookup(new ItemStack(item)) == null) return;
        this.selectedItem = item;

        // If the slot is currently holding a *different* item (e.g. the player picked
        // something else before taking the old one out), discard that stale preview so
        // the newly selected item shows immediately instead of queuing behind it.
        ItemStack current = blockEntity.getItem(ArcaneStationBlockEntity.SLOT_OUTPUT);
        if (!current.isEmpty() && current.getItem() != item) {
            blockEntity.setItem(ArcaneStationBlockEntity.SLOT_OUTPUT, ItemStack.EMPTY);
        }

        refillOutputSlotIfAffordable();
    }

    /**
     * QOL: keeps the output slot stocked with one of the selected item for as long as
     * the player can afford it — so buying doesn't require re-clicking the browse grid
     * every time. Only tops up when empty; never overrides what's already sitting there.
     */
    private void refillOutputSlotIfAffordable() {
        if (selectedItem == null) return;
        if (!blockEntity.getItem(ArcaneStationBlockEntity.SLOT_OUTPUT).isEmpty()) return;

        Aeth cost = AethValues.lookup(new ItemStack(selectedItem));
        if (cost == null) {
            selectedItem = null; // item lost its value somehow (config reload etc.) — drop selection
            return;
        }

        if (AethHelper.get(player).canAfford(cost)) {
            blockEntity.setItem(ArcaneStationBlockEntity.SLOT_OUTPUT, new ItemStack(selectedItem, 1));
        }
    }

    private int computeMaxPage() {
        int known = AethKnowledge.getKnownItems(player).size();
        return known == 0 ? 0 : (known - 1) / ITEMS_PER_PAGE;
    }

    // ── Input / Learn slot processing ──────────────────────────────────────
    // Runs server-side every tick the menu is open (broadcastChanges is called
    // once per tick for the container menu of each player with it open).

    @Override
    public void broadcastChanges() {
        if (!player.level().isClientSide()) {
            processInputSlot();
            processLearnSlot();
            refillOutputSlotIfAffordable();

            int maxPage = computeMaxPage();
            data.set(1, maxPage);
            if (getPage() > maxPage) data.set(0, maxPage);
        }
        super.broadcastChanges();
    }

    /** Green arrow: consume the stack, pay the player, and learn the item. */
    private void processInputSlot() {
        ItemStack stack = blockEntity.getItem(ArcaneStationBlockEntity.SLOT_INPUT);
        if (stack.isEmpty()) return;
        if (AethValues.isExcluded(stack)) return; // not processed — canPlaceItem should already block this

        Aeth unitValue = AethValues.lookup(stack);
        if (unitValue == null) return; // shouldn't happen (canPlaceItem already filters), but be safe

        if (stack.getItem() == AetheriumItems.TOME_OF_ARCANA) {
            for (Item learnable : AethValues.getAllLearnableItems()) {
                AethKnowledge.learn(player, learnable); // no-op if already known, per your javadoc
            }
            AethHelper.add(player, unitValue.multiply(stack.getCount()));
            AethKnowledge.learn(player, stack.getItem());
            blockEntity.setItem(ArcaneStationBlockEntity.SLOT_INPUT, ItemStack.EMPTY);
            return;
        }

        AethHelper.add(player, unitValue.multiply(stack.getCount()));
        AethKnowledge.learn(player, stack.getItem());
        blockEntity.setItem(ArcaneStationBlockEntity.SLOT_INPUT, ItemStack.EMPTY);
    }

    /** Purple highlight: learn the item, but leave it in the slot for the player to take back. */
    private void processLearnSlot() {
        ItemStack stack = blockEntity.getItem(ArcaneStationBlockEntity.SLOT_LEARN);
        if (stack.isEmpty()) return;
        if (AethValues.isExcluded(stack)) return; // not processed — canPlaceItem should already block this
        if (AethValues.lookup(stack) == null) return;

        if (stack.getItem() == AetheriumItems.TOME_OF_ARCANA) {
            for (Item learnable : AethValues.getAllLearnableItems()) {
                AethKnowledge.learn(player, learnable); // no-op if already known, per your javadoc
            }
            return;
        }

        AethKnowledge.learn(player, stack.getItem());
        // Deliberately not cleared — "without consuming it".
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM

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

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
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
        return this.inventory.stillValid(pPlayer);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        // QOL: the vended item is just a preview of what's buyable, not real stock —
        // don't leave it sitting in the station once the player walks away from it.
        if (!pPlayer.level().isClientSide()) {
            blockEntity.setItem(ArcaneStationBlockEntity.SLOT_OUTPUT, ItemStack.EMPTY);
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