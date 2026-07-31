package org.mythic_goose.aetherium.block.entity;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.AetheriumBlockEntities;
import org.mythic_goose.aetherium.api.AethValues;
import org.mythic_goose.aetherium.menu.ArcaneStationMenu;

public class ArcaneStationBlockEntity extends BaseContainerBlockEntity
        implements ExtendedMenuProvider<BlockPos>, ImplementedContainer {

    public static final int SLOT_INPUT  = 0; // green arrow: consume + pay + learn
    public static final int SLOT_LEARN  = 1; // purple: learn only, item stays put
    public static final int SLOT_OUTPUT = 2; // orange: extract-only, purchased items
    public static final int CONTAINER_SIZE = 3;

    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    public ArcaneStationBlockEntity(BlockPos pos, BlockState state) {
        super(AetheriumBlockEntities.ARCANE_STATION_BE, pos, state);
    }

    // ── Container ─────────────────────────────────────────────────────────

    @Override protected @NotNull Component getDefaultName() {
        return Component.translatable("container.aetherium.arcane_station");
    }
    @Override public @NotNull NonNullList<ItemStack> getItems() { return items; }
    @Override protected void setItems(NonNullList<ItemStack> list) { this.items = list; }
    @Override public int getContainerSize() { return CONTAINER_SIZE; }
    @Override public int getMaxStackSize() { return 64; }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        // Both the "spend" slot and the "learn only" slot only accept items that are
        // actually worth something, and that haven't been explicitly excluded (e.g.
        // reserved for some other system) via AethValues.exclude()/excludeTag().
        if (slot == SLOT_INPUT) return AethValues.lookup(stack) != null && !AethValues.isExcluded(stack);
        if (slot == SLOT_LEARN) return AethValues.lookup(stack) != null && !AethValues.isExcluded(stack);
        if (slot == SLOT_OUTPUT) return false; // extract-only
        return super.canPlaceItem(slot, stack);
    }

    // ── Screen factory ────────────────────────────────────────────────────

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory inv) {
        return new ArcaneStationMenu(syncId, inv, getBlockPos());
    }
    @Override public @NonNull BlockPos getScreenOpeningData(ServerPlayer player) { return getBlockPos(); }
    @Override public @NonNull Component getDisplayName() { return getDefaultName(); }

    // ── NBT ───────────────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
    }
}