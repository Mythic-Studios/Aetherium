package org.mythic_goose.aetherium.api.energy_system.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.energy_system.materials.ChargedArmorMaterial;
import org.mythic_goose.aetherium.api.energy_system.tools.ChargeUtil;
import org.mythic_goose.aetherium.api.energy_system.tools.Rechargeable;
import org.mythic_goose.aetherium.component.ModDataComponents;

import java.util.function.Consumer;

public class ChargedArmorItem extends Item implements Rechargeable {
    private final ChargedArmorMaterial material;

    public ChargedArmorItem(ChargedArmorMaterial material, Properties properties) {
        super(properties);
        this.material = material;
    }

    public int maxCharge() { return material.maxCharge(); }
    public int chargePerHit() { return material.chargePerHit(); }

    @Override public int rechargeSegmentSize() { return material.rechargeSegmentSize(); }
    @Override public Aeth rechargeSegmentCost() { return material.rechargeSegmentCost(); }

    public int getCurrentCharge(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.ARMOR_CHARGE, 0);
    }

    public void setCurrentCharge(ItemStack stack, int amount) {
        stack.set(ModDataComponents.ARMOR_CHARGE, Mth.clamp(amount, 0, maxCharge()));
    }

    public void drain(ItemStack stack, int amount) {
        setCurrentCharge(stack, getCurrentCharge(stack) - amount);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCurrentCharge(stack) < maxCharge();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * ((float) getCurrentCharge(stack) / maxCharge()));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb(Math.max(0.0F, (float) getCurrentCharge(stack) / maxCharge()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int charge = getCurrentCharge(itemStack); // was ChargeUtil.get(itemStack)
        int max = maxCharge();

        Component chargeText = Component.literal(String.valueOf(charge)).withStyle(chargeColor(charge, max));
        Component maxChargeText = Component.literal(String.valueOf(max)).withStyle(ChatFormatting.DARK_GREEN);

        builder.accept(Component.literal(" "));
        builder.accept(Component.translatable("item.aetherium.charged_tool.charge", chargeText, maxChargeText));

        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    private ChatFormatting chargeColor(int charge, int maxCharge) {
        if (charge <= 0) {
            return ChatFormatting.DARK_RED;
        }
        if (charge == maxCharge) {
            return ChatFormatting.DARK_GREEN;
        }

        double ratio = (double) charge / maxCharge;
        if (ratio > 2.0 / 3.0) {
            return ChatFormatting.GREEN;
        } else if (ratio > 1.0 / 3.0) {
            return ChatFormatting.YELLOW;
        } else {
            return ChatFormatting.RED;
        }
    }

    private static final Identifier PENALTY_ARMOR = Aetherium.id("empty_charge_penalty_armor");
    private static final Identifier PENALTY_TOUGHNESS = Aetherium.id("empty_charge_penalty_toughness");
    private static final Identifier PENALTY_KNOCKBACK = Aetherium.id("empty_charge_penalty_knockback");

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (!(entity instanceof LivingEntity living)) return;
        if (slot == null || slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) return;

        ArmorType type = armorTypeForSlot(slot);
        if (type == null) return;

        boolean empty = getCurrentCharge(stack) <= 0;

        Identifier armorId = idFor(PENALTY_ARMOR, slot);
        Identifier toughId = idFor(PENALTY_TOUGHNESS, slot);
        Identifier kbId = idFor(PENALTY_KNOCKBACK, slot);

        int defense = material.defense().getOrDefault(type, 0);

        applyOrRemovePenalty(living, Attributes.ARMOR, armorId, -defense, empty);
        applyOrRemovePenalty(living, Attributes.ARMOR_TOUGHNESS, toughId, -material.toughness(), empty);
        applyOrRemovePenalty(living, Attributes.KNOCKBACK_RESISTANCE, kbId, -material.knockbackResistance(), empty);
    }

    private static Identifier idFor(Identifier base, EquipmentSlot slot) {
        return Aetherium.id(base.getPath() + "_" + slot.getName());
    }

    private static void applyOrRemovePenalty(LivingEntity living, Holder<Attribute> attribute, Identifier id,
                                             double amount, boolean shouldApply) {
        AttributeInstance instance = living.getAttribute(attribute);
        if (instance == null) return;

        if (shouldApply) {
            instance.addOrUpdateTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE));
        } else {
            instance.removeModifier(id);
        }
    }

    private static @Nullable ArmorType armorTypeForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> ArmorType.HELMET;
            case CHEST -> ArmorType.CHESTPLATE;
            case LEGS -> ArmorType.LEGGINGS;
            case FEET -> ArmorType.BOOTS;
            default -> null;
        };
    }
}