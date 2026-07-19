package org.mythic_goose.aetherium.component;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.mythic_goose.aetherium.init.ModItems;

import java.util.function.Supplier;

public enum CapsuleType {
    VOIDMASS("voidmass", () -> ModItems.FULL_CAPSULE_VOIDMASS, () -> ModItems.COMPRESSED_VOIDMASS, 2, 6);

    private final String id;
    private final Supplier<Item> fullCapsuleItem;
    private final Supplier<Item> resultItem;
    private final int minYield;
    private final int maxYield;

    CapsuleType(String id, Supplier<Item> fullCapsuleItem, Supplier<Item> resultItem, int minYield, int maxYield) {
        this.id = id;
        this.fullCapsuleItem = fullCapsuleItem;
        this.resultItem = resultItem;
        this.minYield = minYield;
        this.maxYield = maxYield;
    }

    public String getId() {
        return id;
    }

    public Item getFullCapsuleItem() {
        return fullCapsuleItem.get();
    }

    public ItemStack rollResult(RandomSource random) {
        int count = minYield + random.nextInt(maxYield - minYield + 1);
        return new ItemStack(resultItem.get(), count);
    }
}