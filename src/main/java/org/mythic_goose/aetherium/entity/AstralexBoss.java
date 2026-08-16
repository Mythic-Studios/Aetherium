package org.mythic_goose.aetherium.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mythic_goose.aetherium.network.AstralexBossBarPayload;

import java.util.*;

public class AstralexBoss extends Monster implements RangedAttackMob, GeoEntity {

    public enum Phase {SHIELDED, MEMBRANE, HEART, DEAD }

    private static final EntityDataAccessor<Integer> DATA_PHASE =
            SynchedEntityData.defineId(AstralexBoss.class, EntityDataSerializers.INT);

    private static final Set<AstralexBoss> ACTIVE_INSTANCES =
            Collections.newSetFromMap(new WeakHashMap<>());

    private static final int PHASE_TRANSITION_LENGTH_TICKS = 40; // match phase_shift animation's real length in ticks

    private int phaseTransitionTicks = -1; // -1 = not transitioning
    private Phase pendingPhase = null;
    private DamageSource pendingDeathSource = null;

    private static final int DEATH_ANIMATION_LENGTH_TICKS = 100; // match death.animation's real length in ticks

    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent;

    private int deathAnimationTicks = -1; // -1 = not dying yet

    public AstralexBoss(final EntityType<? extends AstralexBoss> type, final Level level) {
        super(type, level);
        this.bossEvent = (ServerBossEvent) Util.make(
                new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(),
                        BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS),
                (e) -> e.setDarkenScreen(true));
        this.moveControl = new FlyingMoveControl<AstralexBoss>(this, 10, false);
        if (!level.isClientSide()) {
            ACTIVE_INSTANCES.add(this);
        }
    }

    @Override
    protected @NonNull PathNavigation createNavigation(@NonNull Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        return flyingPathNavigation;
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (this.level().isClientSide()) {
            return;
        }

        if (this.deathAnimationTicks >= 0) {
            this.deathAnimationTicks++;
            if (this.deathAnimationTicks >= DEATH_ANIMATION_LENGTH_TICKS) {
                if (this.pendingDeathSource != null) {
                    super.dropAllDeathLoot((ServerLevel) this.level(), this.pendingDeathSource);
                }
                this.bossEvent.removeAllPlayers();
                this.remove(RemovalReason.KILLED);
            }
            return;
        }

        if (this.phaseTransitionTicks >= 0) {
            this.phaseTransitionTicks++;
            if (this.phaseTransitionTicks >= PHASE_TRANSITION_LENGTH_TICKS) {
                announcePhase(this.pendingPhase);
                setPhase(this.pendingPhase);
                this.phaseTransitionTicks = -1;
                this.pendingPhase = null;
            }
            this.bossEvent.setProgress(Mth.clamp(progressWithinCurrentPhase(), 0.0f, 1.0f));
            return;
        }

        Phase currentPhase = getPhase();
        Phase expectedPhase = phaseForHealthFraction(this.getHealth() / this.getMaxHealth());

        if (expectedPhase != currentPhase && currentPhase != Phase.DEAD) {
            if (expectedPhase == Phase.DEAD) {
                setPhase(Phase.DEAD);
                announcePhase(Phase.DEAD);
                triggerAnim("phase_controller", "death");
                this.deathAnimationTicks = 0;
                this.setInvulnerable(true);
            } else {
                this.pendingPhase = expectedPhase;
                this.phaseTransitionTicks = 0;
                triggerAnim("phase_controller", "phase_shift");
            }
        }


        this.bossEvent.setProgress(Mth.clamp(progressWithinCurrentPhase(), 0.0f, 1.0f));
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        // Defer loot/XP drops until the death animation finishes — see tick()
        this.pendingDeathSource = damageSource;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1024.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.SCALE, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.6)
                .add(Attributes.FLYING_SPEED, 0.6);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 20.0f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel level) {
        return 1000; // 26 Levels from 0
    }

    // --- Synced phase state ----------------------------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_PHASE, Phase.SHIELDED.ordinal());
    }

    public Phase getPhase() {
        return Phase.values()[this.entityData.get(DATA_PHASE)];
    }

    private void setPhase(Phase phase) {
        this.entityData.set(DATA_PHASE, phase.ordinal());
    }

    // --- Phase / health math ----------------------------------------------

    private Phase phaseForHealthFraction(float fraction) {
        if (fraction > 2.0f / 3.0f) return Phase.SHIELDED;
        if (fraction > 1.0f / 3.0f) return Phase.MEMBRANE;
        if (fraction > 0.0f) return Phase.HEART;
        return Phase.DEAD;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 100) {
            this.remove(RemovalReason.KILLED);
        }
    }

    /** Boss bar progress relative to the CURRENT phase's own third of total health. */
    private float progressWithinCurrentPhase() {
        float fraction = this.getHealth() / this.getMaxHealth();
        return switch (getPhase()) {
            case SHIELDED -> (fraction - 2.0f / 3.0f) / (1.0f / 3.0f);
            case MEMBRANE -> (fraction - 1.0f / 3.0f) / (1.0f / 3.0f);
            case HEART, DEAD -> fraction / (1.0f / 3.0f);
        };
    }

    private void announcePhase(Phase phase) {
        Component message = switch (phase) {
            case MEMBRANE -> Component.translatable("dialog.astralex.first_fight.phase.membrane");
            case HEART -> Component.translatable("dialog.astralex.first_fight.phase.heart");
            case DEAD -> Component.translatable("dialog.astralex.first_fight.phase.dead");
            default -> null; // no message for SHIELDED (starting phase)
        };

        if (message == null) {
            return;
        }

        for (ServerPlayer player : this.bossEvent.getPlayers()) {
            player.sendSystemMessage(message);
        }
    }

    // --- Boss bar plumbing --------------------------------------------------

    @Override
    public void setCustomName(final @Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
        AstralexBossBarPayload.sendTo(player, this.bossEvent.getId(), true);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
        AstralexBossBarPayload.sendTo(player, this.bossEvent.getId(), false);
    }

    public static void removePlayerFromAll(ServerPlayer player) {
        for (AstralexBoss boss : ACTIVE_INSTANCES) {
            boss.bossEvent.removePlayer(player);
        }
    }

    // --- Tick: phase transitions + death sequencing -------------------------

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            return;
        }

        if (this.deathAnimationTicks >= 0) {
            this.deathAnimationTicks++;
            if (this.deathAnimationTicks >= DEATH_ANIMATION_LENGTH_TICKS) {
                this.bossEvent.removeAllPlayers();
                this.remove(RemovalReason.KILLED);
            }
            return;
        }

        Phase currentPhase = getPhase();
        Phase expectedPhase = phaseForHealthFraction(this.getHealth() / this.getMaxHealth());

        if (expectedPhase != currentPhase && currentPhase != Phase.DEAD) {
            setPhase(expectedPhase);

            if (expectedPhase == Phase.DEAD) {
                triggerAnim("phase_controller", "death");
                this.deathAnimationTicks = 0;
                this.setInvulnerable(true); // freeze further damage while death anim plays
            } else {
                triggerAnim("phase_controller", "phase_shift");
            }
        }

        this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0f, 1.0f));
    }

    // --- Phase-gated damage ---------------------------------------------------

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        Phase phase = getPhase();

        if (phase == Phase.SHIELDED) {
            if (!(source.getDirectEntity() instanceof AbstractArrow)) {
                return false;
            }
        }

        if (phase == Phase.HEART) {
            ItemStack weapon = source.getWeaponItem();
            if (weapon == null || !weapon.has(DataComponents.KINETIC_WEAPON)) {
                return false;
            }
        }

        return super.hurtServer(level, source, amount);
    }

    @Override
    public void die(DamageSource source) {
        // Suppress vanilla death; actual removal happens in tick() once the
        // death animation has finished playing.
        if (this.deathAnimationTicks < 0) {
            super.die(source);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        this.bossEvent.removeAllPlayers();
        ACTIVE_INSTANCES.remove(this);
    }

    // --- GeckoLib wiring -------------------------------------------------------


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("movement_controller", 5,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("idle"))));

        controllers.add(new AnimationController<AstralexBoss>("phase_controller", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("idle")))
                .triggerableAnim("phase_shift", RawAnimation.begin().thenPlay("phase_shift"))
                .triggerableAnim("death", RawAnimation.begin().thenPlay("death")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    private void performRangedAttack(int head, LivingEntity target) {
        this.performRangedAttack(head, target.getX(), target.getY() + (double)target.getEyeHeight() * 0.5, target.getZ(), head == 0 && this.random.nextFloat() < 0.001f);
    }



    private static final int FIREBALL_EXPLOSION_POWER = 2;

    private void performRangedAttack(int head, double tx, double ty, double tz, boolean dangerous) {
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1016, this.blockPosition(), 0);
        }
        double hx = this.getX(head);
        double hy = this.getY(head);
        double hz = this.getZ(head);
        double xd = tx - hx;
        double yd = ty - hy;
        double zd = tz - hz;
        Vec3 direction = new Vec3(xd, yd, zd);
        LargeFireball entity = new LargeFireball(this.level(), this, direction.normalize(), FIREBALL_EXPLOSION_POWER);
        entity.setPos(hx, hy, hz);
        this.level().addFreshEntity(entity);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        this.performRangedAttack(0, target);
    }
}