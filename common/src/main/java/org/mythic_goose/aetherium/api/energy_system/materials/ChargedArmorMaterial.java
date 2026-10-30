package org.mythic_goose.aetherium.api.energy_system.materials;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.*;
import org.mythic_goose.aetherium.AetheriumCommon;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.component.ModDataComponents;

import java.util.Map;

public record ChargedArmorMaterial(Map<ArmorType, Integer> defense, int enchantmentValue,
                                   Holder<SoundEvent> equipSound, float toughness, float knockbackResistance,
                                   ResourceKey<EquipmentAsset> assetId,
                                   int maxCharge, int chargePerHit,
                                   int rechargeSegmentSize, Aeth rechargeSegmentCost) {

    public static ChargedArmorMaterial VOIDMASS;
    public static ChargedArmorMaterial ASTRAL;

    private static final TagKey<Item> NO_REPAIR =
            TagKey.create(Registries.ITEM, AetheriumCommon.id("repairs_nothing"));

    /** durability irrelevant now - only used for createAttributes(), never fed into .durability(...) */
    private ArmorMaterial vanillaMaterial() {
        return new ArmorMaterial(Integer.MAX_VALUE, this.defense, this.enchantmentValue, this.equipSound,
                this.toughness, this.knockbackResistance, NO_REPAIR, this.assetId);
    }

    private Item.Properties applyCommonProperties(final Item.Properties properties) {
        return properties.enchantable(this.enchantmentValue)
                .component(ModDataComponents.ARMOR_CHARGE.get(), 0);
    }

    public Item.Properties applyArmorProperties(final Item.Properties properties, final ArmorType type) {
        return properties.enchantable(this.enchantmentValue)
                .component(ModDataComponents.ARMOR_CHARGE.get(), 0)
                .attributes(this.vanillaMaterial().createAttributes(type))
                .component(DataComponents.EQUIPPABLE, Equippable.builder(type.getSlot())
                        .setEquipSound(this.equipSound)
                        .setAsset(this.assetId)
                        .setDamageOnHurt(false)
                        .build());
    }

    public static final ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY =
            ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> VOIDMASS_KEY =
            ResourceKey.create(REGISTRY_KEY, Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "voidmass"));
    public static final ResourceKey<EquipmentAsset> ASTRAL_KEY =
            ResourceKey.create(REGISTRY_KEY, Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "astral"));


    static {
        VOIDMASS = new ChargedArmorMaterial(
                ArmorMaterials.makeDefense(7, 13, 18, 7, 23),
                28, SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0F, 0.35F,
                VOIDMASS_KEY,
                10000, 5,       // maxCharge, chargePerHit
                1000, Aeth.ofUnits(40)); // rechargeSegmentSize, rechargeSegmentCost

        ASTRAL = new ChargedArmorMaterial(
                ArmorMaterials.makeDefense(7, 13, 18, 7, 23),
                28, SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0F, 0.35F,
                ASTRAL_KEY,
                26000, 2,       // maxCharge, chargePerHit
                2000, Aeth.ofUnits(100)); // rechargeSegmentSize, rechargeSegmentCost
    }
}