package org.mythic_goose.aetherium.api.energy_system.materials;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.component.ModDataComponents;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.registries.Registries;
import org.mythic_goose.aetherium.Aetherium;

public record ChargedToolMaterial(TagKey<Block> incorrectBlocksForDrops, float speed, float attackDamageBonus,
                                  int enchantmentValue, int maxCharge, int chargePerUse,
                                  int rechargeSegmentSize, Aeth rechargeSegmentCost) {

    public static final ChargedToolMaterial VOIDMASS;

    /** Tag deliberately has no entries — charge-based tools can't be anvil-repaired. */
    private static final TagKey<Item> NO_REPAIR = TagKey.create(Registries.ITEM, Aetherium.id("repairs_nothing"));

    /**
     * Bridges into a vanilla ToolMaterial for constructors that require one
     * (AxeItem, ShovelItem, HoeItem, and custom builders like .spear()).
     * The "durability" value here is meaningless since these items are
     * undamageable — it's only read by vanilla code paths we don't use.
     * <p>
     * Currently set to max charge until I can fix it - durability shouldn't drain nor should it break after one use
     */
    public ToolMaterial vanillaMaterial() {
        return new ToolMaterial(this.incorrectBlocksForDrops, this.maxCharge, this.speed,
                this.attackDamageBonus, this.enchantmentValue, NO_REPAIR);
    }

    public Item.Properties applyToolProperties(final Item.Properties properties, final TagKey<Block> minesEfficiently,
                                               final float attackDamageBaseline, final float attackSpeedBaseline,
                                               final float disableBlockingSeconds) {
        HolderGetter<Block> lookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties)
                .component(DataComponents.TOOL, new Tool(List.of(
                        Tool.Rule.deniesDrops(lookup.getOrThrow(this.incorrectBlocksForDrops)),
                        Tool.Rule.minesAndDrops(lookup.getOrThrow(minesEfficiently), this.speed)),
                        1.0F, 1, true))
                .attributes(this.createToolAttributes(attackDamageBaseline, attackSpeedBaseline))
                .component(DataComponents.WEAPON, new Weapon(2, disableBlockingSeconds))
                .component(ModDataComponents.TOOL_CHARGE, this.maxCharge);
    }

    private Item.Properties applyCommonProperties(final Item.Properties properties) {
        return properties.enchantable(this.enchantmentValue)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(ModDataComponents.TOOL_CHARGE, 0);
    }

    private ItemAttributeModifiers createToolAttributes(final float attackDamageBaseline, final float attackSpeedBaseline) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                (double)(attackDamageBaseline + this.attackDamageBonus), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeedBaseline, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND).build();
    }

    public Item.Properties applySwordProperties(final Item.Properties properties, final float attackDamageBaseline, final float attackSpeedBaseline) {
        HolderGetter<Block> registrationLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties).component(DataComponents.TOOL,
                new Tool(List.of(Tool.Rule.minesAndDrops(HolderSet.direct(new Holder[]{Blocks.COBWEB.builtInRegistryHolder()}), 15.0F),
                        Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                        Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)),
                        1.0F, 2, false))
                .attributes(this.createSwordAttributes(attackDamageBaseline, attackSpeedBaseline))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    private ItemAttributeModifiers createSwordAttributes(final float attackDamageBaseline, final float attackSpeedBaseline) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(attackDamageBaseline + this.attackDamageBonus), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeedBaseline, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public Item.Properties applySpearProperties(final Item.Properties properties,
                                                final float attackDuration, final float damageMultiplier,
                                                final float delay, final float dismountTime, final float dismountThreshold,
                                                final float knockbackTime, final float knockbackThreshold,
                                                final float damageTime, final float damageThreshold) {
        return this.applyCommonProperties(properties) // enchantable(...) + TOOL_CHARGE(0), no durability at all
                .delayedHolderComponent(DataComponents.DAMAGE_TYPE, DamageTypes.SPEAR)
                .component(DataComponents.KINETIC_WEAPON, new KineticWeapon(
                        10, (int) (delay * 20.0F),
                        KineticWeapon.Condition.ofAttackerSpeed((int) (dismountTime * 20.0F), dismountThreshold),
                        KineticWeapon.Condition.ofAttackerSpeed((int) (knockbackTime * 20.0F), knockbackThreshold),
                        KineticWeapon.Condition.ofRelativeSpeed((int) (damageTime * 20.0F), damageThreshold),
                        0.38F, damageMultiplier,
                        Optional.of(SoundEvents.SPEAR_USE), Optional.of(SoundEvents.SPEAR_HIT)))
                .component(DataComponents.PIERCING_WEAPON, new PiercingWeapon(
                        true, false, Optional.of(SoundEvents.SPEAR_ATTACK), Optional.of(SoundEvents.SPEAR_HIT)))
                .component(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F)
                .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int) (attackDuration * 20.0F)))
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                (double) (0.0F + this.attackDamageBonus), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                (double) (1.0F / attackDuration) - 4.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build())
                .component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F))
                .component(DataComponents.WEAPON, new Weapon(1));
    }



    static {
        VOIDMASS = new ChargedToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 12.0F, 7.0F, 15,
                10000, 1, 1000, Aeth.ofUnits(40));
    }
}
