package org.mythic_goose.aetherium.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.WeatherData;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethHelper;
import org.mythic_goose.aetherium.item.clock_data.ClockData;
import org.mythic_goose.aetherium.item.clock_data.ClockMode;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.util.AetheriumBlockTags;

import java.util.List;
import java.util.function.Consumer;

public class ClockOfMatterItem extends Item {

    // --- Tunables ---------------------------------------------------------
    /** Radius (blocks) the active effect reaches around the holder. */
    public static final double EFFECT_RADIUS = 8.0;
    /** How often (in ticks) the mode effect actually fires while active. */
    public static final int EFFECT_INTERVAL_TICKS = 20;
    /** One "segment" of charge, per your recharge spec. */
    public static final int SEGMENT_SIZE = 1000;
    /** Aeth cost to refill one full segment; partial segments cost proportionally less. */
    public static final Aeth SEGMENT_COST = Aeth.ofUnits(100);

    /** Slowness amplifier applied to hostiles: level 6 (amplifier 5) ~= -90% speed, i.e. 0.1x. */
    private static final int FROST_SLOWNESS_AMPLIFIER = 5;

    public ClockOfMatterItem(Properties properties) {
        super(properties);
    }

    private ClockData dataOf(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CLOCK_DATA, ClockData.DEFAULT);
    }

    private void setData(ItemStack stack, ClockData data) {
        stack.set(ModDataComponents.CLOCK_DATA, data);
    }

    // --- Tooltip that is visible on player item hover ----------------------

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int charge = dataOf(itemStack).charge();
        int maxCharge = ClockData.MAX_CHARGE;

        builder.accept(Component.literal(" "));
        builder.accept(Component.translatable("item.aetherium.clock_of_matter.mode_switched",
                Component.translatable("aetherium.clock_mode." + dataOf(itemStack).mode().modelSuffix())));
        builder.accept(Component.literal("§5Drain Rate§r: " + dataOf(itemStack).mode().drainRate()));
        Component chargeText = Component.literal(String.valueOf(charge)).withStyle(chargeColor(charge, maxCharge));
        Component maxChargeText = Component.literal(String.valueOf(maxCharge)).withStyle(ChatFormatting.DARK_GREEN);

        builder.accept(Component.translatable("item.aetherium.clock_of_matter.charge", chargeText, maxChargeText));

        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    private static ChatFormatting chargeColor(int charge, int maxCharge) {
        if (charge <= 0) {
            return ChatFormatting.DARK_RED;
        }

        double ratio = (double) charge / maxCharge;

        if (charge == maxCharge){
            return ChatFormatting.DARK_GREEN;
        }
        else if (ratio > 2.0 / 3.0) {
            return ChatFormatting.GREEN;
        } else if (ratio > 1.0 / 3.0) {
            return ChatFormatting.YELLOW;
        } else {
            return ChatFormatting.RED;
        }
    }

    // --- Charge bar + active glint -----------------------------------------

    @Override
    public boolean isBarVisible(ItemStack stack) {
        // Same convention as vanilla durability: hidden at full, shown otherwise.
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int charge = dataOf(stack).charge();
        return Mth.clamp(Math.round(13.0F * charge / (float) ClockData.MAX_CHARGE), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float fraction = dataOf(stack).charge() / (float) ClockData.MAX_CHARGE;
        // Same hue sweep vanilla durability uses: red near empty -> green near full.
        return Mth.hsvToRgb(fraction / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return dataOf(stack).active() || stack.isEnchanted();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ClockData data = dataOf(stack);

        if (player.isShiftKeyDown()) {
            return cycleMode(level, player, stack, data);
        }
        return toggleActive(level, player, stack, data);
    }

    private InteractionResult cycleMode(Level level, Player player, ItemStack stack, ClockData data) {
        if (!level.isClientSide()) {
            ClockMode nextMode = data.mode().next();
            // Switching modes always turns the clock off first, so you never end up
            // with (say) Frost's slowness quietly ticking under the Daybreak model.
            setData(stack, data.withMode(nextMode).withActive(false));
            // ITEM_MODEL is a normal component, so we can just override it per-stack
            // instead of needing a data-driven select model keyed off ClockData.
            stack.set(DataComponents.ITEM_MODEL, modelIdFor(nextMode));

            sendActionBarMessage(player,
                    Component.translatable("item.aetherium.clock_of_matter.mode_switched",
                            Component.translatable("aetherium.clock_mode." + nextMode.modelSuffix())));

            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE,
                    SoundSource.PLAYERS, 0.6f, 1.4f);
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * ITEM_MODEL resolves against the items/ folder directly (same lookup as the
     * item's own default), NOT against models/ - so this must point at the small
     * wrapper files in items/, e.g. "aetherium:growth_clock" -> items/growth_clock.json,
     * which in turn references the raw mesh at models/items/growth_clock.json.
     */
    private Identifier modelIdFor(ClockMode mode) {
        return Aetherium.id(mode.modelSuffix() + "_clock");
    }

    private InteractionResult toggleActive(Level level, Player player, ItemStack stack, ClockData data) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!data.active() && data.isEmpty()) {
            sendActionBarMessage(player, Component.translatable("item.aetherium.clock_of_matter.no_charge"));
            level.playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK.value(),
                    SoundSource.PLAYERS, 0.4f, 0.6f);
            return InteractionResult.FAIL;
        }

        boolean newActive = !data.active();
        setData(stack, data.withActive(newActive));
        level.playSound(null, player.blockPosition(),
                (newActive ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE),
                SoundSource.PLAYERS, 0.6f, 1.0f);
        return InteractionResult.SUCCESS;
    }

    /**
     * TODO: Player.displayClientMessage(Component, boolean actionBar) no longer resolves
     * against this mappings/version - routing everything through here as a placeholder
     * using plain chat so the mod compiles. Swap the body once we know the real
     * actionbar-capable signature (grab a decompiled Player.java the same way you did
     * Item/ServerLevel and I'll fix this properly).
     */
    private void sendActionBarMessage(Player player, Component message) {
        player.sendOverlayMessage(message);
    }

    /**
     * Runs every tick the stack exists in any inventory. `slot` tells us which
     * equipment slot (if any) it's currently tracked as - non-null and either
     * MAINHAND/OFFHAND means the player is actively holding it. Cheap no-ops
     * unless the clock is actually active and it's an effect-interval tick.
     */
    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (!(owner instanceof Player player)) return;

        ClockData data = dataOf(itemStack);
        if (!data.active()) return;

        boolean held = slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND;
        if (!held) {
            // Stashed away somewhere (chest slot, etc.) - shut it off rather than
            // let an un-held clock keep draining and applying effects.
            setData(itemStack, data.withActive(false));
            return;
        }

        if (data.isEmpty()) {
            setData(itemStack, data.withActive(false));
            sendActionBarMessage(player, Component.translatable("item.aetherium.clock_of_matter.depleted"));
            return;
        }

        if (level.getGameTime() % EFFECT_INTERVAL_TICKS != 0) return;

        applyEffect(level, player, data.mode());
        setData(itemStack, data.withCharge(data.charge() - data.mode().drainRate()));
    }

    private void applyEffect(ServerLevel level, Player player, ClockMode mode) {
        AABB area = player.getBoundingBox().inflate(EFFECT_RADIUS);
        switch (mode) {
            case GROWTH -> applyGrowth(level, player, area);
            case FROST -> applyFrost(level, area);
            case REGENERATION -> applyRegeneration(level, area);
            case DAYBREAK -> applyDaybreak(level);
            case FEEDME -> applyFeedMe(level, area);
            case SPEEDY_WHITES -> speedyWhites(level, player);
        }
    }

    private void speedyWhites(ServerLevel level, Player player) {
        BlockPos posBelow = player.blockPosition().below();
        BlockState stateBelow = level.getBlockState(posBelow);

        if (stateBelow.is(AetheriumBlockTags.SPEEDY_BLOCKS)) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.SPEED,
                    20,      // duration in ticks (1 second) - gets refreshed each call
                    1,       // amplifier 1 = Speed II (0 = Speed I)
                    false,   // ambient (no beacon-style translucent particles)
                    false,   // show particles
                    true     // show icon
            ));
        }
    }

    private void applyGrowth(ServerLevel level, Player player, AABB area) {
        BlockPos center = player.blockPosition();
        int radius = (int) Math.ceil(EFFECT_RADIUS);

        BlockPos.betweenClosed(
                center.offset(-radius, -3, -radius),
                center.offset(radius, 3, radius)
        ).forEach(pos -> {
            if (!area.contains(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) return;

            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.GRASS_BLOCK)) {
                return;
            }
            if (state.getBlock() instanceof BonemealableBlock bonemealable
                    && bonemealable.isValidBonemealTarget(level, pos, state)
                    && bonemealable.isBonemealSuccess(level, level.getRandom(), pos, state)) {
                bonemealable.performBonemeal(level, level.getRandom(), pos, state);
            }
        });
    }

    private void applyFrost(ServerLevel level, AABB area) {
        // Amplifier 5 (Slowness VI) knocks movement speed down ~90%, landing
        // right around the requested 0.1x. Duration is refreshed every interval
        // as long as the mob stays in range, and expires naturally on its own
        // the moment the clock stops or the mob wanders off.
        List<Monster> monsters = level.getEntitiesOfClass(Monster.class, area);
        for (Monster monster : monsters) {
            monster.addEffect(new MobEffectInstance(
                    MobEffects.SLOWNESS, EFFECT_INTERVAL_TICKS + 20, FROST_SLOWNESS_AMPLIFIER, true, true));
        }
    }

    private void applyRegeneration(ServerLevel level, AABB area) {
        List<Player> players = level.getEntitiesOfClass(Player.class, area);
        for (Player p : players) {
            p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_INTERVAL_TICKS + 60, 3, true, true));
        }
        List<Animal> animals = level.getEntitiesOfClass(Animal.class, area);
        for (Animal a : animals) {
            a.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_INTERVAL_TICKS + 60, 1, true, true));
        }
    }

    private void applyFeedMe(ServerLevel level, AABB area) {
        List<Player> players = level.getEntitiesOfClass(Player.class, area);
        for (Player p : players) {
            p.addEffect(new MobEffectInstance(MobEffects.SATURATION, EFFECT_INTERVAL_TICKS + 10, 1, true, false));
        }
    }

    private void applyDaybreak(ServerLevel level) {
        WeatherData weatherData = level.getWeatherData();
        weatherData.setRaining(false);
        weatherData.setThundering(false);
        weatherData.setRainTime(0);
        weatherData.setThunderTime(0);
        // Hold clear skies a little past the next interval so it doesn't
        // flicker between applications; refreshed every interval while active.
        weatherData.setClearWeatherTime(EFFECT_INTERVAL_TICKS + 20);
    }

    /**
     * Server-side recharge: tops up one segment (or whatever's left to hit
     * MAX_CHARGE, if less than a full segment remains), charging Aeth
     * proportional to how much charge was actually added.
     * Returns false if already full or the player can't afford it.
     */
    public static boolean tryRecharge(Player player, ItemStack stack) {
        ClockData data = stack.getOrDefault(ModDataComponents.CLOCK_DATA, ClockData.DEFAULT);
        int missing = ClockData.MAX_CHARGE - data.charge();
        if (missing <= 0) return false;

        int amount = Math.min(SEGMENT_SIZE, missing);
        // Cost scales down for a partial top-up near max: e.g. missing 400 of a
        // 1000 segment costs 40% of SEGMENT_COST.
        Aeth cost = SEGMENT_COST.multiply(amount).divide(SEGMENT_SIZE);

        if (!AethHelper.spend(player, cost)) return false;

        stack.set(ModDataComponents.CLOCK_DATA, data.withCharge(data.charge() + amount));
        return true;
    }
}