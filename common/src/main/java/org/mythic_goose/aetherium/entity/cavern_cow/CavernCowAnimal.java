package org.mythic_goose.aetherium.entity.cavern_cow;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.AetheriumBlocks;

public class CavernCowAnimal extends Cow implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public CavernCowAnimal(EntityType<? extends Cow> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)2.0F));
        this.goalSelector.addGoal(2, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, (double)1.25F, (i) -> i.is(ItemTags.COW_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, (double)1.25F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos.below()).is(AetheriumBlocks.CRYSTALLIZED_GRASS_BLOCK.get())
                ? 10.0F
                : super.getWalkTargetValue(pos, level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("movement_controller", 5,
                state -> {
                    if (state.isMoving()) {
                        return state.setAndContinue(RawAnimation.begin().thenLoop("walk"));
                    } else {
                        return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
                    }
                }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, (double)10.0F).add(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    @Override
    public @NonNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }
}
