package org.mythic_goose.aetherium.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class AetheriumFoodProperties {

    public static final FoodProperties VOID_BERRIES = new FoodProperties.Builder().nutrition(5).saturationModifier(6.15f).alwaysEdible().build();

    public static final Consumable VOID_BERRIES_EFFECT = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 850, 2)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 850, 2)))
            .consumeSeconds(0.8f).build();
}
