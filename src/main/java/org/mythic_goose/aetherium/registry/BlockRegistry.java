package org.mythic_goose.aetherium.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.mythic_goose.aetherium.Aetherium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Registry helper for blocks.
 *
 * <p>Extend this class, declare your blocks as static fields, then call
 * init() in your {@code onInitialize()} to trigger class-loading
 * and register them all.
 *
 * <h2>Quick start</h2>
 * <pre>{@code
 * public class MyBlocksRegistary extends BlockRegistary {
 *
 *     public static final Block RUBY_ORE = register(
 *         "ruby_ore",
 *         () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
 *     );
 *
 *     public static void registerModBlocks() {} // triggers static field loading
 * }
 * }</pre>
 *
 * Then in your {@code onInitialize()}:
 * <pre>{@code
 * MyBlocksRegistary.registerModBlocks();
 * }</pre>
 *
 * <h2>Block IDs</h2>
 * <p>Always prefix with your mod ID — e.g. namespace {@code "my_mod"}, path {@code "ruby_ore"}.
 * The resulting in-game ID will be {@code my_mod:ruby_ore}.
 *
 * @see ItemRegistry for registering the block's item form via registerBlockItem
 */
public abstract class BlockRegistry {

    private static final List<Block> REGISTERED = new ArrayList<>();

    /**
     * Registers a block and tracks it internally.
     *
     * @param path      block name in snake_case
     * @param factory   supplier that constructs the block
     * @return the registered block instance
     */
    protected static Block register(String path, Function<BlockBehaviour.Properties, Block> factory) {
        ResourceKey<Block> key = ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, path)
        );
        Block block = factory.apply(BlockBehaviour.Properties.of().setId(key));
        Registry.register(BuiltInRegistries.BLOCK, key, block);
        REGISTERED.add(block);
        return block;
    }

    /**
     * All blocks registered through this helper, in registration order.
     * Useful for iterating blocks to register block items in bulk.
     */
    public static List<Block> all() {
        return Collections.unmodifiableList(REGISTERED);
    }
}