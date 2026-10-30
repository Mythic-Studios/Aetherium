package org.mythic_goose.aetherium.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.item.FertilizerItem;
import org.mythic_goose.aetherium.util.AetheriumItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class ClickRechargeMixin {

    @Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
    private void aetherium$rechargeOnClick(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        aetherium$rechargeFertilizer(slotIndex, buttonNum, containerInput, player, ci);
    }

    @Unique
    private void aetherium$rechargeFertilizer(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        AbstractContainerMenu self = (AbstractContainerMenu) (Object) this;

        // Only handle plain left/right clicks on a real slot
        if (containerInput != ContainerInput.PICKUP || slotIndex < 0 || slotIndex >= self.slots.size()) {
            return;
        }

        Slot slot = self.getSlot(slotIndex);
        if (slot == null || !slot.hasItem()) return;

        ItemStack slotStack = slot.getItem();
        ItemStack carried = self.getCarried();

        if (!(slotStack.getItem() instanceof FertilizerItem) || carried.isEmpty()) return;

        boolean isWeak = carried.is(AetheriumItemTags.REFILLS_FERTILIZER_WEAK);
        boolean isStrong = carried.is(AetheriumItemTags.REFILLS_FERTILIZER_STRONG);
        if (!isWeak && !isStrong) return;

        int charge = slotStack.getOrDefault(ModDataComponents.FERTILIZER_CHARGE.get(), FertilizerItem.MAX_USES);
        if (charge >= FertilizerItem.MAX_USES) return; // already full, let vanilla handle it normally

        int refillAmount = isStrong ? 4 : 1;
        int newCharge = Math.min(FertilizerItem.MAX_USES, charge + refillAmount);
        slotStack.set(ModDataComponents.FERTILIZER_CHARGE.get(), newCharge);

        if (!player.hasInfiniteMaterials()) {
            carried.shrink(1);
        }

        slot.setChanged();
        ci.cancel(); // prevents doClick from running (stops the normal pickup/swap behavior)
    }

}