package org.mythic_goose.aetherium.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.init.AetheriumBlocks;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

import java.util.Map;
import java.util.function.Consumer;

public class FertilizerItem extends Item {
    private static final Map<Block, Block> BLOCK_MAP = // Hardcoded Cannot Manually change
            Map.of(
                    AetheriumBlocks.ROTTING_DIRT.get(), AetheriumBlocks.FERTILE_DIRT.get()
            );

    public static final int MAX_USES = 12;

    public FertilizerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // The active system that runs the Fertilizer actually use
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        BlockPos pos = context.getClickedPos();
        BlockPos relative = pos.relative(context.getClickedFace());

        int charge = stack.getOrDefault(ModDataComponents.FERTILIZER_CHARGE.get(), MAX_USES);

        if (charge <= 0) {
            return InteractionResult.PASS; // no charge left
        }

        if (BLOCK_MAP.containsKey(clickedBlock) && !level.isClientSide()) {
            // We are on the Server!
            level.setBlockAndUpdate(context.getClickedPos(), BLOCK_MAP.get(clickedBlock).defaultBlockState());
            assert context.getPlayer() != null;
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getHand());
            level.playSound(null, context.getClickedPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 2.0F,
                    0.8F + level.getRandom().nextFloat() * 0.4F);

            // drains on use — write back the new value AFTER a successful use
            stack.set(ModDataComponents.FERTILIZER_CHARGE.get(), charge - 1);

            return InteractionResult.SUCCESS;
        }

        if (growCrop(stack, level, pos)) {
            if (!level.isClientSide()) {
                stack.causeUseVibration(context.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, pos, 15);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            BlockState clickedState = level.getBlockState(pos);
            boolean solidBlockFace = clickedState.isFaceSturdy(level, pos, context.getClickedFace());
            if (solidBlockFace && growWaterPlant(stack, level, relative, context.getClickedFace())) {
                if (!level.isClientSide()) {
                    stack.causeUseVibration(context.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
                    level.levelEvent(1505, relative, 15);
                }

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    // Bone meal ability

    public static boolean growCrop(ItemStack itemStack, Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        boolean anySuccess = false;

        for (BlockPos targetPos : BlockPos.betweenClosed(
                pos.offset(-1, 0, -1),
                pos.offset(4, 0, 4))) {

            BlockState state = level.getBlockState(targetPos);
            Block stateBlock = state.getBlock();

            if (stateBlock instanceof BonemealableBlock block
                    && block.isValidBonemealTarget(level, targetPos, state)) {

                if (block.isBonemealSuccess(level, level.getRandom(), targetPos, state)) {
                    block.performBonemeal(serverLevel, level.getRandom(), targetPos, state);
                }

                anySuccess = true;
            }
        }

        if (anySuccess) {
            int charge = itemStack.getOrDefault(ModDataComponents.FERTILIZER_CHARGE.get(), MAX_USES);
            itemStack.set(ModDataComponents.FERTILIZER_CHARGE.get(), charge - 1);
        }

        return anySuccess;
    }

    public static boolean growWaterPlant(ItemStack itemStack, Level level, BlockPos pos, @Nullable Direction clickedFace) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).isFull()) {
            if (!(level instanceof ServerLevel)) {
                return true;
            } else {
                ServerLevel serverLevel = (ServerLevel)level;
                RandomSource random = level.getRandom();

                label80:
                for(int j = 0; j < 128; ++j) {
                    BlockPos testPos = pos;
                    BlockState stateToGrow = Blocks.SEAGRASS.defaultBlockState();

                    for(int i = 0; i < j / 16; ++i) {
                        testPos = testPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (level.getBlockState(testPos).isCollisionShapeFullBlock(level, testPos)) {
                            continue label80;
                        }
                    }

                    Holder<Biome> testBiome = level.getBiome(testPos);
                    if (testBiome.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (j == 0 && clickedFace != null && clickedFace.getAxis().isHorizontal()) {
                            stateToGrow = (BlockState) BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.WALL_CORALS, level.getRandom()).map((h) -> ((Block)h.value()).defaultBlockState()).orElse(stateToGrow);
                            if (stateToGrow.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                stateToGrow = (BlockState)stateToGrow.setValue(BaseCoralWallFanBlock.FACING, clickedFace);
                            }
                        } else if (random.nextInt(4) == 0) {
                            stateToGrow = (BlockState)BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, level.getRandom()).map((h) -> ((Block)h.value()).defaultBlockState()).orElse(stateToGrow);
                        }
                    }

                    if (stateToGrow.is(BlockTags.WALL_CORALS, (s) -> s.hasProperty(BaseCoralWallFanBlock.FACING))) {
                        for(int d = 0; !stateToGrow.canSurvive(level, testPos) && d < 4; ++d) {
                            stateToGrow = (BlockState)stateToGrow.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }

                    if (stateToGrow.canSurvive(level, testPos)) {
                        BlockState testState = level.getBlockState(testPos);
                        if (testState.is(Blocks.WATER) && level.getFluidState(testPos).isFull()) {
                            level.setBlock(testPos, stateToGrow, 3);
                        } else if (testState.is(Blocks.SEAGRASS) && ((BonemealableBlock)Blocks.SEAGRASS).isValidBonemealTarget(level, testPos, testState) && random.nextInt(10) == 0) {
                            ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal(serverLevel, random, testPos, testState);
                        }
                    }
                }

                itemStack.shrink(1);
                return true;
            }
        } else {
            return false;
        }
    }

    // --- Tracker for How many uses left ---
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int charge = itemStack.getOrDefault(ModDataComponents.FERTILIZER_CHARGE.get(), MAX_USES);
        builder.accept(Component.translatable("item.aetherium.capsule.charge", charge, MAX_USES)
                .withStyle(ChatFormatting.GRAY));
    }


    // --- Durability-style bar ---

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int charge = stack.getOrDefault(ModDataComponents.FERTILIZER_CHARGE.get(), MAX_USES);
        // 13 px is the vanilla full-bar width
        return Math.round(13.0F * charge / MAX_USES);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x00FF00;
    }
}
