package org.mythic_goose.aetherium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.mythic_goose.aetherium.api.tools.ChargedShovelItem;

import java.util.List;
import java.util.function.Supplier;

public class FertilizedScavanagableBlock extends Block {

    private final Identifier lootTableId;
    private final BlockState resultState;

    public FertilizedScavanagableBlock(Properties properties, Identifier lootTableId, BlockState resultState) {
        super(properties);
        this.lootTableId = lootTableId;
        this.resultState = resultState;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(stack.getItem() instanceof ShovelItem || stack.getItem() instanceof ChargedShovelItem)) {
            return InteractionResult.PASS;
        }

        if (level instanceof ServerLevel serverLevel) {
            dropLoot(serverLevel, pos, state, stack, player);
            level.setBlock(pos, resultState, Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    private void dropLoot(ServerLevel level, BlockPos pos, BlockState state, ItemStack tool, Player player) {
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, lootTableId);
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(key);

        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.TOOL, tool)
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .create(LootContextParamSets.BLOCK);

        List<ItemStack> results = lootTable.getRandomItems(params);
        for (ItemStack drop : results) {
            Block.popResource(level, pos, drop);
        }
    }
}